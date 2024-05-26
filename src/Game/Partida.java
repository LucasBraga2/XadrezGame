package Game;

import Tabuleiro.*;
import PecasFuncoes.*;

public class Partida {

	private Tab tabuleiro;
	private Cor JogadorAtual = Cor.BRANCO;
	private Boolean xeque = false;
	private Boolean xequeMate = false;
	private int pontuacaoBranca = 0;
	private int pontuacaoPreta = 0;

	public Partida(Tab tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public Boolean getXeque() {
		return xeque;
	}

	public void setXeque(Boolean xeque) {
		this.xeque = xeque;
	}

	public Boolean getXequeMate() {
		return xequeMate;
	}

	public void setXequeMate(Boolean xequeMate) {
		this.xequeMate = xequeMate;
	}

	public Cor getJogadorAtual() {
		return JogadorAtual;
	}

	public void setJogadorAtual(Cor jogadorAtual) {
		JogadorAtual = jogadorAtual;
	}
	
	public void addPecaCapturada(Pecas p) {

		if (p.getCor() == Cor.BRANCO) {
			pontuacaoPreta += calculo(p);
		} else {
			pontuacaoBranca += calculo(p);
		}
	}

	public int calculo(Pecas p) {

		 if (p instanceof Peao) {
		        return 1;
		    } else if (p instanceof Bispo || p instanceof Cavalo) {
		        return 3;
		    } else if (p instanceof Torre) {
		        return 5;
		    } else if (p instanceof Rainha) {
		        return 9;
		    } else {
		        return 0;
		    }
	}
	
	public int getPontuacao(Cor cor) {
		
		return (cor == Cor.BRANCO)? pontuacaoBranca : pontuacaoPreta;
	}

	public void posicionandoPeca(Pecas p, String pos) {
		tabuleiro.posicionaPeca(p, pos);
	}

	public void configIncial() {
		posicionandoPeca(new Torre(Cor.BRANCO, tabuleiro, this), "A1");
		posicionandoPeca(new Cavalo(Cor.BRANCO, tabuleiro, this), "B1");
		posicionandoPeca(new Bispo(Cor.BRANCO, tabuleiro, this), "C1");
		posicionandoPeca(new Rainha(Cor.BRANCO, tabuleiro, this), "D1");
		posicionandoPeca(new Rei(Cor.BRANCO, tabuleiro, this), "E1");
		posicionandoPeca(new Bispo(Cor.BRANCO, tabuleiro, this), "F1");
		posicionandoPeca(new Cavalo(Cor.BRANCO, tabuleiro, this), "G1");
		posicionandoPeca(new Torre(Cor.BRANCO, tabuleiro, this), "H1");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro, this), "A2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro, this), "B2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro, this), "C2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro, this), "D2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro, this), "E2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro, this), "F2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro, this), "G2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro, this), "H2");
		posicionandoPeca(new Torre(Cor.PRETO, tabuleiro, this), "A8");
		posicionandoPeca(new Cavalo(Cor.PRETO, tabuleiro, this), "B8");
		posicionandoPeca(new Bispo(Cor.PRETO, tabuleiro, this), "C8");
		posicionandoPeca(new Rainha(Cor.PRETO, tabuleiro, this), "D8");
		posicionandoPeca(new Rei(Cor.PRETO, tabuleiro, this), "E8");
		posicionandoPeca(new Bispo(Cor.PRETO, tabuleiro, this), "F8");
		posicionandoPeca(new Cavalo(Cor.PRETO, tabuleiro, this), "G8");
		posicionandoPeca(new Torre(Cor.PRETO, tabuleiro, this), "H8");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro, this), "A7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro, this), "B7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro, this), "C7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro, this), "D7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro, this), "E7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro, this), "F7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro, this), "G7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro, this), "H7");

	}

}
