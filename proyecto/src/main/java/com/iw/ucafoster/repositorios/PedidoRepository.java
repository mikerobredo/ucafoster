package com.iw.ucafoster.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iw.ucafoster.entidades.Establecimiento;
import com.iw.ucafoster.entidades.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	List<Pedido> findAll();
	Pedido findOne(Integer id);
	
	List<Pedido> findByEstablecimiento(Establecimiento establecimiento);
	List<Pedido> findByEstablecimientoAndEstadoOrderByIdDesc(Establecimiento establecimiento, Pedido.estadoPedido estado);
	
	
}
