package com.iw.ucafoster.vistas.productos;

import com.iw.ucafoster.entidades.Producto;
import com.iw.ucafoster.services.ProductoService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@SpringView(name = ProductosView.VIEW_NAME)
public class ProductosView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "productos";
	
	private Grid<Producto> grid;
    private TextField filter;
    private Button addButton;

    private final ProductoService service;

    private final ProductoEditor editor;

    @Autowired
    public ProductosView(ProductoService service, ProductoEditor editor) {

        this.service = service;
        this.editor = editor;
        this.grid = new Grid<>(Producto.class);
        this.filter = new TextField();
        this.addButton = new Button("Añadir", VaadinIcons.PLUS);
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Productos");
        page_title.addStyleName(ValoTheme.LABEL_H2);
        page_title.addStyleName(ValoTheme.LABEL_BOLD);

        Label separator = new Label("");
        separator.addStyleName("separator");

        HorizontalLayout options = new HorizontalLayout();
        options.addComponents(filter, addButton);

        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        
        grid.setHeight("350px");
        grid.setWidth("100%");
        grid.setColumns("id", "nombre", "descripcion", "precio", "familia");
        filter.setPlaceholder("Buscar por nombre");
        filter.addValueChangeListener(e -> listarProductos(e.getValue()));

        addButton.addClickListener(e -> editor.editProducto(new Producto("", "", 0.0f)));

        addComponents(page_title, separator, options, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> editor.editProducto(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listarProductos(filter.getValue());
        });

        listarProductos(null);
    }

    private void listarProductos(String nombre) {

        if(StringUtils.isEmpty(nombre))
            grid.setItems(service.findAll());
        else
            grid.setItems(service.findByNombreContaining(nombre));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
