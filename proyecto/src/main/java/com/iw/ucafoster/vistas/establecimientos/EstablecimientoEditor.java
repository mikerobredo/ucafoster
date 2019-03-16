package com.iw.ucafoster.vistas.establecimientos;

import com.iw.ucafoster.entidades.Establecimiento;
import com.iw.ucafoster.services.EstablecimientoService;
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
public class EstablecimientoEditor extends VerticalLayout {

    private final EstablecimientoService service;

    /* Current establecimiento */
    private Establecimiento establecimiento;

    private Binder<Establecimiento> binder = new Binder<>(Establecimiento.class);

    /* Text fields */
    TextField nombre = new TextField("Nombre");
    TextField direccion = new TextField("Dirección");
    TextField telefono = new TextField("Teléfono");
    TextField email = new TextField("Email");

    /* Error labels */
    Label nombreStatus = new Label();
    Label direccionStatus = new Label();
    Label telefonoStatus = new Label();
    Label emailStatus = new Label();

    /* Action buttons */
    Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
    Button cancel = new Button("Cancelar");
    Button delete = new Button("Eliminar", VaadinIcons.TRASH);

    /* Layout for buttons */
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public EstablecimientoEditor(EstablecimientoService service) {

        Notification notification = new Notification("Ha ocurrido un error");
        notification.setDescription("<span>Algunos campos no cumplen los requisitos de validación</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.setDelayMsec(5000);

        String errorRequired = "Campo requerido";

        nombreStatus.addStyleName("validation-error");
        direccionStatus.addStyleName("validation-error");
        telefonoStatus.addStyleName("validation-error");
        emailStatus.addStyleName("validation-error");

        this.service = service;

        addComponents(nombreStatus, nombre, direccionStatus, direccion, telefonoStatus, telefono,
                emailStatus, email, actions);

        nombre.setWidth("100%");
        direccion.setWidth("100%");
        telefono.setWidth("100%");
        email.setWidth("100%");

        binder.forField(nombre)
                .asRequired(errorRequired)
                .withStatusLabel(nombreStatus)
                .bind(Establecimiento::getNombre, Establecimiento::setNombre);

        binder.forField(direccion)
                .asRequired(errorRequired)
                .withStatusLabel(direccionStatus)
                .bind(Establecimiento::getDireccion, Establecimiento::setDireccion);

        binder.forField(telefono)
                .asRequired(errorRequired)
                .withStatusLabel(telefonoStatus)
                .bind(Establecimiento::getTelefono, Establecimiento::setTelefono);

        binder.forField(email)
                .asRequired(errorRequired)
                .withStatusLabel(emailStatus)
                .bind(Establecimiento::getEmail, Establecimiento::setEmail);

        setSpacing(true);
        setMargin(false);

        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            if(binder.validate().isOk())
                service.save(establecimiento);
            else
                notification.show(Page.getCurrent());
        });

        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        delete.addClickListener(e -> service.delete(establecimiento));

        cancel.addClickListener(e -> editEstablecimiento(establecimiento));

        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editEstablecimiento(Establecimiento est) {

        if(est == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = est.getId() != null;
        if(persisted)
            establecimiento = service.findOne(est.getId());
        else
            establecimiento = est;
        cancel.setVisible(persisted);
        binder.setBean(establecimiento);
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
