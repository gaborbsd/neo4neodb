package com.github.neo4neodb.web;

import com.github.neo4neodb.domain.Observer;
import com.github.neo4neodb.repository.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Collection;
import java.util.List;

import static org.neo4j.helpers.collection.IteratorUtil.asCollection;

@Controller
@RequestMapping("/observers")
public class ObserverController {
    @Autowired
    ObserverRepository observerRepository;

    @Autowired
    private ObserverResourceAssembler observerResourceAssembler;

    @RequestMapping(value = "/{observerId}", method = RequestMethod.GET)
    @ResponseBody
    public Observer observer(@PathVariable("observerId") Long observerId) {
        Observer observer = observerRepository.findOne(observerId);
        return observer;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<List<ObserverResource>> getObservers() {
        Collection<Observer> observerCollection = asCollection(observerRepository.findBySoftDeleted(false));
        List<ObserverResource> resourceList = observerResourceAssembler.toResources(observerCollection);
        return new ResponseEntity<List<ObserverResource>>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<ObserverResource> createObserver(@RequestBody Observer body) {
        body.setSoftDeleted(false);
        observerRepository.save(body);
        ObserverResource resource = observerResourceAssembler.toResource(body);
        return new ResponseEntity<ObserverResource>(resource, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{observerId}")
    ResponseEntity<ObserverResource> updateObserver(@PathVariable Long observerId,
                                          @RequestBody Observer body){
        Observer observer = observerRepository.findOne(observerId);
        observer.setName(body.getName());
        observerRepository.save(observer);
        ObserverResource resource = observerResourceAssembler.toResource(observer);
        return new ResponseEntity<ObserverResource>(resource, HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.DELETE, value = "/{betId}")
    ResponseEntity<ObserverResource> cancelObserver(@PathVariable Long betId) {
        Observer observer = observerRepository.findOne(betId);
        observer.setSoftDeleted(true);
        observerRepository.save(observer);
        ObserverResource resource = observerResourceAssembler.toResource(observer);
        return new ResponseEntity<ObserverResource>(resource, HttpStatus.OK);
    }

    /*@ExceptionHandler
    ResponseEntity handleExceptions(Exception ex) {
        ResponseEntity responseEntity = null;
        if (ex instanceof ObserverNotFoundException) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if (ex instanceof ObserverNotUnmatchedException) {
            responseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    } */


}
