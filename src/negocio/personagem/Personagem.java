package negocio.personagem;

import java.awt.Color;
import java.awt.Graphics;

import negocio.TipoMovimento;

public abstract class Personagem implements Forma {
	private int vida;
	private boolean morto;
	private int x, y;
	private int width, heigth;
	private Color color;
	private boolean preenchido = true;
	private TipoMovimento movendoPara;
	public static int passoPadrao = 4;

	public Personagem(int x, int y, int width, int heigth) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
	}

	public Personagem(int x, int y, int width, int heigth, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
		this.setColor(color);

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isPreenchido() {
		return preenchido;
	}
	
	public boolean isMorto(){
		return morto;
	}
	
	public void setMorto(boolean morto) {
		this.morto = morto;
	}

	public void setPreenchido(boolean preenchido) {
		this.preenchido = preenchido;
	}

	public void moverParaBaixo() {
		setY(getY() + passoPadrao);
	}

	public void moverParaCima() {
		setY(getY() - passoPadrao);
	}

	public void moverParaEsquerda() {
		setX(getX() - passoPadrao);
	}

	public void moverParaDireita() {
		setX(getX() + passoPadrao);
	}

	public void correrParaBaixo() {
		for (int i = 0; i < 5; i++) {
			moverParaBaixo();
		}
	}

	public void correrParaCima() {
		for (int i = 0; i < 5; i++) {
			moverParaCima();
		}
	}

	public void correrParaEsquerda() {
		for (int i = 0; i < 5; i++) {
			moverParaEsquerda();
		}
	}

	public void correrParaDireita() {
		for (int i = 0; i < 5; i++) {
			moverParaDireita();
		}
	}
	
	public void setVida(int valor){
		this.vida  = valor;
	}
	
	public void addVida(int valor){
		this.vida = this.vida + valor;
	}
	
	public void addDano(int dano){
		this.vida = this.vida - dano;
	}
	
	public int getVida(){
		return vida;
	}

	public abstract void desenhar(Graphics g);

	public boolean isColisao(Personagem f2) {

		boolean isTopLeftinQuadrado = isInQuadrado(getX(), getY(), f2);
		if (isTopLeftinQuadrado)
			return isTopLeftinQuadrado;

		boolean isTopRightInQuadrado = isInQuadrado(getX() + getWidth(),
				getY(), f2);
		if (isTopRightInQuadrado)
			return isTopRightInQuadrado;

		boolean isBottomLeftInQuadrado = isInQuadrado(getX(), getY()
				+ getHeigth(), f2);
		if (isBottomLeftInQuadrado)
			return isBottomLeftInQuadrado;

		boolean isBottomRightInQuadrado = isInQuadrado(getX() + getWidth(),
				getY() + getHeigth(), f2);
		if (isBottomRightInQuadrado)
			return isBottomRightInQuadrado;

		return false;
	}

	public boolean isInQuadrado(int x, int y, Personagem f2) {
		if (x >= f2.getX() && x <= f2.getX() + f2.getWidth() && y >= f2.getY()
				&& y <= f2.getY() + f2.getHeigth()) {			
			return true;
		}
		return false;
	}

	public Graphics configureGraphics(Graphics g) {
		if (getColor() != null) {
			g.setColor(getColor());
		}
		return g;
	}

	public TipoMovimento getMovendoPara() {
		return movendoPara;
	}

	public void setMovendoPara(TipoMovimento movendoPara) {
		this.movendoPara = movendoPara;
	}
}
