package org.acme.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.acme.model.Payments;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentsRepository implements PanacheMongoRepository<Payments> {
}
