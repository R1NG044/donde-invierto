package edu.utn.frba.dds.grupo5.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Calendar;
import java.util.GregorianCalendar;
import edu.utn.frba.dds.grupo5.util.Util;
@Table(name="di_empresa")
@Entity
@NamedQueries({
	@NamedQuery(name="search_all_empresas",query="select e from Empresa e "),
	@NamedQuery(name="search_empresas",query="select e from Empresa e where (:nombre is null or e.nombre=:nombre)")
})
public class Empresa extends Persistent{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5880821639341627201L;
	private List<Periodo> periodos;
	private String nombre;
	private Integer anioFundacion;
	
	public Empresa(){
		
	}
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY,orphanRemoval=true)
	@JoinColumn(name="emp_per_oid",nullable=false,foreignKey=@ForeignKey(name="fk_emp_per_oid"))
	public List<Periodo> getPeriodos() {
		if(periodos==null)
			periodos=new ArrayList<Periodo>();
		return periodos;
	}
	
	public void setPeriodos(List<Periodo> periodos){
		this.periodos = periodos;
	}
	
	public Periodo getPeriodoByName(String name) throws Exception{
		if(!validarPeriodo(name)) {
			throw new Exception("Periodo '"+name+"' no encontrado");
		}
		return Util.filterByPredicate(getPeriodos(), p -> p.getNombre().equalsIgnoreCase(name)).get(0);
	}
	
	public void addPeriodo(Periodo periodo) {
		if(periodos == null)
			periodos = new ArrayList<Periodo>();
		periodos.add(periodo);
	}
	@Column(name="emp_nombre",nullable=false,length=100)
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean validarPeriodo(String periodo) {
		return !Util.filterByPredicate(getPeriodos(),p -> p.getNombre().equalsIgnoreCase(periodo)).isEmpty();
	}	
	
	public List<Periodo> getPeriodosAnio(String anio){
		return Util.filterByPredicate(getPeriodos(),p->p.getAnio().equals(anio));
	}
	@Column(name="emp_fund",nullable=false,length=4)
	public Integer getAnioFundacion(){
		return anioFundacion;
	}
	
	public void setAnioFundacion(Integer anioFundacion) {
		this.anioFundacion = anioFundacion;
	}
	
	public Integer antiguedad(){
		Integer ant=0;
		Calendar c2 = new GregorianCalendar();
		Integer anio = c2.get(Calendar.YEAR);
		ant=anio-anioFundacion;
		return ant;
	}
	
	public String toString(){
		if(nombre != null && anioFundacion != null){
			return "EMPRESA: "+nombre+" FUNDACION: "+anioFundacion;
		}
		return "";
	}
}