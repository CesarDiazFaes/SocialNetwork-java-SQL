package datos;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloPersonajes extends AbstractTableModel{
	
	private String[] columnas = {"Nombre", "Descripcion"};
	private Vector<Personaje> lista = new Vector<Personaje>();
	
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
			Personaje p = lista.get(iFila);
			if (iColumna==0)
				return p.getNombre();
			else if (iColumna==1)
				return p.getDescripcion();
			
			else
				return null;
		}
	}
	
	public void addLista(ArrayList<Personaje> listaPersonajes){
		for(int i=0; i<listaPersonajes.size(); i++){
			this.add(listaPersonajes.get(i));
		}
	}
	
	public Personaje getPersonajeSeleccionado(int i) {
		return this.lista.elementAt(i);
	}
	
	public void add(Personaje p){
		lista.addElement(p);
		this.fireTableDataChanged();
	}
	
	public void clearTabla(){
		Vector<Personaje> nuevaLista = new Vector<Personaje>();
		this.lista = nuevaLista;
		this.fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnas[col];
	}
}
