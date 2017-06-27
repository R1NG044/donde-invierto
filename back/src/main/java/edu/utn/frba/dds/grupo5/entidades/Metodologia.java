package edu.utn.frba.dds.grupo5.entidades;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.utn.frba.dds.grupo5.util.Util;

public class Metodologia {
	
    public Metodologia(){
    	
    }
	
	private String nombre;
	private List<Empresa> empresas;
	private List<ICondicion> condiciones;
	private List<IFiltro> filtros;
	
	public void minimizar(String indicador,int anios) throws Exception{
		ICondicion cond = new CondicionMinimo();
		cond.setIndicador(indicador);
		cond.setAnios(anios);
		getCondiciones().add(cond);
	}
	
	public void maximizar(String indicador,int anios) throws Exception{
		ICondicion cond = new CondicionMaximo();
		cond.setIndicador(indicador);
		cond.setAnios(anios);
		getCondiciones().add(cond);
	}
	
	public void consistencia(String indicador, int anios) throws Exception{
		IFiltro filtro = new FiltroConsistencia();
		filtro.setIndicador(indicador);
		filtro.setAnios(anios);
		getFiltros().add(filtro);
	}
	
	public void aniosAntiguedad(Integer anios){
		IFiltro filtro = new FiltroAntiguedad();
		filtro.setAnios(anios);
		getFiltros().add(filtro);
	}
	
	public void ordenarConsistencia(String indicador, int anios){
		ICondicion cond = new CondicionConsistencia();
		cond.setIndicador(indicador);
		cond.setAnios(anios);
		getCondiciones().add(cond);
	}
	
	public void ordenarAntiguedad(){
		ICondicion cond = new CondicionAntiguedad();
		getCondiciones().add(cond);
	}
	
	
	public List<Empresa> getResult(){
		
		List<Empresa> result = Util.filterByPredicate(empresas, e -> filtrar(e));
		Collections.sort(result, new Comparator<Empresa>() {
			public int compare(Empresa o1, Empresa o2) {
				
				int mayorE1 = Util.filterByPredicate(condiciones, c -> {return c.evaluate(o1, o2)>0;}).size();
				
				int cantCondiciones = getCondiciones().size();
				
				return (mayorE1<(cantCondiciones-mayorE1))?1:((cantCondiciones-mayorE1)==mayorE1?0:-1);
			}
		});
		return result;
	}

	private boolean filtrar(Empresa e) {
		for(IFiltro filtro: filtros){
			if(!filtro.evaluate(e))
				return false;
		}
		return true;
			
	}
	
	public List<ICondicion> getCondiciones() {
		if(condiciones==null)
			condiciones = new ArrayList<>();
		return condiciones;
	}

	public void setCondiciones(List<ICondicion> condiciones) {
		this.condiciones = condiciones;
	}

	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public List<IFiltro> getFiltros() {
		if(filtros==null)
			filtros=new ArrayList<>();
		return filtros;
	}

	public void setFiltros(List<IFiltro> filtros) {
		this.filtros = filtros;
	}

}
