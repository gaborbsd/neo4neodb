package com.github.neo4neodb.repository;

import static org.junit.Assert.*;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.github.neo4neodb.domain.MagnitudeBand;
import com.github.neo4neodb.domain.Observation;
import com.github.neo4neodb.domain.Observer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ObservationRepositoryTest {

	@Configuration
	@ComponentScan("com.github.noe4neodb")
	@EnableNeo4jRepositories("com.github.neo4neodb.repository")
	@EnableTransactionManagement(mode = AdviceMode.PROXY)
	static class Config extends Neo4jConfiguration {

		@Bean
		public GraphDatabaseService graphDatabaseService() {
			return new TestGraphDatabaseFactory().newImpermanentDatabase();
		}
	}

	@Autowired
	private ObservationRepository observationRepository;

	@Autowired
	private ObserverRepository observerRepository;

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
	public void testFindOne() {
		Observation o2 = observationRepository.findOne(observation1.getId());
		assertEquals(observation1, o2);
	}

	@Test
	@Transactional
	public void testGetSimilarEntries() {
		boolean ok = false;
		Iterable<Observation> results = observationRepository
				.getSimilarEntries(10000l, 25.5, new Date().getTime());
		for (Observation t : results) {
			if (t.equals(observation1)) {
				ok = true;
			}
		}
		assertTrue(ok);
	}

	@Test
	@Transactional
	public void testNewObserverForExistingObservation() {
		Observation o1 = observationRepository.findOne(observation1.getId());
		o1.observedBy(user2, new Date().getTime());
		observationRepository.save(o1);
		Observation o2 = observationRepository.findOne(observation1.getId());
		assertTrue(o2.getObservers().contains(user2));
	}

	@Test
	@Transactional
	public void testCreateNewObservation() {
		Observation newObservation = new Observation(20000, 30.5, 10.5,
				MagnitudeBand.V);
		newObservation.observedBy(user2, new Date().getTime());
		observationRepository.save(newObservation);
		Observer tmp = observerRepository.findOne(user2.getId());
		assertTrue(tmp.getObservations().contains(newObservation));
	}
}
