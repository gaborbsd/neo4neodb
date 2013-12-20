package com.github.neo4neodb.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.neo4neodb.domain.Observer;

public interface ObserverRepository extends GraphRepository<Observer> {
    List<Observer> findBySoftDeleted(boolean isSoftDeletd);
    Observer findByEmail(String email);
}