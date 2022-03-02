package simu.framework;

import simu.model.Palvelupiste;
import simu.view.SimulaattoriMainWindowController;

/**
 * Simulaattorin moottori, joka vastaa jokaisen syklin suorittamisesta.
 * 
 * @author Eljas Hirvelä
 *
 */

public abstract class Moottori extends Thread {
	
	private int viive = 500;
	
	private Kello kello;
	
	protected boolean pause = false;
	protected boolean stopped = false;
	
	protected Tapahtumalista tapahtumalista;
	protected Palvelupiste[] palvelupisteet;
	

	public Moottori(){

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia
		
		kello.setAvaamisAika(8); // Korjaamo avataan oletuksena 8
		
		kello.setSulkemisAika(17); // Korjaamo suljetaan oletuksena 17
		
		kello.setPaivat(4); // Simulaatio kestää oletuksena 4 päivää
		
		tapahtumalista = new Tapahtumalista();
		
		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa 
		
		
	}
	
	public void stopSimulaatio() {
		stopped = true;
	}
	
	public void pauseSimulaatio() {
		pause = true;
	}
	
	public void continueSimulaatio() {
		pause = false;
		synchronized(this) {notify();}
	}
	
	public void setViive(int viive) {
		if(viive < 10)
			viive = 10;
		this.viive = viive;
	}
	
	public int getViive() {
		return this.viive;
	}
	
	/**
	 * Metodin sisällä suoritetaan syklit.
	 */
	public void run(){
		alustukset();
		while (simuloidaan()){
			while(pause) {
				synchronized(this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if(stopped)
				break;
			
			kello.setAika(nykyaika());
			suoritaBTapahtumat();
			yritaCTapahtumat();
			try {
				Thread.sleep(viive);
			} catch (InterruptedException e) {
				Trace.out(Trace.Level.INFO, "Moottori ei nukkunut hyvin! *VIHAINEN*");
			}
			valiSuoritukset();
			visualisoi();
		}
		tulokset();
		
	}
	
	/**
	 * Metodi suorittaa kaikki syklin aikavälin B-tapahtumat.
	 */
	private void suoritaBTapahtumat(){
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	/**
	 * Metodi suorittaa kaikki syklin aikavälin mahdolliset C-tapahtumat.
	 */
	private void yritaCTapahtumat(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}
	
	private double nykyaika(){
		return tapahtumalista.getSeuraavanAika();
	}
	
	private boolean simuloidaan(){
		Trace.out(Trace.Level.INFO, "Päivä: "+kello.getPaiva()+" - Kello on: " + kello.getAikaKello());
		if(kello.getPaiva() <= kello.getPaivat()) {
				return true;
		}
		return false;
	}
	
	protected void viivePlus() {
		viive+=25;
	}
	
	protected void viiveMiinus() {
		if(viive >= 35)
			viive-=25;
		else
			viive = 10;
	}
	
	protected Palvelupiste[] getPalvelupisteet() {
		return palvelupisteet;
	}
	

	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void suoritaTapahtuma(Tapahtuma t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void visualisoi();
	
	protected abstract void valiSuoritukset();
	
	protected abstract void paivanVaihdos();
	
	public abstract void setTyontekijat(int maara);
	
	public abstract void setPalvelupisteet(int maara);
	
	public abstract void setController(SimulaattoriMainWindowController controller);
	
	public abstract void kasvataViivetta();
	
	public abstract void laskeViivetta();
	
	public abstract void asiakkaidenMaaritys(double maara);
	
}