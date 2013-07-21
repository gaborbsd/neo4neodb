package com.github.neo4neodb.web;


import com.github.neo4neodb.domain.Observer;
import org.springframework.hateoas.ResourceSupport;

public class ObserverResource extends ResourceSupport {
    private long observerId;
    private String observerName;

    public ObserverResource(){

    }

    public ObserverResource(long observerId, String observerName) {
        this.observerId = observerId;
        this.observerName = observerName;
    }

    public long getObserverId() {
        return observerId;
    }

    public void setObserverId(long observerId) {
        this.observerId = observerId;
    }

    public String getObserverName() {
        return observerName;
    }

    public void setObserverName(String observerName) {
        this.observerName = observerName;
    }


}
