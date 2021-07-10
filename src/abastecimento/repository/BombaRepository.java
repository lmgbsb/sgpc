package abastecimento.repository;


import java.util.ArrayList;
import java.util.List;

import abastecimento.model.Bomba;
import lombok.Getter;

@Getter
public class BombaRepository {

	private List<Bomba> bombas;
	
	public BombaRepository() {
		this.bombas = new ArrayList<Bomba>();
	}
	
	public void add(Bomba b) {
		bombas.add(b);
	}
	

}
