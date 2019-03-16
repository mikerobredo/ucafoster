package com.iw.ucafoster.vistas.establecimientos;

import com.iw.ucafoster.entidades.Establecimiento;
import com.iw.ucafoster.services.EstablecimientoService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@SpringView(name = EstablecimientosView.VIEW_NAME)
public class EstablecimientosView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "establecimientos";

    private Grid<Establecimiento> grid;
    private TextField filter;
    private Button addButton;

    private final EstablecimientoService service;

    private final EstablecimientoEditor editor;

    @Autowired
    public EstablecimientosView(EstablecimientoService service, EstablecimientoEditor editor) {

        this.service = service;
        this.editor = editor;
        this.grid = new Grid<>(Establecimiento.class);
        this.filter = new TextField();
        this.addButton = new Button("Añadir", VaadinIcons.PLUS);
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Establecimientos");
        page_title.addStyleName(ValoTheme.LABEL_H2);
        page_title.addStyleName(ValoTheme.LABEL_BOLD);

        Label separator = new Label("");
        separator.addStyleName("separator");

        HorizontalLayout options = new HorizontalLayout();
        options.addComponents(filter, addButton);

        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        grid.setHeight("350px");
        grid.setWidth("100%");
        grid.setColumns("id", "nombre", "direccion", "telefono", "email");

        filter.setPlaceholder("Buscar por nombre");
        filter.addValueChangeListener(e -> listarEstablecimientos(e.getValue()));

        addButton.addClickListener(e -> editor.editEstablecimiento(new Establecimiento("", "", "", "")));

        addComponents(page_title, separator, options, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> editor.editEstablecimiento(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listarEstablecimientos(filter.getValue());
        });

        listarEstablecimientos(null);
    }

    private void listarEstablecimientos(String nombre) {

        if(StringUtils.isEmpty(nombre))
            grid.setItems(service.findAll());
        else
            grid.setItems(service.findByNombreContaining(nombre));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
