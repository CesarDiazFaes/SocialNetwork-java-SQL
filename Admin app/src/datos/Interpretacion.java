package datos;

public class Interpretacion {
	
	private String nombre;
	private String personaje;
	
	public Interpretacion(String nombre, String personaje) {
		this.nombre = nombre;
		this.personaje = personaje;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getPersonaje() {
		return personaje;
	}
}
