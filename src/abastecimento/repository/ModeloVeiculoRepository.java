package abastecimento.repository;

import java.util.ArrayList;
import java.util.List;

import abastecimento.model.ModeloVeiculo;

public class ModeloVeiculoRepository {
	
	private List<ModeloVeiculo> modelos;
	
	public ModeloVeiculoRepository() {
		modelos = new ArrayList<ModeloVeiculo>();
	}

	public void add(ModeloVeiculo mv) {
		modelos.add(mv);
	}
	
	public ModeloVeiculo getModelo(String modelo) {
		
		for(int i = 0; i < modelos.size(); i++) {
			
			if(modelos.get(i).getDescricao().equals(modelo)) {
				return modelos.get(i);
			}
		}
		
		return null;
	}
}
