package simu.framework;
/**
 * Luokan tarkoituksena on toimia kellona, jonka avulla koko simulaattori ottaa aika-arvot tapahtumille. Luokka on Singleton-tyyppinen, jotta simulaattori pysyy yhdessä ajassa.
 * @author Arttu
 *
 */
public class Kello {

	/**
	 * instanssimuuttuja: aika on minuutteina, kun avaamisAika sekä sulkemisAika ovat tunteina
	 */
	private double aika;
	private int paiva;
	private int avaamisAika;
	private int sulkemisAika;
	private int paivat;
	private static Kello instanssi;
	
	private Kello(){
		aika = 0;
		paiva = 1;
	}
	
	public void nollaaKello() {
		this.aika = 0;
		this.paiva = 1;
	}
	
	public static Kello getInstance(){
		if (instanssi == null){
			instanssi = new Kello();	
		}
		return instanssi;
	}
	
	public void setAika(double aika){
		this.aika = aika;
	}

	public double getAika(){
		return aika;
	}
	
	public void setAvaamisAika(int a) {
		if(a < 0)
			a=0;
		avaamisAika = a;
	}
	
	public void setSulkemisAika(int a) {
		if(a>24)
			a=24;
		sulkemisAika = a;
	}
	
	public void setPaivat(int a) {
		paivat = a;
	}
	
	public int getAvaamisAika() {
		return avaamisAika;
	}
	
	public int getSulkemisAika() {
		return sulkemisAika;
	}
	
	public int getAvaamisAikaMinuutteina() {
		return avaamisAika * 60;
	}
	
	public int getSulkemisAikaMinuutteina() {
		return sulkemisAika * 60;
	}
	
	public int getPaiva() {
		int paivanPituus = (sulkemisAika - avaamisAika)*60;
		return (int) getAika() / paivanPituus + 1;
	}
	/**
	 * Metodin tarkoituksena on laskea tunnit sekä minuutit, jotta simulaattorin aikamääräät ovat luettavissa selkokielellä
	 * @return palauttaa tunnit ja minuutit
	 */
	public String getAikaKello() {
		String tunnit;
		String minuutit;

		int tempNum = (int) (aika - (sulkemisAika - avaamisAika)*60*(getPaiva()-1))/60;
		tunnit = Integer.toString(tempNum+avaamisAika);
		
		tempNum = (int) aika - (sulkemisAika - avaamisAika)*60*(getPaiva()-1)-tempNum*60;
		if(tempNum<10) {
			minuutit = "0"+tempNum;
		} else {
			minuutit = Integer.toString(tempNum);
		}
		
		return (tunnit)+":"+minuutit;
	}
	
	public int getPaivat() {
		return paivat;
	}
	
	public int getPaivanPituus() {
		return sulkemisAika - avaamisAika;
	}
	
	
	public void setPaiva(int p) {
		paiva = p;
	}
	
	public void tarkistaKello() {
		if(avaamisAika > sulkemisAika) {
			int temp = avaamisAika;
			avaamisAika = sulkemisAika;
			sulkemisAika = temp;
		}
	}
}
	
