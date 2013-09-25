package com.github.neo4neodb.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.neo4neodb.domain.MagnitudeBand;
import com.github.neo4neodb.domain.Observation;
import com.github.neo4neodb.domain.Observer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:src/test/resources/testContext.xml")
public class ObservationRepositoryTest {

	@Autowired
	private ObservationRepository repo;

	private Observation o;
	private Observer u;

	@Before
	public void setUp() throws Exception {
		o = new Observation(10000, 25.5, 20.5, MagnitudeBand.V);
		u = new Observer("Gabor");
		u.observed(o, new Date().getTime());
		repo.save(o);
	}

	@Test
	@Transactional
	@Ignore
	public void testGetSimilarEntries() {
		boolean ok = false;
		Iterable<Observation> results = repo.getSimilarEntries(10001, 25.4,
				new Date().getTime());
		for (Iterator<Observation> it = results.iterator(); it.hasNext();)
			if (o.equals(it.hasNext()))
				ok = true;
		assertTrue(ok);
	}
}
