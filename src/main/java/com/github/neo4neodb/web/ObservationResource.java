package com.github.neo4neodb.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.ResourceSupport;

import com.github.neo4neodb.domain.MagnitudeBand;
import com.github.neo4neodb.domain.Observer;

public class ObservationResource extends ResourceSupport {
	private Long observationId;
	private long rightAscension;
	private double declination;
	private double magnitude;
	private MagnitudeBand band;
	private Set<Observer> observers = new HashSet<>();

	public ObservationResource() {

	}

	ObservationResource(Long observationId, long rightAscension,
			double declination, double magnitude, MagnitudeBand band,
			Set<Observer> observers) {
		super();
		this.observationId = observationId;
		this.rightAscension = rightAscension;
		this.declination = declination;
		this.magnitude = magnitude;
		this.band = band;
		this.observers = observers;
	}

	public Long getObservationId() {
		return observationId;
	}

	public void setObservationId(Long observationId) {
		this.observationId = observationId;
	}

	public long getRightAscension() {
		return rightAscension;
	}

	public void setRightAscension(long rightAscension) {
		this.rightAscension = rightAscension;
	}

	public double getDeclination() {
		return declination;
	}

	public void setDeclination(double declination) {
		this.declination = declination;
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
}
