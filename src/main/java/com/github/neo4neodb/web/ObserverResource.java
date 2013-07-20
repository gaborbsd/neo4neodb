package com.github.neo4neodb.web;


import org.springframework.hateoas.ResourceSupport;

public class ObserverResource extends ResourceSupport {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
