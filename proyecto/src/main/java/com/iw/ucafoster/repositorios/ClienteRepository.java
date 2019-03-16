package com.iw.ucafoster.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iw.ucafoster.entidades.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Cliente findOne(Integer Id);

	List<Cliente> findByTelefonoContaining(String telefono);
	
	List<Cliente> findByNombreContaining(String nombre);
	
}
