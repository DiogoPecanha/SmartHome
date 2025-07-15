package com.smarthome.saas.controller;

import com.smarthome.saas.dto.CompanyDTO;
import com.smarthome.saas.entity.Company;
import com.smarthome.saas.enums.SubscriptionPlan;
import com.smarthome.saas.enums.SubscriptionType;
import com.smarthome.saas.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        try {
            Company company = convertToEntity(companyDTO);
            Company savedCompany = companyService.createCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedCompany));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        List<CompanyDTO> companyDTOs = companies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(companyDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        return companyService.findById(id)
                .map(company -> ResponseEntity.ok(convertToDTO(company)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyDTO companyDTO) {
        try {
            Company company = convertToEntity(companyDTO);
            Company updatedCompany = companyService.updateCompany(id, company);
            return ResponseEntity.ok(convertToDTO(updatedCompany));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/subscription")
    public ResponseEntity<CompanyDTO> updateSubscription(
            @PathVariable Long id,
            @RequestParam SubscriptionPlan subscriptionPlan,
            @RequestParam SubscriptionType subscriptionType) {
        try {
            Company updatedCompany = companyService.updateSubscriptionPlan(id, subscriptionPlan, subscriptionType);
            return ResponseEntity.ok(convertToDTO(updatedCompany));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private CompanyDTO convertToDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getEmail(),
                company.getCnpj(),
                company.getSubscriptionPlan(),
                company.getSubscriptionType()
        );
    }

    private Company convertToEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        company.setEmail(companyDTO.getEmail());
        company.setCnpj(companyDTO.getCnpj());
        company.setSubscriptionPlan(companyDTO.getSubscriptionPlan());
        company.setSubscriptionType(companyDTO.getSubscriptionType());
        return company;
    }
}

