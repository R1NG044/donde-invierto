package edu.utn.frba.dds.grupo5.tests;

import org.junit.AfterClass;

import edu.utn.frba.dds.grupo5.service.ServiceManager;

public class TestPersistencia {
	//	TODO
	
	
	
	@AfterClass
	public static void clearDatabase() throws Exception{
		ServiceManager.getInstance().clearRepo();
	}
}