package com.github.neo4neodb.web;

import com.github.neo4neodb.domain.Observer;
import com.github.neo4neodb.repository.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.neo4j.helpers.collection.IteratorUtil.asCollection;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping("/observer")
public class ObserverController {
    @Autowired
    ObserverRepository observerRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    List<ObserverResource> observer() {
        Collection<Observer> observerCollection = asCollection(observerRepository.findAll());
        List<ObserverResource> resources = new ArrayList<ObserverResource>(observerCollection.size());
        for (Observer observer : observerCollection) {
            ObserverResource resource = new ObserverResource();
            resource.setName(observer.getName());
            Link detail = linkTo(ObserverController.class).slash(observer.getId()).withSelfRel();
            resource.add(detail);
            resources.add(resource);
        }


        return resources;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Observer observer(@PathVariable Long id) {
        Observer observer = observerRepository.findOne(id);
        return observer;
    }

}
