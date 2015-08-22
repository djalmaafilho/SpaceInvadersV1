package negocio.personagem;
import java.awt.Color;
import java.awt.Graphics;

public class NaveTerraquea extends Personagem {

	private long ultimoTiro;
	
	public NaveTerraquea(int x, int y, int width, int heigth, Color color) {
		super(x, y, width, heigth, color);
		setVida(100);
	}
	
	public Bala atirar(){
		if(System.currentTimeMillis() - ultimoTiro > 500){
			ultimoTiro = System.currentTimeMillis();
			Bala l = new Bala(getX()+ getWidth()/2 , getY(), Color.YELLOW, this);
			return l;
		}
		return null;
	}

	@Override
	public void desenhar(Graphics g) {
		if (isPreenchido()) {
			g = configureGraphics(g);
			g.fillRect(getX(), getY(), getWidth(), getHeigth());
			
			g.fillRect(getX() + 10, getY() -10, getWidth()-20, getHeigth());
			
			g.fillRect(getX() + 12, getY() -15, getWidth()-24, getHeigth());
			
		} else {
			g.drawRect(getX(), getY(), getWidth(), getHeigth());
		}
	}
	
	

}
