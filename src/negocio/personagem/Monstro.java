package negocio.personagem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monstro extends Personagem {
	private Image imagem;

	public Monstro(int x, int y, int width, int heigth) {
		super(x, y, width, heigth);
		try {
			if (imagem == null) {
				BufferedImage img = ImageIO
						.read(new File("space-invaders.jpeg"));

				imagem = img.getScaledInstance(width, heigth,
						BufferedImage.SCALE_AREA_AVERAGING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void desenhar(Graphics g) {
		if (imagem != null) {
			g.drawImage(imagem, getX(), getY(), null);
		}
	}

	public void moverParaBaixo() {
		setY(getY() + getHeigth());
	}
	
	public Bala atirar(){
		Bala l = new Bala(getX()+ getWidth()/2 , getY() + getHeigth(), Color.RED, this);
		return l;
	}
}
