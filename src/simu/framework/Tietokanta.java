package simu.framework;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import simu.model.Palvelupiste;
import simu.model.PalvelupisteDAO;
import simu.model.Tulos;

/**
 * Luokkaa käytetään tietokannasta tiedon hakua varten, sekä tietokantaan tallenusta varten.
 * 
 * @author Eljas Hirvelä
 *
 */

public class Tietokanta {

	Connection conn;
	
	final String URL = "jdbc:mysql://mysql.metropolia.fi/arttupos";
	final String USERNAME = "arttupos";
	final String PASSWORD = "r3dDevil";
	
	public Tietokanta() {
		try {
			conn = DriverManager.getConnection(
					URL+"?user="+USERNAME+"&password="+PASSWORD);
		} catch (SQLException e){
			System.err.println("Ei saatu yhteyttä tietokantaan! Onko VPN yhteys?");
		}
	}
	
	/**
	 * Metodia käytetään jokaisen tuloksen hakemiseen tietokannasta.
	 * 
	 * @return Jokainen tietokannan tulos palautetaan yhtenä listana.
	 */
	public ArrayList<Tulos> haeTulokset() {
		ArrayList<Tulos> tulokset = new ArrayList<>();
		try {
			
			Statement stmt = conn.createStatement();
			String query = "SELECT "
					+ "Id, Pvm, Tyytyvaisuus, Palvelupisteet, Tyontekijat, Raha, SaapuneetAsiakkaat, PoistuneetAsiakkaat, AvaamisAika, SulkemisAika, Paivat"+
					" FROM Tulokset";
			ResultSet rs = stmt.executeQuery(query);
			int x = 0;
			while (rs.next()) {
				Tulos t = new Tulos();
				
				t.setId(rs.getInt("Id"));
				t.setPvm(rs.getString("Pvm"));
				t.setTyytyvaisuus(rs.getInt("Tyytyvaisuus"));
				t.setPalvelupisteet(rs.getInt("Palvelupisteet"));
				t.setTyontekijat(rs.getInt("Tyontekijat"));
				t.setRaha(rs.getInt("Raha"));
				t.setSaapuneetAsiakkaat(rs.getInt("SaapuneetAsiakkaat"));
				t.setPoistuneetAsiakkaat(rs.getInt("PoistuneetAsiakkaat"));
				t.setAvaamisAika(rs.getInt("AvaamisAika"));
				t.setSulkemisAika(rs.getInt("SulkemisAika"));
				t.setPaivat(rs.getInt("Paivat"));
				
				//Haetaan tulokset palvelupisteet (tiksi, korjaamopisteet)
				Statement stmt2 = conn.createStatement();
				String query2 = "SELECT * FROM Palvelupisteet WHERE Id LIKE "+t.getId();
				ResultSet rs2 = stmt2.executeQuery(query2);
				while(rs2.next()) {
					PalvelupisteDAO pp = new PalvelupisteDAO();
					pp.setId(rs2.getInt("Id"));
					pp.setNimi(rs2.getString("Nimi"));
					pp.setAktiiviaika(rs2.getDouble("Aktiiviaika"));
					pp.setSimulaatioaika(rs2.getDouble("Simulaatioaika"));
					pp.setAsiakkaat(rs2.getInt("Asiakkaat"));
					pp.setTulot(rs2.getInt("Tulot"));
					pp.setTyontekijat(rs2.getInt("Tyontekijat"));
					t.addTyopistee(pp);
				}
				
				tulokset.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tulokset;
	}
	
	/**
	 * Metodia käytetään tietokantaan tallenusta varten. Metodi lähettää tulokset, sekä jokaisen palvelupisteen tiedot tietokantaan.
	 */
	public void lisaaRivi(int rahat, int saapuneetasiakkaat, int poistuneetasiakkaat, int tyytyvaisuus, int palvelupisteet, int tyontekijat, int avaamisAika, int sulkemisAika, int paivat,
			Palvelupiste[] korjaamopisteet) {
		
		if(paivat == 0)
			paivat = 1;
		
		try {
			String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			
			String query = "INSERT INTO Tulokset(Pvm, Tyytyvaisuus, Palvelupisteet, "
					+ "Tyontekijat, Raha, SaapuneetAsiakkaat, PoistuneetAsiakkaat, AvaamisAika, SulkemisAika, Paivat) "+ 
					"VALUES ('"+date+"',"+tyytyvaisuus+","
					+ palvelupisteet+"," + tyontekijat + ","+ rahat + "," + saapuneetasiakkaat + "," + poistuneetasiakkaat + ","
					+ avaamisAika +","+sulkemisAika+","+paivat+")";
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int affectedRows = stmt.executeUpdate();
			if(affectedRows == 0) {
				throw new SQLException("Tuloksen lähettäminen tietokantaan epäonnistui.");
			}
			try(ResultSet avaimet = stmt.getGeneratedKeys()){
				if(avaimet.next()) {
					System.out.println("Tuloksesi ID: "+avaimet.getLong(1));
					
					double simulaatioaika = Math.round((sulkemisAika - avaamisAika) * paivat * 60 * 100.0)/100.0;
					int id = (int) avaimet.getLong(1);
					for(int i = 0; i < korjaamopisteet.length;i++) {
						Statement ppstmt = conn.createStatement();
						String nimi = korjaamopisteet[i].toString();
						double aktiiviaika = Math.round(korjaamopisteet[i].getAktiiviAika()*100.0)/100.0;
						int asiakkaat = korjaamopisteet[i].getAsiakkaat();
						int tulot = korjaamopisteet[i].getTulot();
						int duunarit = korjaamopisteet[i].getTyontekijat();
						query = "INSERT INTO Palvelupisteet(Id, Nimi, Aktiiviaika, Simulaatioaika, Asiakkaat, Tulot, Tyontekijat) "+
						"VALUES ("+id+",'"+nimi+"',"+aktiiviaika+","+simulaatioaika+","+asiakkaat+","+tulot+","+duunarit+")";
						ppstmt.executeQuery(query);
					}
				} else {
					throw new SQLException("Uuden tietokantarivin id:tä ei palautettu.");
				}
			}
					
		} catch (SQLException e) {
			System.err.println("Tietokantaan ei saatu yhteyttä. Onko VPN yhteys päällä?");
		}
	}
	
}
