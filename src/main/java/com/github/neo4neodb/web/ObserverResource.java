package com.github.neo4neodb.web;


import java.util.Set;

import org.springframework.hateoas.ResourceSupport;

import com.github.neo4neodb.domain.Observation;

public class ObserverResource extends ResourceSupport {
	private Long observerId;
	private String name;
	private String password;
	private String email;
	private boolean active;
	private String confirmationCode;
	private Set<Observation> observations;
	private boolean softDeleted;

    public ObserverResource(){

    }

	ObserverResource(Long observerId, String name, String password,
			String email, boolean active, String confirmationCode,
			Set<Observation> observations, boolean softDeleted) {
		super();
		this.observerId = observerId;
		this.name = name;
		this.password = password;
		this.email = email;
		this.active = active;
		this.confirmationCode = confirmationCode;
		this.observations = observations;
		this.softDeleted = softDeleted;
	}

	public Long getObserverId() {
		return observerId;
	}

	public void setObserverId(Long observerId) {
		this.observerId = observerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public Set<Observation> getObservations() {
		return observations;
	}

	public void setObservations(Set<Observation> observations) {
		this.observations = observations;
	}

	public boolean isSoftDeleted() {
		return softDeleted;
	}

	public void setSoftDeleted(boolean softDeleted) {
		this.softDeleted = softDeleted;
	}
}
