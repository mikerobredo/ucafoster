package com.iw.ucafoster.repositorios;

import com.iw.ucafoster.entidades.FamiliaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamiliaProductoRepository extends JpaRepository<FamiliaProducto, Integer> {

    List<FamiliaProducto> findAll();

    List<FamiliaProducto> findByNombreContaining(String nombre);
}
