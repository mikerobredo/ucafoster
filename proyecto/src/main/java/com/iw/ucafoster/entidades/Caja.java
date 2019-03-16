package com.iw.ucafoster.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cajas")
public class Caja {
	// ATRIBUTOS
		// ---------
		
		@Id
		@GeneratedValue
		private Integer id;
		
		@Temporal(TemporalType.TIMESTAMP)
		private Date fechainicio,fechafin;
		
		private Float totalefectivo, totalfacturado;
		
		public Caja(Integer id, Date fechainicio,Date fechafin,  Float totalefectivo, Float totalfacturado) {
			this.id = id;
			this.fechainicio = fechainicio;
			this.fechafin = fechafin;
			this.totalefectivo = totalefectivo;
			this.totalfacturado = totalfacturado;
		}
		//observadores
		public Integer getId() { return id; }
		public Date getfechainicio() { return fechainicio; }
		public Date getfechafin() { return fechafin; }
		public Float gettotalefectivo() { return totalefectivo; }
		public Float gettotalfacturado() { return totalfacturado; }
		
		//modificadores
		
		
		public void setId(Integer id) { this.id = id; }
		public void setfechainicio(Date fechainicio) { this.fechainicio = fechainicio; }
		public void setfechafin(Date fechafin) { this.fechafin = fechafin; }
		public void settotalefectivo(Float totalefectivo) { this.totalefectivo = totalefectivo; }
		public void settotalfacturado(Float totalfacturado) { this.totalfacturado = totalfacturado; }
		
		@Override
		public String toString() {
			return Integer.toString(id) + " >> " + fechainicio + ", " + fechafin + " | " + totalefectivo + " | " + totalfacturado;
		}

}
