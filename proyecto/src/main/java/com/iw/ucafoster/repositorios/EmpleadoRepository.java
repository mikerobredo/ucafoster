package com.iw.ucafoster.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iw.ucafoster.entidades.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    List<Empleado> findAll();
    List<Empleado> findByDniContaining(String dni);
    Empleado findByUsername(String username);
}