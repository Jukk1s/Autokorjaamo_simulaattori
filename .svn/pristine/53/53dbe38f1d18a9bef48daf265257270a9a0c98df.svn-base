package simu.framework;
import eduni.distributions.*;
import simu.model.TapahtumanTyyppi;
/**
 * Tarkoituksena luoda seuraava tapahtuma tapahtumalistaan
 * @author Arttu
 *
 */

public class Saapumisprosessi {
	
	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi){
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	/**
	 * Metodi luo uuden tapahtuman ja lisää sen tapahtumalistaan
	 */
	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika()+generaattori.sample());
		tapahtumalista.lisaa(t);
	}

}
