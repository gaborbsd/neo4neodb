package com.github.neo4neodb.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.neo4neodb.domain.Observation;

public interface ObservationRepository extends GraphRepository<Observation> {
	@Query("MATCH (u:Observer)-[r:OBSERVED]->(o:Observation) "			
			+ "WHERE ({0} * 0.95) < o.rightAscension AND o.rightAscension < ({0} * 1.05) "
			+ "AND ({1} - 1) < o.declination AND o.declination < ({1} + 1) "
			+ "AND r.date > {2} - 172800000 "
			+ "RETURN o "
			+ "ORDER BY r.date DESC")
	Iterable<Observation> getSimilarEntries(Long rightAscension,
			Double declination, Long date);
}
