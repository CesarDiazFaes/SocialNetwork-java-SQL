package datos;

import java.sql.Date;
import java.util.ArrayList;

public class Episodio {
	
	private int idSerie;
	private int temporada;
	private int capitulo;
	private String titulo;
	private String sinopsis;
	private Date estreno;
	private ArrayList<Actor> listaActores;
	private ArrayList<Comentarios> listaComentarios;
	
	public Episodio(){
		
	}
	
	public Episodio(int id, int temp, int cap, String titulo, String sinopsis, Date estreno){
		this.idSerie = id;
		this.temporada = temp;
		this.capitulo = cap;
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.estreno = estreno;
		this.listaActores = new ArrayList<Actor>();
	}

	public int getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}

	public int getTemporada() {
		return temporada;
	}

	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}

	public int getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(int capitulo) {
		this.capitulo = capitulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public Date getEstreno() {
		return estreno;
	}

	public void setEstreno(Date estreno) {
		this.estreno = estreno;
	}

	public ArrayList<Actor> getListaActores() {
		return listaActores;
	}

	public void setListaActores(ArrayList<Actor> listaActores) {
		this.listaActores = listaActores;
	}
	
	public ArrayList<Comentarios> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(ArrayList<Comentarios> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}
	
	public String comentariosToString(){
		String cadena = "";
		
		for (int i=0; i<this.listaComentarios.size(); i++){
			cadena = cadena + listaComentarios.get(i);
			cadena = cadena + '\n';
		}
		
		return cadena;
	}
}
