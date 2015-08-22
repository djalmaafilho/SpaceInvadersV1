package dados;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import negocio.personagem.NaveTerraquea;
import negocio.personagem.Personagem;

/**
 * Guarda todos os personagens existentes no jogo agrupando por tipo.
 * 
 * @author Djalma.
 *
 */
public class Repositorio {

	private Map<String, List<Personagem>> mapa;

	public Repositorio() {
		mapa = new TreeMap<String, List<Personagem>>();
	}

	public synchronized void adicionar(Personagem p) {
		if(!mapa.keySet().contains(p.getClass().getName())){
			mapa.put(p.getClass().getName(), new ArrayList<Personagem>());
		}
		mapa.get(p.getClass().getName()).add(p);
	}

	public synchronized void excluir(Personagem p) {
		if(mapa.keySet().contains(p.getClass().getName())){
			mapa.get(p.getClass().getName()).remove(p);
		}
	}

	public boolean existe(Personagem p) {
		if(mapa.keySet().contains(p.getClass().getName())){
			return mapa.get(p.getClass().getName()).contains(p);
		}
		return false;
	}
	
	public ArrayList<Personagem> listarPersonagens(){
		
		ArrayList<Personagem> lista = new ArrayList<Personagem>();
		
		for(List<Personagem> personagens : mapa.values()){
			lista.addAll(personagens);
		}

		return lista;
	}
	
	public ArrayList<Personagem> listarPersonagens(Class classe){
		ArrayList<Personagem> lista = new ArrayList<Personagem>();
		for(List<Personagem> personagens : mapa.values()){
			if(personagens.size()> 0 && personagens.get(0).getClass() == classe){
				lista.addAll(personagens);
			}
		}
		return lista;
	}
	
	public NaveTerraquea getNave(){
		NaveTerraquea nave = null;
		List<Personagem> naves = listarPersonagens(NaveTerraquea.class);
		if(!naves.isEmpty()){
			nave = (NaveTerraquea)naves.get(0);
		}
		return nave;
	}
	
	public synchronized void removerMortos(){		
		for(List<Personagem> personagens : mapa.values()){
			Iterator<Personagem> it = personagens.iterator();
			while(it.hasNext()){
				Personagem p = it.next();
				if(p.isMorto()){
					it.remove();
				}
			}
			
		}
	}
}