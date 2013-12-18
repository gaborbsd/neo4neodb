package com.github.neo4neodb.web;

import static org.neo4j.helpers.collection.IteratorUtil.asCollection;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.neo4neodb.domain.Observation;
import com.github.neo4neodb.repository.ObservationService;

@Controller
@RequestMapping("/observations")
public class ObservationController {
	@Autowired
	ObservationService observationService;

	@Autowired
	ObservationResourceAssembler observationResourceAssembler;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<List<ObservationResource>> getObservations() {
		Collection<Observation> observations = asCollection(observationService
				.findAll());
		List<ObservationResource> resourceList = observationResourceAssembler
				.toResources(observations);
		return new ResponseEntity<List<ObservationResource>>(resourceList,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<ObservationResource> getObservation(@PathVariable Long id) {
		Observation observation = observationService.findOne(id);
		ObservationResource resource = observationResourceAssembler
				.toResource(observation);
		return new ResponseEntity<ObservationResource>(resource, HttpStatus.OK);

	}

	// XXX: handling session, associating with current user
	// @RequestMapping(method=RequestMethod.POST)
	// ResponseEntity<ObservationResource> createObservation(@RequestBody
	// Observation observation) {
	// observationService.newObservationForObserver(observation, XXX);
	// ObservationResource resource =
	// observationResourceAssembler.toResource(observation);
	// return new ResponseEntity<ObservationResource>(resource,
	// HttpStatus.CREATED);
	// }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ResponseEntity<ObservationResource> deleteObservation(@PathVariable long id) {
		// XXX: auth check; only allow deletion of own observations
		observationService.delete(id);
		return new ResponseEntity<ObservationResource>(
				(ObservationResource) null, HttpStatus.OK);
	}

	@RequestMapping(value = "getSimilar", method = RequestMethod.GET)
	ResponseEntity<List<ObservationResource>> getSimilar(
			@RequestBody Observation observation) {
		Collection<Observation> observations = asCollection(observationService
				.getSimilarObservations(observation));
		List<ObservationResource> resourceList = observationResourceAssembler
				.toResources(observations);
		return new ResponseEntity<List<ObservationResource>>(resourceList,
				HttpStatus.OK);
	}
}
