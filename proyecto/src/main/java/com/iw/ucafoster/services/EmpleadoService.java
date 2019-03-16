package com.iw.ucafoster.services;

import com.iw.ucafoster.entidades.Empleado;
import com.iw.ucafoster.repositorios.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService implements UserDetailsService {

    @Autowired
    EmpleadoRepository repo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Empleado loadUserByUsername(String username) throws UsernameNotFoundException {

        Empleado user = repo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return user;
    }

    public Empleado save(Empleado user) {
        if(user.getId() != null) {
            Empleado before = repo.findOne(user.getId());
            if(!user.getPassword().equals(before.getPassword()))
                user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else
            user.setPassword(passwordEncoder.encode(user.getPassword() != null ? user.getPassword() : "default"));
        return repo.save(user);
    }

    public void delete(Empleado empleado) {

        repo.delete(empleado);
    }

    public List<Empleado> findAll() {

        return repo.findAll();
    }

    public Empleado findOne(Integer id) {

        return repo.findOne(id);
    }

    public List<Empleado> findByDniContaining(String dni) {

        return repo.findByDniContaining(dni);
    }
}
