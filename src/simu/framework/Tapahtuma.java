package simu.framework;
import java.util.Random;

import eduni.distributions.*;
import simu.model.TapahtumanTyyppi;
/**
 * Luokan tarkoituksena on luoda eri tapahtumat simulaattorin eri palveluille
 * @author Arttu
 *
 */
public class Tapahtuma implements Comparable<Tapahtuma> {
	
	/**
	 * Otetaan muuttujaksi TapahtumanTyyppi, jotta eri tapahtumat voidaan luokitella oikein ja niihin voidaan liittää oikeat arvot
	 */
	private TapahtumanTyyppi tyyppi;
	private double aika;
	private int tulot;
	private double keskimAikaMin;
	private double kesto;
	
	private Normal normaalijakauma;
	
	
	public Tapahtuma(TapahtumanTyyppi tyyppi, double aika){
		this.tyyppi = tyyppi;
		this.aika = aika;
		/**
		 * Tässä osoitetaan TapahtumanTyypin mukaan Tapahtumalle oikeat tulot tapahtuma suorittamisesta ja
		 * yhdistetään siihen toisesta switch-case -lohkosta keskimääräinen kesto tapahtuman suorittamiselle.
		 */
		switch(this.tyyppi) {
			case DEP1: //Renkaiden vaihto
				this.tulot = 30;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP1);		
				break;
			case DEP2: //Tuulilasin korjaus
				this.tulot = 75;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP2);
				break;
			case DEP3: //Tuulilasin vaihto
				this.tulot = 150;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP3);
				break;
			case DEP4: //Jarrujen vaihtotyö
				this.tulot = 70;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP4);
				break;
			case DEP5: //Ilmastoinnin huolto
				this.tulot = 80;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP5);
				break;
			case DEP6: //Öljynvaihto
				this.tulot = 40;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP6);
				break;
			case DEP7: //Jakohihnan vaihto
				this.tulot = 250;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP7);
				break;
			case DEP8: //Kytkinremontti
				this.tulot = 350;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP8);
				break;
			case DEP9: //Vuosihuolto
				this.tulot = 145;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP9);
				break;
			case DEP10: //Määräaikaishuolto
				this.tulot = 150;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP10);
				break;
			case DEP11: //Pieni huolto
				this.tulot = 90;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP11);
				break;
			case DEP12: //Iso huolto
				this.tulot = 400;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP12);
				break;
			case DEP13: //Katsastus
				this.tulot = 70;
				this.keskimAikaMin = getKeskimAika(TapahtumanTyyppi.DEP13);
				break;
			default:
				this.tulot = 0;
				this.keskimAikaMin = 0;
		}
		
	}
	
	public static int getKeskimAika(TapahtumanTyyppi tyyppi) {
		switch(tyyppi) {
			case DEP1:
				return 10;
			case DEP2:
				return 30;
			case DEP3:
				return 70;
			case DEP4:
				return 65;
			case DEP5:
				return 45;
			case DEP6:
				return 15;
			case DEP7:
				return 150;
			case DEP8:
				return 240;
			case DEP9:
				return 75;
			case DEP10:
				return 150;
			case DEP11:
				return 30;
			case DEP12:
				return 240;
			case DEP13:
				return 25;
		}
		return 0;
	}
	
	public int getTulot() {return tulot;}
	
	public void setArvottuPalveluaika(double kesto) {
		this.kesto = kesto;
	}
	
	public double getArvottuPalveluaika() {
		return this.kesto;
	}
	
	public void setTyyppi(TapahtumanTyyppi tyyppi) {
		this.tyyppi = tyyppi;
	}
	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}
	public void setAika(double aika) {
		this.aika = aika;
	}
	public double getAika() {
		return aika;
	}
	/**
	 * Tämä metodi yhdistää oikean selkokielisen kuvailun oikeaan tapahtumaan, jotta käyttäjä ymmärtää mikä tapahtuma on meneillään.
	 * @param tyyppi yhdistää kuvailun oikean tyyppiseen tapahtumaan
	 * @return palauttaa selkokielisen kuvailun kyseisestä tapahtumasta
	 */
	public static String tapahtumaKuvailu(TapahtumanTyyppi tyyppi) {
			switch(tyyppi) {
			case DEP1: //Renkaiden vaihto
				return "Renkaiden vaihto";
			case DEP2: //Tuulilasin korjaus
				return "Tuulilasin korjaus";
			case DEP3: //Tuulilasin vaihto
				return "Tuulilasin vaihto";
			case DEP4: //Jarrujen vaihtotyö
				return "Jarrujen vaihtotyö";
			case DEP5: //Ilmastoinnin huolto
				return "Ilmastoinnin huolto";
			case DEP6: //Öljynvaihto
				return "Öljynvaihto";
			case DEP7: //Jakohihnan vaihto
				return "Jakohihnan vaihto";
			case DEP8: //Kytkinremontti
				return "Kytkinremontti";
			case DEP9: //Vuosihuolto
				return "Vuosihuolto";
			case DEP10://Määräaikaishuolto
				return "Määräaikaishuolto";
			case DEP11://Pieni huolto
				return "Pieni huolto";
			case DEP12://Iso huolto
				return "Iso huolto";
			case DEP13://Katsastus
				return "Katsastus";
			case TAU0: //Tauolla
				return "Tauolla";
			default:
				return "Röökillä";
			}
	}
	
	public String tapahtumaKuvailu() {
		switch(tyyppi) {
		case DEP1: //Renkaiden vaihto
			return "Renkaiden vaihto";
		case DEP2: //Tuulilasin korjaus
			return "Tuulilasin korjaus";
		case DEP3: //Tuulilasin vaihto
			return "Tuulilasin vaihto";
		case DEP4: //Jarrujen vaihtotyö
			return "Jarrujen vaihtotyö";
		case DEP5: //Ilmastoinnin huolto
			return "Ilmastoinnin huolto";
		case DEP6: //Öljynvaihto
			return "Öljynvaihto";
		case DEP7: //Jakohihnan vaihto
			return "Jakohihnan vaihto";
		case DEP8: //Kytkinremontti
			return "Kytkinremontti";
		case DEP9: //Vuosihuolto
			return "Vuosihuolto";
		case DEP10: //Määräaikaishuolto
			return "Määräaikaishuolto";
		case DEP11: //Pieni huolto
			return "Pieni huolto";
		case DEP12: //Iso huolto
			return "Iso huolto";
		case DEP13: //Katsastus
			return "Katsastus";
		case TAU0: //Tauolla
			return "Tauolla";
		default:
			return "Röökillä";
		}
}
	/**
	 * Metodin tarkoituksena on skeduloita eli vuorottaa satunnainen tapahtuma seuraavaksi käsiteltäväksi tapahtumaksi 
	 * @return palauttaa sen tyyppisen tapahtuman, joka arvotaan
	 */
	public static TapahtumanTyyppi randomTapahtuma() {
		TapahtumanTyyppi skeduloitavanTapahtumanTyyppi = null;
		Random random = new Random(System.nanoTime());
		int num = random.nextInt(13)+1;
		switch(num) {
			case 1:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP1;
				break;
			case 2:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP2;
				break;
			case 3:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP3;
				break;
			case 4:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP4;
				break;
			case 5:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP5;
				break;
			case 6:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP6;
				break;
			case 7:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP7;
				break;
			case 8:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP8;
				break;
			case 9:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP9;
				break;
			case 10:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP10;
				break;
			case 11:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP11;
				break;
			case 12:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP12;
				break;
			case 13:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP13;
				break;
			default:
				skeduloitavanTapahtumanTyyppi = TapahtumanTyyppi.DEP1;
				break;
		} return skeduloitavanTapahtumanTyyppi;
	}

	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
	
	@Override
	public String toString() {
		return this.tyyppi.toString();
	}
	
}
