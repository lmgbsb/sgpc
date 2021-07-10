package abastecimento.repository;

import java.util.ArrayList;
import java.util.List;

import abastecimento.model.Veiculo;

public class VeiculoRepository {

	private List<Veiculo> veiculos;
	
	public VeiculoRepository() {
		veiculos= new ArrayList<Veiculo>();
	}
	
	public void add(Veiculo v) {
		veiculos.add(v);
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public Veiculo getVeiculo(int ordem) {
		return veiculos.get(ordem);
	}
	
	
}
