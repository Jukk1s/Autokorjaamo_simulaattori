package simu.framework;

import java.util.PriorityQueue;

import simu.model.Pisteet;
import simu.model.TapahtumanTyyppi;
/**
 * Luodaan lista, johon laitetaan järjestyksessä tapahtumia ja poistetaan niitä sieltä.
 * @author Arttu
 *
 */
public class Tapahtumalista {
	/**
	 * PriorityQueue-lista, johon lisätään uusia Tapahtumia ja poistetaan niitä. Tarvitaan lista, joka järjestää elementit tapahtuma-ajan mukaan.
	 */
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();
	
	public Tapahtumalista(){
	 
	}
	
	public Tapahtuma poista(){
		
		Trace.out(Trace.Level.INFO,"Tapahtumalistasta poisto " + lista.peek());
		return lista.remove();
	}
	
	public void lisaa(Tapahtuma t){
		lista.add(t);
	}
	
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}
	
}
