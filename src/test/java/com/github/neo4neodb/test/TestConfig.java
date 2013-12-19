package com.github.neo4neodb.test;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.ImpermanentGraphDatabase;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.support.DelegatingGraphDatabase;
import org.springframework.data.neo4j.support.typerepresentation.TypeRepresentationStrategyFactory;
import org.springframework.mail.MailSender;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.github.neo4neodb.repository")
@EnableNeo4jRepositories("com.github.neo4neodb.repository")
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class TestConfig extends Neo4jConfiguration {
	private final static String storeDir = "/db/test";

	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		return new ImpermanentGraphDatabase(storeDir);
	}

	@Bean
	public DelegatingGraphDatabase delegatingGraphDatabase(
			GraphDatabaseService graphDatabaseService) {
		return new DelegatingGraphDatabase(graphDatabaseService);
	}

	@Bean
	public TypeRepresentationStrategyFactory typeRepresentationStrategyFactory(
			DelegatingGraphDatabase delegatingGraphDatabase) {
		return new TypeRepresentationStrategyFactory(delegatingGraphDatabase,
				TypeRepresentationStrategyFactory.Strategy.Labeled);
	}

	@Bean
	public MailSender mailSender() {
		return new MockMailSender();
	}
}