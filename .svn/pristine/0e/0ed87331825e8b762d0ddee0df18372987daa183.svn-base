package simu.model;

import simu.framework.Kello;

/**
 * Luokan tarkoitus on pitää kirjaa simulaation aikana kertyvistä tuloista, menoista, pisteistä, asiakkaaiden lukumäärästä ja asiakkaiden tyytyväisyyksistä. 
 * Luokka on singleton-tyyppinen.
 * 
 * @author Jukka Hallikainen
 *
 */
public class Pisteet {
	
	private final int MENO1HINTA = 25;
	private final int MENO2HINTA = 200;
	
	private int tulot; //Kokonais pisteet, todelliset pisteet: pisteet - menot
	private int menot; //Korjaamopisteisiin ja työntekijöihin menneet menot
	private int pisteet;
	private double tyytyvaisyys;
	private int asiakasLKM;
	private static Pisteet instanssi;

	
	private Pisteet() {
		this.tulot = 0;
		this.asiakasLKM = 0;
		this.tyytyvaisyys = 0;
	}
	
	public static Pisteet getInstance() {
		if (instanssi == null) {
			instanssi = new Pisteet();
		}
		return instanssi;
	}
	
	public void lisaaTulo(int p) {
		tulot += p;
	}
	
	/**
	 * Metodia käytetään menon lisäämiseen. Parametreinä annetaan määrä ja menon tyyppi (esim. työntekijöiden määrä ja tyyppi on palkka = MENO1HINTA).
	 * 
	 * @return Palauttaa menon suuruuden.
	 */
	public int lisaaMeno(int m, MenoTyypit tyyppi) {
		switch(tyyppi) {
		case MENO1:
			menot+=m*MENO1HINTA*Kello.getInstance().getPaivanPituus();
			return -m*MENO1HINTA*Kello.getInstance().getPaivanPituus();
		case MENO2:
			menot+=m*MENO2HINTA;
			return -m*MENO2HINTA;
		default:
			break;
		}
		return 0;
	}
	
	/**
	 * Metodia käytetään tyytyväisyyden lisäämiseen. Asiakkaan tyytyväisyys (0-100) lisätään kokonaistyytyväisyyteen, sekä asiakaslukumäärää kasvatetaan yhdellä.
	 */
	public void lisaaTyytyvaisyys(double t) {
		tyytyvaisyys += t;
		asiakasLKM++;
	}
	
	/**
	 * Metodi palauttaa keskityytyväisyyden.
	 * @return Keskityytyväisyys lasketaan kokonaistyytyväisyys jaettuna asiakaslukumäärällä.
	 */
	public int getKeskiTyytyvaisyys() {
		if(asiakasLKM!=0)
			return (int) tyytyvaisyys/asiakasLKM;
		else
			return 0;
	}

	/**
	 * Metodi palauttaa sen hetkisen rahatilanteen.
	 * @return Palautetaan tulot miinus menot.
	 */
	public int getTulotMiinusMenot() {
		return tulot - menot;
	}
	
	public int getTulot() {
		return tulot;
	}
	
	public int getMenot() {
		return menot;
	}
	
	/**
	 * Metodi palauttaa kokonaispisteet.
	 * @return Kokonaispisteet koostuu: (tulot - menot) * keskityytyväisyys * 100.
	 */
	public int getKokPisteet() {
		return (int)(getTulotMiinusMenot() * (getKeskiTyytyvaisyys() / 100.0)); // pisteet = tulot * tyytyväisyysprosentti
	}
	
	public int getAsiakkaat() {
		return asiakasLKM; //Palauttaa simulaatiosta poistuneet asiakkaat
	}

	public void setPisteet(int pisteet) {
		this.tulot = pisteet;
	}
	
	public void alusta() {
		this.tulot = 0;
		this.menot = 0;
		this.asiakasLKM = 0;
		this.tyytyvaisyys = 0;
	}

}
