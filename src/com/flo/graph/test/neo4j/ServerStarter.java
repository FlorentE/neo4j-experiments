package com.flo.graph.test.neo4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.neo4j.cypher.javacompat.ExecutionEngine;
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




public class ServerStarter {
	
	private static final String DB_PATH = "target/movieRatingsCypher03";

    public static void main( String[] args )
    {
        ServerStarter javaQuery = new ServerStarter();
        javaQuery.run();
    }

    void run()
    {
    	
    	
        // START SNIPPET: execute
        GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
        AbstractGraphDatabase graphdb = (AbstractGraphDatabase)db;
        WrappingNeoServerBootstrapper srv;
        srv = new WrappingNeoServerBootstrapper( graphdb );
        srv.start();
      
    }
}