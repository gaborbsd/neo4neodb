package com.github.neo4neodb.test;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MockMailSender implements MailSender {
	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		// XXX: log
	}

	@Override
	public void send(SimpleMailMessage[] simpleMessages) throws MailException {
		// XXX: log
	}
}
