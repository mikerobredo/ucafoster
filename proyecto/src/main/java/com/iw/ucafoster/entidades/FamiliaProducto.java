package com.iw.ucafoster.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "familiasproducto")
public class FamiliaProducto {

    // Atributos
    // ---------

    @Id
    @GeneratedValue
    private Integer id;
    private String nombre, descripcion;
    private Float iva;

    // Constructor
    // -----------

    protected FamiliaProducto(){

    }

    public FamiliaProducto(String nombre, String descripcion, float iva) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.iva = iva;
    }

    // Metodos observadores
    // --------------------

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public float getIva() { return iva; }

    // Metodos modificadores
    // ---------------------

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setIva(float iva) { this.iva = iva; }

    // Otros métodos
    // -------------

    @Override
    public String toString(){
    	return this.nombre;
    }
}