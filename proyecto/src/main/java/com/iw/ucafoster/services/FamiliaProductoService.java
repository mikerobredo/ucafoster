package com.iw.ucafoster.services;

import com.iw.ucafoster.entidades.FamiliaProducto;
import com.iw.ucafoster.repositorios.FamiliaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamiliaProductoService {

    @Autowired
    FamiliaProductoRepository repo;

    public FamiliaProducto save(FamiliaProducto familiaProducto) {
        return repo.save(familiaProducto);
    }

    public void delete(FamiliaProducto familiaProducto) {
        repo.delete(familiaProducto);
    }

    public FamiliaProducto findOne(Integer id) {
        return repo.findOne(id);
    }

    public List<FamiliaProducto> findAll() {
        return repo.findAll();
    }

    public List<FamiliaProducto> findByNombreContaining(String nombre) {
        return repo.findByNombreContaining(nombre);
    }
}
