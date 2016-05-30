package swingController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import datos.Actor;
import datos.Interpretacion;
import datos.Episodio;
import datos.Personaje;
import datos.Serie;

public class Program {
	
	private SwingController swing;
	
	public Program(){
		this.swing = new SwingController(this);
	}
	
	public ArrayList<Serie> buscarSeriesPorPalabra(String palabra){
		ArrayList<Serie> lista = new ArrayList<Serie>();
		
		// Obtenemos la conexion
		try {
			ResultSet rs = null;
			PreparedStatement st = null;
			Connection con = null;
			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");

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
				"AdminP1",
				"AdminP1");

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
				"AdminP1",
				"AdminP1");

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

	public void modificarDatosSerie(Serie nuevaSerie) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("UPDATE series SET titular = ?, sinopsis = ? WHERE idSerie = ?");
			st.setString(1, nuevaSerie.getTitular());
			st.setString(2, nuevaSerie.getSinopsis());
			st.setInt(3, nuevaSerie.getIdSerie());
			// Ejecutamos la consulta	
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fallo en la modificacion de datos", "Error", 
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public boolean existeSerie(String titulo){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"AdminP1",
					"AdminP1");
	
			st = con.prepareStatement("Select * FROM series WHERE nombre = ?");
			st.setString(1, titulo);
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
	
	public void crearSerie(Serie serie){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("INSERT INTO series(nombre, titular, sinopsis, estreno, finalizacion) VALUES (?, ?, ?, ?, ?)");
			st.setString(1, serie.getNombre());
			st.setString(2, serie.getTitular());
			st.setString(3, serie.getSinopsis());
			st.setDate(4, serie.getEstreno());
			st.setDate(5, serie.getFinalizacion());

			// Ejecutamos la consulta
			st.executeUpdate();
			
			//Ahora establecemos los generos
			int idSerie = this.buscarSerie(serie.getNombre()).getIdSerie(); //Obtenemos el id de la serie que acabamos de introducir
			ArrayList<String> listaGeneros = new ArrayList<String>();
			listaGeneros = serie.getListaGenero();
			if (listaGeneros != null){
				for (int i=0; i<listaGeneros.size(); i++){
					int idGenero = this.dameIdGenero(listaGeneros.get(i));
					if (idGenero != -1){
						this.setGeneroASerie(idGenero, idSerie);
					}
				}
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
	}
	
	public void setGeneroASerie(int idGenero, int idSerie){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("INSERT INTO pertenece VALUES (?, ?)");
			st.setInt(1, idSerie);
			st.setInt(2, idGenero);

			// Ejecutamos la consulta
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
	
	public int dameIdGenero(String genero){
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");

			st = con.prepareStatement("SELECT idGenero FROM genero WHERE nombre = ?");
			
			st.setString(1, genero);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();
			
			while (rs.next()) {
				return rs.getInt("idGenero");
			}
			
			return -1;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1; //No existe
			
		}
	}
	
	public Date convertirStringEnFecha(String s){
		Date fecha = null;
		
		try {
			fecha = new SimpleDateFormat("yyyy-mm-dd").parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return fecha;
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
					"AdminP1",
					"AdminP1");

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
					"AdminP1",
					"AdminP1");

			st = con.prepareStatement("Select sinopsis,titulo,fechaEstreno FROM episodios WHERE idSerie = ? and temporada = ? and capitulo = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setInt(1, id);
			st.setInt(2, temp);
			st.setInt(3, cap);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Episodio episodio = new Episodio(id, temp, cap, rs.getString("titulo"), rs.getString("sinopsis"), rs.getDate("fechaEstreno"));
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

	public void modificarEpisodio(Episodio nuevo, Episodio actual) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("UPDATE episodios SET titulo = ?, sinopsis = ?, fechaEstreno = ?, capitulo = ?, temporada = ? "
					+ "WHERE idSerie = ? AND temporada = ? AND capitulo = ?");
			st.setString(1, nuevo.getTitulo());
			st.setString(2, nuevo.getSinopsis());
			st.setDate(3, nuevo.getEstreno());
			st.setInt(4, nuevo.getCapitulo());
			st.setInt(5, nuevo.getTemporada());
			st.setInt(6, actual.getIdSerie());
			st.setInt(7, actual.getTemporada());
			st.setInt(8, actual.getCapitulo());
			// Ejecutamos la consulta	
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fallo en la modificacion de datos", "Error", 
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}
	
	public void eliminarEpisodio(int idSerie, int capitulo, int temporada) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("DELETE FROM episodios WHERE idSerie = ? AND capitulo = ? AND temporada = ?");
			st.setInt(1, idSerie);
			st.setInt(2, capitulo);
			st.setInt(3, temporada);
			// Ejecutamos la consulta	
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No se ha podido borrar el episodio", "Error", 
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}

