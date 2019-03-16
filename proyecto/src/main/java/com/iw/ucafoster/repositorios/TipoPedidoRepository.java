package com.iw.ucafoster.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iw.ucafoster.entidades.TipoPedido;

public interface TipoPedidoRepository extends JpaRepository<TipoPedido, Integer>{
	
	TipoPedido findOne(Integer id);
	List<TipoPedido> findAll();
	
}
