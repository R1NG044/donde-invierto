package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

public class TestMetodologias {

	private List<Empresa> empresas;
	
	@Rule 
	public RuleStartupDB testDb = new RuleStartupDB();
	
	@Before
	public void setUp() throws IndicadorException, Exception {
		empresas = ServiceManager.getInstance().getEmpresas();
	}

	@Test
	public void testBuffet() throws Exception {
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Buffet", empresas);

		assertTrue(result.size() == 3);
		assertEquals("Pepsico", result.get(0).getNombre());
		assertEquals("Apple", result.get(1).getNombre());
		assertEquals("Google", result.get(2).getNombre());

		System.out.println("***********TEST BUFFET***********");
		result.forEach(e -> System.out.println(e.toString()));
	}

	@Test
	public void testConstante() throws Exception {
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Constante", empresas);

		assertTrue(result.size() == 3);
		assertEquals("Google", result.get(0).getNombre());
		assertEquals("Pepsico", result.get(1).getNombre());
		assertEquals("Apple", result.get(2).getNombre());

		System.out.println("***********TEST CONSTANTE***********");
		result.forEach(e -> System.out.println(e.toString()));
	}

	@Test
	public void testDebt() throws Exception {
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Debt", empresas);
		assertTrue(result.size() == 4);
		assertEquals("Pepsico", result.get(0).getNombre());
		assertEquals("Apple", result.get(1).getNombre());
		assertEquals("Instagram", result.get(2).getNombre());
		assertEquals("Google", result.get(3).getNombre());

		System.out.println("***********TEST DEBT***********");
		result.forEach(e -> System.out.println(e.toString()));
	}

	@Test
	public void testLastYear() throws Exception {
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("LastYear", empresas);

		assertTrue(result.size() == 4);
		assertEquals("Pepsico", result.get(0).getNombre());
		assertEquals("Apple", result.get(1).getNombre());
		assertEquals("Google", result.get(2).getNombre());
		assertEquals("Instagram", result.get(3).getNombre());

		System.out.println("***********TEST LAST YEAR***********");
		result.forEach(e -> System.out.println(e.toString()));
	}

	@Test
	public void testHistorial() throws Exception {
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Historial", empresas);

		assertTrue(result.size() == 2);
		assertEquals("Pepsico", result.get(0).getNombre());
		assertEquals("Google", result.get(1).getNombre());

		System.out.println("***********TEST HISTORIAL***********");
		result.forEach(e -> System.out.println(e.toString()));
	}

	@After
	public void clearDatabase() throws Exception {
		ServiceManager.getInstance().clearRepo();
	}
}