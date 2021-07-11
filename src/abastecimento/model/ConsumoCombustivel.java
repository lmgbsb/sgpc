package abastecimento.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumoCombustivel {

	
	private TipoCombustivel tipoCombustivel;
	private double quilometrosPorLitro;
	private ModeloVeiculo modelo;
	
	public ConsumoCombustivel(TipoCombustivel tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
	}
	
	
}
