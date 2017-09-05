package edu.utn.frba.dds.grupo5.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Metodologia;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.indicadores.EvaluadorExpresiones;
import edu.utn.frba.dds.grupo5.indicadores.FactoryIndicadores;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.persistencia.Cuentas;
import edu.utn.frba.dds.grupo5.persistencia.Repositorio;
import edu.utn.frba.dds.grupo5.util.ConfigManager;

public class ServiceManager {
	private static ServiceManager instance;
	private KieSession kSession;
	private Repositorio repo;
	
	private ServiceManager() throws Exception{
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		kSession = kContainer.newKieSession();
		
		String ds = ConfigManager.getInstance().getProperty("dbsource");
		
		EntityManagerFactory fact = Persistence.createEntityManagerFactory(ds);
		repo = new Repositorio(fact.createEntityManager());
	}
	
	public static ServiceManager getInstance() throws Exception{
		if(instance==null)
			instance=new ServiceManager();
		return instance;
	}
	
	public void guardarIndicadores(List<Indicador> cargados) throws Exception, IndicadorException{
		for(Indicador ind: cargados){
			Indicador generated = FactoryIndicadores.getInstance().build(ind.getExpression(), ind.getNombre(), repo.getCuentas().all(), repo.getIndicadores().all());
			repo.getIndicadores().save(generated);
		}
	}
	
	public void guardarCuentas(List<Cuenta> cuentas) throws Exception{
		for(Cuenta c : cuentas){
			repo.getCuentas().save(c);
		}
	}
	
	public Cuentas getCuentas() {
		return repo.getCuentas();
	}
	
	public Indicador recuperarIndicador(String nombre) throws Exception{
		Indicador recuperado = repo.getIndicadores().findByName(nombre);
		if(recuperado != null){
			return recuperado;
		}
		throw new Exception("Indicador '"+nombre+"' no encontrado");
	}
	
	public double evaluarIndicador(String ind,Empresa empresa,String per) throws Exception{
		Indicador indicador = recuperarIndicador(ind);
		Periodo periodo = empresa.getPeriodoByName(per);
		
		return EvaluadorExpresiones.realizarCalculo(indicador, periodo);
	}
	
	public double evaluarIndicadorAnio(String ind,Empresa empresa,String anio) throws Exception{
		Indicador indicador = recuperarIndicador(ind);
		
		List<Periodo> periodos = empresa.getPeriodosAnio(anio);
		
		double result = 0;
		
		for(Periodo periodo: periodos){
			result+=EvaluadorExpresiones.realizarCalculo(indicador, periodo);
		}
			
		return result;
	}
	
	public List<Empresa> evaluateMetodologia(String name,List<Empresa> empresas) throws Exception{
		 Metodologia metodologia = new Metodologia();
		 metodologia.setNombre(name);
		 metodologia.setEmpresas(empresas);
		 
		 FactHandle handle = kSession.insert(metodologia);
		 int cantRules = kSession.fireAllRules();
		 kSession.delete(handle);
		 
		 if(cantRules==0){
			 throw new Exception("Metodolog�a "+name+" no encontrada");
		 }
		 
		 return metodologia.getResult();
	}
	
	
	public void guardarEmpresa(Empresa empresa) throws Exception{
		repo.getEmpresas().save(empresa);
	}
	
	public Empresa buscarEmpresa(String nombre) throws Exception{
		return repo.getEmpresas().findByName(nombre);
	}
	
	public List<Empresa> getEmpresas() throws Exception{
		return repo.getEmpresas().all();
	}
	
	public void clearRepo(){
		repo.clearAll();
	}
}
