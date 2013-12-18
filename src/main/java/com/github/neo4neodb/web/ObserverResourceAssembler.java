package com.github.neo4neodb.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.neo4neodb.domain.Observer;

@Component
public class ObserverResourceAssembler extends
		ResourceAssemblerSupport<Observer, ObserverResource> {

	public ObserverResourceAssembler() {
		super(ObserverController.class, ObserverResource.class);
	}

	@Override
	public ObserverResource toResource(Observer observer) {
		ObserverResource resource = instantiateResource(observer);
		resource.add(linkTo(ObserverController.class).slash(observer.getId())
				.withSelfRel());
		return resource;
	}

	@Override
	protected ObserverResource instantiateResource(Observer observer) {
		return new ObserverResource(observer.getId(), observer.getName(),
				observer.getPassword(), observer.getEmail(),
				observer.isActive(), observer.getConfirmationCode(),
				observer.getObservations(), observer.isSoftDeleted());
	}
}
