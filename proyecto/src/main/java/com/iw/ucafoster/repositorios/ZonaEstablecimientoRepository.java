package com.iw.ucafoster.repositorios;

import com.iw.ucafoster.entidades.Establecimiento;
import com.iw.ucafoster.entidades.ZonaEstablecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZonaEstablecimientoRepository extends JpaRepository<ZonaEstablecimiento, Integer> {

    List<ZonaEstablecimiento> findAll();
    List<ZonaEstablecimiento> findByNombreContaining(String nombre);
    List<ZonaEstablecimiento> findByEstablecimiento(Establecimiento establecimiento);
}
