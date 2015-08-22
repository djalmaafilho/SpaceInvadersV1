package negocio.personagem;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Monstro2 extends Personagem{
	private Image imagem2;
	
	public Monstro2(int x, int y, int width, int heigth) {
		super(x, y, width, heigth);
		try{
			BufferedImage img2 = ImageIO.read(new File("monstro2.jpeg"));
			imagem2 = img2.getScaledInstance(width, heigth, BufferedImage.SCALE_AREA_AVERAGING);
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void desenhar(Graphics g) {
		if (imagem2!=null){
			g.drawImage(imagem2, getX(), getY(), null);
		}
		
	}

	
	

}
