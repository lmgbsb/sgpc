package abastecimento.service;

import java.time.Duration;
import java.util.List;

import abastecimento.factory.ModeloFactory;
import abastecimento.factory.VeiculoFactory;
import abastecimento.model.Abastecimento;
import abastecimento.model.Bomba;
import abastecimento.model.ConsumoCombustivel;
import abastecimento.model.TipoCombustivel;
import abastecimento.model.Veiculo;
import abastecimento.repository.AbastecimentoRepository;
import abastecimento.repository.BombaRepository;
import abastecimento.repository.ModeloVeiculoRepository;
import abastecimento.repository.TipoCombustivelRepository;
import abastecimento.repository.VeiculoRepository;

public class PostoService {
	
	private TipoCombustivelRepository tcr;	
	private BombaRepository br;
	private ModeloVeiculoRepository mvr;
	private VeiculoRepository vr;
	private AbastecimentoRepository ar;
	
	public PostoService() {		
		
		//inicializa os tipos de combustivel do posto
		inicializaCombustiveis();
		
		//inicializa as bombas do posto
		inicializaBombas();
		
		//Lê o arquivo "modelos.csv" e carrega o repositório de modelos com
		//objetos do tipo ModeloVeiculo com o seus respectivos TipoCombustivel
		carregaModelos(tcr, mvr, "modelos.csv");
		
		//Lê o arquivo veiculos.csv e carrega o repositorio de veiculos com 
		//objetos do tipo Veiculo com o respectivo ModeloVeiculo
		carregaVeiculos(vr, mvr, "veiculos.csv");		
		
	}	

	//Método inicializa combustíveis do posto conforme as orientações
	private void inicializaCombustiveis() {
		
		tcr = new TipoCombustivelRepository();
		
		TipoCombustivel tc1 = new TipoCombustivel();
		
		tc1.setDescricao("GASOLINA");
		//6 segundos por litro
		tc1.setVelocidadeAbastecimento(6);	
		tc1.setPreco(2.9);
		tcr.add(tc1);
		
		TipoCombustivel tc2 = new TipoCombustivel();
		
		tc2.setDescricao("ETANOL");
		//5 segundos por litro
		tc2.setVelocidadeAbastecimento(5);
		tc2.setPreco(2.27);		
		tcr.add(tc2);
		
	}
	
	//Método inicializa as bomBas do posto conforme as orientações, ou seja
	//uma boma de gasolina e uma bomba de etanol;
	private void inicializaBombas() {
		
		br = new BombaRepository();
		
		Bomba b1 = new Bomba(1, tcr.get("GASOLINA"));
		br.add(b1);
		
		//Bomba de etanol
		Bomba b2 = new Bomba(2, tcr.get("ETANOL"));
		br.add(b2);
				
	}
	
	private void carregaModelos(TipoCombustivelRepository tcr, ModeloVeiculoRepository mvr, String caminhoArquivo) {
		
		mvr = new ModeloVeiculoRepository();
		
		ModeloFactory fabrica = new ModeloFactory(tcr, mvr);
				
		//método lê o arquivo cvs a adciona os objetos do tipo ModeloVeiculo
		//em ModeloRepository, que é um repositório para esse tipo de objetos
		fabrica.leArquivoCsv(caminhoArquivo);
		
		this.mvr = mvr;		
	}
	
	//Método lê o arquivo com a fila de veiculos e adiciona o modelo do veículo
	//conforme o conteúdo desse arquivo.
	private void carregaVeiculos(VeiculoRepository vr, ModeloVeiculoRepository mvr, String arquivoVeiculos) {
		vr = new VeiculoRepository();
		
		VeiculoFactory fabrica = new VeiculoFactory(vr, mvr);
		fabrica.leArquivoCsv(arquivoVeiculos);
		
		this.vr = vr;
	}	

