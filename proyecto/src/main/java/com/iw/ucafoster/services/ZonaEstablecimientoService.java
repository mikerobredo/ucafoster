package com.iw.ucafoster.services;

import com.iw.ucafoster.entidades.Establecimiento;
import com.iw.ucafoster.entidades.ZonaEstablecimiento;
import com.iw.ucafoster.repositorios.ZonaEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonaEstablecimientoService {

    @Autowired
    ZonaEstablecimientoRepository repo;

    public ZonaEstablecimiento save(ZonaEstablecimiento zonaEstablecimiento) {
        return repo.save(zonaEstablecimiento);
    }

    public void delete(ZonaEstablecimiento zonaEstablecimiento) {
        repo.delete(zonaEstablecimiento);
    }

    public ZonaEstablecimiento findOne(Integer id) {
        return repo.findOne(id);
    }

    public List<ZonaEstablecimiento> findAll() {
        return repo.findAll();
    }

    public List<ZonaEstablecimiento> findByNombreContaining(String nombre) {
        return repo.findByNombreContaining(nombre);
    }

    public List<ZonaEstablecimiento> findByEstablecimiento(Establecimiento establecimiento) {
        return repo.findByEstablecimiento(establecimiento);
    }
}
