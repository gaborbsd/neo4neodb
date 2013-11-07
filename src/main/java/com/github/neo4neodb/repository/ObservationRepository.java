package com.github.neo4neodb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.neo4neodb.domain.Observation;

public interface ObservationRepository extends GraphRepository<Observation> {
	@Query("START o=node:observations('*:*') "
			+ "MATCH (u)-[r:OBSERVED]->(o) "
			+ "WHERE {0} * 0.95 < o.ra AND o.ra < {0} * 1.05 "
			+ "AND {1} - 1 < o.dec AND o.dec < {1} + 1 "
			+ "AND r.date > {2} - 172800000 " + "RETURN o, r.date "
			+ "ORDER BY r.date DESC")
	Iterable<Observation> getSimilarEntries(Long rightAscension,
			Double declination, Long date);
}
