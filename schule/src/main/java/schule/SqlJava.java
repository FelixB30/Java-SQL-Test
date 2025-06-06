package schule;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SqlJava {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/schule"; // Datenbank-URL
		String user = "root";
		String password = "Fe27Be10!";
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Verbindung erfolgreich!");
			while (true) {
				Scanner s2 = new Scanner(System.in);
				System.out.print("anzeigen oder eintragen oder beenden (Schreibweise übernemhen)");
				String aoe = s2.nextLine();
				if ("anzeigen".equals(aoe)) {
					Scanner s4 = new Scanner(System.in);
					System.out.print("Alles anzeigen oder Spezielle Information? (Schreibweise übernemhen)");
					String aaos = s4.nextLine();
					if ("Alles anzeigen".equals(aaos)) {
						display(conn);
					}
					if ("Spezielle Information".equals(aaos)) {
						suchen(conn);
					}
				}
				if ("eintragen".equals(aoe)) {
					eintragen(conn);
				}
				if ("beenden".equals(aoe)) {
					System.out.println("Programm beendet");
					break;
				}

			}
		} catch (SQLException e) {
			System.out.println("Fehler bei der Verbindung: " + e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

	}

	public static void display(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Select * from schüler");
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			System.out.println();
			System.out.println("Nachname=" + rs.getString("Name"));
			System.out.println("Name=" + rs.getString("Vorname"));
			System.out.println("Geburtsdatum=" + rs.getDate("Geburtsdatum"));
		}
	}

	public static void suchen(Connection conn) throws SQLException {
		System.out.print("Von welcher Person Brauchen sie Informatioen? (Vorname eingeben in Anführungszeichen)");
		Scanner s3 = new Scanner(System.in);
		String search = s3.nextLine();
		String Variabel = "Select * from schüler where Name="+search;
		System.out.println(Variabel);
		PreparedStatement ps = conn.prepareStatement(Variabel);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Scanner s4 = new Scanner(System.in);
			System.out.println("Welche Information?(id,Name,Nachname,Geburtsdatum)");
			String search2 = s4.nextLine();
			System.out.println(search2+" "+rs.getInt(search2));
			}
		}
	

	public static void eintragen(Connection conn) throws SQLException {
		Scanner s = new Scanner(System.in);
		System.out.print("id Eingeben: ");
		int id = Integer.parseInt(s.nextLine());
		System.out.print("vorname: ");
		String vorname = s.nextLine();
		System.out.print("Nachname: ");
		String nachname = s.nextLine();
		System.out.print("geburtsdatum: ");
		String geburtsdatum = s.nextLine();

		String sql = String.format(
				"insert into schüler (id,Name,Vorname,Geburtsdatum) Values(%d,\"%s\",\"%s\",\"%s\");", id, vorname,
				nachname, geburtsdatum);
		PreparedStatement ps1 = conn.prepareStatement(sql);
		int rs1 = ps1.executeUpdate();
		if (rs1 > 0) {
			System.out.println("Eintrag erfolgreich eingefügt!");
		} else {
			System.out.println("Eintrag nicht erfolgreich eingefügt!");
		}

	}
}
