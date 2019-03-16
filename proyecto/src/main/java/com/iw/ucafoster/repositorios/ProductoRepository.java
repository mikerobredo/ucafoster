package com.iw.ucafoster.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.iw.ucafoster.entidades.FamiliaProducto;
import com.iw.ucafoster.entidades.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	List<Producto> findAll();
	Producto findOne(Integer id);
	
	List<Producto> findByNombreContaining(String nombre);
	List<Producto> findByFamilia(FamiliaProducto familia);
}
