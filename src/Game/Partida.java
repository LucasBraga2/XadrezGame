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
		posicionandoPeca(new Torre(Cor.BRANCO, tabuleiro), "A1");
		posicionandoPeca(new Cavalo(Cor.BRANCO, tabuleiro), "B1");
		posicionandoPeca(new Bispo(Cor.BRANCO, tabuleiro), "C1");
		posicionandoPeca(new Rainha(Cor.BRANCO, tabuleiro), "D1");
		posicionandoPeca(new Rei(Cor.BRANCO, tabuleiro), "E1");
		posicionandoPeca(new Bispo(Cor.BRANCO, tabuleiro), "F1");
		posicionandoPeca(new Cavalo(Cor.BRANCO, tabuleiro), "G1");
		posicionandoPeca(new Torre(Cor.BRANCO, tabuleiro), "H1");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro), "A2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro), "B2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro), "C2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro), "D2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro), "E2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro), "F2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro), "G2");
		posicionandoPeca(new Peao(Cor.BRANCO, false, tabuleiro), "H2");
		posicionandoPeca(new Torre(Cor.PRETO, tabuleiro), "A8");
		posicionandoPeca(new Cavalo(Cor.PRETO, tabuleiro), "B8");
		posicionandoPeca(new Bispo(Cor.PRETO, tabuleiro), "C8");
		posicionandoPeca(new Rainha(Cor.PRETO, tabuleiro), "D8");
		posicionandoPeca(new Rei(Cor.PRETO, tabuleiro), "E8");
		posicionandoPeca(new Bispo(Cor.PRETO, tabuleiro), "F8");
		posicionandoPeca(new Cavalo(Cor.PRETO, tabuleiro), "G8");
		posicionandoPeca(new Torre(Cor.PRETO, tabuleiro), "H8");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro), "A7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro), "B7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro), "C7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro), "D7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro), "E7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro), "F7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro), "G7");
		posicionandoPeca(new Peao(Cor.PRETO, false, tabuleiro), "H7");

	}

}
