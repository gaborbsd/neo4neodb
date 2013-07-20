package com.github.neo4neodb.web;


import com.github.neo4neodb.repository.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SuppressWarnings("unused")
public class IndexController {

    @Autowired
    ObserverRepository observerRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("observerCount", observerRepository.count());
        return "index";
    }

}