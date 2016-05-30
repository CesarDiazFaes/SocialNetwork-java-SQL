package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import datos.Actor;
import datos.Episodio;
import datos.ModeloActores;
import swingController.SwingController;

public class VistaEpisodio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JSlider slider;
	private JTable tableAct;
	private ModeloActores modeloAct;
	private JTextPane comentarios;
	private JTextPane comenta;

	public VistaEpisodio(final SwingController controller, final Episodio episodio) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(560, 10, 460, 880);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel(episodio.getTitulo());
		lblNombre.setFont(new Font("Dialog", Font.BOLD, 25));
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(10, 0, 430, 30);
		contentPane.add(lblNombre);
		
		JLabel lblTemp = new JLabel("Temporada:");
		lblTemp.setBounds(10, 40, 70, 15);
		contentPane.add(lblTemp);
		
		Integer temp = episodio.getTemporada();
		JLabel lblNumTemp = new JLabel(temp.toString());
		lblNumTemp.setBounds(90, 40, 350, 15);
		contentPane.add(lblNumTemp);
		
		JLabel lblCap = new JLabel("Capitulo:");
		lblCap.setBounds(10, 60, 70, 15);
		contentPane.add(lblCap);
		
		Integer cap = episodio.getCapitulo();
		JLabel lblNumCap = new JLabel(cap.toString());
		lblNumCap.setBounds(90, 60, 350, 15);
		contentPane.add(lblNumCap);
		
		JLabel lblEstreno = new JLabel("Estreno:");
		lblEstreno.setBounds(10, 80, 70, 15);
		contentPane.add(lblEstreno);
		
		JLabel lblFecha = new JLabel(episodio.getEstreno().toString());
		lblFecha.setBounds(90, 80, 350, 15);
		contentPane.add(lblFecha);
		
		JLabel lblNota = new JLabel("Nota media:");
		lblNota.setBounds(10, 100, 70, 15);
		contentPane.add(lblNota);
		
		JLabel lblCalificacion = new JLabel();
		lblCalificacion.setBounds(90, 100, 70, 15);
		Integer nota = controller.getNotaMediaEpisodio(episodio);
		lblCalificacion.setText(nota.toString());
		contentPane.add(lblCalificacion);
		
		JLabel lblSinopsis = new JLabel("Sinopsis:");
		lblSinopsis.setBounds(10, 120, 70, 15);
		contentPane.add(lblSinopsis);
		
		JLabel lblComentarios = new JLabel("Comentarios:");
		lblComentarios.setBounds(10, 530, 85, 15);
		contentPane.add(lblComentarios);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 140, 430, 150);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText(episodio.getSinopsis());
		scrollPane.setViewportView(textPane);
		
		JLabel lblEpisodios = new JLabel("Actores:");
		lblEpisodios.setBounds(10, 300, 100, 15);
		contentPane.add(lblEpisodios);
		
		JScrollPane scrollPaneEpisodios = new JScrollPane();
		scrollPaneEpisodios.setBounds(10, 320, 430, 120);
		contentPane.add(scrollPaneEpisodios);
		
		tableAct = new JTable();
		this.modeloAct = new ModeloActores();
		this.modeloAct.addLista(episodio.getListaActores());
		tableAct.setModel(this.modeloAct);
		tableAct.getColumnModel().getColumn(0).setResizable(false);
		tableAct.getColumnModel().getColumn(1).setResizable(false);
		scrollPaneEpisodios.setViewportView(tableAct);
		
		JButton btnVotar = new JButton("Votar");
		btnVotar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.votarEpisodio(episodio.getIdSerie(), episodio.getTemporada(), episodio.getCapitulo());
				controller.cerrarVistaEpisodio();
			}
		});
		btnVotar.setBounds(140, 510, 150, 25);
		contentPane.add(btnVotar);
		
		JButton btnComentar = new JButton("Comentar");
		btnComentar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.comentarEpisodio(episodio.getIdSerie(), episodio.getTemporada(), episodio.getCapitulo());
			}
		});
		btnComentar.setBounds(50, 770, 150, 25);
		contentPane.add(btnComentar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.cerrarVistaSerie();
				controller.crearVistaSerie(controller.obtenerSerie(episodio.getIdSerie()));
				controller.cerrarVistaEpisodio();
				
			}
		});
		btnCerrar.setBounds(230, 770, 150, 25);
		contentPane.add(btnCerrar);
		
		JLabel lblDarNota = new JLabel("Pon nota:");
		lblDarNota.setBounds(10, 450, 80, 15);
		contentPane.add(lblDarNota);
		
		this.slider = new JSlider(0, 10, 5);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setBounds(80, 450, 330, 40);
		contentPane.add(slider);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(10, 550, 430, 120);
		contentPane.add(scrollPane2);
		
		comentarios = new JTextPane();
		comentarios.setEditable(false);
		if (episodio.getListaComentarios().size() != 0)
			comentarios.setText(episodio.comentariosToString());
		else
			comentarios.setText("No hay comentarios, se el primero en comentar.");
		
		scrollPane2.setViewportView(comentarios);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane3.setBounds(10, 690, 430, 60);
		contentPane.add(scrollPane3);
		
		comenta = new JTextPane();
		comenta.setText("Escribe un comentario...");
		scrollPane3.setViewportView(comenta);
	}
	
	public int getNota() {
		return this.slider.getValue();
	}
	
	public void actualizarModeloTabla(ArrayList<Actor> nuevaLista){
		this.modeloAct.clearTabla();
		this.modeloAct.addLista(nuevaLista);
		this.tableAct.setModel(this.modeloAct);
	}
	
	public void actualizaComentarios(String comentarios) {
		this.comentarios.setText(comentarios);
	}
	
	public String getComentario() {
		return this.comenta.getText();
	}
}
