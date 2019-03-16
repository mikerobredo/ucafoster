package com.iw.ucafoster.vistas.mesas;

import com.iw.ucafoster.entidades.Mesa;
import com.iw.ucafoster.entidades.ZonaEstablecimiento;
import com.iw.ucafoster.services.MesaService;
import com.iw.ucafoster.services.ZonaEstablecimientoService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@SpringView(name = MesasView.VIEW_NAME)
public class MesasView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "mesas";

    ZonaEstablecimiento zonaEstablecimientoSeleccionada = null;

    ComboBox<ZonaEstablecimiento> selectZonaEstablecimiento;
    private Grid<Mesa> grid;
    private TextField filter;
    private Button addButton;

    private final MesaService mesaService;
    private final ZonaEstablecimientoService zonaEstablecimientoService;
    private final MesasEditor editor;

    @Autowired
    public MesasView(MesaService mesaService, ZonaEstablecimientoService zonaEstablecimientoService, MesasEditor editor) {

        this.mesaService = mesaService;
        this.zonaEstablecimientoService = zonaEstablecimientoService;
        this.editor = editor;
        this.selectZonaEstablecimiento = new ComboBox<>();
        this.grid = new Grid<>(Mesa.class);
        this.filter = new TextField();
        addButton = new Button("Añadir", VaadinIcons.PLUS);
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Mesas local");
        page_title.addStyleName(ValoTheme.LABEL_H2);
        page_title.addStyleName(ValoTheme.LABEL_BOLD);

        Label separator = new Label("");
        separator.addStyleName("separator");

        selectZonaEstablecimiento.setWidth("100%");
        selectZonaEstablecimiento.setPlaceholder("Seleccione una zona del establecimiento");
        selectZonaEstablecimiento.setItemCaptionGenerator(ZonaEstablecimiento::getNombre);
        selectZonaEstablecimiento.setEmptySelectionAllowed(false);

        selectZonaEstablecimiento.addValueChangeListener(e -> {
            zonaEstablecimientoSeleccionada = e.getValue();
            filter.setValue("");
            listarMesas(null, zonaEstablecimientoSeleccionada);
        });

        HorizontalLayout options = new HorizontalLayout();
        options.addComponents(filter, addButton);

        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        grid.setHeight("350px");
        grid.setWidth("100%");
        grid.setColumns("id", "nombre");
        grid.addColumn(mesa -> mesa.getZonaEstablecimiento())
                .setCaption("zona");

        filter.setPlaceholder("Buscar por nombre");
        filter.addValueChangeListener(e -> listarMesas(e.getValue(), zonaEstablecimientoSeleccionada));

        addButton.addClickListener(e -> editor.editMesa(new Mesa("")));

        addComponents(page_title, separator, options, selectZonaEstablecimiento, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> editor.editMesa(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listarMesas(filter.getValue(), zonaEstablecimientoSeleccionada);
        });

        listarMesas(null, null);
        listarZonas();
    }

    private void listarMesas(String nombre, ZonaEstablecimiento zona) {

        if(zona != null) {
            if(StringUtils.isEmpty(nombre))
                grid.setItems(mesaService.findByZonaEstablecimiento(zona));
            else
                grid.setItems(mesaService.findByZonaEstablecimientoAndNombreContaining(zona, nombre));
        } else
            grid.setItems(new ArrayList<Mesa>());
    }

    private void listarZonas() {

        selectZonaEstablecimiento.setItems(zonaEstablecimientoService.findAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
