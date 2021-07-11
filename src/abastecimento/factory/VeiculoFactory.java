package abastecimento.factory;

import java.io.BufferedReader;
import java.io.FileReader;

import abastecimento.model.ModeloVeiculo;
import abastecimento.model.Veiculo;
import abastecimento.repository.ModeloVeiculoRepository;
import abastecimento.repository.VeiculoRepository;

public class VeiculoFactory {

	private VeiculoRepository vr;
	private ModeloVeiculoRepository mvr;
	
	public VeiculoFactory(VeiculoRepository vr, ModeloVeiculoRepository mvr) {
		this.vr=vr;
		this.mvr=mvr;
	}

	public void leArquivoCsv(String arquivoVeiculos) {
		String row;
		int contador = 0;
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(arquivoVeiculos));
			while ((row = csvReader.readLine()) != null) {
				if (contador != 0) { //contador 0 é o cabeçalho do arquivo e não contem dados
				    String[] data = row.split(";");
				    				    
				    String stringModelo = data[0];
				    String stringPlaca = data[1];
				    
				    Veiculo veiculo = new Veiculo();
				    
				    ModeloVeiculo modelo = mvr.getModelo(stringModelo);
				    veiculo.setModelo(modelo);
				    veiculo.setPlaca(stringPlaca);
				    
				    
				    vr.add(veiculo);
				    
				}
				contador ++;
			}
			csvReader.close();			
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
