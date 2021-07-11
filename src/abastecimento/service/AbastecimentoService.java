package abastecimento.service;

import java.time.Duration;
import java.util.List;

import abastecimento.model.Abastecimento;
import abastecimento.model.Bomba;
import abastecimento.model.TipoCombustivel;
import abastecimento.model.Veiculo;
import abastecimento.repository.AbastecimentoRepository;
import abastecimento.repository.BombaRepository;
import abastecimento.repository.TipoCombustivelRepository;

public class AbastecimentoService {
	
	private BombaRepository br;
	private TipoCombustivelRepository tcr;
	private AbastecimentoRepository ar;
	
	//somatório dos tempos de abastecimento
	private double tempoTotalAbastecimento = 0;
	
	public AbastecimentoService(AbastecimentoRepository abastecimentoRepository, BombaRepository bombaRepository, TipoCombustivelRepository tipoCombustivelRepository) {
		this.ar = abastecimentoRepository;
		this.br = bombaRepository;
		this.tcr = tipoCombustivelRepository;
	}

	public void imprimeAbastecimentos() {
		
		List<Abastecimento> abastecimentos = ar.getAbastecimentos();
		for (int a = 0; a < abastecimentos.size(); a ++) {
			
			Abastecimento abastecimento = abastecimentos.get(a);	
			Veiculo veiculo = abastecimento.getVeiculo();
			Bomba bomba = abastecimento.getBomba();

			System.out.print("Abastecimento com ");
			System.out.print(bomba.getCombustivel().getDescricao());
			System.out.println(" na bomba " + bomba.getId());
			
			//cálculo to tempo de abastecimento
			System.out.println(abastecimento.getTempoAbastecimentoFormatado());			
			
			String tempoTotal = getTempoTotalAbastecimentoFormatado(abastecimento.getTempoAbastecimento());
			
			System.out.print(tempoTotal);
			System.out.print("Modelo: " + veiculo.getModelo().getDescricao());
			System.out.print(" placa: " + veiculo.getPlaca());
			System.out.print(" foi abastecido com " + abastecimento.getQuantidadeCombustivel());
			System.out.println(" litros de " + bomba.getCombustivel().getDescricao());
			
			System.out.println("\n-------------------------------------------------------------------------------------\n");
		}
		
	}
	
	public String getTempoTotalAbastecimentoFormatado(double tempoAbastecimento) {
		
		tempoTotalAbastecimento += tempoAbastecimento;
		
		Duration durationTotal = Duration.ofSeconds((long)tempoTotalAbastecimento);
		
		String tempo =  "[";
		if (durationTotal.toMinutes() < 10){
			tempo += "0";
		}
		tempo += durationTotal.toMinutes();
		tempo += ":";
		
		if (durationTotal.toSecondsPart() < 10){
			tempo += "0";
		}
		tempo += durationTotal.toSecondsPart();
		tempo += "] ";
		
		return tempo;
	}
	
	
	public void totalizarBombas() {
		
		//carrega os abastecimentos de cada bomba do posto
		List<Bomba> bombas = br.getBombas();		
		
		//para cada bomba, listar os abastecimentos
		for(int b = 0; b < bombas.size(); b++) {
			Bomba bomba = bombas.get(b);
			
			List<Abastecimento> abastecimentos = ar.findByCodigoBomba(bomba.getId());
			
			double totalCombustivelBomba = 0;
			for(int a = 0; a< abastecimentos.size(); a++) {
				totalCombustivelBomba += abastecimentos.get(a).getQuantidadeCombustivel();
			}
			
			System.out.print("Total de combustível abastecido na bomba " + bomba.getId());
			System.out.print(" (" + bomba.getCombustivel().getDescricao() + "): ");
			System.out.print(totalCombustivelBomba);
			System.out.println(" litros");			
		}
	}

	public void totalizarCombustiveis() {
		
		//carrega os tipos de combustiveis do posto
		List<TipoCombustivel> combustiveis = tcr.getTiposCombustivel();
		
		//Para cada tipo de combustivel, listar os abastecimentos
		for(int c = 0; c < combustiveis.size(); c ++) {
			
			TipoCombustivel combustivel = combustiveis.get(c);
			
			double totalCombustivel = 0;
			List<Abastecimento> abastecimentos = ar.findByTipoCombustivel(combustivel.getDescricao());
			
			for(int a = 0; a < abastecimentos.size(); a ++) {
				totalCombustivel += abastecimentos.get(a).getQuantidadeCombustivel();
			}
			
			System.out.print("Total geral abastecido de " + combustivel.getDescricao() + ": ");
			System.out.print(totalCombustivel);
			System.out.println(" litros");
			
		}
		
	}
	
}
