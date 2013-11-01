package com.github.neo4neodb.repository;

import com.github.neo4neodb.domain.Observer;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface ObserverRepository extends GraphRepository<Observer> {
    List<Observer> findBySoftDeleted(boolean isSoftDeletd);
}