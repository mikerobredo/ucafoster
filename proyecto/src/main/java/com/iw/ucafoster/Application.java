package com.iw.ucafoster;

import com.iw.ucafoster.services.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iw.ucafoster.entidades.*;
import com.iw.ucafoster.repositorios.*;
import com.iw.ucafoster.security.VaadinSessionSecurityContextHolderStrategy;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(IngredienteRepository ingRepository, ClienteRepository cliRepo,
									  FamiliaProductoRepository familiaRepo, EstablecimientoRepository estRepo,
									  EmpleadoService empService, ProductoService prodRepo, TipoPedidoService tpRepo,
									  TipoPagoService tpagoRepo, ZonaEstablecimientoService zonaService,
									  MesaService mesaService) {
		return (args) -> {

			// Ingredientes
			ingRepository.save(new Ingrediente("Jamon cocido", "Jamon york de toda la vida"));
			ingRepository.save(new Ingrediente("Queso gouda", "Loncha de queso (naranja)"));
			ingRepository.save(new Ingrediente("Bacon", "Panceta"));
			ingRepository.save(new Ingrediente("Tomate", "Rodaja de tomate"));
			ingRepository.save(new Ingrediente("Lechuga", "Lechuga fresca"));
			ingRepository.save(new Ingrediente("Cebolla", "Rodaja de cebolla"));

			// Clientes
			cliRepo.save(new Cliente("Pepe", "Perez", "11122233H", "C/ Palotes 13", "956363636", "pepe@mail.com"));
			cliRepo.save(new Cliente("Juan", "Garcia", "44455566Y", "C/ Luna 12", "956373737", "juan@mail.com"));
			cliRepo.save(new Cliente("Carlos", "Gomez", "77788899J", "C/ Sol 6", "956383838", "carlos@mail.com"));
			cliRepo.save(new Cliente("Manolo", "Lamas", "99911100K", "C/ Suelo 3", "956393939", "manolo@mail.com"));

			// Familias productos
			familiaRepo.save(new FamiliaProducto("Refrescos", "Bebidas y refrescos", 12.0f));
			familiaRepo.save(new FamiliaProducto("Pizzas", "Pizzas", 18.0f));
			familiaRepo.save(new FamiliaProducto("Hamburguesas", "Hamburguesas de pollo y vacuno", 18.0f));
			familiaRepo.save(new FamiliaProducto("Complementos", "Complementos de menus", 16.0f));

			// Establecimientos
			estRepo.save(new Establecimiento("Puerta del sol", "Plaza de puerta del sol", "627121212", "uca-foster@puertasol.com"));
			estRepo.save(new Establecimiento("Genovés", "Parque genovés", "627131313", "uca-foster@genoves.com"));
			estRepo.save(new Establecimiento("San antonio", "Plaza de san antonio", "627141414", "uca-foster@santantonio.com"));
			estRepo.save(new Establecimiento("Caleta", "Playa de la Caleta", "627151515", "uca-foster@caleta.com"));
			
			// Productos
			Producto p1 = new Producto("Burger 1", "Descripcion Burger 1", 5.50f),
					 p2 = new Producto("Burger 2", "Descripcion Burger 2", 4.70f),
					 p3 = new Producto("Burger 3", "Descripcion Burger 3", 3.50f),
					 p6 = new Producto("Burger 4", "Descripcion Burger 3", 3.50f),
					 p7 = new Producto("Burger 5", "Descripcion Burger 3", 3.50f),
					 p8 = new Producto("Burger 6", "Descripcion Burger 3", 3.50f),
					 p9 = new Producto("Burger 7", "Descripcion Burger 3", 3.50f),
					 p10 = new Producto("Burger 8", "Descripcion Burger 3", 3.50f),
					 p4 = new Producto("Cola", "Fresquita", 1.20f),
					 p5 = new Producto("Kas", "Fresquita", 1.20f);
			
			p1.setFamilia(familiaRepo.findOne(3)); p2.setFamilia(familiaRepo.findOne(3)); p3.setFamilia(familiaRepo.findOne(3));
			p6.setFamilia(familiaRepo.findOne(3)); p7.setFamilia(familiaRepo.findOne(3)); p8.setFamilia(familiaRepo.findOne(3));
			p9.setFamilia(familiaRepo.findOne(3)); p10.setFamilia(familiaRepo.findOne(3));
			p4.setFamilia(familiaRepo.findOne(1)); p5.setFamilia(familiaRepo.findOne(1));
			
			prodRepo.save(p1); prodRepo.save(p2); prodRepo.save(p3); prodRepo.save(p4); prodRepo.save(p5);
			prodRepo.save(p6); prodRepo.save(p7); prodRepo.save(p8); prodRepo.save(p9); prodRepo.save(p10);
			
			Establecimiento caleta = new Establecimiento("Caleta", "Playa de la Caleta", "627151515", "uca-foster@caleta.com");
			estRepo.save(caleta);

			Establecimiento gerente = new Establecimiento("Gerente", "Oficina del gerente", "3432324", "uca-foster@gerente.com");
			estRepo.save(gerente);
			
			//Empleados
			Empleado Alberto = new Empleado("Alberto", "Gonzalez", "78998564D", "albertito@ucafoster.com",
					"689785210", "Parque de los patos", "root", "default", "ROL-GERENTE");
			Alberto.setEstablecimiento(gerente);
			empService.save(Alberto);

			Empleado Ramon = new Empleado("Ramon", "Velázquez", "3243223324D", "ramon@ucafoster.com",
					"3232423423", "Camino real", "empleado", "default", "ROL-EMPLEADO");
			Ramon.setEstablecimiento(caleta);
			empService.save(Ramon);

			Empleado Xabi = new Empleado("Xabi", "Hernandez", "23432324H", "xami@ucafoster.com",
					"34324324324", "Camino siegaso", "encargado", "default", "ROL-ENCARGADO");
			Xabi.setEstablecimiento(caleta);
			empService.save(Xabi);
			
			// Tipos de pedido
			tpRepo.save(new TipoPedido("Local", "Pedido para tomar en el restaurante"));
			tpRepo.save(new TipoPedido("Para llevar", "Pedido para llevar pedido en el restaurante"));
			tpRepo.save(new TipoPedido("Telefonico", "Pedido para que sea recogido en el restaurante"));
			
			// Tipos de pago
			tpagoRepo.save(new TipoPago("Efectivo", "Metalico"));
			tpagoRepo.save(new TipoPago("Tarjeta", "VISA/MasterCard"));
			tpagoRepo.save(new TipoPago("PayPal", "Electronico"));

			// Zonas de la caleta
			ZonaEstablecimiento terraza1 = new ZonaEstablecimiento("Terraza 1", "Terraza ala derecha");
			terraza1.setEstablecimiento(caleta);

			ZonaEstablecimiento terraza2 = new ZonaEstablecimiento("Terraza 2", "Terraza ala izquierda");
			terraza2.setEstablecimiento(caleta);

			ZonaEstablecimiento salon = new ZonaEstablecimiento("Salon", "Salon principal");
			salon.setEstablecimiento(caleta);

			zonaService.save(terraza1);
			zonaService.save(terraza2);
			zonaService.save(salon);

			// Mesas de algunas zonas
			Mesa mesa1 = new Mesa("TD1");
			Mesa mesa2 = new Mesa("TD2");

			mesa1.setZonaEstablecimiento(terraza1);
			mesa2.setZonaEstablecimiento(terraza1);

			Mesa mesa3 = new Mesa("TI1");
			Mesa mesa4 = new Mesa("TI2");

			mesa3.setZonaEstablecimiento(terraza2);
			mesa4.setZonaEstablecimiento(terraza2);

			Mesa mesa5 = new Mesa("S1");
			Mesa mesa6 = new Mesa("S2");
			Mesa mesa7 = new Mesa("S3");

			mesa5.setZonaEstablecimiento(salon);
			mesa6.setZonaEstablecimiento(salon);
			mesa7.setZonaEstablecimiento(salon);

			mesaService.save(mesa1); mesaService.save(mesa2); mesaService.save(mesa3);
			mesaService.save(mesa4); mesaService.save(mesa5); mesaService.save(mesa6);
			mesaService.save(mesa7);
		};
	}
	
	@Configuration
	@EnableGlobalMethodSecurity(securedEnabled = true)
	public static class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

		@Autowired
		private UserDetailsService userDetailsService;

		@Bean
		public PasswordEncoder encoder() {
			return new BCryptPasswordEncoder(11);
		}

		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService);
			authProvider.setPasswordEncoder(encoder());
			return authProvider;
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth.authenticationProvider(authenticationProvider());

			// auth
			// .inMemoryAuthentication()
			// .withUser("admin").password("p").roles("ADMIN", "MANAGER",
			// "USER")
			// .and()
			// .withUser("manager").password("p").roles("MANAGER", "USER")
			// .and()
			// .withUser("user").password("p").roles("USER");
			
		}

		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return authenticationManager();
		}

		static {
			// Use a custom SecurityContextHolderStrategy
			SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
		}
	}
}