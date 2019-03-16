package com.iw.ucafoster.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ingredientes")
public class Ingrediente {

    @Id
    @GeneratedValue
    private Integer id;
    private String nombre, descripcion;

    // Constructor
    // -----------

    protected Ingrediente(){

    }

    public Ingrediente(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Metodos observadores
    // --------------------

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    // Metodos modificadores
    // ---------------------

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    @Override
    public String toString(){
    	return Integer.toString(id) + " - " + nombre;
    }
    
    @Override
    public boolean equals(Object obj) {
        return ((Ingrediente)obj).getId().equals(this.getId());
    }
}