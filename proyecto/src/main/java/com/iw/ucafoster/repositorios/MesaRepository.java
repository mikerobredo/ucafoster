package com.iw.ucafoster.repositorios;

import java.util.List;

import com.iw.ucafoster.entidades.ZonaEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iw.ucafoster.entidades.Mesa;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
	
	List<Mesa> findAll();
	List<Mesa> findByZonaEstablecimiento(ZonaEstablecimiento zonaEstablecimiento);
	List<Mesa> findByZonaEstablecimientoAndNombreContaining(ZonaEstablecimiento zonaEstablecimiento, String nombre);
	Mesa findOne(Integer id);
}
