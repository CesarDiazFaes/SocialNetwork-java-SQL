package datos;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloInterpretaciones extends AbstractTableModel{

	private String[] columnas = {"Actor", "Personaje"};
	private Vector<Interpretacion> lista = new Vector<Interpretacion>();
	
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
			Interpretacion actor = lista.get(iFila);
			if (iColumna==0)
				return actor.getNombre();
			else if (iColumna==1)
				return actor.getPersonaje();
			
			else
				return null;
		}
	}
	
	public void addLista(ArrayList<Interpretacion> listaActores){
		for(int i=0; i<listaActores.size(); i++){
			this.add(listaActores.get(i));
		}
	}
	
	public Interpretacion getActorSeleccionado(int i) {
		return this.lista.elementAt(i);
	}
	
	public void add(Interpretacion actor){
		lista.addElement(actor);
		this.fireTableDataChanged();
	}
	
	public void clearTabla(){
		Vector<Interpretacion> nuevaLista = new Vector<Interpretacion>();
		this.lista = nuevaLista;
		this.fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnas[col];
	}
}
