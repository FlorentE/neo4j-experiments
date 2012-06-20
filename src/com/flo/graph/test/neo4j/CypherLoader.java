package com.flo.graph.test.neo4j;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSetting;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.index.ReadableIndex;

import com.flo.graph.test.model.Movie;
import com.flo.graph.test.model.Rating;
import com.flo.graph.test.model.User;
import com.flo.graph.test.reader.MovieReader;
import com.flo.graph.test.reader.RatingReader;
import com.flo.graph.test.reader.UserReader;

public class CypherLoader {
	private static final String DB_PATH = "target/movieRatingsCypher03";
	private static final String DATA_PATH = "target/data/";
	String resultString;
	String columnsString;
	String nodeResult;
	String rows = "";

	public static void main(String[] args) {
		CypherLoader javaQuery = new CypherLoader();
		javaQuery.run();
	}

	void run() {
	
		//Create a graphDB with auto indexing of userId and movieId
		GraphDatabaseBuilder dbBuilder = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(DB_PATH).setConfig(GraphDatabaseSettings.node_keys_indexable,"userId,movieId").setConfig(GraphDatabaseSettings.node_auto_indexing, GraphDatabaseSetting.TRUE);
		GraphDatabaseService db = dbBuilder.newGraphDatabase();
		
	
		//Movie Nodes
		try {
			createMovies(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- movies.dat missing from directory"
					+ DATA_PATH);
		}
		
		//User Nodes
		try {
			createUsers(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- users.dat missing from directory"
					+ DATA_PATH);
		}
		//Rating relations
		try {
			createRatings(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- ratings.dat missing from directory"
					+ DATA_PATH);
		}

		db.shutdown();
	}

	private void createMovies(GraphDatabaseService db)
			throws FileNotFoundException {
		//This parser reads the MovieLens file and returns a list of POJO used to feed the graph
		MovieReader parser = new MovieReader(DATA_PATH + "movies.dat");
		List<Movie> movies = parser.processLineByLine();

		//Actual Neo4J manipulation starts here
		// Start transaction
		Transaction transaction = db.beginTx();
		try {
			ExecutionEngine engine = new ExecutionEngine( db );
			for (Movie m : movies) {
				//Create Movie node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put( "movieId", m.getMovieId());
				params.put("title", m.getTitle());
				
				String query = "CREATE (n{movieId :{movieId}, title :{title} , type: 'Movie'})";
							
				ExecutionResult result= engine.execute(query, params);
				//Create if needed the generas
				for(String g: m.getGeneras())
				{
			        params = new HashMap<String, Object>();
					params.put( "gen", g);
					params.put("movId", m.getMovieId());
					query = "START root=node(*)	RELATE root-[:hasGenera]->(leaf {type:'genera',genera:{gen}} ) WHERE has(root.movieId)  AND root.movieId={movId} RETURN leaf";
					result =engine.execute( query, params );
				}
			}
			System.out.println("Movies transaction marked as success - Commit ["+movies.size()+"] movies nodes");
			transaction.success();
		} catch (Exception e) {
			System.err.println("ERR- Sthg went wrong when writing movies nodes - transaction marked as failure"+e.toString());
			transaction.failure();
		} finally {
			System.out.println("Movies transaction end");
			transaction.finish();
		}
		
		
	}
	
	private void createUsers(GraphDatabaseService db)
			throws FileNotFoundException {
		
		UserReader parser = new UserReader(DATA_PATH + "users.dat");
		List<User> users = parser.processLineByLine();
		// Start transaction
		Transaction transaction = db.beginTx();
		try {
			ExecutionEngine engine = new ExecutionEngine( db );
			for (User u : users) {
				//Create User node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("uId", u.getUserID());
				params.put("gender", u.getGender());
				params.put("ag", u.getAge());
				params.put("zip", u.getZipCode());
				String query = "CREATE (n{type:'User', userId:{uId}, gender:{gender}, age:{ag}, zipCode:{zip} })";
				ExecutionResult result= engine.execute(query,params);

				//Create the relationship with occupation, creation the occupation itself if needed
				//We can re-use the same params
				params.put("occ", u.getOccupation());
				query = "START root=node(*)	RELATE root-[:hasOccupation]->(leaf {type:'occupation', occupation:{occ}} ) WHERE has(root.userId)  AND root.userId={uId} RETURN leaf";
				result= engine.execute(query,params);
				
				//TODO Create the state relationship
			}
			System.out.println("Users transaction marked as success - Commit ["+users.size()+"] users nodes");
			transaction.success();
		} catch (Exception e) {
			System.err.println("ERR- Sthg went wrong when writing users nodes for User - transaction marked as failure"+e.toString());
			transaction.failure();
		} finally {
			System.out.println("User transaction end");
			transaction.finish();
		}

		
	}		

	
	private void createRatings(GraphDatabaseService db)
			throws FileNotFoundException {
		
		RatingReader parser = new RatingReader(DATA_PATH + "ratings.dat");
		
		List<Rating> ratings = parser.processLineByLine();

		// Start transaction
		Transaction transaction = db.beginTx();
		try {
			ExecutionEngine engine = new ExecutionEngine( db );
			for (Rating r : ratings) {
			
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", r.getUserId());
				params.put("movieId", r.getMovieId());
				params.put("stars", r.getNbStars());
				params.put("ratingDate", r.getRatingDate().getTime());
				String query = "START user=node:node_auto_index(userId={userId}), movie=node:node_auto_index(movieId = {movieId})	CREATE user-[:rated {stars :{stars}, ratingDate:{ratingDate}}]->movie	RETURN user";
				ExecutionResult result= engine.execute(query,params);
				
		}
			System.out.println("Ratings transaction marked as success - Commit ["+ratings.size()+"] ratings relations");
			transaction.success();
		} catch (Exception e) {
			System.err.println("ERR- Sthg went wrong when writing ratings - transaction marked as failure"+e.toString());
			transaction.failure();
		} finally {
			System.out.println("Ratings transaction end");
			transaction.finish();
		}

		
	}
}
