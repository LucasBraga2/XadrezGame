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
			
			if((difVert == 1 && difHori == 0) || (difVert == 0 && difHori == 1)|| (difVert == difHori && difVert == 1 && difHori == 1 )) {
				avancar(linha, coluna, linha2, coluna2, p);
				return true;
			}
			
		
		
		}
		return false;
	}
	
	@Override
	public void avancar(int linha, int coluna, int linha2, int coluna2, Pecas p) {
		
		Casas[][] c = t.getCasas();
		
		c[linha][coluna].setPiece(null);
		c[linha][coluna].setEstado(EstadoCasa.LIVRE);
		if (c[linha2][coluna2].getEstado() == EstadoCasa.OCUPADA) {
			Pecas pecaCapturada = c[linha2][coluna2].getPiece();
			game.addPecaCapturada(pecaCapturada);
		}
		c[linha2][coluna2].setPiece(p);
		c[linha2][coluna2].setEstado(EstadoCasa.OCUPADA);
	}
	
	@Override
	public boolean analisaXeque(int linha, int coluna, int direcaoLinha, int direcaoColuna) {
		
		Casas[][] c = t.getCasas();
		int linhaAtual = linha + direcaoLinha;
		int colunaAtual = coluna + direcaoColuna;
		
		while (linhaAtual != linha && colunaAtual != coluna) {
			if (c[linhaAtual][colunaAtual].getEstado() == EstadoCasa.OCUPADA) {
				if(c[linhaAtual][colunaAtual].getPiece() instanceof Rei) {
					return true;
				}
				return false;
			}
			linhaAtual += direcaoLinha;
			colunaAtual += direcaoColuna;
		}
		return false;
	}

}
