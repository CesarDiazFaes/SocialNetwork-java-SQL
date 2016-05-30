package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

import datos.Actor;
import datos.ModeloActores;
import datos.ModeloBusquedaSerie;
import datos.ModeloEpisodio;
import datos.ModeloMisSeries;
import datos.ModeloPersonajes;
import datos.Personaje;
import datos.Serie;
import swingController.SwingController;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class VistaAdministrador extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Serie serie;
	private ModeloBusquedaSerie modeloBusqueda;
	private ModeloMisSeries modeloMisSeries;
	private JList list;
	private JTable tableP;
	private ModeloPersonajes modeloPersonajes;
	private JTextField txtNombrePers;
	private JTextField txtDescripcionPers;
	private JTable tableA;
	private ModeloActores modeloActores;
	private JTextField txtNombreAct;
	private JTextField txtDescripcionAct;

	public VistaAdministrador(final SwingController controller) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAdministracion = new JLabel("Administracion");
		lblAdministracion.setBounds(170, 0, 120, 15);
		contentPane.add(lblAdministracion);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 20, 600, 400);
		contentPane.add(tabbedPane);
		
		JPanel panelSeries = new JPanel();
		tabbedPane.addTab("Series", null, panelSeries, null);
		panelSeries.setLayout(null);
		
		JLabel lblBuscarSerie = new JLabel("Buscar serie:");
		lblBuscarSerie.setBounds(68, 12, 100, 15);
		panelSeries.add(lblBuscarSerie);
		
		textField = new JTextField();
		textField.setBounds(171, 10, 150, 19);
		panelSeries.add(textField);
		textField.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buscarSerie(textField.getText());
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 39, 409, 152);
		panelSeries.add(scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		btnBuscar.setBounds(330, 7, 84, 25);
		panelSeries.add(btnBuscar);
		this.modeloBusqueda = new ModeloBusquedaSerie();
		
		JButton btnVerInformacion = new JButton("Ver Informacion");
		btnVerInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.mostrarSerie();
			}
		});
		btnVerInformacion.setBounds(68, 200, 150, 25);
		panelSeries.add(btnVerInformacion);
		
		JButton btnNuevaSerie = new JButton("Nueva serie");
		btnNuevaSerie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.nuevaSerie(serie);
				controller.cerrarVistaAdmin();
			}
		});
		btnNuevaSerie.setBounds(223, 200, 150, 25);
		panelSeries.add(btnNuevaSerie);
		
		JPanel panelPersonajes = new JPanel();
		tabbedPane.addTab("Personajes", null, panelPersonajes, null);
		panelPersonajes.setLayout(null);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(12, 12, 600, 180);
		panelPersonajes.add(scrollPane2);
		
		tableP = new JTable();
		this.modeloPersonajes = new ModeloPersonajes();
		tableP.setModel(this.modeloPersonajes);
		tableP.getColumnModel().getColumn(1).setPreferredWidth(250);
		this.actualizaModeloPersonajes(controller.getPersonajes());
		scrollPane2.setViewportView(tableP);
		
		txtNombrePers = new JTextField();
		txtNombrePers.setBounds(12, 220, 150, 25);
		txtNombrePers.setText("Nombre personaje");
		panelPersonajes.add(txtNombrePers);
		
		txtDescripcionPers = new JTextField();
		txtDescripcionPers.setBounds(170, 220, 430, 25);
		txtDescripcionPers.setText("Descripcion del personaje");
		panelPersonajes.add(txtDescripcionPers);
		
		JButton btnaddPersonaje = new JButton("Agregar personaje");
		btnaddPersonaje.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.agregarPersonaje();
			}
		});
		btnaddPersonaje.setBounds(250, 250, 120, 25);
		panelPersonajes.add(btnaddPersonaje);
		
		JPanel panelActores = new JPanel();
		tabbedPane.addTab("Actores", null, panelActores, null);
		panelActores.setLayout(null);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setBounds(12, 12, 600, 180);
		panelActores.add(scrollPane3);
		
		tableA = new JTable();
		this.modeloActores = new ModeloActores();
		tableA.setModel(this.modeloActores);
		tableA.getColumnModel().getColumn(1).setPreferredWidth(250);
		this.actualizaModeloActores(controller.getActores());
		scrollPane3.setViewportView(tableA);
		
		txtNombreAct = new JTextField();
		txtNombreAct.setBounds(12, 220, 150, 25);
		txtNombreAct.setText("Nombre actor");
		panelActores.add(txtNombreAct);
		
		txtDescripcionAct = new JTextField();
		txtDescripcionAct.setBounds(170, 220, 430, 25);
		txtDescripcionAct.setText("Fecha de nacimiento(yyyy-mm-dd)");
		panelActores.add(txtDescripcionAct);
		
		JButton btnaddActor = new JButton("Agregar actor");
		btnaddActor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.agregarActor();
			}
		});
		btnaddActor.setBounds(250, 250, 120, 25);
		panelActores.add(btnaddActor);
	}

	public void actualizaModeloBusqueda(ArrayList<Serie> lista){
		ArrayList<String> array = new ArrayList<String>();
		array = this.modeloBusqueda.pasarAString(lista);
		this.modeloBusqueda.addListaSeries(array);
		this.list.setModel(this.modeloBusqueda.getModelo());
	}
	
	public void actualizaModeloListaSeries(ArrayList<Serie> lista){
		this.modeloMisSeries.addTodasMisSeries(lista);
		this.list.setModel(this.modeloBusqueda.getModelo());
	}
	
	public void actualizaModeloPersonajes(ArrayList<Personaje> lista){
		this.modeloPersonajes.clearTabla();
		this.modeloPersonajes.addLista(lista);
		this.tableP.setModel(this.modeloPersonajes);
	}
	
	public void actualizaModeloActores(ArrayList<Actor> lista) {
		this.modeloActores.clearTabla();
		this.modeloActores.addLista(lista);
		this.tableA.setModel(this.modeloActores);
	}
	
	public String getTituloSerie(int index){
		return this.modeloBusqueda.getTituloSerie(index);
	}
	
	public int getIndexLista(){
		return this.list.getSelectedIndex();
	}
	
	public String getNombrePersonaje() {
		return this.txtNombrePers.getText();
	}
	
	public String getDescripcionPersonaje() {
		return this.txtDescripcionPers.getText();
	}
	
	public String getNombreActor() {
		return this.txtNombreAct.getText();
	}
	
	public String getFechaActor() {
		return this.txtDescripcionAct.getText();
	}
}
