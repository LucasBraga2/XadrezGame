package PecasFuncoes;

import Game.Partida;
import Tabuleiro.*;

public class Bispo extends Pecas {
	private Tab t;
	private Partida game;

	public Bispo(Cor cor, Tab tabuleiro, Partida game) {
		super(cor);
		this.t = tabuleiro;
		this.game = game;
	}

	@Override
	public String getSimbolo() {
		return (getCor() == Cor.BRANCO) ? "B" : "b"; // 'P' para peões brancos, 'p' para peões pretos
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
			if (difHori == difVert) {
				int direcaoLinha = Integer.compare(linha2, linha);
				int direcaoColuna = Integer.compare(coluna2, coluna);
				int linhaAtual = linha + direcaoLinha;
				int colunaAtual = coluna + direcaoColuna;

				while (linhaAtual != linha2 && colunaAtual != coluna2) {
					if (c[linhaAtual][colunaAtual].getEstado() == EstadoCasa.OCUPADA) {
						return false;
					}
					linhaAtual += direcaoLinha;
					colunaAtual += direcaoColuna;
				}

					if (c[linha2][coluna2].getEstado() == EstadoCasa.LIVRE) { // Movimento normal
						c[linha][coluna].setPiece(null);
						c[linha][coluna].setEstado(EstadoCasa.LIVRE);
						c[linha2][coluna2].setPiece(p);
						c[linha2][coluna2].setEstado(EstadoCasa.OCUPADA);
						return true;
					} else { // Captura
						Pecas pecaCapturada = c[linha2][coluna2].getPiece();
						game.removePecaDaLista(pecaCapturada);
						c[linha][coluna].setPiece(null);
						c[linha][coluna].setEstado(EstadoCasa.LIVRE);
						c[linha2][coluna2].setPiece(p);
						c[linha2][coluna2].setEstado(EstadoCasa.OCUPADA);
						return true;
					}
				} 
			} 
		return false;
	}

}
