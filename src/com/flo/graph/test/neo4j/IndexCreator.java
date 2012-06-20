package com.flo.graph.test.neo4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.helpers.collection.MapUtil;

import com.flo.graph.test.model.Movie;

public class IndexCreator {
	private static final String DB_PATH = "target/movieRatingsCypher03";

	public static void main(String args[]) {
		/*
		 * When I loaded the graph, I created auto_index nodes on userId and
		 * movieId But I want to be able to search by Movie name, so I have to
		 * create an index aftewards, hence this class
		 */
		GraphDatabaseService db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(DB_PATH);
		IndexManager index = db.index();
		Index<Node> fulltextMoviesIndex = index.forNodes("movies_fulltext",
				MapUtil.stringMap(IndexManager.PROVIDER, "lucene", "type",
						"fulltext"));

		// First, select all nodes that have a "title" property
		ExecutionEngine engine = new ExecutionEngine(db);
		String query = "start n=node(*) where has(n.title) return n;";
		ExecutionResult result = engine.execute(query);

		// Next, iterate over all nodes and add them to the movies-fulltext
		// index
		Transaction transaction = db.beginTx();
		try {

			Iterator<Node> n_column = result.columnAs("n");
			for (Node node : IteratorUtil.asIterable(n_column)) {
				fulltextMoviesIndex.add(node, "title",
						node.getProperty("title"));
			}

			System.out
					.println("Full-text movie titles indexing transaction marked as success ");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when indexing full-text movie titles - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("Movies transaction end");
			transaction.finish();
		}

		db.shutdown();

	}
}
