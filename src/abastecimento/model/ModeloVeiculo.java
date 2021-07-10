package abastecimento.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloVeiculo {

	private int idModelo;
	private String descricao;
	private double capacidadeTanque;
	private List<ConsumoCombustivel> combustiveis;
	
	public ModeloVeiculo() {
		combustiveis = new ArrayList<ConsumoCombustivel>();
	}
	
	public void adicionaTipoCombustivel(ConsumoCombustivel cc) {
		combustiveis.add(cc);
	}
	
}
