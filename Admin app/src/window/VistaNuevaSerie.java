package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import datos.Serie;

import swingController.SwingController;
import javax.swing.JTextField;

public class VistaNuevaSerie extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextPane txtpnTitular;
	private JTextPane textPane;
	private JTextArea campoGenero;
	private JTextField txtFechaEstreno;
	private JTextField txtFechaFin;
	
	public VistaNuevaSerie(final SwingController controller, final Serie serie) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtpnTitular = new JTextPane();
		txtpnTitular.setText("Resumen titular de la serie");
		txtpnTitular.setBounds(10, 35, 430, 40);
		contentPane.add(txtpnTitular);
		
		JLabel lblGenero = new JLabel("Genero:");
		lblGenero.setBounds(10, 80, 70, 15);
		contentPane.add(lblGenero);
		
		JLabel lblSinopsis = new JLabel("Sinopsis:");
		lblSinopsis.setBounds(10, 100, 70, 15);
		contentPane.add(lblSinopsis);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 120, 430, 150);
		contentPane.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setText("Sinopsis de la serie");
		scrollPane.setViewportView(textPane);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.crearNuevaSerie();
				controller.verAdministrador();
				controller.cerrarVistaNuevaSerie();
			}
		});
		btnAceptar.setBounds(120, 280, 100, 25);
		contentPane.add(btnAceptar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.verAdministrador();
				controller.cerrarVistaNuevaSerie();
			}
		});
		btnCerrar.setBounds(260, 280, 100, 25);
		contentPane.add(btnCerrar);
		
		textField = new JTextField("Nombre de la serie");
		textField.setBounds(10, 0, 200, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		campoGenero = new JTextArea("Genero,Genero,Genero");
		campoGenero.setBounds(90, 80, 300, 20);
		contentPane.add(campoGenero);
		
		txtFechaEstreno = new JTextField();
		txtFechaEstreno.setText("fechaEstreno");
		txtFechaEstreno.setBounds(210, 0, 114, 30);
		contentPane.add(txtFechaEstreno);
		txtFechaEstreno.setColumns(10);
		
		txtFechaFin = new JTextField();
		txtFechaFin.setText("fechaFin");
		txtFechaFin.setBounds(325, 0, 114, 30);
		contentPane.add(txtFechaFin);
		txtFechaFin.setColumns(10);
	}
	
	public Serie dameNuevaSerie(){
		String nombre = this.textField.getText();
		String titular = this.txtpnTitular.getText();
		ArrayList<String> listaGeneros = new ArrayList<String>();
		listaGeneros = this.obtenerGeneros();
		Date fechaEstreno = null;
		Date fechaFin = null;
		try {
			fechaEstreno = new SimpleDateFormat("yyyy-mm-dd").parse(this.txtFechaEstreno.getText());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Error en la fecha", "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			fechaFin = new SimpleDateFormat("yyyy-mm-dd").parse(this.txtFechaFin.getText());
		} catch (ParseException e) {
			fechaFin = null;
		}
		
		java.sql.Date comienzo;
		if (fechaEstreno != null)
			comienzo = new java.sql.Date(fechaEstreno.getTime());
		
		else
			comienzo = null;
		
		java.sql.Date fin;
		if (fechaFin != null)
			fin = new java.sql.Date(fechaFin.getTime());
		
		else
			fin = null;
		
		String sinopsis = this.textPane.getText();
		Serie nuevaSerie = new Serie(0, nombre, titular, sinopsis, comienzo, fin);
		nuevaSerie.setListaGenero(listaGeneros);
		return nuevaSerie;
	}
	
	public ArrayList<String> obtenerGeneros(){
		ArrayList<String> listaGeneros = new ArrayList<String>();
		String texto = this.campoGenero.getText();
		int inicio = 0;
		
		if (!texto.equals("")){
			for (int i=0; i<texto.length(); i++){
				if (texto.charAt(i) == ','){
					listaGeneros.add(texto.substring(inicio, i));
					inicio = i+1;
				}
			}
			
			listaGeneros.add(texto.substring(inicio, texto.length()));
			
			return listaGeneros;
		}
		
		else
			return null;
	}
}