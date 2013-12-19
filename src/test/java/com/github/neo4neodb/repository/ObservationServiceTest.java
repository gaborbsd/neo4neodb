package com.github.neo4neodb.repository;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.github.neo4neodb.domain.MagnitudeBand;
import com.github.neo4neodb.domain.Observation;
import com.github.neo4neodb.domain.Observer;
import com.github.neo4neodb.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class ObservationServiceTest {
	@Autowired
	private ObservationRepository observationRepository;

	@Autowired
	private ObserverRepository observerRepository;

	@Autowired
	private ObservationService service;

	private Observation observation1;
	private Observer user1;
	private Observer user2;

	@Before
	@Transactional
	public void setUp() {
		user1 = new Observer("Gabor");
		observerRepository.save(user1);

		user2 = new Observer("Hatim");
		observerRepository.save(user2);

		observation1 = new Observation(10000, 25.5, 20.5, MagnitudeBand.V);
		observation1.observedBy(user1, new Date().getTime());
		observationRepository.save(observation1);
		observationRepository.save(new Observation(20000, 25.5, 20.5,
				MagnitudeBand.V));
		observationRepository.save(new Observation(40000, 25.5, 20.5,
				MagnitudeBand.V));
	}

	@Test
	@Transactional
	public void testAddObserverForExistingObservation() {
		service.addObserverForExistingObservation(observation1, user2);
		Observation o2 = observationRepository.findOne(observation1.getId());
		assertTrue(o2.getObservers().contains(user2));
	}

	@Test
	@Transactional
	public void testNewObservationForObserver() {
		Observation newObservation = new Observation(20000, 30.5, 10.5,
				MagnitudeBand.V);
		service.newObservationForObserver(newObservation, user2);
		Observer tmp = observerRepository.findOne(user2.getId());
		assertTrue(tmp.getObservations().contains(newObservation));
	}

}
