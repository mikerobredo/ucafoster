package com.iw.ucafoster.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iw.ucafoster.entidades.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer>{

    List<Ingrediente> findAll();
    List<Ingrediente> findByNombreContaining(String nombre);
}