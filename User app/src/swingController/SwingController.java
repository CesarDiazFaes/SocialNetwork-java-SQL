package swingController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import window.LogIn;
import window.Principal;
import window.Perfil;
import window.VistaEpisodio;
import window.VistaSerie;

import datos.Comentarios;
import datos.Episodio;
import datos.Serie;
import datos.Usuario;

public class SwingController {
	
	private Program prog;
	private Principal ventanaPrincipal;
	private LogIn ventanaLogin;
	private Perfil perfil;
	private VistaSerie vistaSerie;
	private VistaEpisodio vistaEpisodio;
	
	public SwingController(Program prog){
		this.prog = prog;
		this.ventanaLogin = new LogIn(this);
		this.ventanaLogin.setVisible(true);
	}
	
	public Usuario getUserLogged(){
		return this.prog.getUserLogged();
	}
	
	public void login (Usuario user){
		if (prog.compruebaUsuario(user)){
			prog.setUserLogged(prog.obtenerDatosUsuario(user));
			crearPrincipal();	
			this.ventanaLogin.dispose();
		}
		
		else
			JOptionPane.showMessageDialog(null, "Usuario no registrado", "Error",
					JOptionPane.ERROR_MESSAGE);
	}
	
	public void signUp (Usuario user){
		if(prog.nuevoUsuario(user)){
			crearPrincipal();
		}
	}
	
	public void crearPrincipal(){
		try {
			this.ventanaPrincipal = new Principal(this);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.ventanaPrincipal.setVisible(true);
		this.ventanaLogin.dispose();
	}
	
	public void crearVistaSerie(Serie serie){
		this.vistaSerie = new VistaSerie(this, serie);
		this.vistaSerie.setVisible(true);
	}
	
	public void crearVistaEpisodio(Episodio episodio){
		this.vistaEpisodio = new VistaEpisodio(this, episodio);
		this.vistaEpisodio.setVisible(true);
	}
	
	public void crearEditarPerfil(Usuario user){
		this.perfil = new Perfil(this);
		this.perfil.setVisible(true);
		this.ventanaPrincipal.dispose();
	}
	
	public void modificarPerfil(Usuario user){
		prog.insertarDatosPerfil(user);
		this.perfil.dispose();
		crearPrincipal();
	}

	public void buscarSerie(String palabra){
		ArrayList<Serie> lista = new ArrayList<Serie>();
		lista = this.prog.buscarSeriesPorPalabra(palabra);
		this.ventanaPrincipal.actualizaModeloBusqueda(lista);
	}

	public void mostrarSerie(){
		try{
			int index = this.ventanaPrincipal.getIndexLista();
			String titulo = this.ventanaPrincipal.getTituloSerie(index);
			Serie serie = this.prog.buscarSerie(titulo);
			this.crearVistaSerie(serie);
			this.ventanaPrincipal.dispose();
		}
		
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(null, "Elige una serie de la lista", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void cerrarVistaSerie(){
		this.vistaSerie.dispose();
	}
	
	public void cerrarVistaEpisodio(){
		this.vistaEpisodio.dispose();
	}
	
	public void seguirSerie(Serie serie){
		this.prog.seguirSerie(serie);
		this.ventanaPrincipal.actualizaModeloListaSeries(this.getUserLogged().getListaSeries());
	}

	public void mostrarEpisodiosNoVistos() {
		ArrayList<Episodio> lista = new ArrayList<Episodio>();
		String titulo = this.ventanaPrincipal.getNombreComboBox();
		Serie serie = new Serie();
		serie = this.prog.buscarSerie(titulo);
		lista = this.prog.getListaEpisodiosNoVistos(serie.getIdSerie());
		this.ventanaPrincipal.actualizarModeloTabla(lista);
	}

	public void marcarEpisodio() {
		try{
			ArrayList<Episodio> nuevaLista = new ArrayList<Episodio>();
			Episodio episodio = this.ventanaPrincipal.getEpisodioSeleccionado();
			this.prog.marcarEpisodio(episodio);
			nuevaLista = this.prog.getListaEpisodiosNoVistos(episodio.getIdSerie());
			this.ventanaPrincipal.actualizarModeloTabla(nuevaLista);
			
		} catch(java.lang.ArrayIndexOutOfBoundsException ex){
			JOptionPane.showMessageDialog(null, "Selecciona un episodio de la tabla", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public int getNotaMediaSerie(int idSerie) {
		return this.prog.notaMediaSerie(idSerie);
	}

	public void votarSerie(int idSerie) {
		if (this.prog.haVotado(idSerie))
			this.prog.modificarVoto(idSerie, this.vistaSerie.getNota());
		
		else
			this.prog.votarSerie(idSerie, this.vistaSerie.getNota());
	}

	public int getNotaMediaEpisodio(Episodio ep) {
		return this.prog.notaMediaEpisodio(ep.getIdSerie(), ep.getTemporada(), ep.getCapitulo());
	}

	public void votarEpisodio(int idSerie, int temp, int cap) {
		if (this.prog.haVotadoEpisodio(idSerie, temp, cap))
			this.prog.modificarVotoEpisodio(idSerie, this.vistaEpisodio.getNota(), temp, cap);
		
		else
			this.prog.votarEpisodio(idSerie, this.vistaEpisodio.getNota(), temp, cap);
	}

	public void comentarSerie(int idSerie) {
		this.prog.comentarSerie(idSerie, this.vistaSerie.getComentario());
		ArrayList<Comentarios> lista = this.prog.getComentariosSerie(idSerie);
		String cadena = "";
		for (int i=0; i<lista.size(); i++){
			cadena = cadena + lista.get(i);
			cadena = cadena + '\n';
		}
		
		this.vistaSerie.actualizaComentarios(cadena);
	}
	
	public void comentarEpisodio(int idSerie, int temp, int cap) {
		this.prog.comentarEpisodio(idSerie, temp, cap, this.vistaEpisodio.getComentario());
		ArrayList<Comentarios> lista = this.prog.getComentariosEpisodio(idSerie, temp, cap);
		String cadena = "";
		for (int i=0; i<lista.size(); i++){
			cadena = cadena + lista.get(i);
			cadena = cadena + '\n';
		}
		
		this.vistaEpisodio.actualizaComentarios(cadena);
	}

	public Serie obtenerSerie(int idSerie) {
		return this.prog.buscarSeriePorId(idSerie);
	}
}
