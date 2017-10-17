package edu.utn.frba.dds.grupo5.tests;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.CuentaEmpresa;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.entidades.Usuario;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.service.ServiceManager;
import edu.utn.frba.dds.grupo5.util.Util;

public class RuleStartupDB implements TestRule {

    private class DbInitializer extends ExternalResource {
        @Override protected void before() throws Throwable {
        	RuleStartupDB.this.onSetup();
        };
    }

    private final DbInitializer initializer = new DbInitializer();

    /** Ignore - for internal use by JUnit's Rule handling. */
    public final Statement apply(Statement statement, Description description) {
        return initializer.apply(statement, description);
    }
    
    @SuppressWarnings("unchecked")
    private void onSetup() throws Exception, IndicadorException{
		ServiceManager.getInstance().clearRepo();
		
		String cuentasString = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("cuentas.json"));
		Type listType = new TypeToken<ArrayList<Cuenta>>() {
		}.getType();
		List<Cuenta> cuentas = ((List<Cuenta>) new Gson().fromJson(cuentasString, listType));

		ServiceManager.getInstance().guardarCuentas(cuentas);
		
		cuentas = ServiceManager.getInstance().getCuentas();
		
		String empresasJson = IOUtils.toString(TestIndicadores.class.getClassLoader().getResource("empresas.json"));

		listType = new TypeToken<ArrayList<Empresa>>() {
		}.getType();
		List<Empresa> empresas = ((List<Empresa>) new Gson().fromJson(empresasJson, listType));
		
		for(Empresa e: empresas){
			
			for(Periodo p:e.getPeriodos()){
				for(CuentaEmpresa c:p.getCuentas()){
					Cuenta cp = Util.find(cuentas, c2 -> c2.getDescripcion().equals(c.getCuenta().getDescripcion()));
					if(cp!=null){
						c.setCuenta(cp);
					}
				}
			}
			
			
			ServiceManager.getInstance().guardarEmpresa(e);
		}
		
		String indicadoresString = IOUtils
				.toString(TestIndicadores.class.getClassLoader().getResource("indicadores-predefinidos.json"));
		listType = new TypeToken<ArrayList<Indicador>>() {
		}.getType();
		List<Indicador> indicadores = ((List<Indicador>) new Gson().fromJson(indicadoresString, listType));

		ServiceManager.getInstance().guardarIndicadores(indicadores);
		
		Usuario user = new Usuario();
		user.setUsername("admin");
		user.setPassword("admin");
		
		ServiceManager.getInstance().addUser(user);
    }

}