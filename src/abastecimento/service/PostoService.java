package abastecimento.service;

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
import lombok.Getter;

@Getter
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
		for(int v = 0; v < vr.getVeiculos().size(); v ++) {
			
			Abastecimento abastecimento = new Abastecimento();
			
			Veiculo veiculo = vr.getVeiculo(v);
			
			abastecimento.setVeiculo(veiculo);
			
			
//			É premissa do problema que os carros estão com o tanque vazio e que 
//			a quantidade de combustivel de cada abastecimento será igual à capacidade
//			do seu tanque			
			abastecimento.setQuantidadeCombustivel(veiculo.getModelo().getCapacidadeTanque());
			
//			como ainda não se sabe se o veículo pode ser abastecido com
//			mais de um combustível, consideramos a priori que o de melhor
//			rendimento é o primeiro da lista e depois verificamos se há outros
			ConsumoCombustivel combustivelMelhorRendimento = veiculo.getModelo().getCombustiveis().get(0);
			
			//verifica se o tem mais de um combustivel
			if(veiculo.getModelo().getCombustiveis().size() != 1) {			
				 
				//verifica qual combustivel tem maior rendimento
				for(int r = 0; r < veiculo.getModelo().getCombustiveis().size(); r ++) {
					
					ConsumoCombustivel outroCombustivel = veiculo.getModelo().getCombustiveis().get(r);
					 if (outroCombustivel.getQuilometrosPorLitro() > combustivelMelhorRendimento.getQuilometrosPorLitro()) {
						 combustivelMelhorRendimento = outroCombustivel;							
					 }
				}				
			}			

			//verifica qual bomba tem o mesmo combustível do carro
			List<Bomba> bombas = br.getBombas();
			for (int b = 0; b < bombas.size(); b++) {
				//se o tipo de combustivel da bomba for o mesmo do combustivel
				//de melhor rendimento do automóvel, abastece o veículo
				Bomba bomba = bombas.get(b);
				if(bomba.getCombustivel().getDescricao().equals(combustivelMelhorRendimento.getTipoCombustivel().getDescricao())) {
					//abastece o automóvel
					abastecimento.setBomba(bomba);
				}
			}				
			
			ar.add(abastecimento);
			
		}
	}
}
