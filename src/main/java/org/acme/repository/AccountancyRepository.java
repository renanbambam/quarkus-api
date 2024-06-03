package org.acme.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.acme.model.Accountancy;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountancyRepository implements PanacheMongoRepository<Accountancy> {
}
