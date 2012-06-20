package com.flo.graph.test.neo4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.visualization.graphviz.GraphvizWriter;
import org.neo4j.walk.Walker;




public class CypherRecommender {
	private static final String DATA_PATH = "target/data/";
	
	private static final String DB_PATH = "target/movieRatingsCypher03";
	String resultString;
    String columnsString;
    String nodeResult;
    String rows = "";

    public static void main( String[] args )
    {
        CypherRecommender javaQuery = new CypherRecommender();
        javaQuery.run();
    }

    void run()
    {
    	GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );

        
        ExecutionEngine engine = new ExecutionEngine( db );
        
        
        //ExecutionResult result = engine.execute( " START me=node(1) MATCH user-[:rated]->me RETURN user");
       // ExecutionResult result = engine.execute( " START me=node:node_auto_index(userId = '6') MATCH me-[:rated]->film RETURN film");
        //ExecutionResult result = engine.execute( " START me=node(1) RETURN me");
        //ExecutionResult result = engine.execute( " START me=node:node_auto_index(movieId = '6') MATCH me<-[r:rated]-user where r.stars>3 RETURN count(user)");
       //ExecutionResult result = engine.execute("START film=node:node_auto_index(movieId = '3114') MATCH film<-[r:rated]-user-[o:rated]->stuff, film-[:hasGenera]->gen<-[:hasGenera]-stuff where user.age<10 and r.stars>3  and o.stars>3 and gen.genera='Animation' RETURN stuff.movieId, stuff.title, count(*) ORDER BY count(*) DESC, stuff.title limit 10");
       //ExecutionResult result = engine.execute("START f=node:node_auto_index(movieId = '3114') MATCH f<-[:rated]-n-[r:rated]->film-[:hasGenera]->gen return gen.genera, avg(r.stars) as stars, count(*) as nb  order by stars desc"); 
       //ExecutionResult result = engine.execute("START a=node:allnodes('type':'User') MATCH a.age>30 Return a.userId,a.age  order by age desc limit 10"); 
       
        // START me=node:node_auto_index(movieId = '6') MATCH me<-[r:rated]-user-[o:rated]->stuff where r.stars>3 and o.stars>3 RETURN stuff.title, avg(o.stars), count(o), (avg(o.stars)*count(o)) as scoring order by scoring desc limit 5;
       /* AutoIndexer<Node> nodeAutoIndexer = db.index()
                .getNodeAutoIndexer();
        nodeAutoIndexer.startAutoIndexingProperty( "title" );
        nodeAutoIndexer.startAutoIndexingProperty( "genera" );
      */
        Map<String, Object> params = new HashMap<String, Object>();
		params.put( "movieId", 6);
		
       String query="START me=node:node_auto_index(movieId = '6') MATCH me<-[r:rated]-user-[o:rated]->stuff where r.stars>3 and o.stars>3 RETURN stuff.title, avg(o.stars), count(o), (avg(o.stars)*count(o)) as scoring order by scoring desc limit 5";
       ExecutionResult result = engine.execute(query, params);
       
       System.out.println(result);
        
        
       
    }
}