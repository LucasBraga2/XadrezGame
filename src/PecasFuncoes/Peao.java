package PecasFuncoes;

import Tabuleiro.*;
import Game.Partida;

public class Peao extends Pecas {

	private boolean movimentado;
	private Tab t;
	private Partida game;

	public Peao(Cor cor, boolean movimentado, Tab tabuleiro, Partida game) {
		super(cor);
		this.movimentado = movimentado;
		this.t = tabuleiro;
		this.game = game;
	}

	public boolean isMovimentado() {
		return movimentado;
	}

	public void setMovimentado(boolean movimentado) {
		this.movimentado = movimentado;
	}

	@Override
	public String getSimbolo() {
		return (getCor() == Cor.BRANCO) ? "P" : "p"; // 'P' para peões brancos, 'p' para peões pretos
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

			if (difHori == 0) {
				if (difVert == 2 && !movimentado) {// Checa se é um movimento de duas casas e se o peão não se moveu
													// ainda.
					// Verifica se ambas as casas no caminho estão livres.
					if (c[linha2][coluna].getEstado() == EstadoCasa.LIVRE
							&& c[linha + (linha2 - linha) / 2][coluna].getEstado() == EstadoCasa.LIVRE) {
						c[linha][coluna].setPiece(null);
						c[linha][coluna].setEstado(EstadoCasa.LIVRE);
						c[linha2][coluna].setPiece(p);
						c[linha2][coluna].setEstado(EstadoCasa.OCUPADA);
						setMovimentado(true); // Atualiza o estado de movimentação do peão.
						return true;
					} else {
						return false; // Caminho bloqueado para movimento de duas casas
					}
				} else if (difVert == 1) {// Checa se é um movimento normal de uma casa para frente.
					if (c[linha2][coluna].getEstado() == EstadoCasa.LIVRE) {
						c[linha][coluna].setPiece(null);
						c[linha][coluna].setEstado(EstadoCasa.LIVRE);
						c[linha2][coluna].setPiece(p);
						c[linha2][coluna].setEstado(EstadoCasa.OCUPADA);
						setMovimentado(true); // Atualiza o estado de movimentação do peão.
						return true;
					} else {
						return false; // Caminho bloqueado para movimento de uma casa
					}
				} else {
					return false; // Movimento vertical inválido
				}
			}
			// Captura na diagonal.
			else if (difHori == 1 && difVert == 1 && podeComer(pos, pos2) == 1) {
				avancar(linha, coluna, linha2, coluna2, p);
				setMovimentado(true); // Marca o peão como movido.
				return true;
			} else {
				return false; // Movimento inválido
			}
		}
			
		return false;
	}

	public int podeComer(String pos, String pos2) {

		int[] lc = t.converte(pos);
		int linha = lc[0];
		int coluna = lc[1];
		int[] lc2 = t.converte(pos2);
		int linha2 = lc2[0];
		int coluna2 = lc2[1];
		Casas[][] c = t.getCasas();

		Pecas pecaOrigem = c[linha][coluna].getPiece();
		Pecas pecaDestino = c[linha2][coluna2].getPiece();

		if (linha2 < 0 || linha2 >= c.length || coluna2 < 0 || coluna2 >= c[0].length) {
			return 0; // Posição fora do tabuleiro
		}

		if (pecaDestino != null && pecaDestino.getCor() != pecaOrigem.getCor()) {// Existe peca na diagonal // diagonal
			return 1;// Existe peca para comer
		} else {
			return 0;// Nao existe peca para comer
		}

	}
	
	@Override
	public void avancar(int linha, int coluna, int linha2, int coluna2, Pecas p) {
		
		Casas[][] c = t.getCasas();
		
		c[linha][coluna].setPiece(null);
		c[linha][coluna].setEstado(EstadoCasa.LIVRE);
		if (c[linha2][coluna2].getEstado() == EstadoCasa.OCUPADA) {
			Pecas pecaCapturada = c[linha2][coluna2].getPiece();
			game.addPecaCapturada(pecaCapturada);
			if(pecaCapturada instanceof Rei) {
				if(pecaCapturada.getCor()==Cor.BRANCO) {
					game.setJogadorGanhador(Cor.PRETO);
				}
				else {
					game.setJogadorGanhador(Cor.BRANCO);
				}
				game.setXequeMate(true);
			}
		}
		
		c[linha2][coluna2].setPiece(p);
		c[linha2][coluna2].setEstado(EstadoCasa.OCUPADA);
	}
	
	@Override
	public boolean analisaXeque(int linhaFinal, int colunaFinal) {
		
		 int direcao = getCor() == Cor.BRANCO ? 1 : -1; // Branco avança para cima, Preto para baixo
	        int[] possiveisLinhas = {linhaFinal + direcao};
	        int[] possiveisColunas = {colunaFinal - 1, colunaFinal + 1};

	        for (int novaColuna : possiveisColunas) {
	            if (novaColuna >= 0 && novaColuna < t.getCasas()[0].length && possiveisLinhas[0] >= 0 && possiveisLinhas[0] < t.getCasas().length) {
	                Casas casaDestino = t.getCasas()[possiveisLinhas[0]][novaColuna];
	                if (casaDestino.getEstado() == EstadoCasa.OCUPADA) {
	                    Pecas peca = casaDestino.getPiece();
	                    if (peca instanceof Rei && peca.getCor() != this.getCor()) {
	                        return true; // O peão pode colocar o Rei em xeque
	                    }
	                }
	            }
	        }

	        return false;
	}

}
