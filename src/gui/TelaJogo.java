package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import negocio.JogoListener;

public class TelaJogo extends JFrame implements ActionListener , JogoListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btIniciar, btPausar, btSair;
	private CenarioJogo cv;
	private JLabel labelValorPontos, labelValorVidaNave;

	public TelaJogo() {
		super("Space Invaders");
		configure();

	}

	private void configure() {
		this.setBounds(0, 0, 800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		setUndecorated(true);

		setLayout(new BorderLayout());
		JPanel painelOeste = new JPanel();
		painelOeste.setBackground(Color.GRAY);
		// painelOeste.setBounds(0, 0, 100, 500);
		painelOeste.setPreferredSize((new Dimension(90, 80)));
		add(painelOeste, BorderLayout.WEST);

		btIniciar = new JButton("iniciar");
		// jButton1.setBounds(50, 10, 100, 150);
		btIniciar.setPreferredSize(new Dimension(80, 30));
		painelOeste.add(btIniciar);

		btPausar = new JButton("pausar");
		// jButton2.setBounds(50, 60, 100, 150);
		btPausar.setPreferredSize(new Dimension(80, 30));
		painelOeste.add(btPausar);

		btSair = new JButton("sair");
		// jButton3.setBounds(50, 110, 100, 150);
		btSair.setPreferredSize(new Dimension(80, 30));
		painelOeste.add(btSair);

		btIniciar.addActionListener(this);
		btPausar.addActionListener(this);
		btSair.addActionListener(this);
		
		JLabel labelVidaNave = new JLabel("Vida Nave");
		labelVidaNave.setForeground(Color.GREEN);
		painelOeste.add(labelVidaNave);
		
		labelValorVidaNave = new JLabel("");		
		painelOeste.add(labelValorVidaNave);

		
		JLabel labelPontos = new JLabel("Pontos");		
		labelPontos.setForeground(Color.GREEN);
		painelOeste.add(labelPontos);
		
		labelValorPontos = new JLabel("");		
		painelOeste.add(labelValorPontos);

		
		cv = new CenarioJogo();
		cv.setJogoListener(this);
		add(cv, BorderLayout.CENTER);
		this.setVisible(true);
		cv.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == btSair) {
			System.exit(0);
		} else if (evento.getSource() == btIniciar) {
			cv.requestFocus();
			cv.continuar();
		} else if (evento.getSource() == btPausar) {
			cv.pausar();
		}
	}

	@Override
	public void pontosUpdate(int pontos) {
		labelValorPontos.setText(""+pontos);
	}
	
	@Override
	public void vidaUpdate(int vida) {
		labelValorVidaNave.setText(""+vida);
	}
}
