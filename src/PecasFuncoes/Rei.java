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
	public int getLinha(Cor corDoRei) {
        
		Casas[][] casas = t.getCasas();
		for (int i = 0; i < casas.length; i++) {
            for (int j = 0; j < casas[i].length; j++) {
                if (casas[i][j].getPiece() instanceof Rei && casas[i][j].getPiece().getCor() == corDoRei) {
                    return i;
                }
            }
        }
        return -1; 
    }
	
	public int getColuna(Cor corDoRei) {
        
		Casas[][] casas = t.getCasas();
		for (int i = 0; i < casas.length; i++) {
            for (int j = 0; j < casas[i].length; j++) {
                if (casas[i][j].getPiece() instanceof Rei && casas[i][j].getPiece().getCor() == corDoRei) {
                    return j;
                }
            }
        }
        return -1; 
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
	    Casas[][] casas = t.getCasas();
	    
	    // Verificar todas as posições ao redor do rei para ameaças
	    int[][] direcoes = {
	        {-1, -1}, {-1, 0}, {-1, 1},
	        {0, -1}, /* {0, 0}, rei */ {0, 1},
	        {1, -1}, {1, 0}, {1, 1}
	    };

	    for (int[] direcao : direcoes) {
	        int novaLinha = linhaFinal + direcao[0];
	        int novaColuna = colunaFinal + direcao[1];

	        if (novaLinha >= 0 && novaLinha < 8 && novaColuna >= 0 && novaColuna < 8) { // Certifique-se que está dentro do tabuleiro
	            Casas casa = casas[novaLinha][novaColuna];
	            if (casa.getEstado() == EstadoCasa.OCUPADA && casa.getPiece().getCor() != this.getCor()) {
	                // Se há uma peça adversária na posição, verificar se ela pode atacar o rei
	                if (casa.getPiece().analisaXeque(novaLinha, novaColuna)) {
	                    return true; // O rei está em xeque por esta peça
	                }
	            }
	        }
	    }

	    return false; // Nenhuma peça está colocando o rei em xeque nas posições adjacentes
	}


}
