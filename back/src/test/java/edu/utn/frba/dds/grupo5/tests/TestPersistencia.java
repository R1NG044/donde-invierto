package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Metodologia;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;
import edu.utn.frba.dds.grupo5.util.Util;

public class TestPersistencia {
	private static List<Empresa> empresas;
	private static List<Indicador> indicadores;
	private static List<Cuenta> cuentas;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUp() throws IndicadorException, Exception {
		String empresasString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("empresas.json"));
		Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
		empresas = ((List<Empresa>)new Gson().fromJson(empresasString, listType));
		
		String indicadoresString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("indicadores-predefinidos.json"));
		listType = new TypeToken<ArrayList<Indicador>>(){}.getType();
		indicadores = ((List<Indicador>)new Gson().fromJson(indicadoresString, listType));
		
		String cuentaString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("cuentas.json"));
		listType = new TypeToken<ArrayList<Cuenta>>(){}.getType();
		cuentas = ((List<Cuenta>)new Gson().fromJson(cuentaString, listType));
	}
	
	@Test
	public void testEmpresa() throws Exception{
		for(Empresa emp: empresas){
			ServiceManager.getInstance().guardarEmpresa(emp);
		}
		
		Empresa snapchat = ServiceManager.getInstance().buscarEmpresa("Snapchat");
		
		assertEquals("Snapchat",snapchat.getNombre());
		assertEquals(Integer.valueOf(2011),snapchat.getAnioFundacion());
		assertEquals(2,snapchat.getPeriodos().size());
	}
	
	@Test
	public void testMetodologias() throws Exception, IndicadorException{
		Metodologia m1 = new Metodologia();
		m1.setNombre("m1");
		
		ServiceManager.getInstance().saveMetodologia(m1);
		
		Metodologia m2 = new Metodologia();
		m2.setNombre("m2");
		
		ServiceManager.getInstance().saveMetodologia(m2);
		
		assertEquals(2, ServiceManager.getInstance().getMetodologias().size());
		Metodologia m1DB = ServiceManager.getInstance().getMetodologiaByName("m1");
		Metodologia m2DB = ServiceManager.getInstance().getMetodologiaByName("m2");
		
		assertEquals("m1", m1DB.getNombre());
		assertEquals("m2", m2DB.getNombre());
	}
	
	@Test
	public void testIndicadores() throws Exception, IndicadorException{	
		ServiceManager.getInstance().guardarCuentas(cuentas);
		ServiceManager.getInstance().guardarIndicadores(indicadores);
		
		Indicador prueba = ServiceManager.getInstance().recuperarIndicador("I1");
		
		assertEquals(5, ServiceManager.getInstance().getIndicadores().size());
		assertEquals(1, prueba.getCuentas().size());
		assertEquals(0, prueba.getIndicadores().size());
		assertEquals("cuenta{CASH}*33", prueba.getExpression());
		assertEquals("CASH",prueba.getCuentas().stream().findFirst().get().getDescripcion());
	}
	
	@Test
	public void testCuentas() throws Exception, IndicadorException{
		List<Cuenta> pruebas = new ArrayList<>();
		Cuenta c1 = new Cuenta();
		c1.setDescripcion("cuenta1");
		
		Cuenta c2 = new Cuenta();
		c2.setDescripcion("cuenta2");
		
		pruebas.add(c1);
		pruebas.add(c2);
		
		ServiceManager.getInstance().guardarCuentas(pruebas);

		List<Cuenta> cuentasFromDB = ServiceManager.getInstance().getCuentas();
		
		assertEquals(2, cuentasFromDB.size());
		assertTrue(Util.map(cuentasFromDB, Cuenta::getDescripcion).containsAll(Util.map(pruebas, Cuenta::getDescripcion)) );
	}
	
	@After
	public void clearDatabase() throws Exception{
		ServiceManager.getInstance().clearRepo();
	}	
	
}