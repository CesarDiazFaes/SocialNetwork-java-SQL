package window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import datos.Usuario;
import swingController.SwingController;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogIn extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	public LogIn(final SwingController controller) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(66, 91, 70, 15);
		contentPane.add(lblUsuario);
		
		textField = new JTextField();
		textField.setBounds(193, 91, 163, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Password");
		lblContrasea.setBounds(66, 136, 92, 15);
		contentPane.add(lblContrasea);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(193, 134, 163, 25);
		contentPane.add(passwordField);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Usuario user = new Usuario(textField.getText(),passwordField.getText(), null, null);
				controller.login(user);	
			}
		});
		btnAceptar.setBounds(46, 193, 150, 25);
		contentPane.add(btnAceptar);
		
		JButton btnNuevoUsuario = new JButton("Nuevo usuario");
		btnNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario user = new Usuario(textField.getText(), passwordField.getText(), null, null);
				textField.setText("");
				passwordField.setText("");
				controller.signUp(user);
			}
		});
		btnNuevoUsuario.setBounds(222, 193, 150, 25);
		contentPane.add(btnNuevoUsuario);
		
		JLabel lblBienvenido = new JLabel("Bienvenido");
		lblBienvenido.setFont(new Font("Dialog", Font.BOLD, 20));
		lblBienvenido.setBounds(148, 40, 163, 31);
		contentPane.add(lblBienvenido);
	}
}
