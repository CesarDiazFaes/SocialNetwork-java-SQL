package datos;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloEpisodio extends AbstractTableModel{

	private String[] columnas = {"Capitulo", "Temporada", "Titulo", "Fecha"};
	private Vector<Episodio> lista = new Vector<Episodio>();
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}
	
	@Override
	public Object getValueAt(int iFila, int iColumna) {			
		if (iFila>=lista.size())
			return null;
		else {
			Episodio episodio = lista.get(iFila);
			if (iColumna==0)
				return episodio.getCapitulo();
			else if (iColumna==1)
				return episodio.getTemporada();
			
			else if (iColumna==2)
				return episodio.getTitulo();	
			
			else if (iColumna==3)
				return episodio.getEstreno();
			
			else
				return null;
		}
	}
	
	public void addLista(ArrayList<Episodio> listaEpisodios){
		for(int i=0; i<listaEpisodios.size(); i++){
			this.add(listaEpisodios.get(i));
		}
	}
	
	public Episodio getEpisodioSeleccionado(int i) {
		return this.lista.elementAt(i);
	}
	
	public void add(Episodio episodio){
		lista.addElement(episodio);
		this.fireTableDataChanged();
	}
	
	public void clearTabla(){
		Vector<Episodio> nuevaLista = new Vector<Episodio>();
		this.lista = nuevaLista;
		this.fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnas[col];
	}
}
