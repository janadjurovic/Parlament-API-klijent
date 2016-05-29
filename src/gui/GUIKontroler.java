package gui;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domen.Poslanik;
import gui.model.PoslanikTableModel;
import util.ParlamentAPIKomunikacija;
import util.PoslanikJsonUtility;

public class GUIKontroler {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	private static ParlamentGUI frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ParlamentGUI();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void dodajUTextArea(String tekst){
		frame.getTextArea().append(tekst);
		
	}
	public static void getMembers(){
		
		try {
			JsonArray jArray = new ParlamentAPIKomunikacija().vratiPoslanikeUJsonFormatu();
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/serviceMembers.json")));
			
			String s =  new GsonBuilder().setPrettyPrinting().create().toJson(jArray);
			out.println(s);

			out.close();
			
			dodajUTextArea("Poslanici su uspjesno preuzeti sa servisa. \n");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Doslo je do greske prilikom ucitavanja poslanika!", "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public static void fillTable( ) throws ParseException {
		FileReader reader;
		try {
			reader = new FileReader("data/serviceMembers.json");
			Gson gson = new GsonBuilder().create();
			
			JsonArray poslanici = gson.fromJson(reader, JsonArray.class);
			
			LinkedList<Poslanik> poslaniciLista = ParlamentAPIKomunikacija.parseMembers(poslanici);

			frame.osveziTabelu(poslaniciLista);
			dodajUTextArea("Tabela uspesno popunjena \n");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(frame, "Doslo je do greske prilikom ucitavanja tabele!", "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public static void updateMembers() {
		PoslanikTableModel ptm = (PoslanikTableModel) frame.getTable().getModel();
		
		List<Poslanik> poslanici = ptm.getPoslanici();
		
		JsonArray jsonArray = ParlamentAPIKomunikacija.prebaciUJsonNiz(poslanici);
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/updatedMembers.json")));
			
			out.println(new GsonBuilder().setPrettyPrinting().create().toJson(jsonArray));
			
			out.close();
			
			dodajUTextArea("Izmijenjeni podaci su sacuvani. \n");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Doslo je do greske prilikom cuvanja poslanika!", "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	

}
