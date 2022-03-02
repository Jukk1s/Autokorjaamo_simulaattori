package simu.view;

import java.util.ArrayList;
import java.util.Collections;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import simu.framework.Tietokanta;
import simu.model.PalvelupisteDAO;
import simu.model.Tulos;

/**
 * Käyttöliittymän "Hall of Fame" näkymän kontrolleri. Näyttää kaikki tietokantaan tallennetut suoritukset ja mahdollistaa niiden selaamisen ja tutkimisen.
 * 
 * @author Eljas Hirvelä
 *
 */

public class TuloksetFXMLController {
	
	private ArrayList<Tulos> tulokset = new ArrayList<>();
	
	@FXML
	public void initialize() {
		tulokset = new Tietokanta().haeTulokset();
		suodataPisteet();
	}

	
	@FXML
	private Text suodusTyyppi;
	@FXML
	private TextField pvm;
	@FXML
	private TextField keskraha;
	@FXML
	private TextField keskasiakkaat;
	@FXML
	private TextField keskpisteet;
	@FXML
	private TextField tyytyvaisuus;
	@FXML
	private TextField palvelupisteet;
	@FXML
	private TextField tyontekijat;
	@FXML
	private TextField raha;
	@FXML
	private TextField asiakkaat;
	@FXML
	private TextField pisteet;
	@FXML
	private TextField avaamisaika;
	@FXML
	private TextField sulkemisaika;
	@FXML
	private TextField paivat;
	@FXML
	private ListView fame;
	
	@FXML
	private ListView tuloksenpppisteet;
	@FXML
	private Text ppaktiiviaika;
	@FXML
	private Text ppkayttoaste;
	@FXML
	private Text ppsuoritusteho;
	@FXML
	private Text ppkkpalveluaika;
	@FXML
	private Text ppasiakkaat;
	@FXML
	private Text pptulot;
	@FXML
	private Text pptyontekijat;
	
	@FXML
	public void suodataRaha() {
		if(tulokset != null) {
			Tulos.setVertaus(2);
			Collections.sort(tulokset);
			if(suodusTyyppi != null)
				suodusTyyppi.setText("Suodatus: raha");
			asetaTulokset();
		}
	}
	
	@FXML
	public void suodataPisteet() {
		if(tulokset != null) {
			Tulos.setVertaus(0);
			Collections.sort(tulokset);
			if(suodusTyyppi != null)
				suodusTyyppi.setText("Suodatus: pisteet");
			asetaTulokset();
		}
	}
	
	@FXML
	public void suodataAsiakkaat() {
		if(tulokset != null) {
			Tulos.setVertaus(1);
			Collections.sort(tulokset);
			if(suodusTyyppi != null)
				suodusTyyppi.setText("Suodatus: asiakkaat");
			asetaTulokset();
		}
	}
	
	private void paivitaTiedot() {
		if(fame.getItems()!=null) {
			int index = fame.getSelectionModel().getSelectedIndex();
			if(index < 0)
				index = 0;
			pvm.setText(tulokset.get(index).getPvm());
			keskraha.setText(Double.toString(tulokset.get(index).getKeskRaha()));
			keskasiakkaat.setText(Double.toString(tulokset.get(index).getKeskAsiakkaat()));
			keskpisteet.setText(Double.toString(tulokset.get(index).getKeskPisteet()));
			tyytyvaisuus.setText(Integer.toString(tulokset.get(index).getTyytyvaisuus()));
			palvelupisteet.setText(Integer.toString(tulokset.get(index).getPalvelupisteet()));
			tyontekijat.setText(Integer.toString(tulokset.get(index).getTyontekijat()));
			raha.setText(Integer.toString(tulokset.get(index).getRaha()));
			asiakkaat.setText(Integer.toString(tulokset.get(index).getPoistuneetAsiakkaat()));
			pisteet.setText(Integer.toString(tulokset.get(index).getPisteet()));
			avaamisaika.setText(Integer.toString(tulokset.get(index).getAvaamisAika()));
			sulkemisaika.setText(Integer.toString(tulokset.get(index).getSulkemisAika()));
			paivat.setText(Integer.toString(tulokset.get(index).getPaivat()));
			if(tuloksenpppisteet!=null) {
				for(PalvelupisteDAO ppDAO : tulokset.get(index).getTyopisteet()) {
					tuloksenpppisteet.getItems().add(ppDAO);
					tuloksenpppisteet.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							paivitaPalvelupisteenTiedot();
						}
					});
				}
			}
		}
	}
	
	private void paivitaPalvelupisteenTiedot() {
		if(tuloksenpppisteet!=null) {
			PalvelupisteDAO pp = (PalvelupisteDAO) tuloksenpppisteet.getSelectionModel().getSelectedItem();
			if(pp!=null) {
				ppaktiiviaika.setText(Double.toString(pp.getAktiiviaika()));;
				ppkayttoaste.setText(Double.toString(pp.getKayttoaste()));;
				ppsuoritusteho.setText(Double.toString(pp.getSuoritusteho()));;
				ppkkpalveluaika.setText(Double.toString(pp.getKeskimaarainenpalveluaika()));;
				ppasiakkaat.setText(Integer.toString(pp.getAsikaat()));;
				pptulot.setText(Integer.toString(pp.getTulot()));;
				pptyontekijat.setText(Integer.toString(pp.getTyontekijat()));
			}
		}
	}
	
	private void asetaTulokset() {
		if(fame == null)
			fame = new ListView();
		fame.getItems().clear();
		if(tuloksenpppisteet == null)
			tuloksenpppisteet = new ListView();
		for(Tulos t : tulokset) {
			fame.getItems().add(t);
			fame.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					tuloksenpppisteet.getItems().clear();
					paivitaTiedot();
				}
			});
		}
	}
}
