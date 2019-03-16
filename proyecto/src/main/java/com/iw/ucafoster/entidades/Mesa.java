package com.iw.ucafoster.entidades;

import javax.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {
	
	@Id
	@GeneratedValue
	Integer id;
	String nombre;

	@ManyToOne
	@JoinColumn(name = "id_zona_establecimiento")
	ZonaEstablecimiento zonaEstablecimiento;

	protected Mesa() {}
	
	public Mesa(String nombre){
		this.nombre = nombre;
	}
	
	public Integer getId() { return id; }
	public String getNombre() { return nombre; }
	public ZonaEstablecimiento getZonaEstablecimiento() { return zonaEstablecimiento; }
	
	public void setNombre(String nombre) { this.nombre = nombre; }
	public void setZonaEstablecimiento(ZonaEstablecimiento zonaEstablecimiento) { this.zonaEstablecimiento = zonaEstablecimiento; }
}
