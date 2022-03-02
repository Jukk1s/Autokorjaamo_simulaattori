package simu.model;

import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Trace;


/**
 * Asiakasolio-luokka
 * Luokka "tietää" mikä tapahtuman tyyppi asiakkaalla on
 * Tyytyväisyys jokaiselle asiakkaalle henkilökohtainen, joista sitten lasketaan keskiarvo
 * 
 * @author Jukka Hallikainen
 *
 */
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private double anteeksiannettavaAika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private double tyytyvaisyys;
	
	private TapahtumanTyyppi tapahtuma;
	
	private static int lkm = 0;
	
	public Asiakas() {
	    id = i++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas:" + id + ":"+saapumisaika);
		lkm++;
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}
	
	public static double getLapimenoaikojenKA() {
		return sum / lkm;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	
	public void setTapahtuma(TapahtumanTyyppi t) {
		this.tapahtuma = t;
	}
	

	public TapahtumanTyyppi getTapahtuma() {
		return tapahtuma;
	}
	
	public static int getLkm() {
		return lkm;
	}
	
	public static void nollaaLkm() {
		lkm = 0;
	}
	
	public void setTyytyvaisyys() {
		this.tyytyvaisyys = 100;
		this.anteeksiannettavaAika = 45 + Tapahtuma.getKeskimAika(getTapahtuma()); //Asiakkaan tyytyväisyys ei vähene jos systeemissä vietetty aika on korkeintaan 30 min + keskimääräinen remontin kesto
		double kerroin = (this.poistumisaika - this.saapumisaika) / this.anteeksiannettavaAika;
		if (kerroin > 1) {
			this.tyytyvaisyys -= kerroin * 5; //Esim. jos läpimenoaika on 2 * anteeksiannettava aika, tyytyväisyys vähenee 40 yksikköä
		}
		if (this.tyytyvaisyys < 0) {
			this.tyytyvaisyys = 0;
		}
	}
	
	public double getTyytyvaisyys() {
		return this.tyytyvaisyys;
	}
	
	public void raportti(){
	//	Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui:" +saapumisaika+ " / poistui:" +poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " poistui: "+ poistumisaika+ " / viipyi: " +(poistumisaika-saapumisaika));
		Trace.out(Trace.Level.INFO, "	tavoiteaika " + anteeksiannettavaAika + " ---> tyytyväisyys: " + (int)tyytyvaisyys + " %");
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println("Läpimenoaikojen keskiarvo " + keskiarvo);
	}

}
