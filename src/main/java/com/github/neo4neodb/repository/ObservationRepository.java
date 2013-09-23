package com.github.neo4neodb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.neo4neodb.domain.Observation;
import com.github.neo4neodb.domain.Observer;

public interface ObservationRepository extends GraphRepository<Observation> {
	@Query("MATCH (u:Observer)-[r:OBSERVED]->(o:Observation) "
			+ "WHERE {0} * 0.95 < o.ra AND o.ra < {0} * 1.05 "
			+ "AND {1} - 1 < o.dec AND o.dec < {1} + 1 "
			+ "AND r.date > {2} - 172800000 " + "RETURN o, r.date "
			+ "ORDER BY r.date DESC")
	Iterable<Observation> getSimilarEntries(long ra, double dec, long date);

	@Query("START u=node:Observer({0}), o=node:Observation({1}) "
			+ "CREATE (u-[:OBSERVED {date: {2}}]->o")
	void confirmObservation(Observer u, Observation o, long date);

	@Query("START u=node:Observer({0}) "
			+ "CREATE u-[:OBSERVED {date: {2}}]->({1}))")
	void createNewObservation(Observer u, Observation o, long date);

}
