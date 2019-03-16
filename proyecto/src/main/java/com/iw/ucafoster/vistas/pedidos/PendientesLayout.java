package com.iw.ucafoster.vistas.pedidos;

import org.springframework.beans.factory.annotation.Autowired;

import com.iw.ucafoster.entidades.Empleado;
import com.iw.ucafoster.entidades.Pedido;
import com.iw.ucafoster.security.SecurityUtils;
import com.iw.ucafoster.services.PedidoService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;

@SpringComponent
@UIScope
public class PendientesLayout extends VerticalLayout{
	
	private static final long serialVersionUID = 1L;

	private PedidoService pedService;
	
	/* Componentes */
	private Grid<Pedido> pGrid;
	private Button btnMod;
	
	@Autowired
	public PendientesLayout(PedidoService pedService, PedidosEditor editor){

		setMargin(false);

		/* Servicios */
		this.pedService = pedService;
		
		
		/* Grid */
		pGrid = new Grid<>(Pedido.class);
		pGrid.setCaption("Lista de pedidos pendientes");
		pGrid.setColumns("id", "fecha", "total", "mesa", "tipoPedido");
		pGrid.getColumn("tipoPedido").setCaption("Tipo de Pedido");
		
		pGrid.setSelectionMode(SelectionMode.SINGLE);
		
		/* Boton de modificacion */
		btnMod = new Button("Modificar Pedido");
		btnMod.addClickListener(e -> {
			Pedido ped = pGrid.asSingleSelect().getValue();
			
			if(ped != null){
				editor.editPedido(ped);
				setVisible(false);
			}
		});
		
		pGrid.setWidth("100%");
		
		addComponents(pGrid, btnMod);
		setVisible(false);
		
	}
	
	public void listar(){
		
		Empleado emp = SecurityUtils.getEmpleadoSesion();
		
		pGrid.setItems(pedService.findByEstablecimientoAndEstadoOrderByIdDesc(emp.getEstablecimiento(), Pedido.estadoPedido.PENDIENTE));
		setVisible(true);
	}
	
	

}
