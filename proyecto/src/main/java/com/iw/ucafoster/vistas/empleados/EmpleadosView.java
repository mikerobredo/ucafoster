package com.iw.ucafoster.vistas.empleados;

import com.iw.ucafoster.entidades.Empleado;
import com.iw.ucafoster.entidades.Producto;
import com.iw.ucafoster.services.EmpleadoService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@SpringView(name = EmpleadosView.VIEW_NAME)
public class EmpleadosView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "empleados";

    private Grid<Empleado> grid;
    private TextField filter;
    private Button addButton;

    private final EmpleadoService service;

    private final EmpleadoEditor editor;

    @Autowired
    public EmpleadosView(EmpleadoService service, EmpleadoEditor editor) {

        this.service = service;
        this.editor = editor;
        this.grid = new Grid<>(Empleado.class);
        this.filter = new TextField();
        this.addButton = new Button("Añadir", VaadinIcons.PLUS);
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Empleados");
        page_title.addStyleName(ValoTheme.LABEL_H2);
        page_title.addStyleName(ValoTheme.LABEL_BOLD);

        Label separator = new Label("");
        separator.addStyleName("separator");

        HorizontalLayout options = new HorizontalLayout();
        options.addComponents(filter, addButton);

        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        grid.setHeight("350px");
        grid.setWidth("100%");
        grid.setColumns("id", "dni", "nombre", "apellidos", "username", "rol", "establecimiento");
        filter.setPlaceholder("Buscar por DNI");

        addButton.addClickListener(e -> editor.editEmpleado(new Empleado("", "", "", "",
                "", "", "", "", "")));

        addComponents(page_title, separator, options, grid, editor);

        grid.asSingleSelect().addValueChangeListener(e -> editor.editEmpleado(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listarEmpleados(filter.getValue());
        });

        listarEmpleados(null);
    }

    private void listarEmpleados(String dni) {

        if(StringUtils.isEmpty(dni))
            grid.setItems(service.findAll());
        else
            grid.setItems(service.findByDniContaining(dni));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
