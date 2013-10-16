package com.github.neo4neodb.repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.neo4neodb.domain.Observer;

@Component
public class UserAccountFacade {

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
		// XXX : send email
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
		// XXX: send mail
		repo.save(o);
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
