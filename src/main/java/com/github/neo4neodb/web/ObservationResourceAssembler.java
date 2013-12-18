package com.github.neo4neodb.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.neo4neodb.domain.Observation;

@Component
public class ObservationResourceAssembler extends
		ResourceAssemblerSupport<Observation, ObservationResource> {

	public ObservationResourceAssembler() {
		super(ObservationController.class, ObservationResource.class);
	}

	@Override
	public ObservationResource toResource(Observation observation) {
		ObservationResource resource = instantiateResource(observation);
		resource.add(linkTo(ObservationController.class).slash(
				observation.getId()).withSelfRel());
		return resource;
	}

	@Override
	protected ObservationResource instantiateResource(Observation observation) {
		return new ObservationResource(observation.getId(),
				observation.getRightAscension(), observation.getDeclination(),
				observation.getMagnitude(), observation.getBand(),
				observation.getObservers());
	}

}
