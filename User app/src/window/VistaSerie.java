package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import datos.ModeloEpisodio;
import datos.Serie;
import swingController.SwingController;

import javax.swing.JTable;

public class VistaSerie extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private ModeloEpisodio modelo;
	private JSlider slider;
	private JTextPane comentarios;
	private JTextPane comenta;

	public VistaSerie(final SwingController controller, final Serie serie) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 10, 460, 880);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel(serie.tituloFechaToString());
		lblNombre.setFont(new Font("Dialog", Font.BOLD, 25));
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(10, 0, 430, 30);
		contentPane.add(lblNombre);
		
		JTextPane txtpnTitular = new JTextPane();
		txtpnTitular.setEditable(false);
		txtpnTitular.setText(serie.getTitular());
		txtpnTitular.setBounds(10, 35, 430, 40);
		contentPane.add(txtpnTitular);
		
		JLabel lblGenero = new JLabel("Genero:");
		lblGenero.setBounds(10, 80, 70, 15);
		contentPane.add(lblGenero);
		
		JLabel lblTipogenero = new JLabel(serie.generoToString());
		lblTipogenero.setBounds(90, 80, 350, 15);
		contentPane.add(lblTipogenero);
		
		JLabel lblNota = new JLabel("Nota media:");
		lblNota.setBounds(10, 100, 70, 15);
		contentPane.add(lblNota);
		
		JLabel lblCalificacion = new JLabel();
		lblCalificacion.setBounds(90, 100, 70, 15);
		Integer nota = controller.getNotaMediaSerie(serie.getIdSerie());
		lblCalificacion.setText(nota.toString());
		contentPane.add(lblCalificacion);
		
		JLabel lblSinopsis = new JLabel("Sinopsis:");
		lblSinopsis.setBounds(10, 120, 70, 15);
		contentPane.add(lblSinopsis);
		
		JLabel lblComentarios = new JLabel("Comentarios:");
		lblComentarios.setBounds(10, 560, 85, 15);
		contentPane.add(lblComentarios);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 140, 430, 150);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText(serie.getSinopsis());
		scrollPane.setViewportView(textPane);
		
		JButton btnSeguirSerie = new JButton("Seguir serie");
		btnSeguirSerie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.seguirSerie(serie);
				controller.cerrarVistaSerie();
				controller.crearPrincipal();
			}
		});
		btnSeguirSerie.setBounds(50, 430, 150, 25);
		contentPane.add(btnSeguirSerie);
		
		JButton btnVotar = new JButton("Votar");
		btnVotar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.votarSerie(serie.getIdSerie());
				controller.crearPrincipal();
				controller.cerrarVistaSerie();
			}
		});
		btnVotar.setBounds(140, 540, 150, 25);
		contentPane.add(btnVotar);
		
		JButton btnComentar = new JButton("Comentar");
		btnComentar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.comentarSerie(serie.getIdSerie());
			}
		});
		btnComentar.setBounds(50, 800, 150, 25);
		contentPane.add(btnComentar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.crearPrincipal();
				controller.cerrarVistaSerie();
			}
		});
		btnCerrar.setBounds(230, 800, 150, 25);
		contentPane.add(btnCerrar);
		
		JLabel lblEpisodios = new JLabel("Episodios:");
		lblEpisodios.setBounds(10, 300, 100, 15);
		contentPane.add(lblEpisodios);
		
		JScrollPane scrollPaneEpisodios = new JScrollPane();
		scrollPaneEpisodios.setBounds(10, 320, 430, 87);
		contentPane.add(scrollPaneEpisodios);
		
		table = new JTable();
		this.modelo = new ModeloEpisodio();
		this.modelo.addLista(serie.getListaEpisodios());
		table.setModel(this.modelo);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		scrollPaneEpisodios.setViewportView(table);
		
		JButton btnInformacion = new JButton("Informacion");
		btnInformacion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					controller.crearVistaEpisodio(modelo.getEpisodioSeleccionado(table.getSelectedRow()));
				} catch(java.lang.ArrayIndexOutOfBoundsException ex){
					JOptionPane.showMessageDialog(null, "Selecciona un episodio de la tabla", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnInformacion.setBounds(230, 430, 150, 25);
		contentPane.add(btnInformacion);
		
		JLabel lblDarNota = new JLabel("Pon nota:");
		lblDarNota.setBounds(10, 480, 80, 15);
		contentPane.add(lblDarNota);
		
		this.slider = new JSlider(0, 10, 5);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setBounds(80, 480, 330, 40);
		contentPane.add(slider);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(10, 580, 430, 120);
		contentPane.add(scrollPane2);
		
		comentarios = new JTextPane();
		comentarios.setEditable(false);
		if (serie.getListaComentarios().size() != 0)
			comentarios.setText(serie.comentariosToString());
		else
			comentarios.setText("No hay comentarios, se el primero en comentar.");
		
		scrollPane2.setViewportView(comentarios);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane3.setBounds(10, 720, 430, 60);
		contentPane.add(scrollPane3);
		
		comenta = new JTextPane();
		comenta.setText("Escribe un comentario...");
		scrollPane3.setViewportView(comenta);
	}
	
	public int getNota() {
		return this.slider.getValue();
	}
	
	public String getComentario() {
		return this.comenta.getText();
	}
	
	public void actualizaComentarios(String comentarios) {
		this.comentarios.setText(comentarios);
	}
}
