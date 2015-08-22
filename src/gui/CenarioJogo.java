package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import negocio.JogoControle;
import negocio.JogoListener;
import negocio.TipoMovimento;
import negocio.personagem.NaveAlienigena;
import negocio.personagem.NaveTerraquea;
import negocio.personagem.Personagem;

public class CenarioJogo extends JPanel implements MouseMotionListener,KeyListener ,
		MouseListener{	
	private static final long serialVersionUID = 1L;
	private JogoListener listener;
	private JogoControle controle;
	private TimerTask tt;
	private long delay = 1000 / 24;
	private long delayInicial = 2000;
	
	public CenarioJogo() {
		controle = new JogoControle();
		setBackground(Color.BLACK);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		tt = new UpdateTask();
		Timer t = new Timer();
		t.schedule(tt, delayInicial, delay);
	}

	public void setJogoListener(JogoListener listener){
		this.listener = listener;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!controle.isConfigurado()){
			controle.initLimites(0, 0, getWidth(), getHeight());
			controle.adicionarPersonagem(new NaveTerraquea(getWidth() / 2, getHeight() - 40, 30, 30, Color.YELLOW));
			controle.adicionarPersonagem(new NaveAlienigena(0, 0, 30, 30));
		}
		ArrayList<Personagem> personagens = controle.listarPersonagens();
		for (Personagem forma : personagens) {
			forma.desenhar(g);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	public void teclaPressionada(int code){
		switch (code) {
		case KeyEvent.VK_LEFT:
			controle.moverNave(TipoMovimento.ESQUERDA);
			break;
		case KeyEvent.VK_RIGHT:
			controle.moverNave(TipoMovimento.DIREITA);
			break;
		case KeyEvent.VK_UP:
			controle.moverNave(TipoMovimento.FRENTE);
			break;
		case KeyEvent.VK_DOWN:
			controle.moverNave(TipoMovimento.TRAS);
			break;
		case KeyEvent.VK_SPACE:
			controle.naveAtirar();
			break;	
		default:
			break;
		}
	}
	
	public void pausar(){
		controle.pausarJogo();
	}
	
	public void continuar(){
		controle.continuarJogo();
	}

	class UpdateTask extends TimerTask {
		public void run() {
			controle.updatePersonagens();
			listener.vidaUpdate(controle.getVidaNave());
			listener.pontosUpdate(controle.getPontos());
			repaint();
		}
	}
	
	public JogoControle getControle(){
		return controle;
	}
	
	@Override
	public void keyPressed(KeyEvent evento) {		
		teclaPressionada(evento.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
