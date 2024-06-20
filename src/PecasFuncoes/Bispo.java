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
	public boolean analisaXeque(int linhaFinal, int colunaFinal) {
	    Casas[][] c = t.getCasas();
	    int[][] direcoes = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; // Quatro direções diagonais

	    // Verifica todas as direções para um Rei adversário
	    for (int[] direcao : direcoes) {
	        int linhaAtual = linhaFinal + direcao[0];
	        int colunaAtual = colunaFinal + direcao[1];

	        while (linhaAtual >= 0 && linhaAtual < c.length && colunaAtual >= 0 && colunaAtual < c[0].length) {
	            if (c[linhaAtual][colunaAtual].getEstado() == EstadoCasa.OCUPADA) {
	                Pecas peca = c[linhaAtual][colunaAtual].getPiece();
	                if (peca instanceof Rei && peca.getCor() != this.getCor()) {
	                    return true; // Encontrou um Rei adversário, portanto está em xeque
	                } else {
	                    break; // Outra peça bloqueia a visão
	                }
	            }
	            linhaAtual += direcao[0];
	            colunaAtual += direcao[1];
	        }
	    }
	    return false;
	}
	
	@Override
	public boolean podeAtacar(int linhaAtual, int colunaAtual, int linhaRei, int colunaRei) {
	    if (Math.abs(linhaRei - linhaAtual) == Math.abs(colunaRei - colunaAtual)) {
	        int stepLinha = (linhaRei > linhaAtual) ? 1 : -1;
	        int stepColuna = (colunaRei > colunaAtual) ? 1 : -1;
	        int tempLinha = linhaAtual + stepLinha;
	        int tempColuna = colunaAtual + stepColuna;
	        while (tempLinha != linhaRei && tempColuna != colunaRei) {
	            if (t.getCasas()[tempLinha][tempColuna].getEstado() == EstadoCasa.OCUPADA) {
	                return false; // Caminho bloqueado
	            }
	            tempLinha += stepLinha;
	            tempColuna += stepColuna;
	        }
	        return true;
	    }
	    return false;
	}


}
