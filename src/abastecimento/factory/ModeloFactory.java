package abastecimento.factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import abastecimento.model.ConsumoCombustivel;
import abastecimento.model.ModeloVeiculo;
import abastecimento.repository.ModeloVeiculoRepository;
import abastecimento.repository.TipoCombustivelRepository;

public class ModeloFactory {

	ArrayList<ModeloVeiculo> modelos;
	
	TipoCombustivelRepository tcr;
	ModeloVeiculoRepository mvr;
	
	public ModeloFactory(TipoCombustivelRepository tcr, ModeloVeiculoRepository mvr) {
		this.tcr=tcr;
		this.mvr=mvr;
	}
	
	public void leArquivoCsv(String caminhoArquivo) {
		
		String row;
		int contador = 0;
		try {
			//String current = new java.io.File(".").getCanonicalPath();
			//System.out.println(current);
			
			BufferedReader csvReader = new BufferedReader(new FileReader(caminhoArquivo));
			while ((row = csvReader.readLine()) != null) {
				if (contador != 0) { //contador 0 é o cabeçalho do arquivo e não contem dados
				    String[] data = row.split(";");
				    
				    String stringModelo = data[0];
				    String stringConsumoEtanol = data[1].replace(",", ".");
				    String stringConsumoGasolina = data[2].replace(",", ".");
				    String stringCapacidadeTanque = data[3].replace(",", ".");
				    
				    ModeloVeiculo modelo = new ModeloVeiculo();	
				    modelo.setDescricao(stringModelo);
				    modelo.setCapacidadeTanque(Double.parseDouble(stringCapacidadeTanque));
				    
				    if(stringConsumoGasolina != null && stringConsumoGasolina != "") {
				    	ConsumoCombustivel comsumoGasolina = new ConsumoCombustivel(tcr.get("GASOLINA"));
				    	comsumoGasolina.setQuilometrosPorLitro(Double.parseDouble(stringConsumoGasolina));
				    	modelo.adicionaTipoCombustivel(comsumoGasolina);
				    }
				    
				    if(stringConsumoEtanol != null && stringConsumoEtanol != "") {
				    	ConsumoCombustivel comsumoEtanol = new ConsumoCombustivel(tcr.get("ETANOL"));
				    	comsumoEtanol.setQuilometrosPorLitro(Double.parseDouble(stringConsumoEtanol));
				    	modelo.adicionaTipoCombustivel(comsumoEtanol);
				    }
				    
				    mvr.add(modelo); 	
				}
			    contador ++;
			}
			
			csvReader.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
