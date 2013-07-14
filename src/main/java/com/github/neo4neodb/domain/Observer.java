package com.github.neo4neodb.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class Observer {
    @GraphId
    private Long id;
    private String name;

    @RelatedTo(type = "FRIENDS_WITH", direction = Direction.INCOMING)
    private Set<Observer> friends;

    protected Observer() {
    }

    public Observer(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Observer> getFriends() {
        return friends;
    }

    public void setFriends(Set<Observer> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", friends=[" + (null != friends ? friends.size() : 0) + "]" +
                '}';
    }
}
