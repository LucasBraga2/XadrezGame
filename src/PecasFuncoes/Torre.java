package PecasFuncoes;

import Game.Partida;
import Tabuleiro.*;

public class Torre extends Pecas {

	private Tab t;
	private Partida game;

	public Torre(Cor cor, Tab tabuleiro, Partida game) {
		super(cor);
		this.t = tabuleiro;
		this.game = game;
	}

	@Override
	public String getSimbolo() {
		return (getCor() == Cor.BRANCO) ? "T" : "t"; // 'P' para peões brancos, 'p' para peões pretos
	}

	@Override
	public boolean movimentacaoPeca(String posInicial, String posFinal) {
		int[] lc = t.converte(posInicial);
		int linha = lc[0];
		int coluna = lc[1];
		int[] lc2 = t.converte(posFinal);
		int linha2 = lc2[0];
		int coluna2 = lc2[1];
		Casas[][] c = t.getCasas();

		if (c[linha][coluna].getPiece() != null) {
			Pecas p = c[linha][coluna].getPiece();// Pegando peca da posicao
			
			// Verifica se o movimento é na mesma coluna ou na mesma linha
			if (linha == linha2 || coluna == coluna2) {
				int direcaoLinha = Integer.compare(linha2, linha);//Retorna 1, -1 ou 0(1 PARA SUBIR OU DIREITA)
				int direcaoColuna = Integer.compare(coluna2, coluna);
				int linhaAtual = linha + direcaoLinha;
				int colunaAtual = coluna + direcaoColuna;

				// Verifica se o caminho até o destino está livre
				while (linhaAtual != linha2 || colunaAtual != coluna2) {
					if (c[linhaAtual][colunaAtual].getEstado() == EstadoCasa.OCUPADA) {
						return false; // Caminho bloqueado
					}
					linhaAtual += direcaoLinha;
					colunaAtual += direcaoColuna;
				}
				
				avancar(linha, coluna, linha2, coluna2, p);
				return true;
			}
		}
		return false; // Movimento inválido
	}
	
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

}
