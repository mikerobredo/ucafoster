package com.iw.ucafoster.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.iw.ucafoster.entidades.Empleado;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }
    
    public static String name() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication != null){
    		return authentication.getName();
    	} else {
    		return null;
    	}
    }
    
    public static Empleado getEmpleadoSesion() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication != null){
    		return (Empleado)authentication.getPrincipal();
    	} else {
    		return null;
    	}
    }
    
    public static Collection<? extends GrantedAuthority> roles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication != null ){
        	return authentication.getAuthorities();
        } else{
        	return null;
        }
    }

}
