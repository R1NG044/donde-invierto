package edu.utn.frba.dds.grupo5.entidades;

public class CondicionConsistencia implements ICondicion{
	
	private String indicador;
	private Integer anios;
	
	@Override
	public int evaluate(Empresa e1, Empresa e2) {
		
		if(e1.antiguedad()<anios && e2.antiguedad()>=anios)
			return -1;
		
		if(e1.antiguedad()>=anios && e2.antiguedad()<anios)
			return 1;
		
		FiltroConsistencia filter = new FiltroConsistencia();
		filter.setIndicador(indicador);
		filter.setAnios(anios);
		
		boolean e1EsConsistente = filter.evaluate(e1);
		boolean e2EsConsistente = filter.evaluate(e2);
		
		if(!e1EsConsistente && e2EsConsistente)
			return -1;
		if(e1EsConsistente && !e2EsConsistente)
			return 1;
		
		CondicionMaximo max = new CondicionMaximo();
		max.setAnios(anios);
		max.setIndicador(indicador);
		
		return max.evaluate(e1, e2);
	}

	@Override
	public void setIndicador(String ind) {
		indicador=ind;
	}
	
	@Override
	public void setAnios(Integer anios) {
		this.anios = anios-1;
	}

}
