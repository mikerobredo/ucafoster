package com.iw.ucafoster.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "establecimientos")
public class Establecimiento {

    @Id
    @GeneratedValue
    private Integer id;

    private String nombre, direccion, telefono, email;
    
    // Relacion Establecimiento-Empleado
    @OneToMany(mappedBy = "establecimiento")
    private List<Empleado> empleados = new ArrayList<Empleado>();
    
    // Relacion Establecimiento-Pedido
    @OneToMany(mappedBy = "establecimiento")
    private List<Pedido> pedidos = new ArrayList<Pedido>();

    // Constructor
    // -----------

    protected Establecimiento(){

    }

    public Establecimiento(String nombre, String direccion, String telefono, String email) {

        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    // Metodos observadores
    // --------------------

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public List<Empleado> getEmpleados() { return empleados; }
    public List<Pedido> getPedidos() { return pedidos; }

    // Metodos modificadores
    // ---------------------

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) {this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setEmpleados(List<Empleado> empleados) { this.empleados = empleados; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }

    // Otros métodos
    // -------------

    @Override
    public String toString() {
        return this.nombre;
    }

}