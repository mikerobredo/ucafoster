package com.iw.ucafoster.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw.ucafoster.entidades.Establecimiento;
import com.iw.ucafoster.entidades.Pedido;
import com.iw.ucafoster.repositorios.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository repo;
	
	public Pedido save(Pedido pedido){
		return repo.save(pedido);
	}
	
	public void delete(Pedido pedido){
		repo.delete(pedido);
	}
	
	public Pedido findOne(Integer id){
		return repo.findOne(id);
	}
	
	public List<Pedido> findByEstablecimiento(Establecimiento establecimiento){
		return repo.findByEstablecimiento(establecimiento);
	}
	
	public List<Pedido> findByEstablecimientoAndEstadoOrderByIdDesc(Establecimiento establecimiento, Pedido.estadoPedido estado){
		return repo.findByEstablecimientoAndEstadoOrderByIdDesc(establecimiento, estado);
	}
}
