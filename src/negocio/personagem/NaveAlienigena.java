package negocio.personagem;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NaveAlienigena extends Personagem {
	private static Image imagem;

	public NaveAlienigena(int x, int y, int width, int heigth) {
		super(x, y, width, heigth);
		try {
			if (imagem == null) {
				BufferedImage img = ImageIO.read(new File("nave.jpg"));
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

}
