package com.iw.ucafoster.vistas.familias;

import com.iw.ucafoster.entidades.FamiliaProducto;
import com.iw.ucafoster.services.FamiliaProductoService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class FamiliaEditor extends VerticalLayout {

    private final FamiliaProductoService service;

    /* Current familia */
    private FamiliaProducto familia;

    private Binder<FamiliaProducto> binder = new Binder<>();

    /* Text fields */
    TextField nombre = new TextField("Nombre");
    TextField descripcion = new TextField("Descripción");
    TextField iva = new TextField("IVA");

    /* Error fields */
    Label nombreStatus = new Label();
    Label descripcionStatus = new Label();
    Label ivaStatus = new Label();

    /* Action buttons */
    Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
    Button cancel = new Button("Cancelar");
    Button delete = new Button("Eliminar", VaadinIcons.TRASH);

    /* Layout for buttons */
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public FamiliaEditor(FamiliaProductoService service) {

        Notification notification = new Notification("Ha ocurrido un error");
        notification.setDescription("<span>Algunos campos no cumplen los requisitos de validación</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.setDelayMsec(5000);

        String errorRequired = "Campo requerido";

        nombreStatus.addStyleName("validation-error");
        descripcionStatus.addStyleName("validation-error");
        ivaStatus.addStyleName("validation-error");

        this.service = service;

        addComponents(nombreStatus, nombre, descripcionStatus, descripcion, ivaStatus, iva, actions);

        nombre.setWidth("100%");
        descripcion.setWidth("100%");
        iva.setWidth("100%");

        binder.forField(nombre)
                .asRequired(errorRequired)
                .withStatusLabel(nombreStatus)
                .bind(FamiliaProducto::getNombre, FamiliaProducto::setNombre);

        binder.forField(descripcion)
                .asRequired(errorRequired)
                .withStatusLabel(descripcionStatus)
                .bind(FamiliaProducto::getDescripcion, FamiliaProducto::setDescripcion);

        binder.forField(iva)
                .asRequired(errorRequired)
                .withValidator(str -> str != null && Float.parseFloat(str) > 0.0f, errorRequired)
                .withConverter(new StringToFloatConverter("Introduzca un número"))
                .withStatusLabel(ivaStatus)
                .bind(FamiliaProducto::getIva, FamiliaProducto::setIva);

        setSpacing(true);
        setMargin(false);

        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            if(binder.validate().isOk())
                service.save(familia);
            else
                notification.show(Page.getCurrent());
        });

        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        delete.addClickListener(e -> service.delete(familia));

        cancel.addClickListener(e -> editFamilia(familia));

        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editFamilia(FamiliaProducto fam) {

        if(fam == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = fam.getId() != null;
        if(persisted)
            familia = service.findOne(fam.getId());
        else
            familia = fam;
        cancel.setVisible(persisted);
        binder.setBean(familia);
        setVisible(true);
        save.focus();
        nombre.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {

        save.addClickListener(e -> {
            if(binder.validate().isOk())
                h.onChange();
        });
        delete.addClickListener(e -> h.onChange());
    }
}