	/*
	 * Simula o abastecimento de uma lista de veículos (VeiculosRepository)
	 * informados no arquivo veiculos.csv, realizando o abastecimento apropriado
	 * de cada veículo. 
	 * 
	 * Pressupondo qu todos os veículos estão com o tanque vazio e os terão
	 * completados, e minimizando a relação preço/km rodado ao direcionar os
	 * veículos para as bombas.
	 * 
	 * A saída deve ser produzida na ordem cronológica dos eventos, no seguinte 
	 * formato:
	 * 
	 * [00:05] Veículo modelo FIAT-UNO, placa JGA-7389, foi abastecido com 48 litros de ETANOL
	 * 
	 * [00:10] Veículo modelo AUDI-A4, placa JGB-1234, foi abastecido com 65 litros de GASOLINA
	 * 
	 * (...)
	 * 
	 * Total abastecido na bomba 1 (GASOLINA): 1517 litros
	 * Total abastecido na bomba 2 (ETANOL): 1125 litros
	 * 
	 * Total geral abastecido de GASOLINA: 1517 litros
	 * Total geral abastecido de ETANOL: 11:25 litros
	 */
	public void abasteceVeiculos() {
		ar = new AbastecimentoRepository();
		
		//lê a lista de carros e encaminha para abastecimento
		//List<Bomba> bombas = br.getBombas();
		Veiculo veiculo;
		
		int duracaoTotalAbastecimentoEmSegundos = 0;
		
		for(int i = 0; i< vr.getVeiculos().size(); i++) {
			
			veiculo = vr.getVeiculo(i);
			Abastecimento abastecimento = new Abastecimento();
			abastecimento.setVeiculo(veiculo);
			abastecimento.setQuantidadeCombustivel(veiculo.getModelo().getCapacidadeTanque());
			ConsumoCombustivel combustivel = veiculo.getModelo().getCombustiveis().get(0);
			
			//verifica se o tem mais de um combustivel
			if(veiculo.getModelo().getCombustiveis().size() != 1) {			
				 
				//verifica qual combustivel tem maior rendimento
				for(int z = 0; z < veiculo.getModelo().getCombustiveis().size(); z++) {
					
					ConsumoCombustivel outroCombustivel = veiculo.getModelo().getCombustiveis().get(z);
					 if (outroCombustivel.getQuilometrosPorLitro() > combustivel.getQuilometrosPorLitro()) {
						 combustivel = outroCombustivel;							
					 }
				}				
			}			

			//verifica qual bomba tem o mesmo combustível do carro
			List<Bomba> bombas = br.getBombas();
			for (int b = 0; b < bombas.size(); b++) {
				//se o tipo de combustivel da bomba for o mesmo do combustivel
				//de melhor rendimento do automóvel, abastece o veículo
				Bomba bomba = bombas.get(b);
				if(bomba.getCombustivel().getDescricao().equals(combustivel.getTipoCombustivel().getDescricao())) {
					//abastece o automóvel
					abastecimento.setBomba(bomba);
					System.out.print("Abastecimento com ");
					System.out.print(bomba.getCombustivel().getDescricao());
					System.out.println(" na bomba " + bomba.getId());
				}
			}				
			
			ar.add(abastecimento);
			
			
			//cálculo to tempo de abastecimento
			double capacidadeTanque = veiculo.getModelo().getCapacidadeTanque();
			double litrosPorSegundo = combustivel.getTipoCombustivel().getVelocidadeAbastecimento();
			double tempoAbastecimentoEmSegundos = capacidadeTanque/litrosPorSegundo;
			System.out.println("Tempo abastecimento: " + (int)tempoAbastecimentoEmSegundos + " segundos");
			
			duracaoTotalAbastecimentoEmSegundos += tempoAbastecimentoEmSegundos;
			
			Duration duration = Duration.ofSeconds(duracaoTotalAbastecimentoEmSegundos);
						
			System.out.print("[");
			if (duration.toMinutes() < 10){
				System.out.print("0");
			}
			System.out.print(duration.toMinutes());
			System.out.print(":");
			
			if (duration.toSecondsPart() < 10){
				System.out.print("0");
			}
			System.out.print(duration.toSecondsPart());
			System.out.print("] ");
			
			System.out.print("Modelo: " + veiculo.getModelo().getDescricao());
			System.out.print(" placa: " + veiculo.getPlaca());
			System.out.print(" foi abastecido com " + veiculo.getModelo().getCapacidadeTanque());
			System.out.println(" litros de " + combustivel.getTipoCombustivel().getDescricao());
			
			System.out.println("\n---------------------------------------------------------------------------\n");
						
		}
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
