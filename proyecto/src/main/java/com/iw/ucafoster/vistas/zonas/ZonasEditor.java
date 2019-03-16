package com.iw.ucafoster.vistas.zonas;

import com.iw.ucafoster.entidades.ZonaEstablecimiento;
import com.iw.ucafoster.security.SecurityUtils;
import com.iw.ucafoster.services.ZonaEstablecimientoService;
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
public class ZonasEditor extends VerticalLayout {

    private ZonaEstablecimientoService zonaService;

    /* Zona actual */
    private ZonaEstablecimiento zonaActual;

    private Binder<ZonaEstablecimiento> binder = new Binder<>(ZonaEstablecimiento.class);

    /* Text Fields */
    TextField nombre = new TextField("Nombre");
    TextField descripcion = new TextField("Descripcion");

    /* Error Labels */
    Label nombreStatus = new Label();
    Label descripcionStatus = new Label();

    /* Action buttons */
    Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
    Button cancel = new Button("Cancelar");
    Button delete = new Button("Eliminar", VaadinIcons.TRASH);

    /* Layout for buttons */
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public ZonasEditor(ZonaEstablecimientoService zonaService) {

        Notification notification = new Notification("Ha ocurrido un error");
        notification.setDescription("<span>Algunos campos no cumplen los requisitos de validación</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.setDelayMsec(5000);

        String errorRequired = "Campo requerido";

        nombreStatus.addStyleName("validation-error");
        descripcionStatus.addStyleName("validation-error");

        this.zonaService = zonaService;

        addComponents(nombreStatus, nombre, descripcionStatus, descripcion, actions);

        nombre.setWidth("100%");
        descripcion.setWidth("100%");

        binder.forField(nombre)
                .asRequired(errorRequired)
                .withStatusLabel(nombreStatus)
                .bind(ZonaEstablecimiento::getNombre, ZonaEstablecimiento::setNombre);

        binder.forField(descripcion)
                .asRequired(errorRequired)
                .withStatusLabel(descripcionStatus)
                .bind(ZonaEstablecimiento::getDescripcion, ZonaEstablecimiento::setDescripcion);

        setSpacing(true);
        setMargin(false);

        actions.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            if(binder.validate().isOk()) {
                zonaActual.setEstablecimiento(SecurityUtils.getEmpleadoSesion().getEstablecimiento());
                zonaService.save(zonaActual);
            } else
                notification.show(Page.getCurrent());
        });

        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        delete.addClickListener(e -> zonaService.delete(zonaActual));

        cancel.addClickListener(e -> editZona(zonaActual));

        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editZona(ZonaEstablecimiento zona) {

        if(zona == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = zona.getId() != null;
        if(persisted)
            zonaActual = zonaService.findOne(zona.getId());
        else
            zonaActual = zona;
        cancel.setVisible(persisted);
        binder.setBean(zonaActual);
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
