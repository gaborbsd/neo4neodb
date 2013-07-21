package com.github.neo4neodb.web;


import com.github.neo4neodb.domain.Observer;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class ObserverResourceAssembler extends ResourceAssemblerSupport<Observer, ObserverResource> {

    public ObserverResourceAssembler() {
        super(ObserverController.class, ObserverResource.class);
    }

    @Override
    public ObserverResource toResource(Observer observer) {
        ObserverResource resource = instantiateResource(observer);
        resource.add(linkTo(ObserverController.class).slash(observer.getId()).withSelfRel());
        return resource;
    }

    @Override
    protected ObserverResource instantiateResource(Observer observer) {
        return new ObserverResource(observer.getId(), observer.getName());
    }
}
