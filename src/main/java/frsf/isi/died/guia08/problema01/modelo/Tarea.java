package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;

import frsf.isi.died.guia8.excepciones.MiExcepcion;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	
	public Tarea(Integer idTarea,String desc,Integer duracEstimada) {
		this.id = idTarea;
		this.descripcion = desc;
		this.duracionEstimada = duracEstimada;
		this.facturada = false;
	}
	
	public void asignarEmpleado(Empleado e) throws MiExcepcion {
		if(this.empleadoAsignado != null) throw(new MiExcepcion("La Tarea ya tiene un empleado asignado"));
		if(this.fechaFin != null) throw(new MiExcepcion("La Tarea ya fue Finalizada"));
		if(e.asignarTarea(this)) {
			this.empleadoAsignado = e;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	
	public String asCsv() {
		return this.id+ ";\""+ this.descripcion+"\";"+this.duracionEstimada;
	}
	
	public void setEmpleado(Empleado e) {
		this.empleadoAsignado = e;
	}
}
