package com.iw.ucafoster.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zonas_establecimiento")
public class ZonaEstablecimiento {

    @Id
    @GeneratedValue
    Integer id;

    private String nombre, descripcion;

    @ManyToOne
    @JoinColumn(name = "id_establecimiento")
    private Establecimiento establecimiento;

    @OneToMany(mappedBy = "zonaEstablecimiento")
    private List<Mesa> mesas = new ArrayList<Mesa>();

    protected ZonaEstablecimiento() {}

    public ZonaEstablecimiento(String nombre, String descripcion) {

        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Establecimiento getEstablecimiento() { return establecimiento; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEstablecimiento(Establecimiento establecimiento) { this.establecimiento = establecimiento; }

    @Override
    public String toString() {
        return nombre;
    }
}
