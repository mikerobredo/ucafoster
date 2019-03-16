package com.iw.ucafoster.vistas.pedidos;

import java.util.Iterator;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import com.iw.ucafoster.entidades.FamiliaProducto;
import com.iw.ucafoster.entidades.Producto;
import com.iw.ucafoster.services.FamiliaProductoService;
import com.iw.ucafoster.services.ProductoService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class MenuProductos extends VerticalLayout {
	
	/* Servicios */
	private FamiliaProductoService famServ;
	private ProductoService prodServ;
	
	/* Enlace con el editor del pedido */
	private PedidosEditor linkEditor;
	
	/* Componentes */
	private MenuBar listaFam;
	private VerticalLayout listaProd;
	
	
	@Autowired
	public MenuProductos(FamiliaProductoService famServ, ProductoService prodServ) {

		addStyleName("prod-selector");
		setCaption("Añadir más productos");

		this.famServ = famServ;
		this.prodServ = prodServ;
		
		listaFam = new MenuBar(); // lista de familias
		listaFam.setWidth("100%");

		listaProd = new VerticalLayout();
		listaProd.setMargin(false);
		listaProd.setSpacing(true);
		
		this.addComponents(listaFam, listaProd);
		
	}
	
	public void cargarContenido()
	{
		listaFam.removeItems();
		cargarFamilias();
	}
	
	public void enlazarEditor(PedidosEditor pe){
		this.linkEditor = pe;
	}
	
	
	/**
	 * Carga todas las familias de todos los productos
	 */
	private void cargarFamilias()
	{
		Iterator<FamiliaProducto> famIterator = famServ.findAll().iterator();
		
		while(famIterator.hasNext()){
			FamiliaProducto fp = famIterator.next();
			listaFam.addItem(fp.getNombre(), null, new MenuBar.Command() {

				@Override
				public void menuSelected(MenuBar.MenuItem menuItem) {
					cargarProductos(fp);
				}
			});
		}
	}
	
	/**
	 * Carga los productos de una determinada familia
	 * @param fp familia que se carga
	 */
	private void cargarProductos(FamiliaProducto fp){

		HorizontalLayout btn_group = new HorizontalLayout();
		btn_group.setWidth("100%");

		listaProd.removeAllComponents();

		Iterator<Producto> prodIterator = prodServ.findByFamilia(fp).iterator();

		int i = 0;

		while(prodIterator.hasNext()) {
			if(i == 3) {
				listaProd.addComponent(btn_group);
				btn_group = new HorizontalLayout();
				btn_group.setWidth("100%");
				i = 0;
			}
			Producto p = prodIterator.next();
			Button boton = new Button(p.getNombre());
			boton.setWidth("100%");
			boton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			boton.addClickListener(e -> {
				linkEditor.addLinea(p);
			});
			btn_group.addComponent(boton);
			i ++;

		}
		listaProd.addComponent(btn_group);
	}
}
