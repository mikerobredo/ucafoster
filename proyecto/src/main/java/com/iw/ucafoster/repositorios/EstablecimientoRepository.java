package com.iw.ucafoster.repositorios;

import com.iw.ucafoster.entidades.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Integer> {

    List<Establecimiento> findAll();
    List<Establecimiento> findByNombreContaining(String nombre);
}
