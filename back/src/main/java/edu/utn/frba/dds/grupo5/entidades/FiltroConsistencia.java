package edu.utn.frba.dds.grupo5.entidades;

import java.util.Calendar;

import edu.utn.frba.dds.grupo5.service.ServiceManager;

public class FiltroConsistencia implements IFiltro{

	private String indicador;
	private Integer anios;
	
	@Override
	public boolean evaluate(Empresa e1) {
		
		if(e1.antiguedad()<anios)
			return false;
		
		int actualYear= Calendar.getInstance().get(Calendar.YEAR);
		
		int backYear = actualYear-anios;
		
		double lastValue=0;
		
		while(backYear<=actualYear){
			try{
				double value = ServiceManager.getInstance().evaluarIndicadorAnio(indicador, e1, String.valueOf(backYear));
				if(value<lastValue)
					return false;
				backYear++;
			}catch(Exception e){
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void setAnios(Integer anios) {
		this.anios = (anios-1);
	}

	@Override
	public void setIndicador(String indicador) {
		this.indicador=indicador;
		
	}
	
	
	
}
