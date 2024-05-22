package PecasFuncoes;

import Game.Partida;
import Tabuleiro.Casas;
import Tabuleiro.Cor;
import Tabuleiro.EstadoCasa;
import Tabuleiro.Tab;

public class Rei extends Pecas {
	
	private Tab t;
	private Partida game;
	
	public Rei(Cor cor, Tab tabuleiro, Partida game) {
		super(cor);
		this.t = tabuleiro;
		this.game = game;
	}

	@Override
	public String getSimbolo() {
		return (getCor() == Cor.BRANCO) ? "R" : "r"; // 'P' para peões brancos, 'p' para peões pretos
	}
	
	@Override
	public boolean movimentacaoPeca(String pos, String pos2) {
		return false;
	}
	
	public void avancar(int linha, int coluna, int linha2, int coluna2, Pecas p) {
		
		Casas[][] c = t.getCasas();
		
		c[linha][coluna].setPiece(null);
		c[linha][coluna].setEstado(EstadoCasa.LIVRE);
		if (c[linha2][coluna2].getEstado() == EstadoCasa.OCUPADA) {
			Pecas pecaCapturada = c[linha2][coluna2].getPiece();
			game.removePecaDaLista(pecaCapturada);
		}
		c[linha2][coluna2].setPiece(p);
		c[linha2][coluna2].setEstado(EstadoCasa.OCUPADA);
	}

}
