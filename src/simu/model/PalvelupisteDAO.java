package simu.model;

/**
 * Luokan tarkoitus on helpottaa tiedonahakua tietokannasta. Jokaisen tuloksen palvelupisteelle luodaan oma PalvelupisteDAO-olio.
 * 
 * @author Eljas Hirvelä
 *
 */

public class PalvelupisteDAO {

	private int id;
	private String nimi;
	private double aktiiviaika;
	private double simulaatioaika;
	private int asiakkaat;
	private int tulot;
	private int tyontekijat;
	
	/**
	 * Metodi laskee ja palauttaa palvelupisteen käyttöasteen.
	 * @return Palauttaa käyttöasteen (aktiiviaika / simulaatioaika)
	 */
	public double getKayttoaste() {
		return Math.round((aktiiviaika / simulaatioaika)*10000.0)/100.0;
	}
	
	/**
	 * Metodi laskee ja palauttaa palvelupisteen suoritustehon.
	 * @return Palauttaa suoritustehon (asiakkaat / simulaatioaika)
	 */
	public double getSuoritusteho() {
		return Math.round(((double) asiakkaat / simulaatioaika)*10000.0)/100.0;
	}
	
	/**
	 * Metodi laskee palvelupisteen keskimääräisen palveluajan.
	 * @return Palauttaa keskimääräisen palveluajan (aktiiviaika / asiakkaat).
	 */
	public double getKeskimaarainenpalveluaika() {
		
		return Math.round((aktiiviaika / (double) asiakkaat)*100.0)/100.0;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNimi() {
		return nimi;
	}
	
	public double getAktiiviaika() {
		return aktiiviaika;
	}
	
	public double getSimulaatioaika() {
		return simulaatioaika;
	}
	
	public int getAsikaat() {
		return asiakkaat;
	}
	
	public int getTulot() {
		return tulot;
	}
	
	public int getTyontekijat() {
		return tyontekijat;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	
	public void setAktiiviaika(double aktiiviaika) {
		this.aktiiviaika = aktiiviaika;
	}
	
	public void setSimulaatioaika(double simulaatioaika) {
		this.simulaatioaika = simulaatioaika;
	}
	
	public void setAsiakkaat(int asiakkaat) {
		this.asiakkaat = asiakkaat;
	}
	
	public void setTulot(int tulot) {
		this.tulot = tulot;
	}
	
	public void setTyontekijat(int tyontekijat) {
		this.tyontekijat = tyontekijat;
	}
	
	@Override
	public String toString() {
		return nimi;
	}
	
}
