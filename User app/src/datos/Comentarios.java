package datos;

import java.sql.Date;

public class Comentarios {
	
	private String nick;
	private Date fecha;
	private String texto;
	
	public Comentarios (String nick, Date fecha, String texto) {
		this.nick = nick;
		this.fecha = fecha;
		this.texto = texto;
	}

	public String getNick() {
		return nick;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getTexto() {
		return texto;
	}
	
	public String toString() {
		return "--> [" + this.fecha + "] " + this.nick + " dijo: " + this.texto + '\n';
	}
}
