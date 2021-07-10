package abastecimento.repository;

import java.util.ArrayList;
import java.util.List;

import abastecimento.model.Abastecimento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbastecimentoRepository {
	
	private List<Abastecimento> abastecimentos;
	
	public AbastecimentoRepository() {
		abastecimentos = new ArrayList<Abastecimento>();
	}

	public void add(Abastecimento abastecimento) {
		abastecimentos.add(abastecimento);
	}
		
	public List<Abastecimento> findByCodigoBomba(int codigo){
		
		List<Abastecimento> abastecimentosPorBomba = new ArrayList<Abastecimento>();
		
		List<Abastecimento> abastecimentos = getAbastecimentos();
		
		for(int a = 0; a< abastecimentos.size(); a++) {
			if (abastecimentos.get(a).getBomba().getId() == codigo) {
				abastecimentosPorBomba.add(abastecimentos.get(a));
			}
		}
		
		return abastecimentosPorBomba;
	}

	public List<Abastecimento> findByTipoCombustivel(String tipoCombustivel) {
		
		List<Abastecimento> abastecimentosPorCombustivel = new ArrayList<Abastecimento>();
		
		List<Abastecimento> abastecimentos = getAbastecimentos();
		
		for(int a = 0; a< abastecimentos.size(); a++) {
			if (abastecimentos.get(a).getBomba().getCombustivel().getDescricao().equals(tipoCombustivel)) {
				abastecimentosPorCombustivel.add(abastecimentos.get(a));
			}
		}		
		
		return abastecimentosPorCombustivel;
		
	}

}
