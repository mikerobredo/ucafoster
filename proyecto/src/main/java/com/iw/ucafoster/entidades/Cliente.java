package com.iw.ucafoster.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    // ATRIBUTOS
    // ---------

    @Id
    @GeneratedValue
    private Integer id;
    private String nombre, apellidos, dni, direccion, telefono, email;
    
    // Relacion Cliente-Pedido
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<Pedido>();
    

    // CONSTRUCTORES
    // -------------

    /**
     * Constructor protegido predeterminado
     */
    protected Cliente() {

    }

    public Cliente(String nombre, String apellidos, String dni, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    // MÉTODOS OBSERVADORES
    // --------------------

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDni() { return dni; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    
    public List<Pedido> getPedidos() { return pedidos; }

    // MÉTODOS MODIFICADORES
    // ---------------------

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setDni(String dni) { this.dni = dni; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }

    // OTROS MÉTODOS
    // -------------

    @Override
    public String toString() {
        return Integer.toString(id) + " >> " + apellidos + ", " + nombre + " | " + dni + " | " + email;
    }
}