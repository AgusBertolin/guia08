package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia8.excepciones.MiExcepcion;

public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}; 
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;
	private Predicate<Tarea> puedeAsignarTarea;
	
	public Empleado(Integer nuevoCuil,String nuevoNombre,Double nuevoCostoHora, Tipo nuevoTipo) {
		this.cuil = nuevoCuil;
		this.nombre = nuevoNombre;
		this.tipo = nuevoTipo;
		this.costoHora = nuevoCostoHora;
		this.tareasAsignadas = new ArrayList<Tarea>();
		this.puedeAsignarTarea = ((a) -> a.getEmpleadoAsignado() == null);
		this.calculoPagoPorTarea = (t) -> {
			int auxTime = (Integer.valueOf(t.getFechaFin().getDayOfYear()) - Integer.valueOf(t.getFechaInicio().getDayOfYear()) ) * 4;
			Double auxCosto = 0d;
			if(auxTime < t.getDuracionEstimada()) {
				switch (t.getEmpleadoAsignado().getTipo()) {
					case  CONTRATADO:
						auxCosto = t.getEmpleadoAsignado().getCostoHora() * 1.3;
					case  EFECTIVO:
						auxCosto = t.getEmpleadoAsignado().getCostoHora() * 1.2;
				}
			}
			else {
				if(auxTime > t.getDuracionEstimada() && t.getEmpleadoAsignado().getTipo() == Tipo.CONTRATADO) {
					auxCosto = t.getEmpleadoAsignado().getCostoHora() * 0.75;
				}
				else {
					auxCosto = t.getEmpleadoAsignado().getCostoHora();
				}
			}
			return auxTime * auxCosto;
	};
	}

	public Double salario() {
		List<Tarea> aux = this.tareasAsignadas.stream().filter((a) -> !a.getFacturada()).collect(Collectors.toList());
		Double total = 0d;
		if(!aux.isEmpty()) {
			for(Tarea t: aux) {
				total+= this.costoTarea(t);
				if(t.getFechaFin() != null) t.setFacturada(true);
			}
		}
		return total;
	}
	
	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	public Double costoTarea(Tarea t) {
		return t.getFechaFin() != null? this.calculoPagoPorTarea.apply(t) : t.getDuracionEstimada() * this.costoHora;
	}
		
	public Boolean asignarTarea(Tarea t) throws MiExcepcion {
		if(this.puedeAsignarTarea.test(t)) {
			switch (this.tipo) {
				case  CONTRATADO:
					if(this.tareasAsignadas.stream().filter((a) -> !a.getFacturada()).count() < 5) {
						this.tareasAsignadas.add(t);
						return true;
					}
					else {
						System.out.println("El Empleado ya posee 5 Tareas");
					}
					break;
				case EFECTIVO:
					if(this.tareasAsignadas
							.stream()
							.mapToInt(Tarea::getDuracionEstimada)
							.reduce(0, (acum, ant) -> {return acum+ant;}) < 15) {
						this.tareasAsignadas.add(t);
						return true;
					}
					else {
						System.out.println("El Empleado ya tiene 15 horas acumuladas");
					}
			}
		}
		else {
			if(t.getEmpleadoAsignado() != null) throw (new MiExcepcion("La Tarea que se quiere asignar ya tiene un empleado asignado, seleccione otra Tarea"));
			if(t.getFacturada()) throw (new MiExcepcion("La Tarea que se quiere asignar ya fue finalizada, seleccione otra Tarea"));
		}
		return false;
	}
	
	public void comenzar(Integer idTarea) throws MiExcepcion {
		List<Tarea> aux = this.tareasAsignadas.parallelStream().filter(t -> t.getId() == idTarea).collect(Collectors.toList());
		if(!aux.isEmpty() && aux.get(0).getFechaInicio() == null && aux.get(0).getFechaFin() == null) {
			aux.get(0).setFechaInicio(LocalDateTime.now());
		}
		else {
			throw(new MiExcepcion("El empleado no tiene asignada esta tarea"));
		}
	}
	
	public void finalizar(Integer idTarea) throws MiExcepcion{
		List<Tarea> aux = this.tareasAsignadas.parallelStream().filter(t -> t.getId() == idTarea).collect(Collectors.toList());
		if(!aux.isEmpty() && aux.get(0).getFechaInicio() != null) {
			aux.get(0).setFechaFin(LocalDateTime.now());
		}
		else {
			if(aux.isEmpty()) throw(new MiExcepcion("El empleado no tiene asignada esta tarea"));
			if(aux.get(0).getFechaInicio() == null) throw(new MiExcepcion("El empleado no ha comenzado esta tarea"));
		}
	}

	public void comenzar(Integer idTarea,String fecha) throws MiExcepcion{ 
		List<Tarea> aux = this.tareasAsignadas.parallelStream().filter(t -> t.getId() == idTarea).collect(Collectors.toList());
		if(!aux.isEmpty() && aux.get(0).getFechaInicio() == null && aux.get(0).getFechaFin() == null) {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			LocalDateTime nuevaFecha = LocalDateTime.parse(fecha, formato);
			aux.get(0).setFechaInicio(nuevaFecha);
		}
		else {
			throw(new MiExcepcion("El empleado no tiene asignada esta tarea"));
		}
	}
	
	public void finalizar(Integer idTarea,String fecha) throws MiExcepcion{
		List<Tarea> aux = this.tareasAsignadas.parallelStream().filter(t -> t.getId() == idTarea).collect(Collectors.toList());
		if(!aux.isEmpty() && aux.get(0).getFechaInicio() != null) {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			LocalDateTime nuevaFecha = LocalDateTime.parse(fecha, formato);
			aux.get(0).setFechaFin(nuevaFecha);
		}
		else {
			if(aux.isEmpty()) throw(new MiExcepcion("El empleado no tiene asignada esta tarea"));
			if(aux.get(0).getFechaInicio() == null) throw(new MiExcepcion("El empleado no ha comenzado esta tarea"));
		}
	}
	
	public Double getCostoHora() {
		return this.costoHora;
	}
	
	public Tipo getTipo() {
		return this.tipo;
	}

	public Integer getCuil() {
		return this.cuil;
	}
	
	public List<Tarea> getTareas(){
		return this.tareasAsignadas;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
