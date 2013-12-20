package com.github.neo4neodb.repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.github.neo4neodb.domain.Observation;
import com.github.neo4neodb.domain.Observer;

@Component
public class ObserverService {

	private static final String REGISTRATION_MSG = "Dear {0},\n\nthank you for your "
			+ "registration on Neo4NeoDB. To log in, first you have to activate "
			+ "you registration.  Your activation code is:\n\n{1}\n\n\n"
			+ "The Neo4NeoDB Team";
	private static final String REGISTRATION_SUBJ = "Neo4NeoDB Registration";
	private static final String PWRESET_MSG = "Dear {0},\n\nit seems someone "
			+ "has initiated a password recovery for your account.  You can change "
			+ "your password with the following activation code:\n\n{1}\n\n"
			+ "It the change was not requested by you, please ignore this message.\n\n\n"
			+ "The Neo4NeoDB Team";
	private static final String PWRESET_SUBJ = "Neo4NeoDB Password Recovery";

	@Autowired
	private MailSender mailSender;
	@Autowired
	private ObserverRepository repo;
	private static SecureRandom random = new SecureRandom();
	private static MessageDigest digest;

	static {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// XXX: log
			e.printStackTrace();
		}
	}

	public static String genConfirmationCode() {
		return new BigInteger(130, random).toString(32);
	}

	public static String hash(String pass) {
		return new String(digest.digest(pass.getBytes()));
	}

	public Observer findOne(Long id) {
		return repo.findOne(id);
	}

	public Iterable<Observer> findBySoftDeleted(boolean isSoftDeleted) {
		return repo.findBySoftDeleted(isSoftDeleted);
	}

	public Observer findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public Observer save(Observer observer) {
		return repo.save(observer);
	}

	public void register(Observer observer) {
		String confirmationCode = genConfirmationCode();
		observer.setConfirmationCode(confirmationCode);
		observer.setFriends(new HashSet<Observer>());
		observer.setObservations(new HashSet<Observation>());
		observer.setSoftDeleted(false);
		repo.save(observer);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("noreply@neo4neodb.com");
		msg.setTo(observer.getEmail());
		msg.setSubject(REGISTRATION_SUBJ);
		msg.setText(MessageFormat.format(REGISTRATION_MSG, observer.getName(),
				confirmationCode));
		mailSender.send(msg);
	}

	public boolean activate(Observer observer) {
		Observer o = repo.findOne(observer.getId());
		if (o == null)
			throw new IllegalArgumentException("User does not exist with "
					+ "the specified id.");
		if (o.activate(observer.getConfirmationCode())) {
			repo.save(o);
			return true;
		}
		return false;
	}

	public void resetPassword(long uid) {
		Observer o = repo.findOne(uid);
		if (o == null)
			throw new IllegalArgumentException("User does not exist with "
					+ "the specified id.");
		String confirmationCode = genConfirmationCode();
		o.setConfirmationCode(confirmationCode);
		repo.save(o);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("noreply@neo4neodb.com");
		msg.setTo(o.getEmail());
		msg.setSubject(PWRESET_SUBJ);
		msg.setText(MessageFormat.format(PWRESET_MSG, o.getName(),
				confirmationCode));
		mailSender.send(msg);
	}

	public boolean changePassword(Observer observer) {
		Observer o = repo.findOne(observer.getId());
		if (o == null)
			throw new IllegalArgumentException("User does not exist with "
					+ "the specified id.");
		if (o.resetPassword(observer.getConfirmationCode(),
				hash(observer.getPassword()))) {
			repo.save(o);
			return true;
		}
		return false;
	}
}
