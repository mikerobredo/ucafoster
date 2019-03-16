package com.iw.ucafoster.services;

import java.util.List;

import com.iw.ucafoster.entidades.ZonaEstablecimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw.ucafoster.entidades.Mesa;
import com.iw.ucafoster.repositorios.MesaRepository;

@Service
public class MesaService {
	
	@Autowired
	MesaRepository repo;
	
	public Mesa save(Mesa mesa){
		return repo.save(mesa);
	}
	
	public void delete(Mesa mesa){
		repo.delete(mesa);
	}
	
	public List<Mesa> findAll(){
		return repo.findAll();
	}

	public List<Mesa> findByZonaEstablecimiento(ZonaEstablecimiento zonaEstablecimiento) {
		return repo.findByZonaEstablecimiento(zonaEstablecimiento);
	}

	public List<Mesa> findByZonaEstablecimientoAndNombreContaining(ZonaEstablecimiento zonaEstablecimiento, String nombre) {
		return repo.findByZonaEstablecimientoAndNombreContaining(zonaEstablecimiento, nombre);
	}
	
	public Mesa findOne(Integer id){
		return repo.findOne(id);
	}
}
