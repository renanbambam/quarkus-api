package org.acme.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Expense;

@ApplicationScoped
public class ExpenseRepository implements PanacheMongoRepository<Expense> {
}
