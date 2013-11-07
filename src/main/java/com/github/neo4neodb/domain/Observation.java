package com.github.neo4neodb.domain;

import java.sql.Date;
import java.util.Calendar;
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
@TypeAlias("Observation")
public class Observation {

	@GraphId
	private Long id;

	@Indexed(indexType = IndexType.UNIQUE, indexName = "observations")
	private String observationName;

	// time - millis since epoch
	private long rightAscension;

	// degree
	private double declination;

	// luminosity
	private double magnitude;
	private MagnitudeBand band;

	@Fetch
	@RelatedTo(type = "OBSERVED", direction = Direction.INCOMING)
	private Set<Observer> observers = new HashSet<>();

	// Weird but conventional name
	@Fetch
	@RelatedToVia(type = "OBSERVED", direction = Direction.INCOMING)
	private Set<Observed> observeds = new HashSet<>();

	public Observation() {
	}

	public Observation(long rightAscension, double declination,
			double magnitude, MagnitudeBand band) {
		this.rightAscension = rightAscension;
		this.declination = declination;
		this.magnitude = magnitude;
		this.band = band;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getRightAscension() {
		return rightAscension;
	}

	public void setRightAscension(long rightAscension) {
		this.rightAscension = rightAscension;
	}

	public void setRightAscension(Calendar c) {
		this.rightAscension = c.getTimeInMillis();
	}

	public void setRightAscension(Date d) {
		this.rightAscension = d.getTime();
	}

	public double getDeclination() {
		return declination;
	}

	public void setDeclination(double declination) {
		this.declination = declination;
	}

	public void setDeclination(int deg, int min, int sec, int milli) {
		this.declination = deg + min / 60.0 + sec / 3600.0 + milli / 3600000.0;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public MagnitudeBand getBand() {
		return band;
	}

	public void setBand(MagnitudeBand band) {
		this.band = band;
	}

	public Set<Observer> getObservers() {
		return observers;
	}

	public void setObservers(Set<Observer> observers) {
		this.observers = observers;
	}

	public void observedBy(Observer u, long date) {
		observeds.add(new Observed(u, this, date));
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
		Observation other = (Observation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}