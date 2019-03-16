package com.iw.ucafoster.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw.ucafoster.entidades.FamiliaProducto;
import com.iw.ucafoster.entidades.Producto;
import com.iw.ucafoster.repositorios.ProductoRepository;



@Service 
public class ProductoService {
	
	@Autowired
	ProductoRepository repo;
	
	public Producto save(Producto producto) {
	    return repo.save(producto);
	}
	
	public void delete(Producto producto) {
	    repo.delete(producto);
	}
	
	public Producto findOne(Integer id) {
	    return repo.findOne(id);
	}
	
	public List<Producto> findAll() {
	    return repo.findAll();
	}
	
	public List<Producto> findByNombreContaining(String nombre) {
	    return repo.findByNombreContaining(nombre);
	}
	
	public List<Producto> findByFamilia(FamiliaProducto familia){
		return repo.findByFamilia(familia);
	}

}
