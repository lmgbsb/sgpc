package abastecimento.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bomba {

	private int id;
	private TipoCombustivel combustivel;
	
	//Inicializa bomba com o tipo de combustível
	public Bomba(int id, TipoCombustivel tipoCombustivel) {
		this.id = id;
		this.combustivel = tipoCombustivel;
	}
		
}
