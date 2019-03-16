package com.iw.ucafoster.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iw.ucafoster.entidades.TipoPago;

public interface TipoPagoRepository extends JpaRepository<TipoPago, Integer>{
	
	TipoPago findOne(Integer id);
	List<TipoPago> findAll();

}
