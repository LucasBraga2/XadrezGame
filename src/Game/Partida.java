package Game;

import Tabuleiro.*;
import PecasFuncoes.*;
import java.util.ArrayList;

public class Partida {

	private Tab tabuleiro;
	private Cor JogadorAtual = Cor.BRANCO;
	private Boolean xeque = false;
	private Boolean xequeMate = false;
	private int calculo = 0;

	private ArrayList<Pecas> pecasDePb = new ArrayList<>();// Pecas do player branco
	private ArrayList<Pecas> pecasDePp = new ArrayList<>();// Pecas do player preto
	private ArrayList<Pecas> pecasCapturadasPb = new ArrayList<>();// Pecas capturadas pelo player branco
	private ArrayList<Pecas> pecasCapturadasPp = new ArrayList<>();// Pecas capturadas pelo player preto

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

	public ArrayList<Pecas> getPecasCapturadasPb() {
		return pecasCapturadasPb;
	}

	public void setPecasCapturadasPb(ArrayList<Pecas> pecasCapturadasPb) {
		this.pecasCapturadasPb = pecasCapturadasPb;
	}

	public ArrayList<Pecas> getPecasCapturadasPp() {
		return pecasCapturadasPp;
	}

	public void setPecasCapturadasPp(ArrayList<Pecas> pecasCapturadasPp) {
		this.pecasCapturadasPp = pecasCapturadasPp;
	}

	public ArrayList<Pecas> getPecasDePb() {
		return pecasDePb;
	}

	public void setPecasDePb(ArrayList<Pecas> pecasDePb) {
		this.pecasDePb = pecasDePb;
	}

	public ArrayList<Pecas> getPecasDePp() {
		return pecasDePp;
	}

	public void setPecasDePp(ArrayList<Pecas> pecasDePp) {
		this.pecasDePp = pecasDePp;
	}
	
	public int getCalculo() {
		return calculo;
	}

	public void setCalculo(int calculo) {
		this.calculo = calculo;
	}

	public void removePecaDaLista(Pecas p) {

		if (p.getCor() == Cor.BRANCO) {
			pecasDePb.remove(p);
			pecasCapturadasPp.add(p);
		} else {
			pecasDePp.remove(p);
			pecasCapturadasPb.add(p);
		}
	}

	public int contagemPecasCapturadas(Cor cor) {
		
		if(cor == Cor.BRANCO) {
			for(Pecas p : pecasCapturadasPb) {
				calculo += calculo(p);
			}
		}
		else {
			for(Pecas p : pecasCapturadasPp) {
				calculo += calculo(p);
			}
		}
		return calculo;
	}

	public int calculo(Pecas p) {

		 switch (p.getClass().getSimpleName()) {
	        case "Peao": return 1;
	        case "Bispo":
	        case "Cavalo": return 3;
	        case "Torre": return 5;
	        case "Rainha": return 9;
	        default: return 0;
	    }
	}

	public void posicionandoPeca(Pecas p, String pos) {
		tabuleiro.posicionaPeca(p, pos);
		if (p.getCor() == Cor.BRANCO) {
			pecasDePb.add(p);
		} else {
			pecasDePp.add(p);
		}
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
