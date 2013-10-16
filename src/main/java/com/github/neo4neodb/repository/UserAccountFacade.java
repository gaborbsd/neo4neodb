package com.github.neo4neodb.repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.github.neo4neodb.domain.Observer;

@Component
public class UserAccountFacade {

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

	public void register(String name, String pass, String email) {
		String confirmationCode = genConfirmationCode();
		Observer o = new Observer(name, hash(pass), email, confirmationCode);
		repo.save(o);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("noreply@neo4neodb.com");
		msg.setTo(email);
		msg.setSubject(REGISTRATION_SUBJ);
		msg.setText(MessageFormat.format(REGISTRATION_MSG, name,
				confirmationCode));
		mailSender.send(msg);
	}

	public boolean activate(long uid, String confirmationCode) {
		Observer o = repo.findOne(uid);
		if (o == null)
			throw new IllegalArgumentException("User does not exist with "
					+ "the specified id.");
		if (o.activate(confirmationCode)) {
			repo.save(o);
			return true;
		}
		return false;
	}

	public void requestPassword(long uid) {
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

	public boolean resetPassword(long uid, String confirmationCode, String pass) {
		Observer o = repo.findOne(uid);
		if (o == null)
			throw new IllegalArgumentException("User does not exist with "
					+ "the specified id.");
		if (o.resetPassword(confirmationCode, hash(pass))) {
			repo.save(o);
			return true;
		}
		return false;
	}
}
