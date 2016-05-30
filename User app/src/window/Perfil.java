package window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import swingController.SwingController;

import datos.Usuario;

public class Perfil extends JFrame {

	private Usuario user;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtAaaammdd;
	private JTextField textField_1;
	private JDialog marco;
	private File fileToSave = null;

	public Perfil(final SwingController controller) {
		this.user = controller.getUserLogged();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPerfil = new JLabel("Perfil");
		lblPerfil.setBounds(180, 0, 70, 15);
		contentPane.add(lblPerfil);
		
		JLabel lblNick = new JLabel("Nick:");
		lblNick.setBounds(50, 25, 100, 15);
		contentPane.add(lblNick);
		
		JLabel lblNombreusuario = new JLabel(user.getNick());
		lblNombreusuario.setBounds(200, 25, 170, 15);
		contentPane.add(lblNombreusuario);
		
		JLabel lblContrasea = new JLabel("Password:");
		lblContrasea.setBounds(50, 50, 100, 15);
		contentPane.add(lblContrasea);
		
		textField = new JTextField();
		textField.setBounds(200, 50, 170, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento");
		lblFechaNacimiento.setBounds(50, 75, 130, 15);
		contentPane.add(lblFechaNacimiento);
		
		txtAaaammdd = new JTextField();
		txtAaaammdd.setText("aaaa-mm-dd");
		txtAaaammdd.setBounds(200, 75, 170, 19);
		contentPane.add(txtAaaammdd);
		txtAaaammdd.setColumns(10);
		
		JLabel lblAvatar = new JLabel("Avatar");
		lblAvatar.setBounds(50, 100, 70, 15);
		contentPane.add(lblAvatar);
		
		textField_1 = new JTextField();
		textField_1.setBounds(200, 100, 170, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnExaminar = new JButton("Seleccionar archivo");
		marco = new JDialog();
		marco.setResizable(false);
		marco.setTitle("Añadir foto");
		marco.setBounds(100, 100, 522, 287);
		marco.setLocationRelativeTo(null);
		marco.getContentPane().setLayout(new BorderLayout());
		btnExaminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF", "jpg", "gif");
				fc.setFileFilter(filter);
				fc.setDialogTitle("Seleccione imagen (NO MAS DE 2MB!)");
				int aux = fc.showOpenDialog(marco);
				marco.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				if (aux == JFileChooser.APPROVE_OPTION) {
				    fileToSave = fc.getSelectedFile();
				    textField_1.setText(fileToSave.toString());
				}			
			}
		});
		btnExaminar.setBounds(200, 125, 200, 25);
		contentPane.add(btnExaminar);
		
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Usuario usuarioModificado;
				String nuevaPass = textField.getText();
				if (nuevaPass.equals(""))
					nuevaPass = user.getPassword();
				
				String nuevaFecha = txtAaaammdd.getText();
				if (nuevaFecha.equals("") || (nuevaFecha.equals("aaaa-mm-dd")))
					nuevaFecha = user.getFechaNacimiento();
				
				Blob avatar = null;
				if (textField_1.getText().equals("")){
					avatar = user.getAvatar();
				}
				
				else{
					Path path = Paths.get(textField_1.getText());
					try {
						byte[] data = Files.readAllBytes(path);
						//avatar.
						avatar = user.getAvatar();
						avatar.setBytes(1, data);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				usuarioModificado = new Usuario(user.getNick(), nuevaPass, nuevaFecha, avatar);
				controller.modificarPerfil(usuarioModificado);
			}
		});
		btnAceptar.setBounds(70, 160, 117, 25);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.modificarPerfil(user);
			}
		});
		btnCancelar.setBounds(200, 160, 117, 25);
		contentPane.add(btnCancelar);
	}
}
