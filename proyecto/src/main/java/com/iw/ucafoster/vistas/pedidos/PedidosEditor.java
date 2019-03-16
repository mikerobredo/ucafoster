package com.iw.ucafoster.vistas.pedidos;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.io.IOException;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import com.iw.ucafoster.entidades.Empleado;
import com.iw.ucafoster.entidades.LineaPedido;
import com.iw.ucafoster.entidades.Mesa;
import com.iw.ucafoster.entidades.Pedido;
import com.iw.ucafoster.entidades.Producto;
import com.iw.ucafoster.entidades.TipoPedido;
import com.iw.ucafoster.security.SecurityUtils;
import com.iw.ucafoster.services.PedidoService;
import com.iw.ucafoster.services.TipoPedidoService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid.SelectionMode;

import com.iw.ucafoster.pdfGenerators.PdfCocina;

@SpringComponent
@UIScope
public class PedidosEditor extends VerticalLayout {
	
	/* Servicios */
	private PedidoService service;
	private TipoPedidoService tpServ;

	/* PDFs */
	PdfCocina cocina = null;
	
	/* Pedido que se esta editando */
	private Pedido pedido;
	private List<LineaPedido> lineas;
	private Float totalPedido;
	
	/* Componentes */
	private VerticalLayout lineasLayout;
	private MenuProductos menuProd;
	
	private Grid<LineaPedido> lGrid;
	
	private Label lblTotal;
	private Button btnRealizar;
	private Button btnCancelar;
	
	private NativeSelect<TipoPedido> selectorTipo;
	private NativeSelect<Mesa> selectorMesa;
	private Button btnCobrar;
	
	private cobroWindow wdwCobro;
	private DecimalFormat df;
	
	// Editor de linea pedido
	private VerticalLayout lineaEditor;
	private Button btnMenos;
	private TextField boxCant;
	private Button btnMas;
	private Button btnBorrar;

