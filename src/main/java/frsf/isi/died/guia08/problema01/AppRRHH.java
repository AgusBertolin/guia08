package frsf.isi.died.guia08.problema01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia8.excepciones.MiExcepcion;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public AppRRHH() {
		this.empleados = new ArrayList<Empleado>();
	}
	
	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		this.empleados.add(new Empleado(cuil, nombre, costoHora, Tipo.CONTRATADO));
		// crear un empleado
		// agregarlo a la lista
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		this.empleados.add(new Empleado(cuil, nombre, costoHora, Tipo.EFECTIVO));
		// crear un empleado
		// agregarlo a la lista		
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) {
		try{
			Tarea aux = new Tarea(idTarea, descripcion, duracionEstimada);
			aux.asignarEmpleado(this.buscarEmpleado(e -> e.getCuil() == cuil).get());
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		catch (NoSuchElementException e) {
			System.out.println("No se encontró un empleado con el cuil: " + cuil);
		}
		// crear un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista		
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) {
		try{
			this.buscarEmpleado(e -> e.getCuil() == cuil).get().comenzar(idTarea);
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		catch (NoSuchElementException e) {
			System.out.println("No se encontró un empleado con el cuil: " + cuil);
		}
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) {
		try{
			this.buscarEmpleado(e -> e.getCuil() == cuil).get().finalizar(idTarea);
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		catch (NoSuchElementException e) {
			System.out.println("No se encontró un empleado con el cuil: " + cuil);
		}
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) {
		try(Reader fileReader = new FileReader(nombreArchivo)) {
			try(BufferedReader in = new BufferedReader(fileReader)){
				String linea = null;
				while((linea = in.readLine())!=null) {
					String[] fila = linea.split(";");
					this.agregarEmpleadoContratado(Integer.valueOf(fila[0]), fila[1], Double.valueOf(fila[2]));
					}
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) {
		try(Reader fileReader = new FileReader(nombreArchivo)) {
			try(BufferedReader in = new BufferedReader(fileReader)){
				String linea = null;
				while((linea = in.readLine())!=null) {
					String[] fila = linea.split(";");
					this.agregarEmpleadoEfectivo(Integer.valueOf(fila[0]), fila[1], Double.valueOf(fila[2]));
					}
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}

	public void cargarTareasCSV(String nombreArchivo) {
		try(Reader fileReader = new FileReader(nombreArchivo)) {
			try(BufferedReader in = new BufferedReader(fileReader)){
				String linea = null;
				while((linea = in.readLine())!=null) {
					String[] fila = linea.split(";");
					this.asignarTarea(Integer.valueOf(fila[3]), Integer.valueOf(fila[0]), fila[1], Integer.valueOf(fila[2]));
					}
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void guardarTareasTerminadasCSV() {
		List<Tarea> tareas = this.empleados.stream().map(e -> e.getTareas()).flatMap(x -> x.stream()).filter(t -> !t.getFacturada() && t.getFechaFin() != null).collect(Collectors.toList());
		if(!tareas.isEmpty()) {
			try(Writer fileWriter = new FileWriter("tareas.csv",true)) {
				try(BufferedWriter out = new BufferedWriter(fileWriter)){
					for(Tarea t: tareas) {
						out.write(t.asCsv(t.getEmpleadoAsignado())+ System.getProperty("line.separator"));
					}
				} 
				catch(IOException e) { 
					System.out.println(e.getMessage());
					e.printStackTrace(); 
				}
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(); 
			}
		}
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
	
	public List<Empleado> getEmpleados(){
		return this.empleados;
	}
}
