package com.github.neo4neodb.repository;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.neo4neodb.domain.Observation;
import com.github.neo4neodb.domain.Observer;

@Service
public class ObservationService {
	@Autowired
	private ObservationRepository repo;

	public Iterable<Observation> findAll() {
		return repo.findAll();
	}

	public Observation findOne(Long id) {
		return repo.findOne(id);
	}

	public void delete(Long id) {
		repo.delete(id);
	}

	public void addObserverForExistingObservation(Observation o, Observer u) {
		Observation o1 = repo.findOne(o.getId());
		o1.observedBy(u, new Date().getTime());
		repo.save(o1);
	}

	public void newObservationForObserver(Observation o, Observer u) {
		o.observedBy(u, new Date().getTime());
		repo.save(o);
	}

	public Iterable<Observation> getSimilarObservations(Observation o) {
		return repo.getSimilarEntries(o.getRightAscension(),
				o.getDeclination(), Calendar.getInstance().getTimeInMillis());
	}
}
