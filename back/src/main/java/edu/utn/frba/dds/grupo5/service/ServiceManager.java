package edu.utn.frba.dds.grupo5.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import edu.utn.frba.dds.grupo5.entidades.Cuenta;
import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.entidades.Indicador;
import edu.utn.frba.dds.grupo5.entidades.Metodologia;
import edu.utn.frba.dds.grupo5.entidades.Periodo;
import edu.utn.frba.dds.grupo5.entidades.Usuario;
import edu.utn.frba.dds.grupo5.indicadores.EvaluadorExpresiones;
import edu.utn.frba.dds.grupo5.indicadores.FactoryIndicadores;
import edu.utn.frba.dds.grupo5.indicadores.IndicadorException;
import edu.utn.frba.dds.grupo5.persistencia.Repositorio;
import edu.utn.frba.dds.grupo5.util.ConfigManager;

public class ServiceManager {
	private static ServiceManager instance;
	private KieSession kSession;
	private Repositorio repo;
	private boolean mockContainer;
	private KieServices ks;
	
	private ServiceManager() throws Exception{
		ks = KieServices.Factory.get();
		mockContainer = ConfigManager.getInstance().getBooleanProperty("mock-container");
		KieContainer kContainer = getKieContainer();
		kSession = kContainer.newKieSession();
		
		String ds = ConfigManager.getInstance().getProperty("dbsource");
		
		EntityManagerFactory fact = Persistence.createEntityManagerFactory(ds);
		repo = new Repositorio(fact.createEntityManager());
	}
	
	private KieContainer getKieContainer(){
		if (!mockContainer){
			return ks.newKieContainer(ks.newReleaseId("edu.utn.frba.dds.grupo5", "Metodologias", "LATEST"));
		}else{
			return ks.newKieClasspathContainer();
		}
	}
	
	public static ServiceManager getInstance() throws Exception{
		if(instance==null)
			instance=new ServiceManager();
		return instance;
	}
	
	public void guardarIndicadores(List<Indicador> cargados) throws Exception, IndicadorException{
		for(Indicador ind: cargados){
			guardarIndicador(ind);
		}
	}
	
	public Indicador guardarIndicador(Indicador ind) throws Exception, IndicadorException{
		Indicador generated = FactoryIndicadores.getInstance().build(ind.getExpression(), ind.getNombre(), repo.getCuentas().all(), repo.getIndicadores().all());
		if(ind.getUsuario() != null){
			generated.setUsuario(repo.getUsuarios().findByPK(Usuario.class, ind.getUsuario().getOid()));
		}
		return repo.getIndicadores().save(generated);
	}
	
	public List<Indicador> getIndicadores() throws Exception {
		return repo.getIndicadores().all();
	}
	
	public List<Indicador> getIndicadores(Long userOid) throws Exception {
		return repo.getIndicadores().findByUser(userOid);
	}
	
	public Indicador getIndicador(Long oid) throws Exception {
		return repo.getIndicadores().findByPK(Indicador.class, oid);
	}
	
	public void guardarCuentas(List<Cuenta> cuentas) throws Exception{
		for(Cuenta c : cuentas){
			repo.getCuentas().save(c);
		}
	}
	
	public List<Cuenta> getCuentas() throws Exception {
		return repo.getCuentas().all();
	}
	
	public Cuenta getCuentaByDesc(String desc) throws Exception {
		return repo.getCuentas().findByName(desc);
	}
	
	public Cuenta getCuenta(Long oid) throws Exception {
		return repo.getCuentas().findByPK(Cuenta.class, oid);
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
			 throw new Exception("Metodologia "+name+" no encontrada");
		 }
		 
		 return metodologia.getResult();
	}
	
	public void saveMetodologia(Metodologia metodologia) throws Exception{
		repo.getMetodologias().save(metodologia);
	}
	
	public List<Metodologia> getMetodologias() throws Exception{
		return repo.getMetodologias().all();
	}
	
	public Metodologia getMetodologiaByName(String name) throws Exception{
		return repo.getMetodologias().findByName(name);
	}
	
	public Metodologia getMetodologia(Long oid) throws Exception {
		return repo.getCuentas().findByPK(Metodologia.class, oid);
	}
	
	public void deleteMetodologia(Long oid) throws Exception {
		repo.getEmpresas().delete(Metodologia.class, oid);
	}
	
	public void refreshMetodologias() throws Exception{
		kSession = getKieContainer().newKieSession();
		
		repo.getMetodologias().clear();
		
		List<Rule> rules = new ArrayList<Rule>();
		KieBase newKieBase = kSession.getKieBase();
		newKieBase.getKiePackages().forEach(p -> rules.addAll(p.getRules()));
		
		for(Rule r: rules){
			Metodologia m = new Metodologia();
			m.setNombre(r.getName());
			saveMetodologia(m);
		}
	}
	
	public void guardarEmpresa(Empresa empresa) throws Exception{
		repo.getEmpresas().save(empresa);
	}
	
	public void actualizarEmpresa(Empresa empresa) throws Exception{
		repo.getEmpresas().update(empresa);
	}
	
	public Empresa buscarEmpresa(String nombre) throws Exception{
		return repo.getEmpresas().findByName(nombre);
	}
	
	public List<Empresa> getEmpresas() throws Exception{
		return repo.getEmpresas().all();
	}
	
	public Empresa getEmpresa(Long oid) throws Exception {
		return repo.getEmpresas().findByPK(Empresa.class, oid);
	}
	
	public void deleteEmpresa(Long oid) throws Exception {
		repo.getEmpresas().delete(Empresa.class, oid);
	}
	
	public Usuario doLogin(String usuario,String password) throws Exception{
		return repo.getUsuarios().login(usuario, password);
	}
	
	public void actualizarPeriodo(Periodo per) throws Exception{
		repo.getPeriodos().update(per);
	}
	
	public Periodo getPeriodo(Long oid) throws Exception{
		return repo.getPeriodos().findByPK(Periodo.class, oid);
	}
	
	public void clearRepo() throws Exception{
		repo.clearAll();
	}

}
