package com.iw.ucafoster.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iw.ucafoster.entidades.Cliente;
import com.iw.ucafoster.repositorios.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
    ClienteRepository repo;

    public Cliente save(Cliente cliente) {
        return repo.save(cliente);
    }

    public void delete(Cliente cliente) {
        repo.delete(cliente);
    }
    
    public List<Cliente> findAll(){
    	return repo.findAll();
    }
    
    public Cliente findOne(Integer id){
    	return repo.findOne(id);
    }
    
    public List<Cliente> findByTelefonoContaining(String telefono){
    	return repo.findByTelefonoContaining(telefono);
    }
    
    public List<Cliente> findByNombreContaining(String nombre){
    	return repo.findByNombreContaining(nombre);
    }

	
}
