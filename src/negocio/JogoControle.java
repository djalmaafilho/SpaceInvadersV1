package negocio;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dados.Repositorio;
import negocio.personagem.Bala;
import negocio.personagem.Monstro;
import negocio.personagem.NaveAlienigena;
import negocio.personagem.NaveTerraquea;
import negocio.personagem.Personagem;
import util.Player;

public class JogoControle {
	private int pontos;
	private int[] limites;
	private boolean jogoPausado, jogoTerminado;
	private Repositorio rep ;
	Player player;
	ExecutorService poolThread;
	
	public JogoControle() {
		rep = new Repositorio();
		player = new Player();
		poolThread = Executors.newSingleThreadExecutor();
		player.tocarMID("assets/music.mid", true);
	}
	
	public void initLimites(int left, int top, int right, int bottom){
		int [] novoLimite = new int[] {left, top, right, bottom};
		limites = novoLimite;
	}
	
	public boolean isConfigurado(){
		ArrayList<Personagem> personagens = rep.listarPersonagens();
		boolean contemNaveTerraquea = false;
		boolean contemNaveAlienigena = false;
		
		for(Personagem p : personagens){
			if(p instanceof NaveTerraquea){
				contemNaveTerraquea = true;
			}else if(p instanceof NaveAlienigena){
				contemNaveAlienigena = true;
			}
			
			if(contemNaveAlienigena && contemNaveTerraquea){
				return true;
			}
		}		
		return false;
	}
	
	public boolean isJogoPausado(){
		return jogoPausado;
	}
	
	public boolean isJogoTerminado() {
		return jogoTerminado;
	}
	
	public void pausarJogo(){
		jogoPausado = true;
	}
	
	public void continuarJogo(){
		jogoPausado = false;
	}
	
	public int getVidaNave(){
		return rep.getNave().getVida();
	}
	
	/**
	 * Atualiza o status dos personagens existentes no jogo
	 * 
	 */
	public void updatePersonagens(){
		if(isJogoPausado() || isJogoTerminado()){
			return;
		}
		updateNaveTerraquea();
		updateBalas();
		updateMonstros();
		rep.removerMortos();
	}
	
	private void updateNaveTerraquea(){
		NaveTerraquea nave = rep.getNave();
		if(isColisaoBalasNave(nave) && nave.getVida() <= 0){
			jogoTerminado = true;
			player.pararMID();
		}
	}
	
	private void updateBalas(){
		ArrayList<Personagem> personagens = rep.listarPersonagens(Bala.class);
		for (Personagem bala : personagens) {
			if(((Bala)bala).getOrigem() instanceof NaveTerraquea){
				bala.moverParaCima();
			}else{
				bala.moverParaBaixo();
				if(bala.getMovendoPara() != null && bala.getMovendoPara().equals(TipoMovimento.ESQUERDA)){
					bala.moverParaEsquerda();
				}else if(bala.getMovendoPara() != null && bala.getMovendoPara().equals(TipoMovimento.DIREITA)){
					bala.moverParaDireita();
				}
			}
			eliminarPersonagemForaCenario(bala);
		}
	}
	
	private void eliminarPersonagemForaCenario(Personagem p){
		if(p.getY() < limites[1] || p.getY() > limites[3] ){
			p.setMorto(true);
		}
	}
	
	private void executarSomTiro(){
		Thread t = new Thread() {
			@Override
			public void run() {
				player.tocarAudio("assets/explosion.wav");
			}
		};
		this.poolThread.execute(t);
	}
	
	
	/**
	 * Atualiza todos os monstros do jogo.
	 */
	private void updateMonstros(){
		if(rep.listarPersonagens(Monstro.class).isEmpty()){
			criarMonstros();
		}
		
		NaveTerraquea nave = rep.getNave();
		ArrayList<Personagem> personagens = rep.listarPersonagens(Monstro.class);
		for (Personagem monstro : personagens) {
			if(!monstro.isMorto()){
				if(nave.isColisao(monstro)){
					nave.addDano(20);
					monstro.setMorto(true);
				}else if(isColisaoBalasMonstro(monstro)){
					addPontos(20);
					monstro.setMorto(true);
				}
			}
			updatePosicaoMonstro((Monstro)monstro);
			inimigoAtirar(monstro);
			eliminarPersonagemForaCenario(monstro);
		}
	}
	
	
	private boolean isColisaoBalasMonstro(Personagem p){
		ArrayList<Personagem> personagens = rep.listarPersonagens(Bala.class);
		for (Personagem bala : personagens) {
			if(((Bala)bala).getOrigem() instanceof NaveTerraquea && bala.isColisao(p)) {
				bala.setMorto(true);
				executarSomTiro();
				return true;
			}
		}
		return false;
	}
	
