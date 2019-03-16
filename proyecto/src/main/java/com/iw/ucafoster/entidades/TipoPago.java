package com.iw.ucafoster.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipopago")
public class TipoPago {
	
	@Id
    @GeneratedValue
    Integer id;
    String nombre, descripcion;
    
    // Constructor
    // -----------
    
    protected TipoPago(){

    }
    
    public TipoPago(String nombre, String descripcion){
    	this.nombre = nombre;
    	this.descripcion = descripcion;
    }
    
    // Metodos observadores
    // --------------------

    Integer getId(){ return id; }
    String getNombre(){ return nombre; }
    String getDescripcion() { return descripcion; }

    // Metodos modificadores
    // --------------------

    void setNombre(String nombre) { this.nombre = nombre; }
    void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    // Otros
    // --------------------
    @Override
    public String toString(){
    	return nombre;
    }
    
    @Override
    public boolean equals(Object obj) {
        return ((TipoPago)obj).getId().equals(this.getId());
    }
}
