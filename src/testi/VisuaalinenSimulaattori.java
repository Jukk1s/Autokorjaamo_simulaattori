package testi;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;
import simu.view.SimulaattoriMainWindowController;
import simu.view.TuloksetFXMLController;
import simu.view.TulosController;

/**
 * Metodi avaa käyttöliittymän ja alustaa simulaattorin käyttökuntoon.
 * 
 * @author Eljas Hirvelä
 *
 */

public class VisuaalinenSimulaattori extends Application {
	
	private BorderPane rootLayout;
	private Stage primaryStage;
	Moottori m = null;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		this.primaryStage.setResizable(false);
		
		initRootLayout();
	}
	
	public void initRootLayout() {
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(VisuaalinenSimulaattori.class.getResource("../simu/view/SimulaattoriMainWindow.fxml"));
	        rootLayout = (BorderPane) loader.load();
	        
	        Scene scene = new Scene(rootLayout);
	        primaryStage.setScene(scene);

	        SimulaattoriMainWindowController controller = loader.getController();
	        controller.setSimulaattori(this);
	        
	        primaryStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Metodi lähettää käyttöliittymään syötetyt parametrit mottoriin, sekä kelloon ja käskee moottoria käynnistämään simulaation.
	 */
	public void kaynnistaSimulaatio(int m1, int m2, int m3, int m4, int m5, int m6, double m7, SimulaattoriMainWindowController controller) {
		//m1 = korjaamon avaamis aika
		//m2 = korjaamon sulkemis aika
		//m3 = simuloitavien päivien määrä
		//m4 = simulaattorin viiveen määrä
		//m5 = korjaamon korjauspisteiden määrä
		//m6 = korjaamon työntekijöiden määrä
		//m7 = korjaamon asiakkaat per tunti
		Trace.setTraceLevel(Level.INFO);
		m = new OmaMoottori();
		 
		Kello.getInstance().setAvaamisAika(m1);
		Kello.getInstance().setSulkemisAika(m2);
		Kello.getInstance().setPaivat(m3);
		
		m.setViive(m4);
		m.setController(controller);
		
		//Plussataan palvelupisteisiin yksi koska tiski
		m.setPalvelupisteet(m5+1);
		m.setTyontekijat(m6);
		m.asiakkaidenMaaritys(m7);
		m.start();
	}
	
	/**
	 * Metodi avaa "Hall of Fame"-ikkunan, jossa näkyy tietokantaan tallennetuut tulokset.
	 */
	public void avaaHallOfFame() {
		
		try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(VisuaalinenSimulaattori.class.getResource("../simu/view/TuloksetFXML.fxml"));
	        AnchorPane halloffame = (AnchorPane) loader.load();
			Stage stage = new Stage();
			stage.setTitle("Hall of Fame");
			stage.initOwner(primaryStage);
			Scene scene = new Scene(halloffame);
			stage.setScene(scene);
			
			TuloksetFXMLController fameController = loader.getController();
			
			stage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Metodi avaa simulaation päättymisen ohessa avautuvan tulos näkymän, joka näyttää suoritetut simulaation tulokset.
	 */
	public void avaaTulokset() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(VisuaalinenSimulaattori.class.getResource("../simu/view/Tulos.fxml"));
					AnchorPane tulos = (AnchorPane) loader.load();
					Stage stage = new Stage();
					stage.setTitle("Simuloinnin tulokset");
					stage.initOwner(primaryStage);
					Scene scene = new Scene(tulos);
					stage.setScene(scene);
					
					
					TulosController tulosController = loader.getController();
					
					stage.showAndWait();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		});
		
	}
	
	public void pauseS() {
		m.pauseSimulaatio();
	}
	public void continueS() {
		m.continueSimulaatio();
	}
	public void stopS() {
		m.stopSimulaatio();
	}
	public void viivePlus() {
		m.kasvataViivetta();
	}
	public void viiveMiinus() {
		m.laskeViivetta();
	}
}
