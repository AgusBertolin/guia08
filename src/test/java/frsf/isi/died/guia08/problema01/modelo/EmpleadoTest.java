package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia8.excepciones.MiExcepcion;

public class EmpleadoTest {

	@Test
	public void testSalario() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.EFECTIVO);
		try{
			emp.asignarTarea(new Tarea( 3, "Programar en Java", 10));
			emp.asignarTarea(new Tarea( 1, "Programar en C++", 2));
			emp.asignarTarea(new Tarea( 4, "Testear Programa", 5));
			emp.comenzar(3, "25-05-2020 10:40");
			emp.comenzar(4);
			emp.comenzar(1, "20-05-2020 09:50");
			emp.finalizar(3, "28-05-2020 13:00");
			emp.finalizar(1, "28-05-2020 10:00");
			emp.getTareas().forEach(t -> t.setEmpleado(emp));
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		Double aux = emp.salario();
		assertTrue(aux > 0);
	}

	@Test
	public void testCostoTarea() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		try{
			emp.asignarTarea(new Tarea( 3, "Programar en Java", 12));
			emp.comenzar(3, "25-05-2020 10:40");
			emp.finalizar(3, "28-05-2020 13:00");
			emp.getTareas().get(0).setEmpleado(emp);
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertTrue(emp.costoTarea(emp.getTareas().get(0)) == 672.0);
	}

	@Test
	public void testAsignarTarea() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		try{
			emp.asignarTarea(new Tarea( 3, "Programar en Java", 10));
			emp.asignarTarea(new Tarea( 1, "Programar en C++", 2));
			emp.asignarTarea(new Tarea(  4, "Testear Programa", 5));
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertTrue(emp.getTareas().size() == 3);
	}

	@Test
	public void testComenzarInteger() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		try{
			emp.asignarTarea(new Tarea( 3, "Programar en Java", 10));
			emp.comenzar(3);
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertTrue(emp.getTareas().get(0).getFechaInicio() != null);
	}

	@Test
	public void testFinalizarInteger() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		try{
			emp.asignarTarea(new Tarea( 3, "Programar en Java", 10));
			emp.comenzar(3, "25-05-2020 10:40");
			emp.finalizar(3);
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertTrue(emp.getTareas().get(0).getFechaFin() != null);
	}

	@Test
	public void testComenzarIntegerString() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		try{
			emp.asignarTarea(new Tarea( 3, "Programar en Java", 10));
			emp.comenzar(3, "25-05-2020 10:40");
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertTrue(emp.getTareas().get(0).getFechaInicio() != null);
	}

	@Test
	public void testFinalizarIntegerString() {
		Empleado emp = new Empleado(4, "Lucia", 56.0, Tipo.CONTRATADO);
		try{
			emp.asignarTarea(new Tarea( 3, "Programar en Java", 10));
			emp.comenzar(3, "25-05-2020 10:40");
			emp.finalizar(3, "28-05-2020 13:00");
		}
		catch (MiExcepcion e) {
			System.out.println(e.getMessage());
		}
		assertTrue(emp.getTareas().get(0).getFechaFin() != null);
	}

}
