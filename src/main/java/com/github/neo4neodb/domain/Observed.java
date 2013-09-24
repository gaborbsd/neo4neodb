package com.github.neo4neodb.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "OBSERVED")
public class Observed {
	@StartNode
	private Observer observer;
	@EndNode
	private Observation observation;
	private long date;

	Observed(Observer observer, Observation observation, long date) {
		this.observer = observer;
		this.observation = observation;
		this.date = date;
	}

	public Observer getObserver() {
		return observer;
	}

	public Observation getObservation() {
		return observation;
	}

	public long getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (date ^ (date >>> 32));
		result = prime * result
				+ ((observation == null) ? 0 : observation.hashCode());
		result = prime * result
				+ ((observer == null) ? 0 : observer.hashCode());
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
		Observed other = (Observed) obj;
		if (date != other.date)
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (observer == null) {
			if (other.observer != null)
				return false;
		} else if (!observer.equals(other.observer))
			return false;
		return true;
	}
}
