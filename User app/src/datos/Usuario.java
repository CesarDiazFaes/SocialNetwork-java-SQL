package datos;

import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Usuario {
	private String nick;
	private String password;
	private String fechaNacimiento;
	private Blob avatar;
	private ArrayList<Serie> listaSeries;
	
	public Usuario(){

	}
	
	public Usuario (String nick, String password, String fechaNacimiento, Blob avatar){
		this.nick=nick;
		this.password=password;
		this.fechaNacimiento = fechaNacimiento;
		this.avatar = avatar;
		this.listaSeries = new ArrayList<Serie>();
	}
	
	public String getNick() {
		return nick;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFechaNacimiento(){
		return this.fechaNacimiento;
	}
	
	public int getEdad(){
		return this.calcularEdad();
	}
	
	public Blob getAvatar() {
		return this.avatar;
	}
	
	public void setBlob(Blob avatar){
		this.avatar = avatar;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<Serie> getListaSeries() {
		return listaSeries;
	}

	public void setListaSeries(ArrayList<Serie> listaSeries) {
		this.listaSeries = listaSeries;
	}

	public int calcularEdad(){
		if (this.fechaNacimiento != null){
			Date fechaNac = null;
			try {
				fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(this.fechaNacimiento);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar fechaNacimiento = Calendar.getInstance();
			//Se crea un objeto con la fecha actual
			Calendar fechaActual = Calendar.getInstance();
			//Se asigna la fecha recibida a la fecha de nacimiento.
			fechaNacimiento.setTime(fechaNac);
			//Se restan la fecha actual y la fecha de nacimiento
			int anio = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
			int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
			int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
			//Se ajusta el año dependiendo el mes y el día
			if(mes<0 || (mes==0 && dia<0)){
			    anio--;
			}
			//Regresa la edad en base a la fecha de nacimiento
			return anio;
			}
		return 0;
	}
}