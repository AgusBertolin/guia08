package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.AppRRHH;

public class AppRRHHTest {

	@Test
	public void testAgregarEmpleadoContratado() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoContratado(0, "Mario", 23.5);
		rrhhTest.agregarEmpleadoContratado(4, "Lucia", 56.1);
		rrhhTest.agregarEmpleadoContratado(6, "Ana", 63.12);
		rrhhTest.agregarEmpleadoContratado(1, "Raul", 46.8);
		assertTrue(rrhhTest.getEmpleados().size() == 4);
	}

	@Test
	public void testAgregarEmpleadoEfectivo() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoEfectivo(0, "Mario", 23.5);
		rrhhTest.agregarEmpleadoEfectivo(4, "Lucia", 56.1);
		rrhhTest.agregarEmpleadoEfectivo(6, "Ana", 63.12);
		rrhhTest.agregarEmpleadoEfectivo(1, "Raul", 46.8);
		assertTrue(rrhhTest.getEmpleados().size() == 4);
	}

	@Test
	public void testAsignarTarea() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoEfectivo(0, "Mario", 23.5);
		rrhhTest.asignarTarea(0, 1, "Barrer", 2);
		rrhhTest.asignarTarea(0, 3, "Programar en Java", 10);
		assertTrue(rrhhTest.getEmpleados().get(0).getTareas().size() == 2);
	}
	
	@Test
	public void testAsignarTareaEmpleadoInexistente() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoEfectivo(6, "Ana", 63.12);
		rrhhTest.asignarTarea(0, 1, "Barrer", 2);
		assertTrue(rrhhTest.getEmpleados().stream().map(e -> e.getTareas()).flatMap(x -> x.stream()).count() == 0);
	}

	@Test
	public void testEmpezarTarea() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoEfectivo(0, "Mario", 23.5);
		rrhhTest.asignarTarea(0, 3, "Programar en Java", 10);
		rrhhTest.empezarTarea(0, 3);
		assertTrue(rrhhTest.getEmpleados().get(0).getTareas().get(0).getFechaInicio() != null);
	}

	@Test
	public void testTerminarTarea() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoEfectivo(0, "Mario", 23.5);
		rrhhTest.asignarTarea(0, 3, "Programar en Java", 10);
		rrhhTest.empezarTarea(0, 3);
		rrhhTest.terminarTarea(0, 3);
		assertTrue(rrhhTest.getEmpleados().get(0).getTareas().get(0).getFechaFin() != null);
	}
	
	@Test
	public void testTerminarTareaSinEmpezar() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoEfectivo(0, "Mario", 23.5);
		rrhhTest.asignarTarea(0, 3, "Programar en Java", 10);
		rrhhTest.terminarTarea(0, 3);
		assertFalse(rrhhTest.getEmpleados().get(0).getTareas().get(0).getFechaFin() != null);
	}

	@Test
	public void testCargarEmpleadosContratadosCSV() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.cargarEmpleadosContratadosCSV("EmpleadosEjemplo.csv");
		assertTrue(rrhhTest.getEmpleados().size() > 0);
	}

	@Test
	public void testCargarEmpleadosEfectivosCSV() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.cargarEmpleadosEfectivosCSV("EmpleadosEjemplo.csv");
		assertTrue(rrhhTest.getEmpleados().size() > 0);
	}

	@Test
	public void testCargarTareasCSV() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoEfectivo(0, "Mario", 23.5);
		rrhhTest.agregarEmpleadoContratado(4, "Lucia", 56.0);
		rrhhTest.cargarTareasCSV("TareasEjemplo.csv");
		assertTrue(rrhhTest.getEmpleados().stream().map(e -> e.getTareas()).flatMap(x -> x.stream()).count() > 0);
	}

	@Test
	public void testFacturar() {
		AppRRHH rrhhTest = new AppRRHH();
		rrhhTest.agregarEmpleadoEfectivo(0, "Mario", 23.5);
		rrhhTest.agregarEmpleadoContratado(4, "Lucia", 56.0);
		
		rrhhTest.asignarTarea(0, 3, "Programar en Java", 10);
		rrhhTest.asignarTarea(0, 1, "Programar en C++", 5);
		rrhhTest.asignarTarea(4, 4, "Testear Programa", 5);
		
		rrhhTest.empezarTarea(0, 3);
		rrhhTest.empezarTarea(0, 1);
		rrhhTest.empezarTarea(4, 4);
		
		rrhhTest.terminarTarea(4, 4);
		rrhhTest.terminarTarea(0, 3);
		
		Double aux = rrhhTest.facturar();
		assertTrue(aux != 0);
	}

}
