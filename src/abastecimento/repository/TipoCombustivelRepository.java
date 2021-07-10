package abastecimento.repository;

import java.util.ArrayList;
import java.util.List;

import abastecimento.model.TipoCombustivel;

public class TipoCombustivelRepository {
	
	private List<TipoCombustivel> tiposCombustivel;
	
	public TipoCombustivelRepository() {
		this.tiposCombustivel = new ArrayList<TipoCombustivel>();
	}

	public void add(TipoCombustivel tc) {
		tiposCombustivel.add(tc);
	}
	
	public TipoCombustivel get(String tipoCombustivel) {
		
		TipoCombustivel tc;
		
		for (int i = 0; i < tiposCombustivel.size(); i++) {
			tc = (tiposCombustivel.get(i));
			if (tc.getDescricao().equalsIgnoreCase(tipoCombustivel)) {
				return tc;
			}
		}
		
		return null;
	}

	public List<TipoCombustivel> getTiposCombustivel() {
		
		return tiposCombustivel;
		
	}
}
