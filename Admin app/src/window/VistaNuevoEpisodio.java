package window;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;

import datos.Episodio;
import swingController.SwingController;

public class VistaNuevoEpisodio extends JFrame {

	private JPanel contentPane;
	private JTextField txtTituloepisodio;
	private JTextField txtFechaestreno;
	private JTextField textNumEpisodio;
	private JTextField txtNumtemporada;
	private JTextPane textPane;

	public VistaNuevoEpisodio(final SwingController controller, final int idSerie) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(630, 100, 460, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCapitulo = new JLabel("Capitulo:");
		lblCapitulo.setBounds(10, 80, 70, 15);
		contentPane.add(lblCapitulo);
		
		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(10, 10, 70, 15);
		contentPane.add(lblTitulo);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(300, 10, 70, 15);
		contentPane.add(lblFecha);
		
		JLabel lblTemporada = new JLabel("Temporada:");
		lblTemporada.setBounds(200, 80, 100, 15);
		contentPane.add(lblTemporada);
		
		JLabel lblSinopsis = new JLabel("Sinopsis:");
		lblSinopsis.setBounds(10, 100, 70, 15);
		contentPane.add(lblSinopsis);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 120, 430, 140);
		contentPane.add(scrollPane);
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.cerrarVistaNuevoEpisodio();
			}
		});
		btnCerrar.setBounds(240, 270, 130, 25);
		contentPane.add(btnCerrar);
		
		JButton btnModificarDatos = new JButton("Crear Episodio");
		btnModificarDatos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.nuevoEpisodio(idSerie);
				controller.cerrarVistaNuevoEpisodio();
			}
		});
		btnModificarDatos.setBounds(100, 270, 130, 25);
		contentPane.add(btnModificarDatos);
		
		txtTituloepisodio = new JTextField();
		txtTituloepisodio.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtTituloepisodio.setBounds(10, 40, 280, 30);
		contentPane.add(txtTituloepisodio);
		txtTituloepisodio.setColumns(10);
		
		txtFechaestreno = new JTextField();
		txtFechaestreno.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtFechaestreno.setBounds(300, 40, 150, 30);
		contentPane.add(txtFechaestreno);
		txtFechaestreno.setColumns(10);
		
		textNumEpisodio = new JTextField();
		textNumEpisodio.setBounds(80, 80, 70, 20);
		contentPane.add(textNumEpisodio);
		textNumEpisodio.setColumns(10);
		
		txtNumtemporada = new JTextField();
		txtNumtemporada.setBounds(300, 80, 70, 20);
		contentPane.add(txtNumtemporada);
		txtNumtemporada.setColumns(10);
	}
	
	public Episodio cogerDatosEpisodio(int idSerie) {
		try {
			String titulo = this.txtTituloepisodio.getText();
			int cap = Integer.parseInt(this.textNumEpisodio.getText());
			int temp = Integer.parseInt(this.txtNumtemporada.getText());
			String sinopsis = this.textPane.getText();
			Date fecha = null;
			fecha = new SimpleDateFormat("yyyy-mm-dd").parse(this.txtFechaestreno.getText());
			java.sql.Date date;
			if (fecha != null)
				date = new java.sql.Date(fecha.getTime());
			
			else
				date = null;
			
			Episodio ep = new Episodio(idSerie, temp, cap, titulo, sinopsis, date);
			return ep;
		
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Error en la fecha", "Error", 
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Temporada y capitulo deben ser numeros", "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}
}

