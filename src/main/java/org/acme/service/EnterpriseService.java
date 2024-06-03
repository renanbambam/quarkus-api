package org.acme.service;

import io.quarkus.mongodb.panache.common.PanacheUpdate;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.acme.dto.EnterpriseDTO;
import org.acme.model.Enterprise;
import org.acme.repository.EnterpriseRepository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EnterpriseService {

    @Inject
    EnterpriseRepository enterpriseRepository;

    @Transactional
    public void createEnterprise(EnterpriseDTO enterpriseDTO) {
        Enterprise enterprise = new Enterprise();
        enterprise.setEmail(enterpriseDTO.getEmail());
        enterprise.setPassword(enterpriseDTO.getPassword());
        enterprise.setName(enterpriseDTO.getName());
        enterprise.setRole("ENTERPRISE");
        enterpriseRepository.persist(enterprise);
    }

    public List<EnterpriseDTO> listEnterprises() {
        return enterpriseRepository.listAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Enterprise> getEnterpriseByName(String name) {
        return enterpriseRepository.find("name", name).list();
    }

    public List<Enterprise> getEnterpriseByEmail(String email) {
        return enterpriseRepository.find("email", email).list();
    }

    public void updateEnterprise(EnterpriseDTO enterpriseDTO, ObjectId id) {
        Enterprise enterprise = enterpriseRepository.findById(id);
        if (enterprise != null) {
            enterprise.setEmail(enterpriseDTO.getEmail());
            enterprise.setName(enterpriseDTO.getName());
            enterprise.setPassword(enterpriseDTO.getPassword());
            enterprise.setActiveRefreshTokens(enterpriseDTO.getRefreshToken());
            enterpriseRepository.persistOrUpdate(enterprise);
        } else {
            throw new EntityNotFoundException("Nenhum registro encontrado com o ID fornecido: " + id);
        }
    }

    private EnterpriseDTO toDTO(Enterprise enterprise) {
        EnterpriseDTO dto = new EnterpriseDTO();
        dto.setName(enterprise.getName());
        dto.setEmail(enterprise.getEmail());
        dto.setPassword(enterprise.getPassword());
        return dto;
    }
}
