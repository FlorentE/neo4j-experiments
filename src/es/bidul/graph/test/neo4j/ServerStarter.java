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

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;




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