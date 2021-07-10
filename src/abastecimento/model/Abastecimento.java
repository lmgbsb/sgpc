package abastecimento.model;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Abastecimento {

	private Bomba bomba;
	private Veiculo veiculo;
	private double quantidadeCombustivel;
	private int tempoAbastecimentoSegundos;
	private Instant momento;
	
}
