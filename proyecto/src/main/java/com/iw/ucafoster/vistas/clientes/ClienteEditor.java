package com.iw.ucafoster.vistas.clientes;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.iw.ucafoster.entidades.Cliente;
import com.iw.ucafoster.services.ClienteService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class ClienteEditor extends VerticalLayout{
	
	private final ClienteService service;

    /* Current establecimiento */
    private Cliente cliente;

    private Binder<Cliente> binder = new Binder<>(Cliente.class);

    /* Text fields */
    TextField nombre = new TextField("Nombre");
    TextField apellidos = new TextField("Apellidos");
    TextField dni = new TextField("DNI");
    TextField direccion = new TextField("Dirección");
    TextField telefono = new TextField("Teléfono");
    TextField email = new TextField("Email");

    /* Field errors */
    Label nombreStatus = new Label();
    Label apellidosStatus = new Label();
    Label dniStatus = new Label();
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
    public ClienteEditor(ClienteService service) {

        Notification notification = new Notification("Ha ocurrido un error");
        notification.setDescription("<span>Algunos campos no cumplen los requisitos de validación</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.setDelayMsec(5000);

        String errorRequired = "Campo requerido";

        nombreStatus.addStyleName("validation-error");
        apellidosStatus.addStyleName("validation-error");
        dniStatus.addStyleName("validation-error");
        direccionStatus.addStyleName("validation-error");
        telefonoStatus.addStyleName("validation-error");
        emailStatus.addStyleName("validation-error");

        this.service = service;

        addComponents(nombreStatus, nombre, apellidosStatus, apellidos, dniStatus, dni,
                direccionStatus, direccion, telefonoStatus, telefono, emailStatus, email, actions);

        nombre.setWidth("100%");
        apellidos.setWidth("100%");
        dni.setWidth("100%");
        direccion.setWidth("100%");
        telefono.setWidth("100%");
        email.setWidth("100%");

        binder.forField(nombre)
                .asRequired(errorRequired)
                .withStatusLabel(nombreStatus)
                .bind(Cliente::getNombre, Cliente::setNombre);

        binder.forField(apellidos)
                .asRequired(errorRequired)
                .withStatusLabel(apellidosStatus)
                .bind(Cliente::getApellidos, Cliente::setApellidos);

        binder.forField(dni)
                .asRequired(errorRequired)
                .withStatusLabel(dniStatus)
                .bind(Cliente::getDni, Cliente::setDni);

        binder.forField(direccion)
                .asRequired(errorRequired)
                .withStatusLabel(direccionStatus)
                .bind(Cliente::getDireccion, Cliente::setDireccion);

        binder.forField(telefono)
                .asRequired(errorRequired)
                .withStatusLabel(telefonoStatus)
                .bind(Cliente::getTelefono, Cliente::setTelefono);

        binder.forField(email)
                .asRequired(errorRequired)
                .withStatusLabel(emailStatus)
                .bind(Cliente::getEmail, Cliente::setEmail);

        setSpacing(true);
        setMargin(false);

        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            if(binder.validate().isOk())
                service.save(cliente);
            else
                notification.show(Page.getCurrent());

        });

        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        delete.addClickListener(e -> service.delete(cliente));

        cancel.addClickListener(e -> editCliente(cliente));

        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editCliente(Cliente clt) {

        if(clt == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = clt.getId() != null;
        if(persisted)
            cliente = service.findOne(clt.getId());
        else
            cliente = clt;
        cancel.setVisible(persisted);
        binder.setBean(cliente);
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
