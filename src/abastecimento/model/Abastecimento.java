package abastecimento.model;

import java.time.Duration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Abastecimento {

	private Bomba bomba;
	private Veiculo veiculo;
	private double quantidadeCombustivel;
	
	public double getTempoAbastecimento() {
		return (quantidadeCombustivel * bomba.getCombustivel().getVelocidadeAbastecimento());
	}
	
	public String getTempoAbastecimentoFormatado() {
		
		double tempoAbastecimentoEmSegundos = getTempoAbastecimento();
		
		Duration duracaoAbastecimento = Duration.ofSeconds((long) tempoAbastecimentoEmSegundos);		
		
		String duracao = "Tempo abastecimento: ";
		duracao += (int)tempoAbastecimentoEmSegundos;
		duracao += " segundos";
		duracao += " = ";
		duracao += duracaoAbastecimento.toMinutes();
		duracao += " minutos e ";
		duracao += duracaoAbastecimento.toSecondsPart();
		duracao += " segundos";
		
		return duracao;
	}
	
}
