package com.github.neo4neodb.domain;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Observation {

	@GraphId
	private Long id;

	// time - millis since epoch
	private long rightAscension;

	// degree
	private double declination;

	// luminosity
	private double magnitude;
	private MagnitudeBand band;

	public Observation() {
	}

	public Observation(long rightAscension, double declination, double magnitude, MagnitudeBand band) {
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