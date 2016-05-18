package gui;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import domen.Poslanik;
import gui.model.PoslanikTableModel;
import util.ParlamentAPIKomunikacija;
import util.PoslanikJsonUtility;

public class GUIKontroler {
	
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
			
			dodajUTextArea("Poslanici su uspjesno preuzeti sa servisa \n");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Doslo je do greske prilikom ucitavanja poslanika!", "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public static void fillTable() {
		try {
			List<Poslanik> poslanici = PoslanikJsonUtility.deserialize();
			PoslanikTableModel ptm = (PoslanikTableModel) frame.getTable().getModel();
			ptm.ucitaj(poslanici);
			dodajUTextArea("Tabela je popunjena podacima preuzetim sa servisa  \n");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Doslo je do greske prilikom ucitavanja poslanika!", "Greska",
					JOptionPane.ERROR_MESSAGE);
	
		}
		
	}
	
	

}
