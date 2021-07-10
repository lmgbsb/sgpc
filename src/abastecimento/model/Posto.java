package abastecimento.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Posto {
	
	private int id;
	private String nome;
	private ArrayList <Bomba> bombas;
	
}
