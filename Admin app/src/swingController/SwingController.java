package swingController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import datos.Actor;
import datos.Interpretacion;
import datos.Episodio;
import datos.Personaje;
import datos.Serie;
import window.*;

public class SwingController {
	
	private Program prog;
	private VistaAdministrador vistaAdministrador;
	private VistaSerieAdmin vistaSerieAdmin;
	private VistaNuevaSerie vistaNuevaSerie;
	private VistaEpisodioAdmin vistaEpisodio;
	private VistaNuevoEpisodio vistaNuevoEpisodio;
	
	public SwingController(Program prog){
		this.prog = prog;
		this.vistaAdministrador = new VistaAdministrador(this);
		this.vistaAdministrador.setVisible(true);
	}
	
	public void verSerie(Serie serie){
		this.vistaSerieAdmin = new VistaSerieAdmin(this, serie);
		this.vistaSerieAdmin.setVisible(true);
	}
	
	public void nuevaSerie(Serie serie){
		this.vistaNuevaSerie = new VistaNuevaSerie(this, serie);
		this.vistaNuevaSerie.setVisible(true);
	}
	
	public void verAdministrador(){
		this.vistaAdministrador = new VistaAdministrador(this);
		this.vistaAdministrador.setVisible(true);
	}
	
	public void verEpisodio(Episodio episodio){
		this.vistaEpisodio = new VistaEpisodioAdmin(this, episodio);
		this.vistaEpisodio.setVisible(true);
	}
	
	public void buscarSerie(String palabra){
		ArrayList<Serie> lista = new ArrayList<Serie>();
		lista = this.prog.buscarSeriesPorPalabra(palabra);
		this.vistaAdministrador.actualizaModeloBusqueda(lista);
	}
	
