package Game;

import Tabuleiro.*;
import PecasFuncoes.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Partida {

	private Tab tabuleiro;
	private Cor JogadorAtual = Cor.BRANCO;
	private Boolean xequeNoPreto = false;
	private Boolean xequeNoBranco = false;
	private Boolean xequeMate = false;
	private int pontuacaoBranca = 0;
	private int pontuacaoPreta = 0;
	
	public Partida(Tab tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public Boolean getXequeNoPreto() {
		return xequeNoPreto;
	}



	public void setXequeNoPreto(Boolean xequeNoPreto) {
		this.xequeNoPreto = xequeNoPreto;
	}



	public Boolean getXequeNoBranco() {
		return xequeNoBranco;
	}



	public void setXequeNoBranco(Boolean xequeNoBranco) {
		this.xequeNoBranco = xequeNoBranco;
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
	
	public void criaArquivo() {
	    try {
	        File arq = new File("jogadasXadrez.txt");
	        if (arq.exists()) {
	            if (arq.delete()) {
	                System.out.println("Arquivo antigo excluído.");
	            } else {
	                System.out.println("Não foi possível excluir o arquivo antigo.");
	            }
	        }
	        if (arq.createNewFile()) {
	            System.out.println("Novo arquivo criado: " + arq.getName());
	        } else {
	            System.out.println("Arquivo já existe.");
	        }
	    } catch (IOException e) {
	        System.out.println("Aconteceu um erro.");
	        e.printStackTrace();
	    }
	}

	public void gravaEmDisco(Players j, Pecas p, String casaOri, String casaDest) {
	    try (FileWriter EscreverArq = new FileWriter("jogadasXadrez.txt", true); // true para modo de anexação
	         BufferedWriter buffer = new BufferedWriter(EscreverArq);
	         PrintWriter out = new PrintWriter(buffer)) {

	        String nomePeca = null;
	        String simbolo = p.getSimbolo();
	        switch (simbolo) {
	            case "P":
	            case "p":
	                nomePeca = "Peão";
	                break;
	            case "T":
	            case "t":
	                nomePeca = "Torre";
	                break;
	            case "Q":
	            case "q":
	                nomePeca = "Rainha";
	                break;
	            case "R":
	            case "r":
	                nomePeca = "Rei";
	                break;
	            case "B":
	            case "b":
	                nomePeca = "Bispo";
	                break;
	            case "C":
	            case "c":
	                nomePeca = "Cavalo";
	                break;
	        }

	        out.println("Jogador: " + j.getNome());
	        out.println("\nPeça movida: " + nomePeca);
	        out.println("\nCasa de origem: " + casaOri);
	        out.println("\nCasa destino: " + casaDest);
	        out.println("----------------------------");
	        System.out.println("Movimento registrado no arquivo.");
	    } catch (IOException e) {
	        System.out.println("Aconteceu um erro.");
	        e.printStackTrace();
	    }
	}

	
	public void lerDisco() {
	    try {
	        File lerArq = new File("jogadasXadrez.txt");
	        Scanner ler = new Scanner(lerArq);
	        while (ler.hasNextLine()) {
	            String data = ler.nextLine();
	            System.out.println(data);
	        }
	        ler.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("Ocorreu um erro.");
	        e.printStackTrace();
	    }
	}

	
	public boolean moveRemoveXeque(String posInicial, String posFinal, Cor corDoJogador) {
	    // Simula o movimento
	    int[] inicial = tabuleiro.converte(posInicial);
	    int[] finalPos = tabuleiro.converte(posFinal);
	    Casas[][] casas = tabuleiro.getCasas();
	    Casas casaInicial = casas[inicial[0]][inicial[1]];
	    Casas casaFinal = casas[finalPos[0]][finalPos[1]];
	    Pecas peca = casaInicial.getPiece();
	    Pecas pecaCapturada = casaFinal.getPiece();

	    // Realiza o movimento
	    casaInicial.setPiece(null);
	    casaFinal.setPiece(peca);

	    // Verifica se ainda está em xeque
	    boolean aindaEmXeque = verificaSeReiEmXeque(corDoJogador);

	    // Desfaz o movimento
	    casaInicial.setPiece(peca);
	    casaFinal.setPiece(pecaCapturada);

	    return !aindaEmXeque;
	}



	public boolean verificaSeReiEmXeque(Cor corDoRei) {
	    Rei rei = tabuleiro.encontreORei(corDoRei);
	    int linhaRei = rei.getLinha(corDoRei);
	    int colunaRei = rei.getColuna(corDoRei);

	    if (linhaRei == -1 || colunaRei == -1) {
	        throw new IllegalStateException("O rei da cor " + corDoRei + " não foi encontrado no tabuleiro.");
	    }

	    for (int i = 0; i < tabuleiro.getCasas().length; i++) {
	        for (int j = 0; j < tabuleiro.getCasas()[i].length; j++) {
	            Pecas peca = tabuleiro.getCasas()[i][j].getPiece();
	            if (peca != null && peca.getCor() != corDoRei) {
	                if (peca.podeAtacar(i, j, linhaRei, colunaRei)) {
	                    return true;  // O Rei está em xeque
	                }
	            }
	        }
	    }
	    return false;
	}
	
	public List<Pecas> listaDePecasAdversarias(Cor corDoRei) {
	    
		  List<Pecas> adversarias = new ArrayList<>();
		    Casas[][] c = tabuleiro.getCasas();
		    Cor corAdversaria = (corDoRei == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;

		    for (int i = 0; i < c.length; i++) {
		        for (int j = 0; j < c[i].length; j++) {
		            Pecas peca = c[i][j].getPiece();
		            if (peca != null && peca.getCor() == corAdversaria) {
		                adversarias.add(peca);
		            }
		        }
		    }
		    return adversarias;
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
