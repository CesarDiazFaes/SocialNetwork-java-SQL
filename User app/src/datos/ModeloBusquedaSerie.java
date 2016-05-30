package datos;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class ModeloBusquedaSerie {
	
	private DefaultListModel modelo;
	
	public ModeloBusquedaSerie(){
		this.modelo = new DefaultListModel();
	}
	
	public DefaultListModel getModelo() {
		return this.modelo;
	}
	
	public void setModelo(DefaultListModel model){
		this.modelo = model;
	}
	
	public String getTituloSerie(int index){
		String titulo = this.modelo.get(index).toString();
		int i=0;
		
		while (i<titulo.length() && (titulo.charAt(i) != '(')){
			i++;
		}
		
		return titulo.substring(0, i);
	}
	
	public void addListaSeries(ArrayList<String> lista){
		this.modelo.clear();
		
		for (int i=0; i<lista.size(); i++){
			this.modelo.addElement(lista.get(i));
		}
	}
	
	public ArrayList<String> pasarAString(ArrayList<Serie> lista){
		ArrayList<String> listaString = new ArrayList<String>();
		String serie;
		
		for (int i=0; i<lista.size(); i++){
			serie = lista.get(i).tituloFechaToString();
			listaString.add(serie);
		}
		
		return listaString;
	}
}
