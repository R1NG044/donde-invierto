package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;
import edu.utn.frba.dds.grupo5.util.Util;

public class TestIndicadores {
	
	@Rule 
	public RuleStartupDB testDb = new RuleStartupDB();
	private static Empresa empresa;
	private static List<Indicador> indicadores;
	
	@Before
	public void setUp() throws IndicadorException, Exception {
		
		String indicadoresUsuario = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("indicadores-usuario.json"));
		Type listType = new TypeToken<ArrayList<Indicador>>(){}.getType();
		indicadores = new Gson().fromJson(indicadoresUsuario, listType);
		try{
			ServiceManager.getInstance().guardarIndicadores(indicadores);
		}catch(Exception e){
			
		}
		empresa = Util.find(ServiceManager.getInstance().getEmpresas(),e -> e.getNombre().equals("Snapchat"));
	}
	
	@Test
	public void testCargaIndicadoresUsuario() throws Exception, IndicadorException{
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
		Indicador ind = ServiceManager.getInstance().recuperarIndicador("indicador1");
		assertEquals("indicador1", ind.getNombre());
		assertEquals("cuenta{EBITDA}*2+cuenta{CASH}", ind.getExpression());
		assertEquals(2, ind.getCuentas().size());
		assertEquals(0, ind.getIndicadores().size());
	}
	
	@Test
	public void evaluarIndicadores() throws Exception, IndicadorException{
		assertEquals(250400, ServiceManager.getInstance().evaluarIndicador("indicador1", empresa, "Semestral"),0);
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
