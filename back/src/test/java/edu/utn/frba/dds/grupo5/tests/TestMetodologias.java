package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Metodologia;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;


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
	public void testBuffet(){
		 KieServices ks = KieServices.Factory.get();
		 KieContainer kContainer = ks.getKieClasspathContainer();
		 KieSession kSession = kContainer.newKieSession();
		 
		 CuentaEmpresa cuentaEmpresa = new CuentaEmpresa();
		 cuentaEmpresa.setValor(Double.valueOf(50));
		 
		 Metodologia metodologia = new Metodologia();
		 metodologia.setNombre("Buffet");
		 metodologia.setEmpresas(empresas);
		 
		 FactHandle handle = kSession.insert(metodologia);
		 kSession.fireAllRules();
		 kSession.delete(handle);
		 
		 assertTrue(metodologia.getResult().size()==3);
		 assertEquals("Pepsico",metodologia.getResult().get(0).getNombre());
		 assertEquals("Apple",metodologia.getResult().get(1).getNombre());
		 assertEquals("Google",metodologia.getResult().get(2).getNombre());
	} 
}
