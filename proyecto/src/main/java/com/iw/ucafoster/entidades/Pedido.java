package com.iw.ucafoster.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "pedidos")
public class Pedido {
	
	public enum estadoPedido{ PAGADO, PENDIENTE, ANULADO }
	

	//Atributos
	//---------
	
	@Id
	@GeneratedValue	
	private Integer id;
	private Float total, efectivo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	private estadoPedido estado;
	
	// Composicion Pedido-LineaPedido
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<LineaPedido> lineasPedido = new ArrayList<LineaPedido>();
	
	// Relacion Pedido-TipoPedido
	@ManyToOne
	@JoinColumn(name = "id_tipoPedido")
	private TipoPedido tipopedido;
	
	// Relacion Pedido-Empleado
	@ManyToOne
	@JoinColumn(name = "id_emp")
	private Empleado empleado;
	
	// Relacion Pedido-Establecimiento
	@ManyToOne
	@JoinColumn(name = "id_est")
	private Establecimiento establecimiento;
	
	// Relacion Pedido-TipoPago
	@ManyToOne
	@JoinColumn(name = "id_tipoPago")
	private TipoPago tipopago;
	
	// Relacion Pedido-Cliente
	@ManyToOne
	@JoinColumn(name = "id_clt")
	private Cliente cliente;
	
	// Relacion Pedido-Mesa
	@ManyToOne
	@JoinColumn(name = "id_mesa")
	private Mesa mesa;
	
	
	//Constructores
	//-------------
	
	/**
	 * Constructor predeterminado
	 * Por defecto, los pedidos estan pendientes inicialmente
	 */
	public Pedido(){
		this.estado = estadoPedido.PENDIENTE; 
	}
	
	public Pedido(Float total, Float efectivo, Date fecha, estadoPedido estado, ArrayList<LineaPedido> lineas,
				  Cliente cliente, Mesa mesa, Establecimiento establecimiento)
	{
		this.total = total;
		this.efectivo = efectivo;
		this.fecha = fecha;
		this.estado = estado;
		this.lineasPedido = lineas;
		this.cliente = cliente;
		this.mesa = mesa;
		this.establecimiento = establecimiento;
	}
	
	//Metodos observadores
	//--------------------
	
	public Integer getId() { return id; }
	public Float getTotal() { return total; }
	public Float getEfectivo() {return efectivo; }
	public Date getFecha() { return fecha; }
	public estadoPedido getEstado() { return estado; }
	
	public List<LineaPedido> getLineasPedido() { return lineasPedido; }
	public TipoPedido getTipoPedido() { return tipopedido; }
	public Empleado getEmpleado() { return empleado; }
	public Establecimiento getEstablecimiento() { return establecimiento; }
	public TipoPago getTipoPago() { return tipopago; }
	public Cliente getCliente() { return cliente; }
	public Mesa getMesa() { return mesa; }
	
	
	
	//Metodos modificadores
	//---------------------
	
	public void setTotal(Float total) { this.total = total; }
	public void setEfectivo(Float efectivo) { this.efectivo = efectivo; }
	public void setFecha(Date fecha) { this.fecha = fecha; }
	public void setEstado(estadoPedido estado) { this.estado = estado; }
	
	public void setLineasPedido(List<LineaPedido> lineasPedido) { this.lineasPedido = lineasPedido; }
	public void setTipoPedido(TipoPedido tipopedido) { this.tipopedido = tipopedido; }
	public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
	public void setEstablecimiento(Establecimiento establecimiento) { this.establecimiento = establecimiento; }
	public void setTipoPago(TipoPago tipopago) { this.tipopago = tipopago; }
	public void setCliente(Cliente cliente) { this.cliente = cliente; }
	public void setMesa(Mesa mesa) { this.mesa = mesa; }
	
	
	
}
