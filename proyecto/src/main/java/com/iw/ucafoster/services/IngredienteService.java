package com.iw.ucafoster.services;

import com.iw.ucafoster.entidades.Ingrediente;
import com.iw.ucafoster.repositorios.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    IngredienteRepository repo;

    public Ingrediente save(Ingrediente ingrediente) {
        return repo.save(ingrediente);
    }

    public void delete(Ingrediente ingrediente) {
        repo.delete(ingrediente);
    }

    public Ingrediente findOne(Integer id) {
        return repo.findOne(id);
    }

    public List<Ingrediente> findAll() {
        return repo.findAll();
    }

    public List<Ingrediente> findByNombreContaining(String nombre) {
        return repo.findByNombreContaining(nombre);
    }
}