	public void mostrarSerie(){
		try{
			int index = this.vistaAdministrador.getIndexLista();
			String titulo = this.vistaAdministrador.getTituloSerie(index);
			Serie serie = this.prog.buscarSerie(titulo);
			this.verSerie(serie);
			this.vistaAdministrador.dispose();
		}
		
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(null, "Elige una serie de la lista", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void crearNuevaSerie(){
		Serie serie = this.vistaNuevaSerie.dameNuevaSerie();
		if (!this.prog.existeSerie(serie.getNombre()))
			this.prog.crearSerie(serie);
		
		else {
			JOptionPane.showMessageDialog(null, "La serie ya existe", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void modificarDatosSerie(Serie nuevaSerie) {
		this.prog.modificarDatosSerie(nuevaSerie);
	}
	
	public Serie cambiarSerie(Serie serie){
		return this.vistaSerieAdmin.cambioSerie(serie);
	}
	
	public void cerrarVistaSerie(){
		this.vistaSerieAdmin.dispose();
	}
	
	public void cerrarVistaNuevaSerie(){
		this.vistaNuevaSerie.dispose();
	}
	
	public void cerrarVistaAdmin(){
		this.vistaAdministrador.dispose();
	}
	
	public void cerrarVistaEpisodio(){
		this.vistaEpisodio.dispose();
	}

	public void modificarEpisodio(Episodio actual) {
		Episodio nuevo = this.vistaEpisodio.cogerDatosEpisodio(actual.getIdSerie());
		if ((nuevo.getCapitulo() != actual.getCapitulo()) || (nuevo.getTemporada() != actual.getTemporada())) {
			if (this.prog.getDatosEpisodio(actual.getIdSerie(), nuevo.getTemporada(), nuevo.getCapitulo()) == null)
				this.prog.modificarEpisodio(nuevo, actual);
			
			else {
				JOptionPane.showMessageDialog(null, "El episodio ya existe", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		else
			this.prog.modificarEpisodio(nuevo, actual);
	}
	
	public void actualizarEpisodioVistaSerie(int idSerie) {	//Si se modifica un episodio lo actualiza en la tabla de episodios de la serie
		this.vistaSerieAdmin.actualizarModeloTabla(this.prog.getListaEpisodios(idSerie));
	}

	public void borrarEpisodio(Episodio ep) {
		this.prog.eliminarEpisodio(ep.getIdSerie(), ep.getCapitulo(), ep.getTemporada());
	}
	
	public void verNuevoEpisodio(int id) {
		this.vistaNuevoEpisodio = new VistaNuevoEpisodio(this, id);
		this.vistaNuevoEpisodio.setVisible(true);
	}
	
	public void cerrarVistaNuevoEpisodio() {
		this.vistaNuevoEpisodio.dispose();
	}

	public void nuevoEpisodio(int id) {
		Episodio ep = this.vistaNuevoEpisodio.cogerDatosEpisodio(id);
		if (ep != null) {
			if (ep.getCapitulo() < 0 || ep.getTemporada() < 0) {
				JOptionPane.showMessageDialog(null, "Temporada y/o capitulo deben ser enteros positivos", "Error", 
						JOptionPane.ERROR_MESSAGE);
			}
			
			else if (this.prog.getDatosEpisodio(id, ep.getTemporada(), ep.getCapitulo()) != null) {
				JOptionPane.showMessageDialog(null, "El episodio ya existe, cambie temporada y/o capitulo", "Error", 
						JOptionPane.ERROR_MESSAGE);
			}
			
			else {
				this.prog.crearEpisodio(ep);
				this.actualizarEpisodioVistaSerie(id);
			}
		}
	}

	public void actualizarTablaActores(int idSerie, int temp, int cap) {
		ArrayList<Interpretacion> lista = this.prog.getListaActoresEpisodio(idSerie, temp, cap);
		this.vistaEpisodio.actualizarTabla(lista);	
	}

	public void agregarActorAEpisodio(Episodio episodio) {
		Interpretacion actor = this.vistaEpisodio.getDatosActor();
		if (!this.prog.existeActor(actor.getNombre())) {
			JOptionPane.showMessageDialog(null, "El actor no existe", "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
			
		else if (!this.prog.existePersonaje(actor.getPersonaje())) {
			JOptionPane.showMessageDialog(null, "El personaje no existe", "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		else {
			this.prog.agregarActorAEpisodio(episodio, actor);
			this.actualizarEpisodioVistaSerie(episodio.getIdSerie());
		}
	}
	
	public void eliminarActor(Episodio episodio, Interpretacion actor) {
		this.prog.eliminarActor(episodio, actor);
		this.actualizarEpisodioVistaSerie(episodio.getIdSerie());
	}

	public ArrayList<Personaje> getPersonajes() {
		return this.prog.buscarTodosLosPersonajes();
	}

	public void agregarPersonaje() {
		this.prog.insertarPersonaje(this.vistaAdministrador.getNombrePersonaje(), this.vistaAdministrador.getDescripcionPersonaje());
		this.vistaAdministrador.actualizaModeloPersonajes(this.prog.buscarTodosLosPersonajes());
	}

	public ArrayList<Actor> getActores() {
		return this.prog.buscarTodosLosActores();
	}

	public void agregarActor() {
		try {
			String nombreActor = this.vistaAdministrador.getNombreActor();
			Date fecha = null;
			fecha = new SimpleDateFormat("yyyy-mm-dd").parse(this.vistaAdministrador.getFechaActor());
			java.sql.Date date;
			if (fecha != null)
				date = new java.sql.Date(fecha.getTime());
			
			else
				date = null;
			
			if (date == null) {
				JOptionPane.showMessageDialog(null, "Fecha incorrecta", "Error", 
						JOptionPane.ERROR_MESSAGE);
			}
			
			else {
				this.prog.insertarActor(nombreActor, date);
				this.vistaAdministrador.actualizaModeloActores(this.prog.buscarTodosLosActores());
			}
		
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Fecha incorrecta", "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
