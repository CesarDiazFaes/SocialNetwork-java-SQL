package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import datos.Episodio;
import datos.ModeloEpisodio;
import datos.Serie;
import swingController.SwingController;

public class VistaSerieAdmin extends JFrame {

	private JPanel contentPane;
	private JTextPane txtpnTitular;
	private JTextPane textPane;
	private JLabel lblSinopsis;
	private JTable table;
	private ModeloEpisodio modeloTabla;

	public VistaSerieAdmin(final SwingController controller, final Serie serie) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel(serie.tituloFechaToString());
		lblNombre.setFont(new Font("Dialog", Font.BOLD, 25));
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(10, 0, 480, 30);
		contentPane.add(lblNombre);
		
		txtpnTitular = new JTextPane();
		txtpnTitular.setText(serie.getTitular());
		txtpnTitular.setBounds(10, 35, 480, 40);
		contentPane.add(txtpnTitular);
		
		JLabel lblGenero = new JLabel("Genero:");
		lblGenero.setBounds(10, 80, 70, 15);
		contentPane.add(lblGenero);
		
		JLabel lblTipogenero = new JLabel(serie.generoToString());
		lblTipogenero.setBounds(90, 80, 350, 15);
		contentPane.add(lblTipogenero);
		
		lblSinopsis = new JLabel("Sinopsis:");
		lblSinopsis.setBounds(10, 100, 70, 15);
		contentPane.add(lblSinopsis);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 120, 480, 150);
		contentPane.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setText(serie.getSinopsis());
		scrollPane.setViewportView(textPane);
		
		JScrollPane scrollPaneEpisodios = new JScrollPane();
		scrollPaneEpisodios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneEpisodios.setBounds(10, 300, 480, 140);
		contentPane.add(scrollPaneEpisodios);
		
		table = new JTable();
		this.modeloTabla = new ModeloEpisodio();
		this.actualizarModeloTabla(serie.getListaEpisodios());
		table.setModel(this.modeloTabla);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		scrollPaneEpisodios.setViewportView(table);
		
		JButton btnSeguirSerie = new JButton("Modificar datos");
		btnSeguirSerie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Serie nuevaSerie = new Serie();
				nuevaSerie = controller.cambiarSerie(serie);
				if (nuevaSerie != null)
					controller.modificarDatosSerie(nuevaSerie);
					
				controller.verAdministrador();
				controller.cerrarVistaSerie();
			}
		});
		btnSeguirSerie.setBounds(60, 470, 122, 25);
		contentPane.add(btnSeguirSerie);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.verAdministrador();
				controller.cerrarVistaSerie();
			}
		});
		btnCerrar.setBounds(326, 500, 130, 25);
		contentPane.add(btnCerrar);
		
		JButton btnBorrar = new JButton("Borrar episodio");
		btnBorrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					controller.borrarEpisodio(modeloTabla.getEpisodioSeleccionado(table.getSelectedRow()));
					controller.actualizarEpisodioVistaSerie(serie.getIdSerie());
				} catch(java.lang.ArrayIndexOutOfBoundsException ex){
					JOptionPane.showMessageDialog(null, "Selecciona un episodio de la tabla", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBorrar.setBounds(60, 500, 122, 25);
		contentPane.add(btnBorrar);
		
		JButton btnMostrarInformacion = new JButton("Info episodio");
		btnMostrarInformacion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					controller.verEpisodio(modeloTabla.getEpisodioSeleccionado(table.getSelectedRow()));
				} catch(java.lang.ArrayIndexOutOfBoundsException ex){
					JOptionPane.showMessageDialog(null, "Selecciona un episodio de la tabla", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnMostrarInformacion.setBounds(186, 470, 135, 25);
		contentPane.add(btnMostrarInformacion);
		
		JButton btnAnadirCapitulo = new JButton("Nuevo episodio");
		btnAnadirCapitulo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.verNuevoEpisodio(serie.getIdSerie());
			}
		});
		btnAnadirCapitulo.setBounds(326, 470, 130, 25);
		contentPane.add(btnAnadirCapitulo);
		
		JLabel lblEpisodios = new JLabel("Episodios:");
		lblEpisodios.setBounds(10, 280, 100, 15);
		contentPane.add(lblEpisodios);
	}
	
	public Serie cambioSerie(Serie serie){
		String titular = this.txtpnTitular.getText();
		String sinopsis = this.textPane.getText();
		
		if (titular.equals(serie.getTitular()) && (sinopsis.equals(serie.getSinopsis())))
			return null;
		
		else{
			Serie nuevaSerie = serie;
			nuevaSerie.setTitular(titular);
			nuevaSerie.setSinopsis(sinopsis);
			return nuevaSerie;
		}
	}
	
	public void actualizarModeloTabla(ArrayList<Episodio> nuevaLista){
		this.modeloTabla.clearTabla();
		this.modeloTabla.addLista(nuevaLista);
		this.table.setModel(this.modeloTabla);
	}
}
