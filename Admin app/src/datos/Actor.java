package datos;

import java.sql.Blob;
import java.sql.Date;

public class Actor {
	
	private String nombre;
	private Date fechaNacimiento;
	private Blob foto;
	
	public Actor() {
		
	}
	
	public Actor(String nombre, Date fecha, Blob foto) {
		this.nombre = nombre;
		this.fechaNacimiento = fecha;
		this.foto = foto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}
}
