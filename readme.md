Cypher Movie Recommender
========================
Florent Empis - bidul.es - florent.empis@gmail.com

Please see my detailled blog post [here](http://blog.bidul.es/post/2012/06/21/Neo4J/Cypher-Recommendation-engine4)
This project is a basic tutorial to discover Neo4J and Cypher
It is largely based on [Marko A. Rodriguez Gremlin tutorial](http://markorodriguez.com/2011/09/22/a-graph-based-movie-recommender-engine/)

Using MovieLens dataset, build a suite of classes to:
*Digest MovieLens dataset and load it into a Neo4J embedded database
*Manually add full-text indexing
*Querying the graph for movie recommendations

Please note that this project does *not* provide very accurate recommendations, but is merely a hands-on guide to Cypher. 
Improving recommendation should be easy enough though, by adding genre and user specific context in the query, but this is left as an exercise to the reader
(or will be added in a future release, who knows :-) )

(the project is Eclipse and Maven based)

Usage is as follow:
1. Download MovieLens [dataset](http://www.grouplens.org/node/73)
2. Unpack to target/data/ directory
3. Run es.bidul.graph.test.neo4j.CypherLoader
4. Run es.bidul.graph.test.neo4j.IndexCreator
5. Run es.bidul.graph.test.neo4j.CypherRecommender
6. Run ServerStarter and go to [Neo4J Webconsole](http://localhost:7474)
7. Play with the dataset and experiment new queries