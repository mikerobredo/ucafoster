package com.iw.ucafoster.entidades;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    // Atributos
    // ---------

    @Id
    @GeneratedValue
    private Integer id;
    private String nombre, descripcion;
    private Float precio;
    
    // Relacion Producto-FamiliaProducto
    @ManyToOne
    @JoinColumn(name = "id_fam")
    private FamiliaProducto familia;
    
    // Composicion Producto-Ingrediente (equivalente a una relacion ManyToMany)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "prod_ing", joinColumns = @JoinColumn(name = "id_prod"), inverseJoinColumns = @JoinColumn(name = "id_ing"))
    private Set<Ingrediente> ingredientes = new HashSet<Ingrediente>();

    // Constructor
    // -----------

    public Producto(){

    }
    
    public Producto(String nombre, String descripcion, Float precio){
    	this.nombre = nombre;
    	this.descripcion = descripcion;
    	this.precio = precio;
    }

    // Metodos observadores
    // --------------------

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public float getPrecio() { return precio; }
    
    public FamiliaProducto getFamilia() { return familia; }
    public Set<Ingrediente> getIngredientes() { return ingredientes; }

    // Metodos modificadores
    // ---------------------

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPrecio(float precio) { this.precio = precio; }
    
    public void setFamilia(FamiliaProducto familia) { this.familia = familia; }
    public void setIngredientes(Set<Ingrediente> ingredientes) { this.ingredientes = ingredientes; }
    
    // Otros metodos
    // ---------------------
    @Override
    public String toString(){
    	return nombre;
    }
}