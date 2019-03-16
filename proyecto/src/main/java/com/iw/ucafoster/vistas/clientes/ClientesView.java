package com.iw.ucafoster.vistas.clientes;


import com.iw.ucafoster.entidades.Cliente;
import com.iw.ucafoster.services.ClienteService;
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

@SpringView(name = ClientesView.VIEW_NAME)
public class ClientesView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "clientes";

    private Grid<Cliente> grid;
    private TextField filter;
    private Button addButton;

    private final ClienteService service;

    private final ClienteEditor editor;

    @Autowired
    public ClientesView(ClienteService service, ClienteEditor editor) {

        this.service = service;
        this.editor = editor;
        this.grid = new Grid<>(Cliente.class);
        this.filter = new TextField();
        this.addButton = new Button("Añadir", VaadinIcons.PLUS);
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Clientes");
        page_title.addStyleName(ValoTheme.LABEL_H2);
        page_title.addStyleName(ValoTheme.LABEL_BOLD);

        Label separator = new Label("");
        separator.addStyleName("separator");

        HorizontalLayout options = new HorizontalLayout();
        options.addComponents(filter, addButton);

        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        grid.setHeight("350px");
        grid.setWidth("100%");
        grid.setColumns("id", "nombre", "apellidos", "dni", "direccion", "telefono", "email");

        filter.setPlaceholder("Buscar por telefono");
        filter.addValueChangeListener(e -> listarClientes(e.getValue()));

        addButton.addClickListener(e -> editor.editCliente(new Cliente("", "", "", "", "", "")));

        addComponents(page_title, separator, options, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> editor.editCliente(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listarClientes(filter.getValue());
        });

        listarClientes(null);
    }

    private void listarClientes(String telefono) {

        if(StringUtils.isEmpty(telefono))
            grid.setItems(service.findAll());
        else
            grid.setItems(service.findByTelefonoContaining(telefono));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
