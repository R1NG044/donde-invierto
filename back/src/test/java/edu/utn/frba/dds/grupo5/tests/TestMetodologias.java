package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Empresa;
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
/*	@Test
	public void testConfiable() throws Exception{
		List<Empresa> result = ServiceManager.getInstance().evaluateMetodologia("Confiable", empresas);
		 
		assertTrue(result.size()==3);
		 assertEquals("",result.get(0).getNombre());
		 assertEquals("",result.get(1).getNombre());
		 assertEquals("",result.get(2).getNombre());
		
}*/
}