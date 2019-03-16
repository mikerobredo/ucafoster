package com.iw.ucafoster.vistas.familias;

import com.iw.ucafoster.entidades.FamiliaProducto;
import com.iw.ucafoster.services.FamiliaProductoService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@SpringView(name = FamiliasView.VIEW_NAME)
public class FamiliasView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "familias";

    private Grid<FamiliaProducto> grid;
    private TextField filter;
    private Button addButton;

    private final FamiliaProductoService service;

    private final FamiliaEditor editor;

    @Autowired
    public FamiliasView(FamiliaProductoService service, FamiliaEditor editor) {

        this.service = service;
        this.editor = editor;
        this.grid = new Grid<>(FamiliaProducto.class);
        this.filter = new TextField();
        this.addButton = new Button("Añadir", VaadinIcons.PLUS);
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Familias de productos");
        page_title.addStyleName(ValoTheme.LABEL_H2);
        page_title.addStyleName(ValoTheme.LABEL_BOLD);

        Label separator = new Label("");
        separator.addStyleName("separator");

        HorizontalLayout options = new HorizontalLayout();
        options.addComponents(filter, addButton);

        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        grid.setHeight("350px");
        grid.setWidth("100%");
        grid.setColumns("id", "nombre", "descripcion", "iva");

        filter.setPlaceholder("Buscar por nombre");
        filter.addValueChangeListener(e -> listarFamiliasProductos(e.getValue()));

        addButton.addClickListener(e -> editor.editFamilia(new FamiliaProducto("", "", 0.0f)));

        addComponents(page_title, separator, options, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> editor.editFamilia(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listarFamiliasProductos(filter.getValue());
        });

        listarFamiliasProductos(null);
    }

    private void listarFamiliasProductos(String nombre) {

        if(StringUtils.isEmpty(nombre))
            grid.setItems(service.findAll());
        else
            grid.setItems(service.findByNombreContaining(nombre));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
