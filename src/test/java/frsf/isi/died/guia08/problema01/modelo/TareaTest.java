package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia8.excepciones.MiExcepcion;

public class TareaTest {

	@Test
	public void asignarEmpleadoTest() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		Tarea t = new Tarea( 3, "Programar en Java", 12);
		try{
			t.asignarEmpleado(emp);
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertTrue(t.getEmpleadoAsignado() == emp);
	}

	@Test
	public void asignarEmpleadoTestEmpleadoYaAsignado() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		Tarea t = new Tarea( 3, "Programar en Java", 12);
		try{
			t.asignarEmpleado(new Empleado(0, "Mario", 23.5, Tipo.EFECTIVO));
			t.asignarEmpleado(emp);
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertFalse(t.getEmpleadoAsignado() == emp);
	}
	
	@Test
	public void asignarEmpleadoTestTareaFinalizada() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		Tarea t = new Tarea( 3, "Programar en Java", 12);
		t.setFechaFin(LocalDateTime.now());
		try{
			t.asignarEmpleado(emp);
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertFalse(t.getEmpleadoAsignado() == emp);
	}
}
