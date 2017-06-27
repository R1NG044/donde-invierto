package edu.utn.frba.dds.grupo5.entidades;

public class CondicionMinimo implements ICondicion{
	
	private String indicador;
	private Integer anios;
	
	@Override
	public int evaluate(Empresa e1, Empresa e2) {
		
		ICondicion condicionMaximo = new CondicionMaximo();
		condicionMaximo.setIndicador(indicador);
		condicionMaximo.setAnios(anios);
		
		return (condicionMaximo.evaluate(e1, e2)*-1);
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
