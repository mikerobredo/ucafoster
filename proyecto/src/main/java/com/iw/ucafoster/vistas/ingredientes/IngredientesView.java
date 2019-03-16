package com.iw.ucafoster.vistas.ingredientes;

import com.iw.ucafoster.entidades.Ingrediente;
import com.iw.ucafoster.services.IngredienteService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@SpringView(name = IngredientesView.VIEW_NAME)
public class IngredientesView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "ingredientes";

    private Grid<Ingrediente> grid;
    private TextField filter;
    private Button addButton;

    private final IngredienteService service;

    private final IngredienteEditor editor;

    @Autowired
    public IngredientesView(IngredienteService service, IngredienteEditor editor) {

        this.service = service;
        this.editor = editor;
        this.grid = new Grid<>(Ingrediente.class);
        this.filter = new TextField();
        this.addButton = new Button("Añadir", VaadinIcons.PLUS);
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Ingredientes");
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

        filter.setPlaceholder("Buscar por nombre");
        filter.addValueChangeListener(e -> listarIngredientes(e.getValue()));

        addButton.addClickListener(e -> editor.editIngrediente(new Ingrediente("", "")));

        addComponents(page_title, separator, options, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> editor.editIngrediente(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listarIngredientes(filter.getValue());
        });

        listarIngredientes(null);
    }

    private void listarIngredientes(String nombre) {

        if(StringUtils.isEmpty(nombre))
            grid.setItems(service.findAll());
        else
            grid.setItems(service.findByNombreContaining(nombre));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
