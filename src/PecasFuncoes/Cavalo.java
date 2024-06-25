package PecasFuncoes;

import Game.Partida;
import Tabuleiro.Casas;
import Tabuleiro.Cor;
import Tabuleiro.EstadoCasa;
import Tabuleiro.Tab;

public class Cavalo extends Pecas {
	
	private Tab t;
	private Partida game;
	
	public Cavalo(Cor cor, Tab tabuleiro, Partida game) {
		super(cor);
		this.t = tabuleiro;
		this.game = game;	
	}

	@Override
	public String getSimbolo() {
		return (getCor() == Cor.BRANCO) ? "C" : "c"; // 'P' para peões brancos, 'p' para peões pretos
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
			
			if((difHori == 1 && difVert == 2) || (difHori == 2 && difVert == 1)) {
				
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
		 
		int[][] direcoes = {
		            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
		            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
		        };

		        Casas[][] c = t.getCasas();
		        for (int[] dir : direcoes) {
		            int linhaNova = linhaFinal + dir[0];
		            int colunaNova = colunaFinal + dir[1];

		            // Verifica se a nova posição está dentro dos limites do tabuleiro
		            if (linhaNova >= 0 && linhaNova < c.length && colunaNova >= 0 && colunaNova < c[0].length) {
		                if (c[linhaNova][colunaNova].getEstado() == EstadoCasa.OCUPADA) {
		                    Pecas peca = c[linhaNova][colunaNova].getPiece();
		                    // Verifica se a peça é um rei e se é de cor oposta
		                    if (peca instanceof Rei && peca.getCor() != this.getCor()) {
		                        return true; // O cavalo está colocando o rei adversário em xeque
		                    }
		                }
		            }
		        }
		        return false; // Nenhum rei em xeque encontrado nas posições possíveis
		    }

}
