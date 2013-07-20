package com.github.neo4neodb.web;

import com.github.neo4neodb.domain.Observer;
import com.github.neo4neodb.repository.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.neo4j.helpers.collection.IteratorUtil.asCollection;

@Controller
@RequestMapping("/observer")
public class ObserverController {
    @Autowired
    ObserverRepository observerRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    List<Observer> getObservers() {
        Collection<Observer> observerCollection = asCollection(observerRepository.findAll());
        observerRepository.save(new Observer(new Date().toString()));
        return new ArrayList<Observer>(observerCollection);
    }

}
