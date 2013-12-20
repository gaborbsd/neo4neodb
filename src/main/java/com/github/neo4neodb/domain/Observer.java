package com.github.neo4neodb.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
@TypeAlias("Observer")
public class Observer {
	@GraphId
	private Long id;

	private String name;

	// hashed by application layer
	private String password;

	@Indexed(indexType = IndexType.UNIQUE, indexName = "observers")
	private String email;

	private boolean active;

	private String confirmationCode;

	@Fetch
	@RelatedTo(type = "OBSERVED", direction = Direction.OUTGOING)
	private Set<Observation> observations = new HashSet<>();

	// Weird but conventional name
	@Fetch
	@RelatedToVia(type = "OBSERVED", direction = Direction.OUTGOING)
	private Set<Observed> observeds = new HashSet<>();

	private boolean softDeleted;

	public boolean activate(String confirmationCode) {
		if (confirmationCode.equals(this.confirmationCode)) {
			this.active = true;
			this.confirmationCode = null;
			return true;
		}
		return false;
	}

	public boolean resetPassword(String confirmationCode, String pass) {
		if (confirmationCode.equals(this.confirmationCode)) {
			this.confirmationCode = null;
			this.password = pass;
			return true;
		}
		return false;
	}

	public Observer(String name, String password, String email,
			String confirmationCode) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.confirmationCode = confirmationCode;
		this.active = false;
		this.friends = new HashSet<>();
		this.observations = new HashSet<>();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isSoftDeleted() {
		return softDeleted;
	}

	public void setSoftDeleted(boolean softDeleted) {
		this.softDeleted = softDeleted;
	}

	@RelatedTo(type = "FRIENDS_WITH", direction = Direction.INCOMING)
	private Set<Observer> friends;

	protected Observer() {
	}

	public Observer(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
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

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public Set<Observer> getFriends() {
		return friends;
	}

	public void setFriends(Set<Observer> friends) {
		this.friends = friends;
	}

	public Set<Observation> getObservations() {
		return observations;
	}

	public void setObservations(Set<Observation> observations) {
		this.observations = observations;
	}

	public void observed(Observation o, long date) {
		observeds.add(new Observed(this, o, date));
	}

	@Override
	public String toString() {
		return "Friend{" + "id=" + id + ", name='" + name + '\''
				+ ", friends=[" + (null != friends ? friends.size() : 0) + "]"
				+ '}';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Observer other = (Observer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
