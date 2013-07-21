package com.github.neo4neodb.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.Set;

@NodeEntity
@TypeAlias("Observer")
public class Observer {
    @GraphId
    private Long id;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "observers")
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    private boolean softDeleted;

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
