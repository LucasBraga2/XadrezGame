package Tabuleiro;

public class Players {

	private String nome;
	private Cor cor;

	public Players(String nome, Cor cor) {
		this.nome = nome;
		this.cor = cor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cor getcor() {
		return cor;
	}

	public void setcor(Cor cor) {
		this.cor = cor;
	}

	public void imprime_player(int x){
		
	System.out.println("Jogador "+x);
	System.out.println("Nome: "+ nome);
	if(cor == Cor.BRANCO) {
		System.out.println("Cor das peças: Brancas");
		System.out.println("-----------------------------------");
	}
	else {
		System.out.println("Cor das peças: Pretas");
		System.out.println("-----------------------------------");
	}
	
	}

}
