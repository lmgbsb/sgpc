package abastecimento;

import abastecimento.service.PostoService;

public class Application {

	public static void main(String[] args) {
		
		//O construtor de PostoService carrega a partir dos arquivos csv os 
		//repositorios TipoCombustivelRepository, BombaRepository e VeiculoRepository
		//que são necessários para encaminhar os veiculos para abastecimento
		PostoService ps = new PostoService();
		
		//encaminha os carros para abastecimento
		ps.abasteceVeiculos();
		
		System.out.println("#####################");
		System.out.println("#Resumo da Simulação#");
		System.out.println("#####################\n");
		
		//Totaliza abastecimentos do dia por bomba
		ps.totalizarBombas();
		
		//Totaliza abastecimentos do dia por combustível
		ps.totalizarCombustiveis();
	}
}
