package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class TelaSplash extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer t, t1;
	private JProgressBar jPBar;

	public TelaSplash() {
		super("Space Invaders");
		configure();
		FechamentoTask tt = new FechamentoTask();
		t = new Timer();
		t.schedule(tt, 5000);
		ProgressBarTask tPB = new ProgressBarTask();
		t1 = new Timer();
		t1.schedule(tPB, 0, 50);

	}

	private void configure() {
		// TODO Auto-generated method stub
		this.setBounds(0, 0, 800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		setUndecorated(true);

		setLayout(new BorderLayout());
		JPanel painelNorte = new JPanel();
		painelNorte.setBackground(Color.GRAY);
		add(painelNorte, BorderLayout.NORTH);
		JLabel lbN = new JLabel("Space Invaders");
		lbN.setForeground(Color.WHITE);
		painelNorte.add(lbN);

		JPanel painelSul = new JPanel();
		painelSul.setBackground(Color.GRAY);
		add(painelSul, BorderLayout.SOUTH);
		JLabel lbS = new JLabel("Rubens 2015");
		lbS.setForeground(Color.WHITE);
		painelSul.add(lbS);

		JPanel painelCentral = new JPanel();
		painelCentral.setBackground(Color.WHITE);
		add(painelCentral, BorderLayout.CENTER);
		try {
			BufferedImage img = ImageIO.read(new File("monstro.jpeg"));
			Image imagem = img.getScaledInstance(200, 200,
					BufferedImage.SCALE_AREA_AVERAGING);
			JLabel jImg = new JLabel(new ImageIcon(imagem));
			painelCentral.setLayout(new BorderLayout());
			painelCentral.add(jImg, BorderLayout.CENTER);

			jPBar = new JProgressBar();
			painelCentral.add(jPBar, BorderLayout.SOUTH);

		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setVisible(true);

	}

	class FechamentoTask extends TimerTask {
		public void run() {
			dispose();
			t.cancel();
			t1.cancel();
			new TelaJogo();

		}
	}

	class ProgressBarTask extends TimerTask {
		public void run() {
			jPBar.setValue(jPBar.getValue() + 1);
		}
	}
}
