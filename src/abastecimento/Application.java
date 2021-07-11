package abastecimento;

import abastecimento.service.AbastecimentoService;
import abastecimento.service.PostoService;

public class Application {

	public static void main(String[] args) {
		
		//O construtor de PostoService carrega a partir dos arquivos csv os 
		//repositorios TipoCombustivelRepository, BombaRepository e VeiculoRepository
		//que são necessários para encaminhar os veiculos para abastecimento
		PostoService ps = new PostoService();
		
		//encaminha os carros para abastecimento
		ps.abasteceVeiculos();		

		//Inicializa o serviço de gerenciamento de abastecimento
		AbastecimentoService as = new AbastecimentoService(ps.getAr(), ps.getBr(), ps.getTcr());
		
		//Imprime o resultado da simulação na ordem cronológica dos eventos
		as.imprimeAbastecimentos();
		
		System.out.println("#####################");
		System.out.println(" Resumo da Simulação ");
		System.out.println("#####################\n");
		
		//Totaliza abastecimentos do dia por bomba
		as.totalizarBombas();
		
		//Totaliza abastecimentos do dia por combustível
		as.totalizarCombustiveis();
	}
}
