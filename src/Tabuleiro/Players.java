package Tabuleiro;

import PecasFuncoes.*;
import java.util.ArrayList;

public class Players {

	private String nome;
	private Cor cor;
	private ArrayList<Pecas> pLista;

	public Players(String nome, Cor cor, ArrayList<Pecas> pLista) {
		this.nome = nome;
		this.cor = cor;
		this.pLista = pLista;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cor getcor() {
		return cor;
	}

	public void setcor(Cor cor) {
		this.cor = cor;
	}
	
	public ArrayList<Pecas> getpLista() {
		return pLista;
	}

	public void setpLista(ArrayList<Pecas> pLista) {
		this.pLista = pLista;
	}

	public void imprime_player(int x){
		
	System.out.println("Jogador "+x);
	System.out.println("Nome: "+ nome);
	if(cor == Cor.BRANCO) {
		System.out.println("Cor das peças: Brancas");
		System.out.println("-----------------------------------");
	}
	else {
		System.out.println("Cor das peças: Pretas");
		System.out.println("-----------------------------------");
	}
	
	}

}
