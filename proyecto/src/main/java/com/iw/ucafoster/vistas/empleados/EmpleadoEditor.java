package com.iw.ucafoster.vistas.empleados;

import com.iw.ucafoster.entidades.Empleado;
import com.iw.ucafoster.entidades.Establecimiento;
import com.iw.ucafoster.services.EmpleadoService;
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
public class EmpleadoEditor extends VerticalLayout {

    private final EmpleadoService empleadoService;
    private final EstablecimientoService establecimientoService;

    /* Current empleado */
    private Empleado empleado;

    private Binder<Empleado> binder = new Binder<>(Empleado.class);

    /* Text fields */
    TextField nombre = new TextField("Nombre");
    TextField apellidos = new TextField("Apellidos");
    TextField dni = new TextField("DNI");
    TextField email = new TextField("Email");
    TextField telefono = new TextField("Teléfono");
    TextField direccion = new TextField("Dirección");
    TextField username = new TextField("Nombre de usuario");
    TextField password = new TextField("Contraseña");
    ComboBox<String> rol = new ComboBox<>();
    ComboBox<Establecimiento> establecimiento = new ComboBox<>();

    /* Error labels */
    Label nombreStatus = new Label();
    Label apellidosStatus = new Label();
    Label dniStatus = new Label();
    Label emailStatus = new Label();
    Label telefonoStatus = new Label();
    Label direccionStatus = new Label();
    Label usernameStatus = new Label();
    Label passwordStatus = new Label();
    Label rolStatus = new Label();
    Label establecimientoStatus = new Label();

    /* Action buttons */
    Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
    Button cancel = new Button("Cancelar");
    Button delete = new Button("Eliminar", VaadinIcons.TRASH);

    /* Layout for buttons */
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public EmpleadoEditor(EmpleadoService empleadoService, EstablecimientoService establecimientoService) {

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
        emailStatus.addStyleName("validation-error");
        telefonoStatus.addStyleName("validation-error");
        direccionStatus.addStyleName("validation-error");
        usernameStatus.addStyleName("validation-error");
        passwordStatus.addStyleName("validation-error");
        rolStatus.addStyleName("validation-error");
        establecimientoStatus.addStyleName("validation-error");

        this.empleadoService = empleadoService;
        this.establecimientoService = establecimientoService;

        addComponents(nombreStatus, nombre, apellidosStatus, apellidos, dniStatus, dni,
                emailStatus, email, telefonoStatus, telefono, direccionStatus, direccion,
                usernameStatus, username, passwordStatus, password, rolStatus, rol,
                establecimientoStatus, establecimiento, actions);

        nombre.setWidth("100%");
        apellidos.setWidth("100%");
        dni.setWidth("100%");
        email.setWidth("100%");
        telefono.setWidth("100%");
        direccion.setWidth("100%");
        username.setWidth("100%");
        password.setWidth("100%");

        rol.setWidth("100%");
        rol.setItems("ROL-EMPLEADO", "ROL-ENCARGADO");
        rol.setCaption("Rol");
        rol.setPlaceholder("Seleccione un rol");
        rol.setEmptySelectionAllowed(false);
        rol.setRequiredIndicatorVisible(true);

        establecimiento.setWidth("100%");
        establecimiento.setItems(establecimientoService.findAll());
        establecimiento.setCaption("Establecimiento");
        establecimiento.setPlaceholder("Seleccione un establecimiento");
        establecimiento.setItemCaptionGenerator(Establecimiento::getNombre);
        establecimiento.setEmptySelectionAllowed(false);
        establecimiento.setRequiredIndicatorVisible(true);

        binder.forField(nombre)
                .asRequired(errorRequired)
                .withStatusLabel(nombreStatus)
                .bind(Empleado::getNombre, Empleado::setNombre);

        binder.forField(apellidos)
                .asRequired(errorRequired)
                .withStatusLabel(apellidosStatus)
                .bind(Empleado::getApellidos, Empleado::setApellidos);

        binder.forField(dni)
                .asRequired(errorRequired)
                .withStatusLabel(dniStatus)
                .bind(Empleado::getDni, Empleado::setDni);

        binder.forField(email)
                .asRequired(errorRequired)
                .withStatusLabel(emailStatus)
                .bind(Empleado::getEmail, Empleado::setEmail);

        binder.forField(telefono)
                .asRequired(errorRequired)
                .withStatusLabel(telefonoStatus)
                .bind(Empleado::getTelefono, Empleado::setTelefono);

        binder.forField(direccion)
                .asRequired(errorRequired)
                .withStatusLabel(direccionStatus)
                .bind(Empleado::getDireccion, Empleado::setDireccion);

        binder.forField(username)
                .asRequired(errorRequired)
                .withStatusLabel(usernameStatus)
                .bind(Empleado::getUsername, Empleado::setUsername);

        binder.forField(password)
                .asRequired(errorRequired)
                .withStatusLabel(passwordStatus)
                .bind(Empleado::getPassword, Empleado::setPassword);

        binder.forField(rol)
                .withValidator(str -> str.length() > 0 && str != "", errorRequired)
                .withStatusLabel(rolStatus)
                .bind(Empleado::getRol, Empleado::setRol);

        binder.forField(establecimiento)
                .asRequired(errorRequired)
                .withStatusLabel(establecimientoStatus)
                .bind(Empleado::getEstablecimiento, Empleado::setEstablecimiento);

        setSpacing(true);
        setMargin(false);

        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            if(binder.validate().isOk())
                empleadoService.save(empleado);
            else
                notification.show(Page.getCurrent());
        });

        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        delete.addClickListener(e -> {
            empleadoService.delete(empleado);
            establecimiento.setValue(null);
        });

        cancel.addClickListener(e -> editEmpleado(empleado));

        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editEmpleado(Empleado emp) {

        refrescar();

        if(emp == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = emp.getId() != null;
        if(persisted)
            empleado = empleadoService.findOne(emp.getId());
        else
            empleado = emp;
        cancel.setVisible(persisted);
        binder.setBean(empleado);
        setVisible(true);
        save.focus();
        nombre.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {

        save.addClickListener(e -> {
            if(binder.validate().isOk()) {
                h.onChange();
                rol.setValue(null);
                establecimiento.setValue(null);
            }
        });
        delete.addClickListener(e -> h.onChange());
    }

    public void refrescar(){

        establecimiento.setItems(establecimientoService.findAll());
    }
}
