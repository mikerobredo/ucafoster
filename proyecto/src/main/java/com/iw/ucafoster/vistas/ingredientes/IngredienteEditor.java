package com.iw.ucafoster.vistas.ingredientes;

import com.iw.ucafoster.entidades.Ingrediente;
import com.iw.ucafoster.services.IngredienteService;
import com.vaadin.data.Binder;
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
public class IngredienteEditor extends VerticalLayout {

    private final IngredienteService service;

    /* Current ingrediente */
    private Ingrediente ingrediente;

    private Binder<Ingrediente> binder = new Binder<>(Ingrediente.class);

    /* Text fields */
    TextField nombre = new TextField("Nombre");
    TextField descripcion = new TextField("Descripción");

    /* Error labels */
    Label nombreStatus = new Label();
    Label descripcionStatus = new Label();

    /* Action Buttons */
    Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
    Button cancel = new Button("Cancelar");
    Button delete = new Button("Eliminar", VaadinIcons.TRASH);

    /* Layout for buttons */
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public IngredienteEditor(IngredienteService service) {

        Notification notification = new Notification("Ha ocurrido un error");
        notification.setDescription("<span>Algunos campos no cumplen los requisitos de validación</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.setDelayMsec(5000);

        String errorRequired = "Campo requerido";

        nombreStatus.addStyleName("validation-error");
        descripcionStatus.addStyleName("validation-error");

        this.service = service;

        addComponents(nombreStatus, nombre, descripcionStatus, descripcion, actions);

        nombre.setWidth("100%");
        descripcion.setWidth("100%");

        binder.forField(nombre)
                .asRequired(errorRequired)
                .withStatusLabel(nombreStatus)
                .bind(Ingrediente::getNombre, Ingrediente::setNombre);

        binder.forField(descripcion)
                .asRequired(errorRequired)
                .withStatusLabel(descripcionStatus)
                .bind(Ingrediente::getDescripcion, Ingrediente::setDescripcion);

        setSpacing(true);
        setMargin(false);

        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            if(binder.validate().isOk())
                service.save(ingrediente);
            else
                notification.show(Page.getCurrent());
        });

        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        delete.addClickListener(e -> service.delete(ingrediente));

        cancel.addClickListener(e -> editIngrediente(ingrediente));

        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public void editIngrediente(Ingrediente ing) {

        if(ing == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = ing.getId() != null;
        if(persisted)
            ingrediente = service.findOne(ing.getId());
        else
            ingrediente = ing;
        cancel.setVisible(persisted);
        binder.setBean(ingrediente);
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
