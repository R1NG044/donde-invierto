package edu.utn.frba.dds.grupo5.entidades;

import java.util.Calendar;

import edu.utn.frba.dds.grupo5.service.ServiceManager;

public class CondicionMinimo implements ICondicion{
	
	private String indicador;
	private Integer anios;
	
	@Override
	public int evaluate(Empresa e1, Empresa e2) {
		
		if(e1.antiguedad()<anios && e2.antiguedad()>=anios)
			return -1;
		
		if(e1.antiguedad()>=anios && e2.antiguedad()<anios)
			return 1;
		
		int actualYear= Calendar.getInstance().get(Calendar.YEAR);
		
		int backYear = actualYear-anios;
		
		Integer cant1 = 0;
		Integer cant2 = 0;
		
		while(backYear<=actualYear){
			double value1=0;
			double value2=0;
			try{
				value1 = ServiceManager.getInstance().evaluarIndicadorAnio(indicador, e1, String.valueOf(backYear));
			}catch(Exception e){
				return -1;
			}
			try{
				value2 = ServiceManager.getInstance().evaluarIndicadorAnio(indicador, e2, String.valueOf(backYear));
			}catch(Exception e){
				return 1;
			}
			if(value1<value2)
				cant1++;
			else if (value1>value2)
				cant2++;
			
			backYear++;
		}
		
		return cant2.compareTo(cant1);
	}

	@Override
	public void setIndicador(String ind) {
		indicador=ind;
		
	}

	@Override
	public void setAnios(Integer anios) {
		this.anios=anios-1;
		
	}

}
