package PecasFuncoes;

import Game.Partida;
import Tabuleiro.*;

public class Rainha extends Pecas{
	
	private Tab t;
	private Partida game;
	
	public Rainha(Cor cor, Tab tabuleiro, Partida game) {
		super(cor);
		this.t = tabuleiro;
		this.game = game;	
	}
	
	@Override
	public String getSimbolo() {
        return (getCor() == Cor.BRANCO) ? "Q" : "q"; // 'P' para peões brancos, 'p' para peões pretos
    }
	
	@Override
	public boolean move(String pos, String pos2) {
		return false;
	}
	

}
