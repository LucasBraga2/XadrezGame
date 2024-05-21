package PecasFuncoes;

import Game.Partida;
import Tabuleiro.Casas;
import Tabuleiro.Cor;
import Tabuleiro.EstadoCasa;
import Tabuleiro.Tab;

public class Cavalo extends Pecas {
	
	private Tab t;
	private Partida game;
	
	public Cavalo(Cor cor, Tab tabuleiro) {
		super(cor);
		this.t = tabuleiro;
		this.game = new Partida(t);	
	}

	@Override
	public String getSimbolo() {
		return (getCor() == Cor.BRANCO) ? "C" : "c"; // 'P' para peões brancos, 'p' para peões pretos
	}

	@Override
	public boolean move(String pos, String pos2) {
		
		int[] lc = t.converte(pos);
		int linha = lc[0];
		int coluna = lc[1];
		int[] lc2 = t.converte(pos2);
		int linha2 = lc2[0];
		int coluna2 = lc2[1];
		int difHori = Math.abs(coluna2 - coluna); // Diferença horizontal 
		int difVert = Math.abs(linha2 - linha); // Diferença vertical 
		Casas[][] c = t.getCasas();
		
		if (c[linha][coluna].getPiece() != null) {
			Pecas p = c[linha][coluna].getPiece();// Pegando peca da posicao
			
			if((difHori == 1 && difVert == 2) || (difHori == 2 && difVert == 1)) {
				if(c[linha2][coluna2].getEstado() == EstadoCasa.LIVRE) {//Movimento normal
					c[linha][coluna].setPiece(null);
		            c[linha][coluna].setEstado(EstadoCasa.LIVRE);
		            c[linha2][coluna2].setPiece(p);
		            c[linha2][coluna2].setEstado(EstadoCasa.OCUPADA);
		            return true;
				}
				else {//Captura
					Pecas pecaCapturada = c[linha2][coluna2].getPiece();
					game.removePecaDaLista(pecaCapturada);
					c[linha][coluna].setPiece(null);
		            c[linha][coluna].setEstado(EstadoCasa.LIVRE);
		            c[linha2][coluna2].setPiece(p);
		            c[linha2][coluna2].setEstado(EstadoCasa.OCUPADA);
		            return true;
				}
			}
			else {
				return false;
			}
		}
		return false;
	}

}
