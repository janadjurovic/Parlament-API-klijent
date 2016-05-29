package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.table.DefaultTableModel;

import domen.Poslanik;
import gui.model.PoslanikTableModel;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class ParlamentGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnGET;
	private JButton btnFill;
	private JButton btnUpdateMembers;
	private JScrollPane scrollPane;
	private JTable table;
	private JScrollPane scrollPane_1;
	private JTextArea textArea;



	/**
	 * Create the frame.
	 */
	public ParlamentGUI() {
		setTitle("Parlament members");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.EAST);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		contentPane.add(getScrollPane_1(), BorderLayout.SOUTH);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(150, 10));
			panel.add(getBtnGET());
			panel.add(getBtnFill());
			panel.add(getBtnUpdateMembers());
		}
		return panel;
	}
	private JButton getBtnGET() {
		if (btnGET == null) {
			btnGET = new JButton("GET members");
			btnGET.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUIKontroler.getMembers();
				}
			});
			btnGET.setPreferredSize(new Dimension(120, 23));
		}
		return btnGET;
	}
	private JButton getBtnFill() {
		if (btnFill == null) {
			btnFill = new JButton("Fill table");
			btnFill.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						GUIKontroler.fillTable();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnFill.setPreferredSize(new Dimension(120, 23));
		}
		return btnFill;
	}
	private JButton getBtnUpdateMembers() {
		if (btnUpdateMembers == null) {
			btnUpdateMembers = new JButton("Update members");
			btnUpdateMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUIKontroler.updateMembers();
				}
			});
			btnUpdateMembers.setPreferredSize(new Dimension(135, 23));
		}
		return btnUpdateMembers;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	public JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new PoslanikTableModel(null));
			
		}
		return table;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setPreferredSize(new Dimension(2, 70));
			scrollPane_1.setViewportView(getTextArea());
		}
		return scrollPane_1;
	}
	public JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Status", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		}
		return textArea;
	}
	
	public void osveziTabelu(LinkedList<Poslanik> poslanici){
		PoslanikTableModel model = (PoslanikTableModel)table.getModel();
		model.osveziTabelu(poslanici);
	}
}