	public void crearEpisodio(Episodio ep) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("INSERT INTO episodios VALUES (?, ?, ?, ?, ?, ?)");
			st.setInt(1, ep.getIdSerie());
			st.setInt(2, ep.getTemporada());
			st.setInt(3, ep.getCapitulo());
			st.setString(4, ep.getSinopsis());
			st.setString(5, ep.getTitulo());
			st.setDate(6, ep.getEstreno());

			// Ejecutamos la consulta
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
	
	public ArrayList<Interpretacion> getListaActoresEpisodio(int idSerie, int temp, int cap) {
		ArrayList<Interpretacion> lista = new ArrayList<Interpretacion>();
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
				Interpretacion actor = new Interpretacion(rs.getString("actor"), rs.getString("personaje"));
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
	
	public boolean existeActor(String nombre) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"AdminP1",
					"AdminP1");

			st = con.prepareStatement("Select nombre FROM actor WHERE nombre = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, nombre);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return false;
	}
	
	public boolean existePersonaje(String nombre) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"AdminP1",
					"AdminP1");

			st = con.prepareStatement("Select nombre FROM personaje WHERE nombre = ?");
			
			// Rellenamos los marcadores de la consulta parametrica
			st.setString(1, nombre);
			
			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return false;
	}

	public void agregarActorAEpisodio(Episodio ep, Interpretacion ac) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("INSERT INTO interpreta VALUES (?, ?, ?, ?, ?)");
			st.setInt(1, ep.getIdSerie());
			st.setInt(2, ep.getTemporada());
			st.setInt(3, ep.getCapitulo());
			st.setString(4, ac.getNombre());
			st.setString(5, ac.getPersonaje());

			// Ejecutamos la consulta
			st.executeUpdate();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ya existe el actor en el episodio", "Error", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}	
	}

	public void eliminarActor(Episodio ep, Interpretacion act) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {			
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("DELETE FROM interpreta WHERE idSerie = ? AND capitulo = ? AND temporada = ? " +
					"AND actor = ? AND personaje = ?");
			st.setInt(1, ep.getIdSerie());
			st.setInt(2, ep.getCapitulo());
			st.setInt(3, ep.getTemporada());
			st.setString(4, act.getNombre());
			st.setString(5, act.getPersonaje());	
			st.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido borrar el actor", "Error", 
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
	}

	public ArrayList<Personaje> buscarTodosLosPersonajes() {
		ArrayList<Personaje> lista = new ArrayList<Personaje>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"AdminP1",
					"AdminP1");

			st = con.prepareStatement("Select * FROM personaje");

			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Personaje p = new Personaje(rs.getString("nombre"), rs.getString("descripcion"));
				lista.add(p);
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
		return lista;
	}

	public void insertarPersonaje(String nombre, String descripcion) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("INSERT INTO personaje VALUES (?, ?)");
			st.setString(1, nombre);
			st.setString(2, descripcion);

			// Ejecutamos la consulta
			st.executeUpdate();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ya existe el personaje en la base de datos", "Error", 
					JOptionPane.ERROR_MESSAGE);
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (st != null) st.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}	
	}

	public ArrayList<Actor> buscarTodosLosActores() {
		ArrayList<Actor> lista = new ArrayList<Actor>();
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		// Obtenemos la conexion
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/abd",
					"AdminP1",
					"AdminP1");

			st = con.prepareStatement("Select * FROM actor");

			// Ejecutamos la consulta			
			rs = st.executeQuery();

			while (rs.next()) {
				Actor p = new Actor(rs.getString("nombre"), rs.getDate("fechaNacimiento"), rs.getBlob("foto"));
				lista.add(p);
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
		return lista;
	}
	
	public void insertarActor(String nombre, java.sql.Date fecha) {
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/abd",
				"AdminP1",
				"AdminP1");
			
			//Insertamos los datos en la BD
			st = con.prepareStatement("INSERT INTO actor(nombre, fechaNacimiento) VALUES (?, ?)");
			st.setString(1, nombre);
			st.setDate(2, fecha);

			// Ejecutamos la consulta
			st.executeUpdate();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ya existe el actor en la base de datos", "Error", 
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
