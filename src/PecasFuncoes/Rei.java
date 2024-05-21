package PecasFuncoes;

import Game.Partida;
import Tabuleiro.Cor;
import Tabuleiro.Tab;

public class Rei extends Pecas {
	
	private Tab t;
	private Partida game;
	
	public Rei(Cor cor, Tab tabuleiro) {
		super(cor);
		this.t = tabuleiro;
		this.game = new Partida(t);
	}

	@Override
	public String getSimbolo() {
		return (getCor() == Cor.BRANCO) ? "R" : "r"; // 'P' para peões brancos, 'p' para peões pretos
	}
	
	@Override
	public boolean move(String pos, String pos2) {
		return false;
	}

}
