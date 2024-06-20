package Program;
import java.io.*;
import Tabuleiro.Cor;
import Tabuleiro.Players;
import Tabuleiro.Tab;
import PecasFuncoes.*;
import java.util.Scanner;
import Game.Partida;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Tab t = new Tab();// Tabuleiro
		Partida match = new Partida(t);
		match.criaArquivo();

		t.inicializa_tabuleiro();

		System.out.println("Digite o nome do player de cor branca");
		Players p1 = new Players(sc.next(), Cor.BRANCO);// Player 1
		System.out.println("Digite o nome do player de cor preta");
		Players p2 = new Players(sc.next(), Cor.PRETO);// Player 2
		sc.nextLine();
		
		Pecas p, pFinal;

		match.configIncial();

		p1.imprime_player(1);
		p2.imprime_player(2);		
		t.imprimeTabuleiro(match);

		while (!match.getXequeMate()) {

			Players jogadorAtual = (match.getJogadorAtual() == Cor.BRANCO) ? p1 : p2;//DEFININDO JOGADOR DO TURNO
			System.out.println("Vez de: " + jogadorAtual.getNome());
			System.out.println("Cor: "+ jogadorAtual.getcor());
			
			boolean emXeque = (jogadorAtual.getcor() == Cor.BRANCO) ? match.getXequeNoBranco() : match.getXequeNoPreto();
		    
		    if (emXeque) {
		        System.out.println("Você está em xeque! Você deve mover seu Rei ou bloquear/capturar a peça de ataque.");
		    }
		    
			System.out.println("Digite a peça que deseja mover (e.g., 'A2'). A coluna deve ser de A-H e a linha de 1-8:");	
			String posInicial = sc.nextLine().trim().toUpperCase();//Digitar posicao inicial
			
			if (posInicial.isEmpty() || posInicial.length() != 2) {
				System.out.println("Entrada inválida, tente novamente.");
				continue;
			}

			if (!t.posicaoValida(posInicial)) {
				System.out.println("Posição inválida, tente novamente.");
				continue;
			}
			p = t.pegaPeca(posInicial);

			if (p == null || p.getCor() != jogadorAtual.getcor()) {
				System.out.println("Não há peça sua nesta posição ou posição vazia, tente novamente.");
				continue;
			}
			
			System.out.println("Digite a posição para qual deseja mover a peça: ");
			String posFinal = sc.nextLine().trim().toUpperCase();//Digitar posicao final
			
			if (posFinal.isEmpty() || posFinal.length() != 2) {
				System.out.println("Entrada inválida, tente novamente.");
				continue;
			}
			
			if (!t.posicaoValida(posFinal)) {
				System.out.println("Posição inválida, tente novamente.");
				continue;
			}
			if(t.existePeca(posFinal) == 0) {
				
				pFinal = t.pegaPeca(posFinal);
				
				if (pFinal == null || pFinal.getCor() == jogadorAtual.getcor()) {
					System.out.println("A peça que deseja comer não é sua ou posição vazia, tente novamente.");
					continue;
				}
			}
			
			  if (match.moveRemoveXeque(posInicial, posFinal, jogadorAtual.getcor())) { // Verifica se o movimento é válido e não deixa o rei em xeque
	  
	              if(!p.movimentacaoPeca(posInicial, posFinal)) {//MOVIMENTO
	  				System.out.println("Posição inválida, tente novamente.");
	  				System.out.println("Enter para continuar");
	  				sc.nextLine();				
	  				t.imprimeTabuleiro(match);
	  				continue;
	  			}
	  			else {//Foi movimentado
	  				match.gravaEmDisco(jogadorAtual, t.pegaPeca(posFinal), posInicial, posFinal);
	  				 if (p.analisaXeque(t.converte(posFinal)[0], t.converte(posFinal)[1])) {
	  	                    if (jogadorAtual.getcor() == Cor.BRANCO) {
	  	                        match.setXequeNoPreto(true);
	  	                        System.out.println("O Rei Preto esta em xeque!");
	  	                    } else {
	  	                        match.setXequeNoBranco(true);
	  	                        System.out.println("O Rei Branco esta em xeque!");
	  	                    }
	  	                }
	  			}
	              //match.setJogadorAtual(match.getJogadorAtual() == Cor.BRANCO ? Cor.PRETO : Cor.BRANCO);
	          } else {
	              System.out.println("Este movimento não resolve a situação de xeque, tente outro.");
	          }
	      	
			match.setJogadorAtual(match.getJogadorAtual() == Cor.BRANCO ? Cor.PRETO : Cor.BRANCO);//TROCA DE TURNO DE JOGADORES			
			t.imprimeTabuleiro(match);
			System.out.println("Enter para continuar");
			sc.nextLine();
	}
			sc.close();
		}
		
	}

