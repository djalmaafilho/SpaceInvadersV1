package negocio.personagem;
import java.awt.Color;
import java.awt.Graphics;


public class Circulo extends Personagem{
	
	public Circulo(int x, int y, int width, int heigth, Color color) {
		super(x,y,width,heigth,color);
		
	}

	@Override
	public void desenhar(Graphics g) {
		g = configureGraphics(g);
		if(isPreenchido()){
		g.fillOval(getX(), getY(), getWidth(), getHeigth());
		}else{
			g.drawOval(getX(), getY(), getWidth(), getHeigth());
		}
	}
	

}

