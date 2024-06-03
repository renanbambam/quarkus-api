package org.acme.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Enterprise;

@ApplicationScoped
public class EnterpriseRepository implements PanacheMongoRepository<Enterprise> {
}
