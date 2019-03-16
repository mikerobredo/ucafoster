package com.iw.ucafoster.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw.ucafoster.entidades.TipoPedido;
import com.iw.ucafoster.repositorios.TipoPedidoRepository;

@Service
public class TipoPedidoService {

	@Autowired
	TipoPedidoRepository repo;
	
	public TipoPedido save(TipoPedido tp){
		return repo.save(tp);
	}
	
	public void delete(TipoPedido tp){
		repo.delete(tp);
	}
	
	public TipoPedido findOne(Integer id){
		return repo.findOne(id);
	}
	
	public List<TipoPedido> findAll(){
		return repo.findAll();
	}
	
}
