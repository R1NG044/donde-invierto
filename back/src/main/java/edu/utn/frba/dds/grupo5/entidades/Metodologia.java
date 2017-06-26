package edu.utn.frba.dds.grupo5.entidades;
import java.util.List;

import edu.utn.frba.dds.grupo5.entidades.Empresa;
import edu.utn.frba.dds.grupo5.util.Util;
public class Metodologia {
	public String nombre;
	List<Empresa> empresas;
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void addEmpresa(Empresa empresa){
		empresas.add(empresa);
	}
	public void setEmpresas(List<Empresa> empresas){
		this.empresas=empresas;
	}
	public Boolean existeEmpresa(String emp){
		return !Util.filterByPredicate(getEmpresas(),e -> e.getNombre().equalsIgnoreCase(emp)).isEmpty();
	}
	public Empresa getEmpresaByName(String name) throws Exception{
		if(!existeEmpresa(name)) {
			throw new Exception("Empresa '"+name+"' no encontrado");
		}
		return Util.filterByPredicate(getEmpresas(), e -> e.getNombre().equalsIgnoreCase(name)).get(0);
	}
	public String getNombre(){
		return nombre;
	}
	public List<Empresa> getEmpresas(){
		return empresas;
	}
	@Override
	public String toString() {
		return "Metodologia [nombre=" + nombre + ", empresas=" + empresas + "]";
	}
}
