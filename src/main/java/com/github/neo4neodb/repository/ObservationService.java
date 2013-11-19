package com.github.neo4neodb.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.neo4neodb.domain.Observation;
import com.github.neo4neodb.domain.Observer;

@Service
public class ObservationService {
	@Autowired
	private ObservationRepository repo;

	public void addObserverForExistingObservation(Observation o, Observer u) {
		Observation o1 = repo.findOne(o.getId());
		o1.observedBy(u, new Date().getTime());
		repo.save(o1);
	}

	public void newObservationForObserver(Observation o, Observer u) {
		o.observedBy(u, new Date().getTime());
		repo.save(o);
	}
}
