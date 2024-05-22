package Tabuleiro;

import PecasFuncoes.*;
import Game.Partida;

public class Tab {

	private Casas[][] casas;

	public Tab() {
		casas = new Casas[8][8];
	}

	public Casas[][] getCasas() {
		return casas;
	}

	public int[] converte(String pos) {

		if (pos == null || pos.length() != 2) {
			throw new IllegalArgumentException("Posição fornecida é inválida: " + pos);
		}
		int coluna = pos.charAt(0) - 'A'; // Convertendo 'A' a 'H' para 0 a 7
		int linha = Character.getNumericValue(pos.charAt(1)) - 1; // Convertendo '1' a '8' para 0 a 7
		if (linha < 0 || linha >= 8 || coluna < 0 || coluna >= 8) {
			throw new IllegalArgumentException("Posição fora dos limites do tabuleiro.");
		}
		return new int[] { linha, coluna };
	}

	public Boolean posicaoValida(String pos) {

		 if (pos == null || pos.isEmpty() || pos.length() != 2) {
		        return false;
		    }

		 int coluna = pos.charAt(0) - 'A';
		int linha = Character.getNumericValue(pos.charAt(1)) - 1;

		if (linha < 0 || linha >= 8 || coluna < 0 || coluna >= 8) {
			return false;
		}

		    return true;
	}

	public Pecas pegaPeca(String pos) {
		int[] lc = converte(pos);
		int linha = lc[0];
		int coluna = lc[1];

		return casas[linha][coluna].getPiece();
	}

	public void inicializa_tabuleiro() {

		char[] letras = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };

		for (int i = 0; i < casas.length; i++) { // Linhas
			for (int j = 0; j < casas[i].length; j++) { // Colunas
				casas[i][j] = new Casas();

				if ((i + j) % 2 == 0) {
					casas[i][j].setCor(Cor.BRANCO); // Branco
				} else {
					casas[i][j].setCor(Cor.PRETO); // Preto
				}

				String posi = "" + letras[j] + (i + 1);
				casas[i][j].setPos(posi);

				casas[i][j].setEstado(EstadoCasa.LIVRE);
			}
		}
	}

	public int existePeca(String pos) {
		if (pos.length() != 2) {
			System.out.println("Formato de posição inválido.");
			return -1; // Retornar -1 para indicar erro no formato.
		}

		int[] lc = converte(pos);
		int linha = lc[0];
		int coluna = lc[1];
		// System.out.println("Coluna: "+coluna+" Linha: "+linha);
		if (coluna < 0 || coluna >= 8 || linha < 0 || linha >= 8) {
			System.out.println("Posição fora do tabuleiro.");
			return -1; // Retornar -1 para indicar posição fora do tabuleiro.
		}

		if (casas[linha][coluna].getEstado() == EstadoCasa.OCUPADA) {
			return 0; // Ocupada retorna 0
		} else {
			return 1; // Livre retorna 1
		}
	}

	public void posicionaPeca(Pecas p, String pos) {

		int[] lc = converte(pos);
		int linha = lc[0];
		int coluna = lc[1];

		if (existePeca(pos) != 1) {
			System.out.println("Esta casa está ocupada.");
		} else {
			casas[linha][coluna].setEstado(EstadoCasa.OCUPADA);
			casas[linha][coluna].setPiece(p);
		}
	}

	public void imprimeTabuleiro(Partida g) {
		System.out.println("Brancas, Pontuação: " +"(+"+g.contagemPecasCapturadas(Cor.BRANCO)+")");
		System.out.println("  A B C D E F G H");
		for (int i = 0; i < casas.length; i++) {
			System.out.print((i + 1) + " "); // Imprime o número da linha
			for (int j = 0; j < casas[i].length; j++) {
				Casas casa = casas[i][j];
				if (casa.getEstado() == EstadoCasa.OCUPADA) { // Verifica se a casa está ocupada
					Pecas peca = casa.getPiece();
					System.out.print(peca.getSimbolo() + " "); // Imprime o símbolo da peça
				} else {
					System.out.print(". "); // Imprime um ponto para casas vazias
				}
			}
			System.out.println(); // Nova linha após cada linha do tabuleiro
		}
		System.out.println("Pretas, Pontuação: " +"(+"+g.contagemPecasCapturadas(Cor.PRETO)+")");
	}

}
