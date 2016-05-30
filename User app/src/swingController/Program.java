package swingController;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import datos.Actor;
import datos.Comentarios;
import datos.Episodio;
import datos.Serie;
import datos.Usuario;

public class Program {
	
	private Usuario userLogged;
	private SwingController controller;
	
	public Program(){
		this.userLogged = new Usuario();
		this.controller = new SwingController(this);
	}
	
	public void setUserLogged(Usuario user){
		this.userLogged = user;
	}
	
	public Usuario getUserLogged(){
		return this.userLogged;
	}
	
	public Blob getAvatarPredefinido(){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");
	
			st = con.prepareStatement("Select avatar FROM usuarios WHERE nick = ?");
			st.setString(1, "default");
			rs = st.executeQuery();
			
			while (rs.next()) {
				return rs.getBlob("avatar");
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Usuario default no creado", "Error", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	public boolean compruebaUsuario(Usuario user){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");
	
			st = con.prepareStatement("Select nick,password FROM usuarios WHERE nick = ? and password = ?");
			st.setString(1, user.getNick());
			st.setString(2, user.getPassword());
			rs = st.executeQuery();
			
			while (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return false;
	}
	
	public boolean nuevoUsuario(Usuario user){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("INSERT INTO usuarios VALUES (?, ?, ?, ?)");
			st.setString(1, user.getNick());
			st.setString(2, user.getPassword());
			st.setNull(3, 0);
			Blob avatar = this.getAvatarPredefinido();
			st.setBlob(4, avatar);

			// Ejecutamos la consulta
			st.executeUpdate();
			Usuario nuevoUser = new Usuario(user.getNick(), user.getPassword(), null, avatar);
			this.setUserLogged(nuevoUser);
			return true;
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Usuario ya registrado", "Error", 
					JOptionPane.ERROR_MESSAGE);
			return false;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}

