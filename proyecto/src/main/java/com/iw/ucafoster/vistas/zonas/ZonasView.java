package com.iw.ucafoster.vistas.zonas;

import com.iw.ucafoster.entidades.ZonaEstablecimiento;
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

@SpringView(name = ZonasView.VIEW_NAME)
public class ZonasView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "zonas";

    private Grid<ZonaEstablecimiento> grid;
    private TextField filter;
    private Button addButton;

    private final ZonaEstablecimientoService zonaService;
    private final ZonasEditor editor;

    @Autowired
    public ZonasView(ZonaEstablecimientoService zonaService, ZonasEditor editor) {

        this.zonaService = zonaService;
        this.editor = editor;
        this.grid = new Grid<>(ZonaEstablecimiento.class);
        this.filter = new TextField();
        this.addButton = new Button("Añadir", VaadinIcons.PLUS);
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Zonas local");
        page_title.addStyleName(ValoTheme.LABEL_H2);
        page_title.addStyleName(ValoTheme.LABEL_BOLD);

        Label separator = new Label("");
        separator.addStyleName("separator");

        HorizontalLayout options = new HorizontalLayout();
        options.addComponents(filter, addButton);

        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        grid.setHeight("350px");
        grid.setWidth("100%");
        grid.setColumns("id", "nombre", "descripcion");

        grid.getColumn("id").setWidth(100);

        filter.setPlaceholder("Buscar por nombre");
        filter.addValueChangeListener(e -> listarZonas(e.getValue()));

        addButton.addClickListener(e -> editor.editZona(new ZonaEstablecimiento("", "")));

        addComponents(page_title, separator, options, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> editor.editZona(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listarZonas(filter.getValue());
        });

        listarZonas(null);
    }

    private void listarZonas(String nombre) {

        if(StringUtils.isEmpty(nombre))
            grid.setItems(zonaService.findAll());
        else
            grid.setItems(zonaService.findByNombreContaining(nombre));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
