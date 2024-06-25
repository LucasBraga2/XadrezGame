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
		 Casas[][] c = t.getCasas();
		    // Direções para movimento horizontal e vertical
		    int[][] direcoes = {
		        {1, 0}, {0, 1}, {-1, 0}, {0, -1}
		    };

		    // Itera sobre cada direção
		    for (int[] direcao : direcoes) {
		        int linhaAtual = linhaFinal + direcao[0];
		        int colunaAtual = colunaFinal + direcao[1];

		        // Percorre em uma direção até encontrar uma peça ou sair do tabuleiro
		        while (linhaAtual >= 0 && linhaAtual < c.length && colunaAtual >= 0 && colunaAtual < c[0].length) {
		            if (c[linhaAtual][colunaAtual].getEstado() == EstadoCasa.OCUPADA) {
		                Pecas peca = c[linhaAtual][colunaAtual].getPiece();
		                // Verifica se a peça é um rei e se é de cor oposta
		                if (peca instanceof Rei && peca.getCor() != this.getCor()) {
		                    return true; // A torre coloca o rei adversário em xeque
		                }
		                break; // Outra peça bloqueia a visão
		            }
		            linhaAtual += direcao[0];
		            colunaAtual += direcao[1];
		        }
		    }
		    return false;
	}

}
