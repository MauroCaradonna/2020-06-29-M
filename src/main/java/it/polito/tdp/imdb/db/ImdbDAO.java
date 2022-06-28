package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.DirettoreAttore;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(Map<Integer, Actor> actorIdMap){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int id = res.getInt("id");
				Actor actor = new Actor(id, res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				actorIdMap.put(id, actor);
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(Map<Integer, Director> directorIdMap){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int id = res.getInt("id");
				Director director = new Director(id, res.getString("first_name"), res.getString("last_name"));
				directorIdMap.put(id, director);
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Director> listAllDirectorsByYear(int anno, Map<Integer, Director> directorIdMap){
		String sql = "SELECT director_id "
				+ "FROM movies_directors "
				+ "WHERE movie_id IN ( "
				+ "SELECT id "
				+ "FROM movies "
				+ "WHERE year = ?)";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(directorIdMap.get(res.getInt("director_id")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<DirettoreAttore> listAllDirectorsActors(int anno, Map<Integer, Director> directorIdMap, Map<Integer, Actor> actorIdMap){
		String sql = "SELECT md.director_id, r.actor_id "
				+ "FROM movies_directors md, movies m, roles r "
				+ "WHERE md.movie_id = m.id AND m.year = ? AND r.movie_id= m.id";
		List<DirettoreAttore> result = new ArrayList<DirettoreAttore>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(new DirettoreAttore(directorIdMap.get(res.getInt("director_id")), actorIdMap.get(res.getInt("actor_id"))));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
