/**
 * Copyright (C) 2012 Florent Empis florent.empis@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.bidul.graph.test.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class CypherRecommender {
	private static final String DATA_PATH = "target/data/";

	private static final String DB_PATH = "target/movieRatingsCypher03";
	String resultString;
	String columnsString;
	String nodeResult;
	String rows = "";

	public static void main(String[] args) {
		CypherRecommender javaQuery = new CypherRecommender();
		javaQuery.run();
	}

	void run() {
		GraphDatabaseService db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(DB_PATH);

		try {

			ExecutionEngine engine = new ExecutionEngine(db);
			
			// Get a reco using a movieId
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("movieId", 6);
			String query = "START me=node:node_auto_index(movieId = '6') MATCH me<-[r:rated]-user-[o:rated]->stuff where r.stars>3 and o.stars>3 RETURN stuff.title, avg(o.stars), count(o), (avg(o.stars)*count(o)) as scoring order by scoring desc limit 5";
			ExecutionResult result = engine.execute(query, params);
			System.out.println(result);
			
			// Get a reco using a lucene query
			params.put("query", "title:Toy");
			query = "START n=node:movies_fulltext({query}) MATCH n<-[r:rated]-user-[o:rated]->stuff where r.stars>3 and o.stars>3 RETURN stuff.title, avg(o.stars), count(o), (avg(o.stars)*count(o)) as scoring order by scoring desc limit 5";

			result = engine.execute(query, params);
			System.out.println(result);

			// Many example queries. Some work, some don't, play around :-)
			// ExecutionResult result = engine.execute(
			// " START me=node(1) MATCH user-[:rated]->me RETURN user");
			// ExecutionResult result = engine.execute(
			// " START me=node:node_auto_index(userId = '6') MATCH me-[:rated]->film RETURN film");
			// ExecutionResult result = engine.execute(
			// " START me=node(1) RETURN me");
			// ExecutionResult result = engine.execute(
			// " START me=node:node_auto_index(movieId = '6') MATCH me<-[r:rated]-user where r.stars>3 RETURN count(user)");
			// ExecutionResult result =
			// engine.execute("START film=node:node_auto_index(movieId = '3114') MATCH film<-[r:rated]-user-[o:rated]->stuff, film-[:hasGenera]->gen<-[:hasGenera]-stuff where user.age<10 and r.stars>3  and o.stars>3 and gen.genera='Animation' RETURN stuff.movieId, stuff.title, count(*) ORDER BY count(*) DESC, stuff.title limit 10");
			// ExecutionResult result =
			// engine.execute("START f=node:node_auto_index(movieId = '3114') MATCH f<-[:rated]-n-[r:rated]->film-[:hasGenera]->gen return gen.genera, avg(r.stars) as stars, count(*) as nb  order by stars desc");
			// ExecutionResult result =
			// engine.execute("START a=node:allnodes('type':'User') MATCH a.age>30 Return a.userId,a.age  order by age desc limit 10");

			// START me=node:node_auto_index(movieId = '6') MATCH
			// me<-[r:rated]-user-[o:rated]->stuff where r.stars>3 and o.stars>3
			// RETURN stuff.title, avg(o.stars), count(o),
			// (avg(o.stars)*count(o)) as scoring order by scoring desc limit 5;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.shutdown();
		}
	}
}