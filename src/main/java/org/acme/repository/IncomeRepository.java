package org.acme.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.acme.model.Income;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IncomeRepository implements PanacheMongoRepository<Income> {
}
