package com.smarthome.saas.service;

import com.smarthome.saas.entity.Company;
import com.smarthome.saas.enums.SubscriptionPlan;
import com.smarthome.saas.enums.SubscriptionType;
import com.smarthome.saas.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        if (companyRepository.existsByEmail(company.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }
        
        if (companyRepository.existsByCnpj(company.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já está em uso");
        }
        
        return companyRepository.save(company);
    }

    @Transactional(readOnly = true)
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Company> findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company updateSubscriptionPlan(Long companyId, SubscriptionPlan subscriptionPlan, SubscriptionType subscriptionType) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        
        company.setSubscriptionPlan(subscriptionPlan);
        company.setSubscriptionType(subscriptionType);
        
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        existingCompany.setName(updatedCompany.getName());
        existingCompany.setEmail(updatedCompany.getEmail());
        existingCompany.setCnpj(updatedCompany.getCnpj());

        return companyRepository.save(existingCompany);
    }

    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        companyRepository.deleteById(id);
    }
}

