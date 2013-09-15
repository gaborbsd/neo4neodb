package com.github.neo4neodb.domain;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Observation {

	@GraphId
	private Long id;

	// right ascension (time - millis since epoch)
	private long ra;

	// declination (degree)
	private double dec;

	// magnitude
	private double mag;

	// band
	private MagnitudeBand band;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getRa() {
		return ra;
	}

	public void setRa(long ra) {
		this.ra = ra;
	}

	public void setRa(Calendar c) {
		this.ra = c.getTimeInMillis();
	}

	public void setRa(Date d) {
		this.ra = d.getTime();
	}

	public double getDec() {
		return dec;
	}

	public void setDec(double dec) {
		this.dec = dec;
	}

	public void setDec(int deg, int min, int sec, int milli) {
		this.dec = deg + min / 60.0 + sec / 3600.0 + milli / 3600000.0;
	}

	public double getMag() {
		return mag;
	}

	public void setMag(double mag) {
		this.mag = mag;
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
