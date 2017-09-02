package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;


public class TestMetodologias {
	
	private List<Empresa> empresas;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws IndicadorException, Exception {
		String empresasJson = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("empresas.json"));
		
		Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
		empresas = ((List<Empresa>)new Gson().fromJson(empresasJson, listType));
		
		String cuentasString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("cuentas.json"));
		listType = new TypeToken<ArrayList<Cuenta>>(){}.getType();
		List<Cuenta> cuentas = ((List<Cuenta>)new Gson().fromJson(cuentasString, listType));
		
		ServiceManager.getInstance().guardarCuentas(cuentas);
		
		String indicadoresString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("indicadores-predefinidos.json"));
		listType = new TypeToken<ArrayList<Indicador>>(){}.getType();
		List<Indicador> indicadores = ((List<Indicador>)new Gson().fromJson(indicadoresString, listType));
		
		ServiceManager.getInstance().guardarIndicadores(indicadores);
	}
	
	@Test
	public void testBuffet() throws Exception{
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Buffet", empresas);
		 
		 assertTrue(result.size()==3);
		 assertEquals("Pepsico",result.get(0).getNombre());
		 assertEquals("Apple",result.get(1).getNombre());
		 assertEquals("Google",result.get(2).getNombre());
	} 
	@Test
	public void testConstante() throws Exception{
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Constante", empresas);
		 
		assertTrue(result.size()==3);
		 assertEquals("Google",result.get(0).getNombre());
		 assertEquals("Pepsico",result.get(1).getNombre());
		 assertEquals("Apple",result.get(2).getNombre());
		
}
	@Test
	public void testDebt() throws Exception{
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Debt", empresas);
		 assertTrue(result.size()==4);
		 assertEquals("Pepsico",result.get(0).getNombre());
		 assertEquals("Apple",result.get(1).getNombre());
		 assertEquals("Instagram",result.get(2).getNombre());
		 assertEquals("Google",result.get(3).getNombre());
		
		
}
	@Test
	public void testLastYear() throws Exception{
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("LastYear", empresas);
		 
		assertTrue(result.size()==4);
		 assertEquals("Pepsico",result.get(0).getNombre());
		 assertEquals("Apple",result.get(1).getNombre());
		 assertEquals("Google",result.get(2).getNombre());
		 assertEquals("Instagram",result.get(3).getNombre());
		
}
	
	@Test
	public void testHistorial() throws Exception{
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Historial", empresas);
		
		assertTrue(result.size()==2);
		assertEquals("Pepsico",result.get(0).getNombre());
		assertEquals("Google",result.get(1).getNombre());
	}
	
	@After
	public void clearDatabase() throws Exception{
		ServiceManager.getInstance().clearRepo();
	}
}