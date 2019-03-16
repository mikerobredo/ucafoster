package com.iw.ucafoster.vistas.pedidos;

import java.io.IOException;
import java.text.DecimalFormat;

import com.iw.ucafoster.pdfGenerators.PdfCliente;
import com.iw.ucafoster.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;

import com.iw.ucafoster.entidades.Pedido;
import com.iw.ucafoster.entidades.TipoPago;
import com.iw.ucafoster.services.TipoPagoService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.RadioButtonGroup;

@SpringComponent
@UIScope
public class cobroWindow extends Window{
	
	private static final long serialVersionUID = 1L;
	
	/* Servicios */
	private TipoPagoService tpServ;
	private PedidoService pedServ;
	
	/* Datos pedido */
	private Pedido pedido;
	private Float totalPedido;
	
	/* Componentes */
	private Label lblTotal;
	private RadioButtonGroup<TipoPago> rdMetodos;
	private TextField boxEntregado;
	private Label lblCambio;
	private Button btnCobrar;
	private Button btnFactura;
	
	private DecimalFormat df;
	
	@Autowired
	public cobroWindow(TipoPagoService tpServ, PedidoService pedServ){
		super("Cobro del pedido");
		center();
		
		setWidth(500.0f, Unit.PIXELS);
	    VerticalLayout contenido = new VerticalLayout();
	    
	    this.tpServ = tpServ;
	    this.pedServ = pedServ;
	    df = new DecimalFormat("###0.00€");
	    
	    /********************************************************
		 * 	Importe del pedido y metodos de pago
		 ********************************************************/
	    lblTotal = new Label("");
	    rdMetodos = new RadioButtonGroup<>("Metodo de pago");
	    
	    /********************************************************
		 * 	Entregado por el cliente y cambio
		 ********************************************************/
	    HorizontalLayout entregadoLayout = new HorizontalLayout();
	    boxEntregado = new TextField("Importe entregado por el cliente: ");
	    boxEntregado.setValue(Float.toString(0.0f));
	    lblCambio = new Label("");
	    entregadoLayout.addComponents(boxEntregado, lblCambio);
	    
	    
	    btnCobrar = new Button("Cobrar");
	    btnFactura = new Button("Factura");

	    HorizontalLayout botones = new HorizontalLayout(btnCobrar, btnFactura);
	    
	    cargarListeners();
	    
	    contenido.setMargin(true);
	    contenido.addComponents(lblTotal, rdMetodos, entregadoLayout, botones);
	    setContent(contenido);
	}
	
	
	public void cobrarPedido(Pedido ped){
		
		UI.getCurrent().addWindow(this);
    	this.focus();
		
    	rdMetodos.setItems(tpServ.findAll());
	    rdMetodos.setSelectedItem(tpServ.findOne(1)); // metodo de pago por defecto: el primero de la BD
	    
	    pedido = ped;
	    totalPedido = pedido.getTotal();
	    lblTotal.setValue("Importe total del pedido: "+df.format(totalPedido));
	    
	}
	
	private void cargarListeners(){
		
		boxEntregado.addValueChangeListener(e -> {
			String txtValor = boxEntregado.getValue();
    		
    		if(txtValor.isEmpty()){
    			lblCambio.setValue("");
    		}else{
    			Float cambio = Float.valueOf(txtValor) - totalPedido;
    			lblCambio.setValue("Cambio: "+ df.format(cambio));
    		}
		});
		
		btnCobrar.addClickListener(e -> {
			String txtValor = boxEntregado.getValue();
			if(txtValor.isEmpty()){
				Notification.show("El cliente debe entregar algo");
			}else{
				Float cambio = Float.valueOf(txtValor) - totalPedido;
				
				if(cambio < 0){
					Notification.show("Falta dinero");
				}else{
					pedido.setEstado(Pedido.estadoPedido.PAGADO);
					pedido.setTipoPago(rdMetodos.getSelectedItem().get());
					this.close();
				}
			}
			
		});

		btnFactura.addClickListener(e -> {

			try {
				PdfCliente pdf = new PdfCliente(pedServ.save(pedido));
				pdf.convertToPdf();
			} catch(IOException error) {
				error.printStackTrace();
			}
		});
		
	}
	

		
}
