package edu.utn.frba.dds.grupo5.tests;

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
		 /*KieServices ks = KieServices.Factory.get();
		 KieContainer kContainer = ks.getKieClasspathContainer();
		 KieSession kSession = kContainer.newKieSession();
		 
		 CuentaEmpresa cuentaEmpresa = new CuentaEmpresa();
		 cuentaEmpresa.setValor(Double.valueOf(50));
		 
		 List<String> list = new ArrayList<>();
		 kSession.setGlobal("list", list);
		 kSession.insert(cuentaEmpresa);
		 kSession.fireAllRules();
		 
		http://localhost:8080/kie-drools-wb/maven2/groupId/artifactId/1.0/artifactId-1.0.jar
		 assertTrue(list.size()==1);*/
		
		CuentaEmpresa cuentaEmpresa = new CuentaEmpresa();
		cuentaEmpresa.setValor(Double.valueOf(30));
		
		KieServices ks = KieServices.Factory.get();
		KieContainer kieContainer = ks.newKieContainer(ks.newReleaseId("edu.utn.frba.dds.grupo5", "Metodologias", "LATEST"));
		
		List<String> list = new ArrayList<>();
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(cuentaEmpresa);
		kieSession.setGlobal("list", list);
		kieSession.fireAllRules();
	} 
}
