package Tabuleiro;
import PecasFuncoes.*;

public class Casas {
		
private Cor cor;
private String pos;
private EstadoCasa estado;
private Pecas piece;

public Cor getCor() {
	return cor;
}
public void setCor(Cor cor) {
	this.cor = cor;
}
public String getPos() {
	return pos;
}
public void setPos(String pos) {
	this.pos = pos;
}
public EstadoCasa getEstado() {
	return estado;
}
public void setEstado(EstadoCasa estado) {
	this.estado = estado;
}
public Pecas getPiece() {
	return piece;
}
public void setPiece(Pecas piece) {
	this.piece = piece;
}


}