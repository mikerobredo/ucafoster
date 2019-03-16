package com.iw.ucafoster.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw.ucafoster.entidades.TipoPago;
import com.iw.ucafoster.repositorios.TipoPagoRepository;

@Service
public class TipoPagoService {
	
	@Autowired
	TipoPagoRepository repo;
	
	public TipoPago save(TipoPago tp){
		return repo.save(tp);
	}
	
	public void delete(TipoPago tp){
		repo.delete(tp);
	}
	
	public TipoPago findOne(Integer id){
		return repo.findOne(id);
	}
	
	public List<TipoPago> findAll(){
		return repo.findAll();
	}
	
}
