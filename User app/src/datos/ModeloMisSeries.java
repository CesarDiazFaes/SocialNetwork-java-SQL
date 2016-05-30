package datos;

import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;

public class ModeloMisSeries extends DefaultListModel implements ComboBoxModel{

	private ArrayList<String> miLista;
	private String selection;
	
	public ModeloMisSeries(){
		this.miLista = new ArrayList<String>();
	}
	
	public void addTodasMisSeries(ArrayList<Serie> lista){
		this.miLista.clear();
		int i=0;
		
		for (i=0; i<lista.size(); i++){
			this.miLista.add(lista.get(i).tituloFechaToString());
		}
		
		this.fireContentsChanged(miLista, 0, i);
	}

	@Override
	public Object getElementAt(int index) {
		return this.miLista.get(index);
	}

	@Override
	public int getSize() {
		return this.miLista.size();
	}

	@Override
	public String getSelectedItem() {
		return this.selection;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		this.selection = (String) arg0;
	}
}
