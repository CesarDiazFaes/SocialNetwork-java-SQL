package window;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;

import java.awt.FlowLayout;

import javax.swing.JScrollPane;

import datos.Episodio;
import datos.ModeloMisSeries;
import datos.ModeloEpisodio;
import datos.Serie;
import datos.Usuario;
import datos.ModeloBusquedaSerie;

import swingController.SwingController;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUsuario;
	private JLabel lblEdad;
	private JTextField textField;
	private JButton btnVerInformacion;
	private JList list;
	private JComboBox comboBox;
	private JTable table;
	private Usuario user;
	private ModeloBusquedaSerie modeloBusqueda;
	private ModeloMisSeries modeloMisSeries;
	private ModeloEpisodio modeloTabla;

	public Principal(final SwingController controller) throws SQLException, IOException {
		this.user = controller.getUserLogged();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 470, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAvatar = new JButton();

		Blob blob = user.getAvatar();
		int blobLength = (int) blob.length();
		
        byte[] blobAsBytes = blob.getBytes(1, blobLength);
        final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(blobAsBytes));

		btnAvatar.setIcon(new ImageIcon(bufferedImage));

		btnAvatar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.crearEditarPerfil(user);
			}
		});
		
		lblUsuario = new JLabel("Usuario");
		lblUsuario.setText(user.getNick());
		lblUsuario.setFont(new Font("Dialog", Font.BOLD, 16));
		
		lblEdad = new JLabel("edad");
		Integer edad = user.getEdad();
		if (edad != 0)
			lblEdad.setText(edad.toString());
		
		else
			lblEdad.setText("vacio");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JLabel lblSeguidoresDeSeries = new JLabel("Seguidores de series");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(7)
							.addComponent(btnAvatar, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addGap(107)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(11)
									.addComponent(lblEdad, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(132)
							.addComponent(lblSeguidoresDeSeries)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblSeguidoresDeSeries)
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAvatar, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(39)
							.addComponent(lblEdad))
						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Buscar series", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Buscar serie:");
		panel_2.add(lblNewLabel);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(15);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.buscarSerie(textField.getText());
			}
		});
		panel_2.add(btnBuscar);
		
		this.modeloBusqueda = new ModeloBusquedaSerie();
		list = new JList(modeloBusqueda.getModelo());
		JScrollPane scrollList = new JScrollPane();
		scrollList.setViewportView(list);
		panel_1.add(scrollList, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.SOUTH);
		
		btnVerInformacion = new JButton("Ver Informacion");
		btnVerInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.mostrarSerie();
			}
		});
		panel_3.add(btnVerInformacion);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Mis series", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.NORTH);
		
		JLabel lblMisSeries = new JLabel("serie:");
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_4.add(lblMisSeries);
		
		modeloMisSeries = new ModeloMisSeries();
		comboBox = new JComboBox(modeloMisSeries);
		this.actualizaModeloListaSeries(controller.getUserLogged().getListaSeries());
		comboBox.setPreferredSize(new Dimension(210, 24));
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.mostrarEpisodiosNoVistos();
			}
		});
		panel_4.add(comboBox);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(null);
		
		JLabel lblSeries = new JLabel("Episodios no vistos:");
		lblSeries.setBounds(0, 0, 70, 15);
		panel_5.add(lblSeries);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 27, 395, 114);
		panel_5.add(scrollPane);
		
		table = new JTable();
		this.modeloTabla = new ModeloEpisodio();
		table.setModel(this.modeloTabla);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		scrollPane.setViewportView(table);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnInformacion = new JButton("Informacion");
		btnInformacion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					controller.crearVistaEpisodio(modeloTabla.getEpisodioSeleccionado(table.getSelectedRow()));
				} catch(java.lang.ArrayIndexOutOfBoundsException ex){
					JOptionPane.showMessageDialog(null, "Selecciona un episodio de la tabla", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_6.add(btnInformacion);
		
		JButton btnMarcarComoVisto = new JButton("Marcar como visto");
		btnMarcarComoVisto.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.marcarEpisodio();	
			}
		});
		panel_6.add(btnMarcarComoVisto);
		
		contentPane.setLayout(gl_contentPane);
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
	
	public void actualizarModeloTabla(ArrayList<Episodio> nuevaLista){
		this.modeloTabla.clearTabla();
		this.modeloTabla.addLista(nuevaLista);
		this.table.setModel(this.modeloTabla);
	}
	
	public String getTituloSerie(int index){
		return this.modeloBusqueda.getTituloSerie(index);
	}
	
	public int getIndexLista(){
		return this.list.getSelectedIndex();
	}
	
	public Episodio getEpisodioSeleccionado(){
		return this.modeloTabla.getEpisodioSeleccionado(this.table.getSelectedRow());
	}
	
	public String getNombreComboBox(){
		String texto = this.modeloMisSeries.getSelectedItem();
		int i=0;
		
		if (texto != null){
			while (i<texto.length() && (texto.charAt(i)) != ('(')){
				i++;
			}
			
			texto = texto.substring(0, i-1);
		}
		return texto;
	}
}
