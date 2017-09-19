package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

public class TestIndicadores {
	
	private static String indicadoresUsuario;
	private static Empresa empresa;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUp() throws IndicadorException, Exception {
		ServiceManager.getInstance().clearRepo();
		indicadoresUsuario = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("indicadores-usuario.json"));
		
		String empresas = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("empresas.json"));
		
		Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
		empresa = ((List<Empresa>)new Gson().fromJson(empresas, listType)).get(0);
		
		String cuentasString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("cuentas.json"));
		listType = new TypeToken<ArrayList<Cuenta>>(){}.getType();
		List<Cuenta> cuentas = ((List<Cuenta>)new Gson().fromJson(cuentasString, listType));
		
		ServiceManager.getInstance().guardarCuentas(cuentas);
	}
	
	@Test
	public void testCargaIndicadoresUsuario() throws Exception, IndicadorException{
		Type listType = new TypeToken<ArrayList<Indicador>>(){}.getType();
		List<Indicador> indicadores = new Gson().fromJson(indicadoresUsuario, listType);
		
		assertEquals(4, indicadores.size());
		
		assertEquals("indicador1", indicadores.get(0).getNombre());
		assertEquals("indicador2", indicadores.get(1).getNombre());
		assertEquals("indicador3", indicadores.get(2).getNombre());
		assertEquals("indicador4", indicadores.get(3).getNombre());
		
		assertEquals("cuenta{EBITDA}*2+cuenta{CASH}", indicadores.get(0).getExpression());
		assertEquals("(cuenta{EBITDA}*2+indicador{indicador1})/2", indicadores.get(1).getExpression());
		assertEquals("indicador{indicador2}+cuenta{CASH}", indicadores.get(2).getExpression());
		assertEquals("cuenta{EBITDA}+10", indicadores.get(3).getExpression());
	}	
	
	@Test
	public void guardarRecuperarIndicador() throws Exception, IndicadorException{
		Type listType = new TypeToken<ArrayList<Indicador>>(){}.getType();
		ServiceManager.getInstance().guardarIndicadores(new Gson().fromJson(indicadoresUsuario, listType));
		Indicador ind = ServiceManager.getInstance().recuperarIndicador("indicador1");
		
		assertEquals("indicador1", ind.getNombre());
		assertEquals("cuenta{EBITDA}*2+cuenta{CASH}", ind.getExpression());
		assertEquals(2, ind.getCuentas().size());
		assertEquals(0, ind.getIndicadores().size());
	}
	
	@Test
	public void evaluarIndicadores() throws Exception, IndicadorException{
		assertEquals(250400, ServiceManager.getInstance().evaluarIndicador("indicador1", empresa, "Semestral"),0);
		assertEquals(225400, ServiceManager.getInstance().evaluarIndicador("indicador2", empresa, "Semestral"),0);
		assertEquals(275400, ServiceManager.getInstance().evaluarIndicador("indicador3", empresa, "Semestral"),0);
		assertEquals(40010, ServiceManager.getInstance().evaluarIndicador("indicador4", empresa, "Anual"),0);
	}
	
	@AfterClass
	public static void clearDatabase() throws Exception{
		ServiceManager.getInstance().clearRepo();
	}
	
}
