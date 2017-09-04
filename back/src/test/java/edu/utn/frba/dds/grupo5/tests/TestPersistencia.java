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

import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;

public class TestPersistencia {
	//	TODO
	
	private static List<Empresa> empresas;
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUp() throws IndicadorException, Exception {
		String empresasString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("empresas.json"));
		Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
		empresas = ((List<Empresa>)new Gson().fromJson(empresasString, listType));
	}
	
	@Test
	public void testEmpresa() throws Exception{
		for(Empresa emp: empresas){
			ServiceManager.getInstance().guardarEmpresa(emp);
		}
		assertEquals(6,ServiceManager.getInstance().getEmpresas().size());
		
		Empresa snapchat = ServiceManager.getInstance().buscarEmpresa("Snapchat");
		
		assertEquals("Snapchat",snapchat.getNombre());
		assertEquals(Integer.valueOf(2011),snapchat.getAnioFundacion());
		assertEquals(2,snapchat.getPeriodos().size());
	} 
	
	@AfterClass
	public static void clearDatabase() throws Exception{
		ServiceManager.getInstance().clearRepo();
	}
}