	public Usuario obtenerDatosUsuario(Usuario user){
		Usuario datosUser;
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select nick,password,fechaNacimiento,avatar FROM usuarios WHERE nick = ? and password = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, user.getNick());
			st.setString(2, user.getPassword());
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				String nick = rs.getString("nick");
				String password = rs.getString("password");
				String fecha = rs.getString("fechaNacimiento");
				Blob avatar = rs.getBlob("avatar");
				ArrayList<Serie> lista = this.obtenerListaSeriesUsuario(nick);
				datosUser = new Usuario(nick, password, fecha, avatar);
				datosUser.setListaSeries(lista);
				return datosUser;
			}
			
			return null;	
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<Serie> obtenerListaSeriesUsuario(String nick){
		ArrayList<Serie> lista = new ArrayList<Serie>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select series.nombre,estreno,finalizacion FROM series,sigue WHERE sigue.nick = ? and " +
					"sigue.idSerie = series.idSerie");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, nick);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Serie serie = new Serie();
				serie.setNombre(rs.getString("nombre"));
				serie.setEstreno(rs.getDate("estreno"));
				serie.setFinalizacion(rs.getDate("finalizacion"));
				lista.add(serie);
			}
			
			return lista;	
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public void insertarDatosPerfil(Usuario user) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("UPDATE usuarios SET password = ?, fechaNacimiento = ?, " +
					"avatar = ? WHERE nick = ?");
			st.setString(1, user.getPassword());
			Date fecha = this.convertirStringEnFecha(user.getFechaNacimiento());
			//Convertimos la fecha a SQL
			java.sql.Date fechaFinal;
			if (fecha != null)
				fechaFinal = new java.sql.Date(fecha.getTime());
			
			else
				fechaFinal = null;
			
			st.setDate(2, fechaFinal);
			st.setBlob(3, user.getAvatar());
			st.setString(4, user.getNick());
			// Ejecutamos la consulta	
			st.executeUpdate();
			
			ArrayList<Serie> lista = new ArrayList<Serie>();
			lista = this.obtenerListaSeriesUsuario(user.getNick());
			user.setListaSeries(lista);
			this.setUserLogged(user);
				
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fallo en la modificación de datos", "Error", 
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<Serie> buscarSeriesPorPalabra(String palabra){
		ArrayList<Serie> lista = new ArrayList<Serie>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {		
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select nombre,estreno,finalizacion FROM series WHERE nombre LIKE ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, "%" + palabra + "%");
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Serie serie = new Serie();
				serie.setNombre(rs.getString("nombre"));
				serie.setEstreno(rs.getDate("estreno"));
				serie.setFinalizacion(rs.getDate("finalizacion"));
				lista.add(serie);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public Serie buscarSerie(String titulo){
		Serie serie = new Serie();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select idSerie,nombre,titular,sinopsis,estreno,finalizacion FROM series WHERE nombre = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, titulo);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				serie.setIdSerie(rs.getInt("idSerie"));
				serie.setNombre(rs.getString("nombre"));
				serie.setTitular(rs.getString("titular"));
				serie.setSinopsis(rs.getString("sinopsis"));
				serie.setEstreno(rs.getDate("estreno"));
				serie.setFinalizacion(rs.getDate("finalizacion"));
				serie.setListaGenero(this.cogerGeneroSerie(serie.getIdSerie()));
				serie.setListaEpisodios(this.getListaEpisodios(rs.getInt("idSerie")));
				serie.setListaComentarios(this.getComentariosSerie(rs.getInt("idSerie")));
			}
			
			return serie;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public Serie buscarSeriePorId(int id){
		Serie serie = new Serie();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select * FROM series WHERE idSerie = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, id);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				serie.setIdSerie(rs.getInt("idSerie"));
				serie.setNombre(rs.getString("nombre"));
				serie.setTitular(rs.getString("titular"));
				serie.setSinopsis(rs.getString("sinopsis"));
				serie.setEstreno(rs.getDate("estreno"));
				serie.setFinalizacion(rs.getDate("finalizacion"));
				serie.setListaGenero(this.cogerGeneroSerie(serie.getIdSerie()));
				serie.setListaEpisodios(this.getListaEpisodios(rs.getInt("idSerie")));
				serie.setListaComentarios(this.getComentariosSerie(rs.getInt("idSerie")));
			}
			
			return serie;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<String> cogerGeneroSerie(int id){
		ArrayList<String> lista = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select genero.nombre FROM genero,pertenece WHERE pertenece.idSerie = ? and " +
					"pertenece.idGenero = genero.idGenero");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, id);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				lista.add(rs.getString("genero.nombre"));
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public Date convertirStringEnFecha(String s){
		Date fecha = null;
		
		try {
			fecha = new SimpleDateFormat("yyyy-mm-dd").parse(s);
		} catch (ParseException e) {
			return null;
		} catch (java.lang.NullPointerException e){
			return null;
		}
		
		return fecha;
	}

	public void seguirSerie(Serie serie) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {		
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("INSERT INTO sigue VALUES (?, ?)");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, this.userLogged.getNick());
			st.setInt(2, serie.getIdSerie());
			
			// Ejecutamos la consulta			
			st.executeUpdate();
			
			ArrayList<Serie> lista = new ArrayList<Serie>();
			lista = this.obtenerListaSeriesUsuario(this.userLogged.getNick());
			this.userLogged.setListaSeries(lista);
			
		} catch (SQLException e) {
			this.dejarDeSeguir(serie);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public void dejarDeSeguir(Serie serie){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("DELETE FROM sigue WHERE nick = ? AND idSerie = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, this.userLogged.getNick());
			st.setInt(2, serie.getIdSerie());
			
			// Ejecutamos la consulta			
			st.executeUpdate();
			
			ArrayList<Serie> lista = new ArrayList<Serie>();
			lista = this.obtenerListaSeriesUsuario(this.userLogged.getNick());
			this.userLogged.setListaSeries(lista);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido borrar", "Aviso", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<Episodio> getListaEpisodios(int idSerie){
		ArrayList<Episodio> lista = new ArrayList<Episodio>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select temporada,capitulo,sinopsis,titulo,fechaEstreno FROM episodios WHERE idSerie = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				int temporada = rs.getInt("temporada");
				int capitulo = rs.getInt("capitulo");
				String sinopsis = rs.getString("sinopsis");
				String titulo = rs.getString("titulo");
				java.sql.Date fecha = rs.getDate("fechaEstreno");
				Episodio episodio = new Episodio(idSerie, temporada, capitulo, titulo, sinopsis, fecha);
				episodio.setListaActores(this.getListaActoresEpisodio(idSerie, temporada, capitulo));
				episodio.setListaComentarios(this.getComentariosEpisodio(idSerie, temporada, capitulo));
				lista.add(episodio);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<Episodio> getListaEpisodiosNoVistos(int idSerie){
		ArrayList<Episodio> lista = new ArrayList<Episodio>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select * FROM episodios " +
					"WHERE idSerie = ? and NOT EXISTS (Select * FROM visto WHERE nick = ? and visto.idSerie = episodios.idSerie " + 
							"and visto.temporada = episodios.temporada and visto.capitulo = episodios.capitulo)");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			st.setString(2, this.userLogged.getNick());
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				int temporada = rs.getInt("temporada");
				int capitulo = rs.getInt("capitulo");
				String sinopsis = rs.getString("sinopsis");
				String titulo = rs.getString("titulo");
				java.sql.Date fecha = rs.getDate("fechaEstreno");
				Episodio episodio = new Episodio(idSerie, temporada, capitulo, titulo, sinopsis, fecha);
				episodio.setListaActores(this.getListaActoresEpisodio(idSerie, temporada, capitulo));
				episodio.setListaComentarios(this.getComentariosEpisodio(idSerie, temporada, capitulo));
				lista.add(episodio);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public Episodio getDatosEpisodio(int id, int temp, int cap){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("Select sinopsis,titulo,fechaEstreno FROM episodios WHERE idSerie = ? and temporada = ? and capitulo = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, id);
			st.setInt(2, temp);
			st.setInt(3, cap);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Episodio episodio = new Episodio(id, temp, cap, rs.getString("titulo"), rs.getString("sinopsis"), rs.getDate("fechaEstreno"));
				episodio.setListaActores(this.getListaActoresEpisodio(id, temp, cap));
				episodio.setListaComentarios(this.getComentariosEpisodio(id, temp, cap));
				return episodio;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}

	public void marcarEpisodio(Episodio episodio) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("INSERT INTO visto VALUES(?, ?, ?, ?)");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, this.userLogged.getNick());
			st.setInt(2, episodio.getIdSerie());
			st.setInt(3, episodio.getCapitulo());
			st.setInt(4, episodio.getTemporada());
			
			// Ejecutamos la consulta			
			st.executeUpdate();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido marcar el episodio", "Aviso", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}

	public int notaMediaSerie(int idSerie) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"UsuarioP1",
					"UsuarioP1");

			st = con.prepareStatement("Select * FROM votaserie WHERE idSerie = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();
			int numVotos = 0;
			int sumaVotos = 0;

			while (rs.next()) {
				sumaVotos = sumaVotos + rs.getInt("nota");
				numVotos++;
			}
			if (sumaVotos == 0)
				return 0;
			else
				return sumaVotos/numVotos;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}

	public void votarSerie(int idSerie, int nota) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("INSERT INTO votaserie VALUES(?, ?, ?)");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, this.userLogged.getNick());
			st.setInt(2, idSerie);
			st.setInt(3, nota);
			
			// Ejecutamos la consulta			
			st.executeUpdate();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha registrado el voto", "Aviso", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public boolean haVotado(int idSerie) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;

		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"UsuarioP1",
					"UsuarioP1");

			st = con.prepareStatement("Select * FROM votaserie WHERE idSerie = ? and nick = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			st.setString(2, this.userLogged.getNick());
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();
			int contador = 0;

			while (rs.next()) {
				contador++;
			}
			
			if (contador > 0)
				return true;
			
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public void modificarVoto(int idSerie, int nota) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("UPDATE votaserie SET nota = ? WHERE nick = ? AND idSerie = ?");
			st.setInt(1, nota);
			st.setString(2, this.userLogged.getNick());
			st.setInt(3, idSerie);

			st.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public int notaMediaEpisodio(int idSerie, int temp, int cap) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"UsuarioP1",
					"UsuarioP1");

			st = con.prepareStatement("Select * FROM votaepisodio WHERE idSerie = ? AND temporada = ? AND capitulo = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			st.setInt(2, temp);
			st.setInt(3, cap);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();
			int numVotos = 0;
			int sumaVotos = 0;

			while (rs.next()) {
				sumaVotos = sumaVotos + rs.getInt("nota");
				numVotos++;
			}
			if (sumaVotos == 0)
				return 0;
			else
				return sumaVotos/numVotos;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}

	public void votarEpisodio(int idSerie, int nota, int temp, int cap) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("INSERT INTO votaepisodio VALUES(?, ?, ?, ?, ?)");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, this.userLogged.getNick());
			st.setInt(2, idSerie);
			st.setInt(3, cap);
			st.setInt(4, temp);
			st.setInt(5, nota);
			
			// Ejecutamos la consulta			
			st.executeUpdate();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha registrado el voto", "Aviso", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public boolean haVotadoEpisodio(int idSerie, int temp, int cap) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;

		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"UsuarioP1",
					"UsuarioP1");

			st = con.prepareStatement("Select * FROM votaepisodio WHERE idSerie = ? AND nick = ? " + 
			"AND capitulo = ? AND temporada = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			st.setString(2, this.userLogged.getNick());
			st.setInt(3, cap);
			st.setInt(4, temp);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();
			int contador = 0;

			while (rs.next()) {
				contador++;
			}
			
			if (contador > 0)
				return true;
			
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public void modificarVotoEpisodio(int idSerie, int nota, int temp, int cap) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("UPDATE votaepisodio SET nota = ? WHERE nick = ? AND idSerie = ? " + 
			"AND capitulo = ? AND temporada = ?");
			st.setInt(1, nota);
			st.setString(2, this.userLogged.getNick());
			st.setInt(3, idSerie);
			st.setInt(4, cap);
			st.setInt(5, temp);

			st.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<Actor> getListaActoresEpisodio(int idSerie, int temp, int cap) {
		ArrayList<Actor> lista = new ArrayList<Actor>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"UsuarioP1",
					"UsuarioP1");

			st = con.prepareStatement("Select actor,personaje FROM interpreta WHERE idSerie = ? AND temporada = ? AND capitulo = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			st.setInt(2, temp);
			st.setInt(3, cap);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Actor actor = new Actor(rs.getString("actor"), rs.getString("personaje"));
				lista.add(actor);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<Comentarios> getComentariosSerie(int idSerie) {
		ArrayList<Comentarios> lista = new ArrayList<Comentarios>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"UsuarioP1",
					"UsuarioP1");

			st = con.prepareStatement("Select * FROM comentaserie WHERE idSerie = ? ORDER BY fecha");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Comentarios comentario = new Comentarios(rs.getString("nick"), rs.getDate("fecha"), rs.getString("texto"));
				lista.add(comentario);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<Comentarios> getComentariosEpisodio(int idSerie, int temp, int cap) {
		ArrayList<Comentarios> lista = new ArrayList<Comentarios>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"UsuarioP1",
					"UsuarioP1");

			st = con.prepareStatement("Select * FROM comentaepisodio WHERE idSerie = ? AND temporada = ? AND capitulo = ? ORDER BY fecha");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, idSerie);
			st.setInt(2, temp);
			st.setInt(3, cap);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Comentarios comentario = new Comentarios(rs.getString("nick"), rs.getDate("fecha"), rs.getString("texto"));
				lista.add(comentario);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error realizando el comentario", "Aviso", 
					JOptionPane.ERROR_MESSAGE);
			return null;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}

	public void comentarSerie(int idSerie, String comentario) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("INSERT INTO comentaserie(nick, idSerie, fecha, texto) VALUES(?, ?, ?, ?)");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, this.userLogged.getNick());
			st.setInt(2, idSerie);
			st.setTimestamp(3, null);
			st.setString(4, comentario);
			
			// Ejecutamos la consulta			
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error realizando el comentario", "Aviso", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public void comentarEpisodio(int idSerie, int temp, int cap, String comentario) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"UsuarioP1",
				"UsuarioP1");

			st = con.prepareStatement("INSERT INTO comentaepisodio(nick, idSerie, capitulo, temporada, fecha, texto) VALUES(?, ?, ?, ?, ?, ?)");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, this.userLogged.getNick());
			st.setInt(2, idSerie);
			st.setInt(3, cap);
			st.setInt(4, temp);
			st.setTimestamp(5, null);
			st.setString(6, comentario);
			
			// Ejecutamos la consulta			
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error realizando el comentario", "Aviso", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
}
