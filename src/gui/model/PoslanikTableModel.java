package gui.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import domen.Poslanik;

public class PoslanikTableModel extends AbstractTableModel {

	private final String[] kolone = new String[] {"ID", "Name", "Last name", "Birth date"};
	private List<Poslanik> poslanici;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	
	public PoslanikTableModel(LinkedList<Poslanik> poslanici) {
		if(poslanici == null){
			this.poslanici = new LinkedList<>();
		}else{
			this.poslanici = poslanici;
		}
	}
	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public int getRowCount() {
		return poslanici.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Poslanik p = new Poslanik();
		switch(columnIndex){
		case 0: return p.getId();
		case 1: return p.getIme();
		case 2: return p.getPrezime();
		case 3: return p.getDatumRodjenja();
		default: return null;
		}
		
	}
	
	@Override
	public String getColumnName(int column) {
		return kolone[column];
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return false;
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Poslanik p = poslanici.get(rowIndex);
		
		boolean greska = false;
		String s = "";
		
		switch (columnIndex) {
		case 1:
			String ime = aValue.toString();
			if(ime != null || ime.isEmpty()){
				p.setIme(ime);
			}
			else {
				greska = true;
				s = "Ime ne smije biti null ili prazan string";
			}
			break;
		case 2:
			String prezime = aValue.toString();
			if(prezime != null || prezime.isEmpty()){
				p.setIme(prezime);
			}
			else {
				greska = true;
				s = "Prezime ne smije biti null ili prazan string";
			}
			break;
		case 3:
			String datum = aValue.toString();
			try {
				Date d =  (Date) sdf.parse(datum);
				p.setDatumRodjenja(d);
			} catch (ParseException e) {
				greska = true;
				s = "Datum mora biti u formatu: dd.MM.yyyy.";
			}

			break;
		default:
			return;
		}
		if (greska) {
			JOptionPane.showMessageDialog(null, s, "Greska", JOptionPane.ERROR_MESSAGE);
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
		
	}
	public void ucitaj(List<Poslanik> poslanici){
		this.poslanici = poslanici;
		fireTableDataChanged();
	}
	
	public Poslanik getByIndex(int index){
		return poslanici.get(index);
	}
	
	
	
}
