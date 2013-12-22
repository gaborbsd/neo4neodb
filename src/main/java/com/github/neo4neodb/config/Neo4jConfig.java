package com.github.neo4neodb.config;

import java.util.Properties;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.data.neo4j.support.DelegatingGraphDatabase;
import org.springframework.data.neo4j.support.typerepresentation.TypeRepresentationStrategyFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.github.noe4neodb")
@EnableNeo4jRepositories("com.github.neo4neodb.repository")
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@ImportResource("classpath:WEB-INF/spring-security.xml")
@PropertySource(name = "mailsender", value = "classpath:mailsender.properties")
public class Neo4jConfig extends Neo4jConfiguration {
	private static final String connectionUrl = "http://localhost:7474/db/data";

	@Value("${mailsender.smtp-server}")
	private String smtpServer;
	@Value("${mailsender.smtp-port}")
	private int smtpPort;
	@Value("${mailsender.smtp-username}")
	private String smtpUser;
	@Value("${mailsender.smtp-password}")
	private String smtpPassword;
	@Value("${mailsender.smtp-auth}")
	private String smtpAuth;
	@Value("${mailsender.smtp-tls}")
	private String smtpStartTls;

	@Bean(name = "graphDatabaseService")
	public SpringRestGraphDatabase graphDatabaseService() {
		SpringRestGraphDatabase sgdb = new SpringRestGraphDatabase(
				connectionUrl);
		return sgdb;
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

	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.neo4j.jdbc.Driver");
		dataSource.setUrl(connectionUrl);
		return dataSource;
	}

	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(smtpServer);
		mailSender.setPort(smtpPort);
		mailSender.setUsername(smtpUser);
		mailSender.setPassword(smtpPassword);
		Properties javaMailProps = new Properties();
		javaMailProps.setProperty("mail.smtp.auth", smtpAuth);
		javaMailProps.setProperty("mail.smtp.starttls.enable", smtpStartTls);
		return mailSender;
	}
}
