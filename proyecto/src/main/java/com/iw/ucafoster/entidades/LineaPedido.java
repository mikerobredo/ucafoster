package com.iw.ucafoster.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lineaPedido")
public class LineaPedido {
	
	@GeneratedValue
	@Id
	private Integer id;
	private Float precioUd;
	private Integer cantidad;
	
	// Agregacion LineaPedido-Producto
	@ManyToOne
	@JoinColumn(name = "prod_id")
	private Producto producto;
	
	
	
	// Constructores
    // -------------
	
	protected LineaPedido(){
		
	}
	
	public LineaPedido(Producto producto){
		this.producto = producto;
		this.precioUd = producto.getPrecio();
		this.cantidad = 1;
	}
	
	// Metodos observadores
    // ---------------------
	
	public Integer getId() { return id; }
	public Float getPrecio() { return precioUd; }
	public Integer getCantidad() { return cantidad; }
	
	public Producto getProducto() { return producto; }
	
	// Metodos modificadores
    // ---------------------
	
	public void setPrecio(Float precioUd) { this.precioUd = precioUd; }
	public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
	
	public void setProducto(Producto producto) { this.producto = producto; }
	
}
