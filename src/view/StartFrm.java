package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import com.flickr4java.flickr.FlickrException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import controller.Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class StartFrm extends JFrame {

	private JPanel contentPane;
	private JTextField searchTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrm frame = new StartFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrm() {
		setTitle("Klasterizacija slika");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 532, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUnesitePojamZa = new JLabel("Unesite pojam za pretragu slika");
		lblUnesitePojamZa.setBounds(10, 37, 204, 14);
		contentPane.add(lblUnesitePojamZa);
		
		searchTF = new JTextField();
		searchTF.setBounds(236, 34, 110, 20);
		contentPane.add(searchTF);
		searchTF.setColumns(10);
		
		JButton pretraziSlikeBtn = new JButton("Pretrazi slike");
		pretraziSlikeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(searchTF.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Unesite parametar za pretragu slika");
				}else{
					String searchParam = searchTF.getText();
					try {
						Controller.getInstance().searchPhotos(searchParam);
						//Controller.getInstance().createDataSet();
					} catch (FlickrException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Greska pri pretrazi slika");
						e.printStackTrace();
					}
				}
			}
		});
		pretraziSlikeBtn.setBounds(370, 26, 136, 37);
		contentPane.add(pretraziSlikeBtn);
		
		final JTextArea rezultatiTA = new JTextArea();
		rezultatiTA.setBounds(10, 110, 496, 140);
		contentPane.add(rezultatiTA);
		
		JButton izvrsiKlasterizacijuBtn = new JButton("Izvrsi klasterizaciju");
		izvrsiKlasterizacijuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					rezultatiTA.setText(Controller.getInstance().izvrsiKlasterizaciju());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Klasterizacija nije uspela");
					e.printStackTrace();
				}
			}
		});
		izvrsiKlasterizacijuBtn.setBounds(337, 74, 169, 23);
		contentPane.add(izvrsiKlasterizacijuBtn);
		
		JButton kreirajDataSetBtn = new JButton("Kreiraj DataSet");
		kreirajDataSetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Controller.getInstance().createDS();
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Los JSON file");
					e.printStackTrace();
				} catch (JsonIOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Greska!");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Greska! File nije pronadjen!");
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Greska!Parsiranje datuma nije uspelo");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Greska!");
					e.printStackTrace();
				}
			}
		});
		kreirajDataSetBtn.setBounds(86, 74, 169, 23);
		contentPane.add(kreirajDataSetBtn);
	}
}
