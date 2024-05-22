package PecasFuncoes;

import Tabuleiro.Cor;


public abstract class Pecas {

	private Cor cor;
	
	public Pecas(Cor cor) {
		super();
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public abstract String getSimbolo();

	public abstract boolean movimentacaoPeca(String posInicial, String posFinal);
	
	public abstract void avancar(int linha, int coluna, int linha2, int coluna2, Pecas p);
		
	

}