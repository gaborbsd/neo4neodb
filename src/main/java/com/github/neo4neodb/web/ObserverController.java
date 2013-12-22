package com.github.neo4neodb.web;

import static org.neo4j.helpers.collection.IteratorUtil.asCollection;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.neo4neodb.domain.Observer;
import com.github.neo4neodb.repository.ObserverService;

@Controller
@RequestMapping("/observers")
public class ObserverController {
	@Autowired
	ObserverService observerService;

	@Autowired
	private ObserverResourceAssembler observerResourceAssembler;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	ResponseEntity<List<ObserverResource>> getObservers() {
		Collection<Observer> observerCollection = asCollection(observerService
				.findBySoftDeleted(false));
		for (Observer o : observerCollection) {
			o.setPassword(null);
			o.setConfirmationCode(null);
		}
		List<ObserverResource> resourceList = observerResourceAssembler
				.toResources(observerCollection);
		return new ResponseEntity<List<ObserverResource>>(resourceList,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<ObserverResource> createObserver(
			@RequestBody Observer observer) {
		observerService.register(observer);
		observer.setPassword(null);
		observer.setConfirmationCode(null);
		ObserverResource resource = observerResourceAssembler
				.toResource(observer);
		return new ResponseEntity<ObserverResource>(resource,
				HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/example", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ObserverResource> example() {
		Observer observer = new Observer("test", "test", "test@test.test", "asd");
		observer.activate("asd");
		ObserverResource resource = observerResourceAssembler.instantiateResource(observer);
		return new ResponseEntity<ObserverResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{observerId}", method = RequestMethod.GET)
	@ResponseBody
	public Observer observer(@PathVariable("observerId") Long observerId) {
		Observer observer = observerService.findOne(observerId);
		observer.setPassword(null);
		observer.setConfirmationCode(null);
		return observer;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{observerId}")
	ResponseEntity<ObserverResource> updateObserver(
			@PathVariable Long observerId, @RequestBody Observer body) {
		String email = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		if (email == null) {
			throw new SecurityException("Unathenticated");
		}
		Observer observer = observerService.findOne(observerId);
		if (!observer.getEmail().equals(email)) {
			throw new SecurityException("Forbidden");
		}
		observer.setName(body.getName());
		observer.setEmail(body.getEmail());
		observer.setPassword(ObserverService.hash(body.getPassword()));
		observerService.save(observer);
		observer.setPassword(null);
		observer.setConfirmationCode(null);
		ObserverResource resource = observerResourceAssembler
				.toResource(observer);
		return new ResponseEntity<ObserverResource>(resource, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(method = RequestMethod.DELETE, value = "/{betId}")
	 * ResponseEntity<ObserverResource> cancelObserver(@PathVariable Long betId)
	 * { Observer observer = observerRepository.findOne(betId);
	 * observer.setSoftDeleted(true); observerRepository.save(observer);
	 * ObserverResource resource =
	 * observerResourceAssembler.toResource(observer); return new
	 * ResponseEntity<ObserverResource>(resource, HttpStatus.OK); }
	 */

	@RequestMapping(value = "/{id}/activate", method = RequestMethod.GET)
	ResponseEntity<ObserverResource> activate(@PathVariable Long id) {
		Observer observer = observerService.findOne(id);
		observerService.activate(observer);
		observer.setPassword(null);
		observer.setConfirmationCode(null);
		ObserverResource resource = observerResourceAssembler
				.toResource(observer);
		return new ResponseEntity<ObserverResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/resetPassword", method = RequestMethod.GET)
	ResponseEntity<ObserverResource> resetPassword(@PathVariable Long id) {
		observerService.resetPassword(id);
		Observer observer = observerService.findOne(id);
		observer.setPassword(null);
		observer.setConfirmationCode(null);
		ObserverResource resource = observerResourceAssembler
				.toResource(observer);
		return new ResponseEntity<ObserverResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
	ResponseEntity<ObserverResource> resetPassword(@RequestBody Observer body) {
		observerService.changePassword(body);
		Observer observer = observerService.findOne(body.getId());
		observer.setPassword(null);
		observer.setConfirmationCode(null);
		ObserverResource resource = observerResourceAssembler
				.toResource(observer);
		return new ResponseEntity<ObserverResource>(resource, HttpStatus.OK);
	}

	/*
	 * @ExceptionHandler ResponseEntity handleExceptions(Exception ex) {
	 * ResponseEntity responseEntity = null; if (ex instanceof
	 * ObserverNotFoundException) { responseEntity = new
	 * ResponseEntity(HttpStatus.NOT_FOUND); } else if (ex instanceof
	 * ObserverNotUnmatchedException) { responseEntity = new
	 * ResponseEntity(HttpStatus.CONFLICT); } else { responseEntity = new
	 * ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); } return
	 * responseEntity; }
	 */

}
