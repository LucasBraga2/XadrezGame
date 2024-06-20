package PecasFuncoes;

import Game.Partida;
import Tabuleiro.*;

public class Rainha extends Pecas {

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
			int direcaoLinha = Integer.compare(linha2, linha);// Retorna 1, -1 ou 0(1 PARA SUBIR OU DIREITA)
			int direcaoColuna = Integer.compare(coluna2, coluna);
			int linhaAtual = linha + direcaoLinha;
			int colunaAtual = coluna + direcaoColuna;

			// Verifica se o movimento é na mesma coluna ou na mesma linha
			if (linha == linha2 || coluna == coluna2) {

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
				
			} else if (difHori == difVert) {// Movimento na diagonal

				// Verifica se o caminho até o destino está livre
				while (linhaAtual != linha2 && colunaAtual != coluna2) {
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
		    int[][] direcoes = {
		        {1, 0}, {0, 1}, {-1, 0}, {0, -1}, // Movimentos verticais e horizontais (Torre)
		        {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Movimentos diagonais (Bispo)
		    };

		    // Verificar em todas as direções
		    for (int[] direcao : direcoes) {
		        int linhaAtual = linhaFinal + direcao[0];
		        int colunaAtual = colunaFinal + direcao[1];

		        while (linhaAtual >= 0 && linhaAtual < c.length && colunaAtual >= 0 && colunaAtual < c[0].length) {
		            if (c[linhaAtual][colunaAtual].getEstado() == EstadoCasa.OCUPADA) {
		                Pecas peca = c[linhaAtual][colunaAtual].getPiece();
		                if (peca instanceof Rei && peca.getCor() != this.getCor()) {
		                    return true; // Encontrou um Rei adversário, portanto está em xeque
		                }
		                break; // Outra peça bloqueia a visão
		            }
		            linhaAtual += direcao[0];
		            colunaAtual += direcao[1];
		        }
		    }
		    return false;
	}
	
	
	@Override
	public boolean podeAtacar(int linhaAtual, int colunaAtual, int linhaRei, int colunaRei) {
	    // Combina os movimentos da torre e do bispo
	    return podeAtacarComoTorre(linhaAtual, colunaAtual, linhaRei, colunaRei) ||
	           podeAtacarComoBispo(linhaAtual, colunaAtual, linhaRei, colunaRei);
	}
	
	private boolean podeAtacarComoTorre(int linhaAtual, int colunaAtual, int linhaRei, int colunaRei) {
	    if (linhaAtual == linhaRei || colunaAtual == colunaRei) {
	        int stepLinha = (linhaRei == linhaAtual) ? 0 : (linhaRei > linhaAtual) ? 1 : -1;
	        int stepColuna = (colunaRei == colunaAtual) ? 0 : (colunaRei > colunaAtual) ? 1 : -1;
	        int tempLinha = linhaAtual + stepLinha;
	        int tempColuna = colunaAtual + stepColuna;
	        while (tempLinha != linhaRei || tempColuna != colunaRei) {
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
	
	private boolean podeAtacarComoBispo(int linhaAtual, int colunaAtual, int linhaRei, int colunaRei) {
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
