package Game;

import Tabuleiro.*;
import PecasFuncoes.*;
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
	private String posDoXeque;
	private Pecas pDoXeque;
	private Cor jogadorGanhador;
	
	public Cor getJogadorGanhador() {
		return jogadorGanhador;
	}

	public void setJogadorGanhador(Cor jogadorGanhador) {
		this.jogadorGanhador = jogadorGanhador;
	}

	public Pecas getpDoXeque() {
		return pDoXeque;
	}

	public void setpDoXeque(Pecas pDoXeque) {
		this.pDoXeque = pDoXeque;
	}

	public String getPosDoXeque() {
		return posDoXeque;
	}

	public void setPosDoXeque(String posDoXeque) {
		this.posDoXeque = posDoXeque;
	}

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

	
	public void lerDisco(String nomeJogador) {
	    try {
	        File lerArq = new File("jogadasXadrez.txt");
	        Scanner ler = new Scanner(lerArq);
	        boolean lerTodas = (nomeJogador == null || nomeJogador.isEmpty());
	        boolean jogadorEncontrado = false;
	        
	        while (ler.hasNextLine()) {
	            String data = ler.nextLine();
	            if (lerTodas || data.contains("Jogador: " + nomeJogador)) {
	                jogadorEncontrado = true;
	                System.out.println(data);
	                // Imprime as próximas linhas relacionadas a esta jogada até encontrar a linha separadora
	                while (ler.hasNextLine()) {
	                    data = ler.nextLine();
	                    System.out.println(data);
	                    if (data.equals("----------------------------")) {
	                        break;
	                    }
	                }
	            }
	        }
	        ler.close();
	        if (!jogadorEncontrado && !lerTodas) {
	            System.out.println("Nenhuma jogada encontrada para o jogador: " + nomeJogador);
	        }
	    } catch (FileNotFoundException e) {
	        System.out.println("O arquivo de jogadas não foi encontrado.");
	        e.printStackTrace();
	    }
	}



	public void menu(Players p1, Players p2) {
		int opcao = 0;
		Scanner sc = new Scanner(System.in);

		while (opcao != 4) {
		    System.out.println("Verificar jogadas de: ");
		    System.out.println("1 - " + p1.getNome());
		    System.out.println("2 - " + p2.getNome());
		    System.out.println("3 - Todas as jogadas do jogo");
		    System.out.println("4 - Sair");
		    
		    System.out.print("Escolha uma opção: ");
		    opcao = sc.nextInt();
		    sc.nextLine();  

		    switch (opcao) {
		        case 1:
		            lerDisco(p1.getNome());
		            break;
		        case 2:
		            lerDisco(p2.getNome());
		            break;
		        case 3:
		            lerDisco(null);  
		            break;
		        case 4:
		            System.out.println("Saindo...");
		            break;
		        default:
		            System.out.println("Opção inválida! Tente novamente.");
		            break;
		    }
		    if (opcao != 4) {
		        System.out.println("Pressione Enter para continuar...");
		        sc.nextLine();  
		    }
		}

		sc.close();

	}
	
	public void desfazerMovimento(String posInicial, String posFinal, Pecas comida) {
	    int[] inicial = tabuleiro.converte(posInicial);
	    int[] finalPos = tabuleiro.converte(posFinal);
	    Casas[][] casas = tabuleiro.getCasas();
	    Casas casaInicial = casas[inicial[0]][inicial[1]];
	    Casas casaFinal = casas[finalPos[0]][finalPos[1]];
	    Pecas peca = casaFinal.getPiece();

	    casaInicial.setPiece(peca);
	    casaFinal.setPiece(comida);
	    casaInicial.setEstado(EstadoCasa.OCUPADA);
	    casaFinal.setEstado(comida != null ? EstadoCasa.OCUPADA : EstadoCasa.LIVRE);
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
