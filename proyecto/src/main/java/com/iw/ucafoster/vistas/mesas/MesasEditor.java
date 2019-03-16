package com.iw.ucafoster.vistas.mesas;

import com.iw.ucafoster.entidades.Mesa;
import com.iw.ucafoster.entidades.ZonaEstablecimiento;
import com.iw.ucafoster.services.MesaService;
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
public class MesasEditor extends VerticalLayout {

    private final MesaService mesaService;
    private final ZonaEstablecimientoService zonaEstablecimientoService;

    /* Mesa actual */
    private Mesa mesaActual;

    private Binder<Mesa> binder = new Binder<>(Mesa.class);

    /* Text fields */
    TextField nombre = new TextField("Nombre");

    /* Selector zona */
    ComboBox<ZonaEstablecimiento> selectorZona = new ComboBox<>();

    /* Error labels */
    Label nombreStatus = new Label();
    Label zonaStatus = new Label();

    /* Action buttons */
    Button save = new Button("Guardar", VaadinIcons.CHECK_CIRCLE);
    Button cancel = new Button("Cancelar");
    Button delete = new Button("Eliminar", VaadinIcons.TRASH);

    /* Layout for buttons */
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public MesasEditor(MesaService mesaService, ZonaEstablecimientoService zonaEstablecimientoService) {

        Notification notification = new Notification("Ha ocurrido un error");
        notification.setDescription("<span>Algunos campos no cumplen los requisitos de validación</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.setDelayMsec(5000);

        String errorRequired = "Campo requerido";

        nombreStatus.addStyleName("validation-error");
        zonaStatus.addStyleName("validation-error");

        this.mesaService = mesaService;
        this.zonaEstablecimientoService = zonaEstablecimientoService;

        addComponents(nombreStatus, nombre, zonaStatus, selectorZona, actions);

        nombre.setWidth("100%");

        selectorZona.setWidth("100%");
        selectorZona.setCaption("Zona del establecimiento");
        selectorZona.setPlaceholder("Seleccione una zona");
        selectorZona.setItemCaptionGenerator(ZonaEstablecimiento::getNombre);
        selectorZona.setEmptySelectionAllowed(false);
        selectorZona.setRequiredIndicatorVisible(true);

        binder.forField(nombre)
                .asRequired(errorRequired)
                .withStatusLabel(nombreStatus)
                .bind(Mesa::getNombre, Mesa::setNombre);

        binder.forField(selectorZona)
                .asRequired(errorRequired)
                .withStatusLabel(zonaStatus)
                .bind(Mesa::getZonaEstablecimiento, Mesa::setZonaEstablecimiento);

        setSpacing(true);
        setMargin(false);

        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> {
            if(binder.validate().isOk())
                mesaService.save(mesaActual);
            else
                notification.show(Page.getCurrent());
        });

        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        delete.addClickListener(e -> {
            mesaService.delete(mesaActual);
            selectorZona.setValue(null);
        });

        cancel.addClickListener(e -> editMesa(mesaActual));

        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editMesa(Mesa mesa) {

        refrescar();
        if(mesa == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = mesa.getId() != null;
        if(persisted)
            mesaActual = mesaService.findOne(mesa.getId());
        else
            mesaActual = mesa;
        cancel.setVisible(persisted);
        binder.setBean(mesaActual);
        setVisible(true);
        save.focus();
        nombre.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {

        save.addClickListener(e -> {
            if(binder.validate().isOk()) {
                h.onChange();
                selectorZona.setValue(null);
            }
        });
        delete.addClickListener(e -> h.onChange());
    }

    private void refrescar() {

        selectorZona.setItems(zonaEstablecimientoService.findAll());
    }
}
