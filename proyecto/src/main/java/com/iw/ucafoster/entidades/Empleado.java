package com.iw.ucafoster.entidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "empleados")
public class Empleado implements UserDetails {

    // ATRIBUTOS
    // ---------

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Integer id;
    private String nombre, apellidos, dni, email, telefono, direccion, password, rol, username;
    
    @ManyToOne
    @JoinColumn(name = "est_id")
    private Establecimiento establecimiento;

    // CONSTRUCTORES
    // -------------

    /**
     * Constructor protegido predeterminado
     */
    protected Empleado() {

    }

    /**
     * Constructor públio
     * @param nombre Nombre del empleado
     * @param apellidos Apellidos del empleado
     * @param dni DNI del empleado
     * @param email Email del empleado
     * @param telefono Teléfono del empleado
     * @param direccion Dirección del empleado
     * @param username Nombre de usuario del empleado
     * @param password Contraseña del empleado
     * @param rol Rol del empleado
     */
    public Empleado(String nombre, String apellidos, String dni, String email, String telefono,
                    String direccion,String username, String password, String rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.password = password;
        this.rol = rol;
        this.username = username;
    }

    // MÉTODOS OBSERVADORES
    // --------------------

    /**
     * @return Id del empleado
     */
    public Integer getId() { return id; }

    /**
     * @return Nombre del empleado
     */
    public String getNombre() { return nombre; }

    /**
     * @return Apellidos del empleado
     */
    public String getApellidos() { return apellidos; }

    /**
     * @return DNI del empleado
     */
    public String getDni() { return dni; }

    /**
     * @return Email del empleado
     */
    public String getEmail() { return email; }

    /**
     * @return Teléfono del empleado
     */
    public String getTelefono() { return telefono; }

    /**
     * @return Dirección del empleado
     */
    public String getDireccion() { return direccion; }

    /**
     * @return Rol del empleado
     */
    public String getRol() { return rol; }

    /**
     * @return Establecimiento del empleado
     */
    public Establecimiento getEstablecimiento() { return establecimiento; }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return username;
    }

    // MÉTODOS MODIFICADORES
    // ---------------------
    
    /**
     * 
     * @param rol del usuario
     */
    public void setRol(String rol) {
    	this.rol = rol;
    }
    
    /**
     * 
     * @param password del empleado
     */
    public void setPassword(String password) {
    	this.password = password;
    }

    /**
     * @param nombre Nuevo nombre del empleado
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param apellidos Nuevos apellidos del empleado
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @param dni Nuevo DNI del empleado
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @param email Nuevo email del empleado
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param telefono Nuevo teléfono del empleado
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @param direccion Nueva dirección del empleado
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param establecimiento Nuevo establecimiento
     */
    public void setEstablecimiento(Establecimiento establecimiento) { this.establecimiento = establecimiento; }

    /**
     * @param username Nuevo nombre de usuario
     */
    public void setUsername(String username) { this.username = username; }

    // OTROS MÉTODOS
    // -------------

    @Override
    public String toString() {
        return Long.toString(id) + " >> " + apellidos + ", " + nombre + " | " + dni + " | " + email +" | " + username;
    }
    
    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority(this.rol));
		return list;
		
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}