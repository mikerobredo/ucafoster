package com.iw.ucafoster.services;

import com.iw.ucafoster.entidades.Establecimiento;
import com.iw.ucafoster.repositorios.EstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablecimientoService {

    @Autowired
    EstablecimientoRepository repo;

    public Establecimiento save(Establecimiento establecimiento) {
        return repo.save(establecimiento);
    }

    public void delete(Establecimiento establecimiento) {
        repo.delete(establecimiento);
    }

    public Establecimiento findOne(Integer id) {
        return repo.findOne(id);
    }

    public List<Establecimiento> findAll() {
        return repo.findAll();
    }

    public List<Establecimiento> findByNombreContaining(String nombre) {
        return repo.findByNombreContaining(nombre);
    }
}
