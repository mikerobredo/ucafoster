package com.iw.ucafoster.security;

import org.springframework.stereotype.Component;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;

/**
 * This demonstrates how you can control access to views.
 */
@Component
public class SampleViewAccessControl implements ViewAccessControl {

    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
    	

    	if(SecurityUtils.hasRole("ROL-GERENTE")){
    		return true;
    	} else if (beanName.equals("establecimientosView")) {
            return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("establecimientoEditor")) {
    		return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("empleadosView")) {
            return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("empleadosEditor")) {
    		return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("clientesView")) {
            return (SecurityUtils.hasRole("ROL-ENCARGADO") || SecurityUtils.hasRole("ROL-EMPLEADO"));
    	} else if (beanName.equals("clientesEditor")) {
    		return (SecurityUtils.hasRole("ROL-ENCARGADO") || SecurityUtils.hasRole("ROL-EMPLEADO"));
    	} else if (beanName.equals("familiasView")) {
            return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("familiaEditor")) {
    		return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("ingredientesView")) {
            return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("ingredienteEditor")) {
    		return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("productosView")) {
            return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("productoEditor")) {
    		return SecurityUtils.hasRole("ROL-GERENTE");
    	} else if (beanName.equals("cajasView")) {
			return (SecurityUtils.hasRole("ROL-ENCARGADO"));
		} else if (beanName.equals("zonasView")) {
			return SecurityUtils.hasRole("ROL-ENCARGADO");
		} else if (beanName.equals("zonasEditor")) {
			return SecurityUtils.hasRole("ROL-ENCARGADO");
		} else if (beanName.equals("mesasView")) {
			return SecurityUtils.hasRole("ROL-ENCARGADO") || SecurityUtils.hasRole("ROL-EMPLEADO");
		} else if (beanName.equals("mesasEditor")) {
			return SecurityUtils.hasRole("ROL-ENCARGADO") || SecurityUtils.hasRole("ROL-EMPLEADO");
    	} else if (beanName.equals("pedidosEditor")) {
    		return (SecurityUtils.hasRole("ROL-ENCARGADO") || SecurityUtils.hasRole("ROL-EMPLEADO"));
    	} else if (beanName.equals("pedidosView")) {
    		return (SecurityUtils.hasRole("ROL-ENCARGADO") || SecurityUtils.hasRole("ROL-EMPLEADO"));
    	} else {
        	return false;
        }
    }
}
