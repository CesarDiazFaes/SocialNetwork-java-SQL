package datos;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloActores extends AbstractTableModel{

	private String[] columnas = {"Actor", "Personaje"};
	private Vector<Actor> lista = new Vector<Actor>();
	
	@Override
	public int getColumnCount() {
		return 2;
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
			Actor actor = lista.get(iFila);
			if (iColumna==0)
				return actor.getNombre();
			else if (iColumna==1)
				return actor.getPersonaje();
			
			else
				return null;
		}
	}
	
	public void addLista(ArrayList<Actor> listaActores){
		for(int i=0; i<listaActores.size(); i++){
			this.add(listaActores.get(i));
		}
	}
	
	public Actor getActorSeleccionado(int i) {
		return this.lista.elementAt(i);
	}
	
	public void add(Actor actor){
		lista.addElement(actor);
		this.fireTableDataChanged();
	}
	
	public void clearTabla(){
		Vector<Actor> nuevaLista = new Vector<Actor>();
		this.lista = nuevaLista;
		this.fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnas[col];
	}
}
