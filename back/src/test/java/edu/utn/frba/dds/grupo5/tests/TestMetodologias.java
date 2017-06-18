package edu.utn.frba.dds.grupo5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;

public class TestMetodologias {
	
	@Test
	public void testFirstRule(){
		 KieServices ks = KieServices.Factory.get();
		 KieContainer kContainer = ks.getKieClasspathContainer();
		 KieSession kSession = kContainer.newKieSession();
		 
		 CuentaEmpresa cuentaEmpresa = new CuentaEmpresa();
		 cuentaEmpresa.setValor(Double.valueOf(50));
		 
		 List<String> list = new ArrayList<>();
		 kSession.setGlobal("list", list);
		 kSession.insert(cuentaEmpresa);
		 kSession.fireAllRules();
		 
		 assertTrue(list.size()==1);
	} 
}
