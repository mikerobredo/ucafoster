package com.iw.ucafoster.vistas.pedidos;

import javax.annotation.PostConstruct;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import com.iw.ucafoster.entidades.Pedido;
import com.iw.ucafoster.services.FamiliaProductoService;
import com.iw.ucafoster.services.ProductoService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = PedidosView.VIEW_NAME)
public class PedidosView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "pedidos";

	/* Navegación */
	MenuBar section_navbar = new MenuBar();

	MenuItem nuevoPedido;
	MenuItem pendientes;
	MenuItem historial;

	MenuBar.Command command;
    
    private PedidosEditor editor;
    private PendientesLayout pendientesL;
	
	/* Servicios */
	
	private final ProductoService prodServ;
	private final FamiliaProductoService famServ;

    @Autowired
    public PedidosView(ProductoService prodServ, FamiliaProductoService famServ, PedidosEditor editor, PendientesLayout pendientesL) {
    	
    	this.prodServ = prodServ;
    	this.famServ = famServ;
    	this.editor = editor;
    	this.pendientesL = pendientesL;

    	command = new MenuBar.Command() {

            @Override
            public void menuSelected(MenuItem menuItem) {

                switch(menuItem.getText()) {
                    case "NUEVO":
                    	if(!editor.isVisible()){
                    		editor.editPedido(new Pedido());
                    		pendientesL.setVisible(false);
                    	}
                        break;
                    case "PENDIENTES":
                    	pendientesL.listar();
                    	editor.setVisible(false);
                        break;
                    case "HISTORIAL":
                        break;
                }
                nuevoPedido.setStyleName(null);
                pendientes.setStyleName(null);
                historial.setStyleName(null);
                menuItem.setStyleName("item-selected");
                
            }
        };

    	nuevoPedido = section_navbar.addItem("NUEVO", VaadinIcons.PLUS, command);
    	pendientes = section_navbar.addItem("PENDIENTES", VaadinIcons.ARROW_BACKWARD, command);
    	historial = section_navbar.addItem("HISTORIAL", VaadinIcons.HOURGLASS_END, command);

    	section_navbar.addStyleName(ValoTheme.MENUBAR_SMALL);
    	section_navbar.setWidth("100%");
    }

    @PostConstruct
    void init() {

        Label page_title = new Label("Realizar Pedido");
        page_title.addStyleName(ValoTheme.LABEL_H2);
        page_title.addStyleName(ValoTheme.LABEL_BOLD);

        Label separator = new Label("");
        separator.addStyleName("separator");

        addComponents(page_title, separator, section_navbar, editor, pendientesL);

        nuevoPedido.setStyleName("item-selected");
        editor.editPedido(new Pedido());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}