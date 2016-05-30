package datos;

import java.sql.Date;
import java.util.ArrayList;

public class Serie {
	
	private int idSerie;
	private String nombre;
	private String titular;
	private String sinopsis;
	private Date estreno;
	private Date finalizacion;
	private ArrayList<String> listaGeneros;
	private ArrayList<Episodio> listaEpisodios;

	public Serie(){
		
	}
	
	public Serie(int id, String nombre, String titular, String sinopsis, Date estreno, Date fin){
		this.idSerie = id;
		this.nombre = nombre;
		this.titular = titular;
		this.sinopsis = sinopsis;
		this.estreno = estreno;
		this.finalizacion = fin;
	}

	public int getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
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

	public Date getFinalizacion() {
		return finalizacion;
	}

	public void setFinalizacion(Date finalizacion) {
		this.finalizacion = finalizacion;
	}
	
	public ArrayList<String> getListaGenero() {
		return listaGeneros;
	}

	public void setListaGenero(ArrayList<String> listaGenero) {
		this.listaGeneros = listaGenero;
	}
	
	public String tituloFechaToString(){
		Date estreno = this.getEstreno();
		Date fin = this.getFinalizacion();
		
		String valor = this.getNombre() + " (" + this.cogerAnio(estreno) + " - ";
		if (fin == null)
			valor = valor + "?)";
		
		else
			valor = valor + this.cogerAnio(fin) + ")";
		
		return valor;
	}
	
	public String cogerAnio(Date fecha){
		String anio = fecha.toString();
		int i = 0;
		
		while(i<anio.length() && (anio.charAt(i) != '-')){
			i++;
		}
		
		return anio.substring(0, i);
	}
	
	public String generoToString(){
		String generoString = "";
		
		for (int i=0; i<this.listaGeneros.size(); i++){
			generoString = generoString + listaGeneros.get(i);
			
			if (i!=this.listaGeneros.size()-1)
				generoString = generoString + ", ";			
		}
		
		return generoString;
	}

	public ArrayList<Episodio> getListaEpisodios() {
		return listaEpisodios;
	}

	public void setListaEpisodios(ArrayList<Episodio> listaEpisodios) {
		this.listaEpisodios = listaEpisodios;
	}
}
