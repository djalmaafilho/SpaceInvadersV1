package negocio.personagem;
import java.awt.Color;
import java.awt.Graphics;


public class Bala extends Personagem{

	private int dano = 10;// Dano padrao
	private Object origem;
	
	public Bala(int x, int y,Color color) {
		super(x, y, 0, 10, color);
	}
	
	public Bala(int x, int y,Color color, Object origem) {
		super(x, y, 0, 10, color);
		setOrigem(origem);
	}

	@Override
	public void desenhar(Graphics g) {
		g = configureGraphics(g);
		g.drawLine(getX()+ getWidth() , getY(), getX() + getWidth(), getY()-10);
	}
	
	public void setOrigem(Object origem) {
		this.origem = origem;
	}
	
	public Object getOrigem() {
		return origem;
	}
	
	public void setDano(int dano) {
		this.dano = dano;
	}
	
	public int getDano() {
		return dano;
	}
}
