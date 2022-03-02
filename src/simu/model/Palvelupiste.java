package simu.model;

import java.util.LinkedList;
import java.util.Random;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

/**
 * Palvelupiste-olioluokka
 * 
 * @author Jukka Hallikainen
 *
 */
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi; 
	
	private double tapahtumanLoppumisAika;
	
	private int tyontekijat;
	private int asiakkaat;
	private double tyonTehokkuus;
	
	private double palvelunKesto;
	private double aktiiviAika;
	
	private int tulot = 0;
	
	private int id;
	private static int ppMaara = 0;
	
	private boolean varattu = false;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		setTyontekijat(0);//Asetetaan tätä kautta koska metodi laskee tehokkuuden samalla
		asiakkaat = 0;
		aktiiviAika = 0;
		id = ppMaara++;
	}
	
	public Palvelupiste(Tapahtumalista tapahtumalista,TapahtumanTyyppi tyyppi) {
		this.tapahtumalista = tapahtumalista;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		asiakkaat = 0;
		aktiiviAika = 0;
		id = ppMaara++;
	}
	
	public Palvelupiste(Tapahtumalista tapahtumalista) { //Korjaamopiste
		this.tapahtumalista = tapahtumalista;
		asiakkaat = 0;
		aktiiviAika = 0;
		id = ppMaara++;
	}


	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		asiakkaat++;
	}
	
	/**
	 * @return poistaa ja palauttaa asiakas-olion, joka on tapahtumalistassa ensimmäisenä
	 */
	public Asiakas poistaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		jono.peek().setTapahtuma(skeduloitavanTapahtumanTyyppi);
		aktiiviAika += palvelunKesto;
		return jono.poll();
	}
	
	/**
	 *Arpoo tapahtuman keston jakauman perusteella, suorittaa palvelun, lisää tapahtuman loppumisen listaan
	 */
	public void aloitaPalvelu() {
		if(skeduloitavanTapahtumanTyyppi!=TapahtumanTyyppi.ARR1) {
			varattu = true;
			skeduloitavanTapahtumanTyyppi = Tapahtuma.randomTapahtuma();

			
			double keskiarvo = Tapahtuma.getKeskimAika(skeduloitavanTapahtumanTyyppi);
			double varianssi = keskiarvo;
			double muuttujaX = (OmaMoottori.arvoAika(keskiarvo, varianssi));
			palvelunKesto = muuttujaX/tyonTehokkuus;
			System.out.println("Palvelun kesto: "+muuttujaX+"/"+tyonTehokkuus+" -> "+palvelunKesto + " ----- Tapahtumatyypin keston keskiarvo: " + keskiarvo);
			
			tapahtumanLoppumisAika = Kello.getInstance().getAika() + palvelunKesto;
		
			
			tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,tapahtumanLoppumisAika));
		}
	}

	public void setTyontekijat(int tyontekijat) {
		this.tyontekijat = tyontekijat;
		laskeTehokkuus();
	}
	
	public void increaseTyontekijat() {
		this.tyontekijat++;
		laskeTehokkuus();
	}

	public boolean onVarattu(){
		return varattu;
	}


	public boolean onJonossa(){
		return jono.size() != 0;
	}
	
	public int getJononPituus() {
		return jono.size();
	}

	public int getTyontekijat() {
		return this.tyontekijat;
	}
	
	public int getAsiakkaat() {
		return this.asiakkaat;
	}
	
	public static void resetId() {
		ppMaara = 0;
	}
	
	public void lisaaTulo(int tulo) {
		tulot += tulo;
	}
	
	public double getTuntiTulo() {
		return Math.round((double)tulot/(Kello.getInstance().getPaivanPituus()*Kello.getInstance().getPaiva())*100.0)/100.0;
	}
	
	public double getAktiiviAika() {
		return aktiiviAika;
	}
	
	public int getTulot() {
		return tulot;
	}
	
	private void laskeTehokkuus() {
		double b = (1.02 - 0.02 * Math.pow(tyontekijat, 2));
		
		//Tehokkuus per työntekijä voi olla vähintään 50%
		if(b<0.5)
			b=0.5;
		
		//100% * työntekijät * tehokkuus % per työntekijä
		tyonTehokkuus = 1 * tyontekijat * b;
	}
	
	public int getTyontehokkuus() {
		double temp = tyonTehokkuus * 100;
		return (int) temp;
	}
	
	public boolean vertaaTapahtumaan(Tapahtuma t) {
		return skeduloitavanTapahtumanTyyppi == t.getTyyppi() && t.getAika() == tapahtumanLoppumisAika;
	}
	
	public String getDuuni() {
		return Tapahtuma.tapahtumaKuvailu(skeduloitavanTapahtumanTyyppi);
	}
	
	@Override
	public String toString() {
		if(id==0)
			return "Tiski";
		return "Korjaamopiste "+id;
	}
	
}