	private boolean isColisaoBalasNave(Personagem nave){
		ArrayList<Personagem> personagens = rep.listarPersonagens(Bala.class);
		for (Personagem bala : personagens) {
			if(!(((Bala)bala).getOrigem() instanceof NaveTerraquea) && bala.isColisao(nave)) {
				nave.addDano(((Bala)bala).getDano());
				bala.setMorto(true);
				executarSomTiro();
				return true;
			}
		}
		return false;
	}
	
	public void criarMonstros() {
		int quantidade = 10;
		int largura = limites[2];
		
		for (int i = 0; i < 10; i++) {
			adicionarPersonagem(new Monstro(i * largura/quantidade,0,largura/(quantidade * 2),largura/(quantidade * 2)));
		}
	}
	
	
	private void updatePosicaoMonstro(Monstro forma){
		if(forma.getY() + (2 * forma.getHeigth())>= limites[3]){
			return;
		}
		
		if(forma.getMovendoPara() == null){
			forma.setMovendoPara(TipoMovimento.DIREITA);
		}
		
		if(forma.getMovendoPara().equals(TipoMovimento.ESQUERDA)){
			forma.moverParaEsquerda();
		}else if(forma.getMovendoPara().equals(TipoMovimento.DIREITA)){
			forma.moverParaDireita();
		}
		
		if(forma.getMovendoPara().equals(TipoMovimento.ESQUERDA) && forma.getX() <= limites[0]){
			forma.moverParaBaixo();
			forma.moverParaDireita();
			forma.setMovendoPara(TipoMovimento.DIREITA);
		}else if(forma.getMovendoPara().equals(TipoMovimento.DIREITA) && forma.getX() >= limites[2] - forma.getWidth()){
			forma.moverParaBaixo();
			forma.moverParaEsquerda();
			forma.setMovendoPara(TipoMovimento.ESQUERDA);
		}
	}
	
	
	public void adicionarPersonagem(Personagem p) {
		rep.adicionar(p);
	}

	public void excluirPersonagem(Personagem p) {
		rep.excluir(p);
	}

	public boolean existePersonagem(Personagem p) {
		return rep.existe(p);
	}
	
	public ArrayList<Personagem> listarPersonagens(){
		return rep.listarPersonagens();
	}
	
	public void moverNave(TipoMovimento tipo){
		if(isJogoPausado()){
			return;
		}
		NaveTerraquea nave = rep.getNave();
		if(nave != null){
			if(tipo.equals(TipoMovimento.ESQUERDA)){
				nave.correrParaEsquerda();
			}else if(tipo.equals(TipoMovimento.DIREITA)){
				nave.correrParaDireita();
			}else if(tipo.equals(TipoMovimento.FRENTE)){
				nave.correrParaCima();
			}else if(tipo.equals(TipoMovimento.TRAS)){
				nave.correrParaBaixo();
			}
		}
	}
	
	public void naveAtirar(){
		NaveTerraquea nave = rep.getNave();
		Bala bala = nave.atirar();
		if(bala != null)
		adicionarPersonagem(bala);
	}
	
	public void inimigoAtirar(Personagem inimigo){
		//logica para gerar tiro aleatorio.
		if( System.currentTimeMillis() % 30 == 0){
			if(inimigo instanceof Monstro){
				Monstro m = (Monstro)inimigo;
				Bala bala = m.atirar();
				if(bala != null){
					if(System.currentTimeMillis() % 60 == 0){
						TipoMovimento tipoMovimento = inimigo.getMovendoPara();
						bala.setMovendoPara(tipoMovimento);
					}
					adicionarPersonagem(bala);
				}
			}
		}
	}
	
	public boolean validarMovimento(Personagem personagen){ 
		
		return true;
	}
	
	private void addPontos(int valor){
		pontos += valor;
	}
	
	public int getPontos() {
		return pontos;
	}
}