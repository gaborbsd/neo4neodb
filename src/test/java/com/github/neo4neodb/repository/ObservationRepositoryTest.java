package com.github.neo4neodb.repository;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Iterator;

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
public class ObservationRepositoryTest extends Neo4jConfiguration {

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

	private Observation o;
	private Observer u;

	@Before
	public void setUp() throws Exception {
		o = new Observation(10000, 25.5, 20.5, MagnitudeBand.V);
		u = new Observer("Gabor");
		u.observed(o, new Date().getTime());
		observationRepository.save(o);
	}

	@Test
	@Transactional
	public void testGetSimilarEntries() {
		boolean ok = false;
		Iterable<Observation> results = observationRepository
				.getSimilarEntries(10001, 25.4, new Date().getTime());
		for (Iterator<Observation> it = results.iterator(); it.hasNext();)
			if (o.equals(it.hasNext()))
				ok = true;
		assertTrue(ok);
	}
}
