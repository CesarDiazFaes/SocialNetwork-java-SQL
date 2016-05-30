package window;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;

import datos.Interpretacion;
import datos.Episodio;
import datos.ModeloInterpretaciones;
import swingController.SwingController;

public class VistaEpisodioAdmin extends JFrame {

	private JPanel contentPane;
	private JTextField txtTituloepisodio;
	private JTextField txtFechaestreno;
	private JTextField textNumEpisodio;
	private JTextField txtNumtemporada;
	private JTextPane textPane;
	private JTable tableAct;
	private ModeloInterpretaciones modeloAct;
	private JTextField txtActor;
	private JTextField txtPersonaje;

	public VistaEpisodioAdmin(final SwingController controller, final Episodio episodio) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(630, 100, 460, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCapitulo = new JLabel("Capitulo:");
		lblCapitulo.setBounds(10, 80, 70, 15);
		contentPane.add(lblCapitulo);
		
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
		textPane.setText(episodio.getSinopsis());
		scrollPane.setViewportView(textPane);
		
		JLabel lblEpisodios = new JLabel("Actores:");
		lblEpisodios.setBounds(10, 270, 100, 15);
		contentPane.add(lblEpisodios);
		
		JScrollPane scrollPaneEpisodios = new JScrollPane();
		scrollPaneEpisodios.setBounds(10, 290, 430, 120);
		contentPane.add(scrollPaneEpisodios);
		
		tableAct = new JTable();
		this.modeloAct = new ModeloInterpretaciones();
		this.modeloAct.addLista(episodio.getListaActores());
		tableAct.setModel(this.modeloAct);
		tableAct.getColumnModel().getColumn(0).setResizable(false);
		tableAct.getColumnModel().getColumn(1).setResizable(false);
		scrollPaneEpisodios.setViewportView(tableAct);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.cerrarVistaEpisodio();
			}
		});
		btnCerrar.setBounds(310, 470, 130, 25);
		contentPane.add(btnCerrar);
		
		JButton btnModificarDatos = new JButton("Modificar datos");
		btnModificarDatos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.modificarEpisodio(episodio);
				controller.actualizarEpisodioVistaSerie(episodio.getIdSerie());
				controller.cerrarVistaEpisodio();
			}
		});
		btnModificarDatos.setBounds(30, 470, 130, 25);
		contentPane.add(btnModificarDatos);
		
		JButton btnEliminar = new JButton("Eliminar actor");
		btnEliminar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.eliminarActor(episodio, modeloAct.getActorSeleccionado(tableAct.getSelectedRow()));
				controller.actualizarTablaActores(episodio.getIdSerie(), episodio.getTemporada(), episodio.getCapitulo());
			}
		});
		btnEliminar.setBounds(170, 470, 130, 25);
		contentPane.add(btnEliminar);
		
		txtTituloepisodio = new JTextField();
		txtTituloepisodio.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtTituloepisodio.setText(episodio.getTitulo());
		txtTituloepisodio.setBounds(10, 0, 280, 30);
		contentPane.add(txtTituloepisodio);
		txtTituloepisodio.setColumns(10);
		
		txtFechaestreno = new JTextField();
		txtFechaestreno.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtFechaestreno.setText(episodio.getEstreno().toString());
		txtFechaestreno.setBounds(300, 0, 150, 30);
		contentPane.add(txtFechaestreno);
		txtFechaestreno.setColumns(10);
		
		textNumEpisodio = new JTextField();
		textNumEpisodio.setBounds(80, 80, 70, 20);
		Integer capitulo = episodio.getCapitulo();
		textNumEpisodio.setText(capitulo.toString());
		contentPane.add(textNumEpisodio);
		textNumEpisodio.setColumns(10);
		
		txtNumtemporada = new JTextField();
		txtNumtemporada.setBounds(300, 80, 70, 20);
		Integer temporada = episodio.getTemporada();
		txtNumtemporada.setText(temporada.toString());
		contentPane.add(txtNumtemporada);
		txtNumtemporada.setColumns(10);
		
		txtActor = new JTextField();
		txtActor.setBounds(10, 430, 150, 25);
		txtActor.setText("Nombre actor");
		contentPane.add(txtActor);
		
		txtPersonaje = new JTextField();
		txtPersonaje.setBounds(165, 430, 150, 25);
		txtPersonaje.setText("Nombre personaje");
		contentPane.add(txtPersonaje);
		
		JButton btnaddActor = new JButton("Agregar actor");
		btnaddActor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.agregarActorAEpisodio(episodio);
				controller.actualizarTablaActores(episodio.getIdSerie(), episodio.getTemporada(), episodio.getCapitulo());
			}
		});
		btnaddActor.setBounds(320, 430, 120, 25);
		contentPane.add(btnaddActor);
	}
	
	public Episodio cogerDatosEpisodio(int idSerie) {
		String titulo = this.txtTituloepisodio.getText();
		int cap = Integer.parseInt(this.textNumEpisodio.getText());
		int temp = Integer.parseInt(this.txtNumtemporada.getText());
		String sinopsis = this.textPane.getText();
		Date fecha = null;
		try {
			fecha = new SimpleDateFormat("yyyy-mm-dd").parse(this.txtFechaestreno.getText());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Error en la fecha", "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		java.sql.Date date;
		if (fecha != null)
			date = new java.sql.Date(fecha.getTime());
		
		else
			date = null;
		
		Episodio ep = new Episodio(idSerie, temp, cap, titulo, sinopsis, date);
		return ep;
	}
	
	public Interpretacion getDatosActor() {
		return new Interpretacion(txtActor.getText(), txtPersonaje.getText());
	}
	
	public void actualizarTabla(ArrayList<Interpretacion> nuevaLista){
		this.modeloAct.clearTabla();
		this.modeloAct.addLista(nuevaLista);
		this.tableAct.setModel(this.modeloAct);
	}
}