	@Autowired
	public PedidosEditor(MenuProductos menuProd, PedidoService service, TipoPedidoService tpServ, cobroWindow wdwCobro) {

		setMargin(false);

		//lineas = new ArrayList<>();
		this.service = service;
		this.tpServ = tpServ;
		
		/* Resumen del pedido (izquierda) */
		
		lineasLayout = new VerticalLayout();
		lineasLayout.setMargin(false);
		lineasLayout.setSizeFull();
		
		df = new DecimalFormat("###0.00€");
		
		/* Menu de productos (derecha) */
		
		this.menuProd = menuProd;
		menuProd.enlazarEditor(this);
		menuProd.setSizeFull();
		menuProd.setMargin(false);
		
		/* Ventana de cobro (flotante) */
		this.wdwCobro = wdwCobro;
		
		/********************************************************
		 * 	Grid de lineas del pedido
		 ********************************************************/
		
		
		lGrid = new Grid<>(LineaPedido.class);
		lGrid.setColumns("cantidad", "producto");
		lGrid.addColumn(LineaPedido -> df.format(LineaPedido.getPrecio()))
						.setCaption("Precio/Ud")
						.setWidth(100);
		
		lGrid.addColumn(LineaPedido -> df.format(LineaPedido.getCantidad() * LineaPedido.getPrecio())
						).setCaption("Total");
		lGrid.setWidth("100%");
		lGrid.setHeight("300px");
		lGrid.setSelectionMode(SelectionMode.SINGLE);

		lGrid.getColumn("cantidad").setWidth(100);
		//lGrid.getColumn("precio").setWidth(100);
		lGrid.getColumns().get(3).setWidth(100);
		
		/********************************************************
		 * 	Componentes editor linea pedido
		 ********************************************************/
		lineaEditor = new VerticalLayout();
		lineaEditor.setMargin(false);
		HorizontalLayout cantidadEditor = new HorizontalLayout();
		cantidadEditor.setCaption("Modificar cantidad");
		cantidadEditor.setMargin(false);
		cantidadEditor.setSpacing(true);
		
		btnMenos = new Button();
		btnMenos.setIcon(VaadinIcons.MINUS);
		btnMenos.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnMenos.setWidth("50px");

		boxCant = new TextField();
		boxCant.setWidth("100px");

		btnMas = new Button();
		btnMas.setIcon(VaadinIcons.PLUS);
		btnMas.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnMas.setWidth("50px");

		btnBorrar = new Button();
		btnBorrar.setIcon(VaadinIcons.TRASH);
		btnBorrar.addStyleName(ValoTheme.BUTTON_DANGER);
		
		cantidadEditor.addComponents(btnMenos, boxCant, btnMas, btnBorrar);
		lineaEditor.addComponents(cantidadEditor);
		
		/********************************************************
		 * 	Realizar o cancelar el pedido
		 ********************************************************/

		HorizontalLayout pedido_buttons = new HorizontalLayout();
		pedido_buttons.setWidth("100%");
		pedido_buttons.addStyleName("botones-pedido");

		btnRealizar = new Button("Listo", VaadinIcons.REPLY);
		btnRealizar.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnRealizar.addStyleName(ValoTheme.BUTTON_SMALL);

		btnCancelar = new Button("Cancelar", VaadinIcons.CLOSE);
		btnCancelar.addStyleName(ValoTheme.BUTTON_DANGER);
		btnCancelar.addStyleName(ValoTheme.BUTTON_SMALL);

		pedido_buttons.addComponents(btnRealizar, btnCancelar);

		pedido_buttons.setComponentAlignment(btnRealizar, Alignment.MIDDLE_LEFT);
		pedido_buttons.setComponentAlignment(btnCancelar, Alignment.MIDDLE_RIGHT);
		
		/********************************************************
		 * 	Importe total, cobrar, tipo de pedido y mesa
		 ********************************************************/
		
		lblTotal = new Label("");
		btnCobrar = new Button("Cobrar");
		
		selectorTipo = new NativeSelect<>("Selecciona el tipo de pedido");
		selectorTipo.setEmptySelectionAllowed(false);
		
		selectorMesa = new NativeSelect<>("Selecciona la mesa");
		
		cargarListeners();
		
		lineasLayout.addComponents(pedido_buttons, lGrid, lineaEditor);
		
		addComponents(lineasLayout, menuProd, selectorTipo, lblTotal, btnCobrar);
		
		setVisible(false);
	}
	
	
	public void editPedido(Pedido ped){
		
		/* Cargar el menu de productos */
		menuProd.cargarContenido();
		
		/* Carga los tipos de pedido */
		selectorTipo.setItems(tpServ.findAll());
		
		/* Ver que pedido se esta editando */
		if(ped == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = ped.getId() != null;
        if(persisted){ // el pedido esta en la BD
        	pedido = service.findOne(ped.getId());
        }else{ // es un pedido nuevo
        	pedido = ped;
        	Empleado emp = SecurityUtils.getEmpleadoSesion();
        	
        	Timestamp stamp = new Timestamp(System.currentTimeMillis());
        	pedido.setFecha(new Date(stamp.getTime()));
        	pedido.setTipoPedido(tpServ.findOne(1));
        	pedido.setEmpleado(emp);
        	pedido.setEstablecimiento(emp.getEstablecimiento());
        }
        
        
        btnCobrar.setVisible(pedido.getEstado() != Pedido.estadoPedido.PAGADO);
		
		/* Cargar los datos del pedido */
		lineas = pedido.getLineasPedido();
		selectorTipo.setSelectedItem(pedido.getTipoPedido());
		
		calcularImporte();
		refrescarLista();
		
		setVisible(true);
	}
	
	/**
	 * Metodo que añade una linea al pedido
	 * @param p es el producto de la linea
	 */
	public void addLinea(Producto p){
		LineaPedido lp = new LineaPedido(p);
		lp.setPrecio(p.getPrecio() *  (1 + p.getFamilia().getIva() / 100));
		lineas.add(lp); // se almacena el precio unitario + IVA
		refrescarLista();
		calcularImporte();
	}
	
	/**
	 * Metodo que añade listeners a los diversos componentes del editor
	 */
	private void cargarListeners(){
		
		/********************************************************************
		 * 	Listeners editor linea pedido
		 ********************************************************************/
		lGrid.addSelectionListener(e -> {
			LineaPedido lp = lGrid.asSingleSelect().getValue();
			
        	if(lp != null){
        		boxCant.setValue(Integer.toString(lp.getCantidad()));
        	}
		});
		
		btnMenos.addClickListener(e -> {
        	LineaPedido lp = lGrid.asSingleSelect().getValue();
        	
        	if(lp != null){
        		lp.setCantidad(lp.getCantidad()-1);
        		refrescarLista();
        		calcularImporte();
        		lGrid.asSingleSelect().setValue(lp);
        	}
        });
        
        btnMas.addClickListener(e -> {
        	LineaPedido lp = lGrid.asSingleSelect().getValue();
        	
        	if(lp != null){
        		lp.setCantidad(lp.getCantidad()+1);
        		refrescarLista();
        		calcularImporte();
        		lGrid.asSingleSelect().setValue(lp);
        	}
        });
        
        boxCant.addValueChangeListener(e -> {
        	LineaPedido lp = lGrid.asSingleSelect().getValue();
        	
        	if(lp != null){
        		String txtValor = boxCant.getValue();
        		
        		if(txtValor.isEmpty()){
        			lp.setCantidad(0);
        		}else{
        			lp.setCantidad(Integer.parseInt(txtValor));
        		}
        		
        		refrescarLista();
        		calcularImporte();
        		lGrid.asSingleSelect().setValue(lp);
        	}
        });
        
        btnBorrar.addClickListener(e -> {
        	LineaPedido lp = lGrid.asSingleSelect().getValue();
        	
        	if(lp != null){
        		lineas.remove(lp);
        		refrescarLista();
        		calcularImporte();
        	}
        	
        });
        
        /********************************************************************
		 * 	Listeners realizar, cancelar y cobrar pedido
		 ********************************************************************/
        
        btnRealizar.addClickListener(e -> {

        	try {
				pedido.setTotal(totalPedido);
				pedido.setTipoPedido(selectorTipo.getValue());
				Pedido before = null;
				if(pedido.getId() != null)
					before = service.findOne(pedido.getId());
				pedido = service.save(pedido);
				cocina = new PdfCocina(pedido, before);
				cocina.convertToPdf();
			} catch(IOException error) {
        		error.printStackTrace();
			}
        	setVisible(false);
        });
        
        btnCancelar.addClickListener(e -> {
        	setVisible(false);
        });
        
        btnCobrar.addClickListener(e -> {
        	pedido.setTotal(totalPedido);
        	wdwCobro.cobrarPedido(pedido);
        	btnCobrar.setVisible(false);
        });
        
        /********************************************************************
		 * 	Ventana de cobro
		 ********************************************************************/
        
        wdwCobro.addCloseListener(e -> {
        	btnCobrar.setVisible(pedido.getEstado() == Pedido.estadoPedido.PENDIENTE);
        });
        
        
        
	}
	
	/**
	 * Refresca la lista de las lineas del pedido
	 */
	private void refrescarLista(){
		lGrid.setItems(lineas);
	}
	
	/**
	 * Metodo que calcula el importe total del pedido
	 */
	private void calcularImporte(){
		
		totalPedido = 0.0f;
		
		for(LineaPedido lp: lineas){
			totalPedido += lp.getCantidad() * lp.getPrecio();
			
		}
		
		lblTotal.setValue("Importe total (IVA incluido): "+df.format(totalPedido));
		
	}

}
