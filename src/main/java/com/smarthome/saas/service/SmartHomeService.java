package com.smarthome.saas.service;

import com.smarthome.saas.entity.Company;
import com.smarthome.saas.entity.SmartHome;
import com.smarthome.saas.enums.SubscriptionPlan;
import com.smarthome.saas.exception.SubscriptionLimitExceededException;
import com.smarthome.saas.repository.CompanyRepository;
import com.smarthome.saas.repository.SmartHomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SmartHomeService {

    private final SmartHomeRepository smartHomeRepository;
    private final CompanyRepository companyRepository;

    public SmartHome createSmartHome(SmartHome smartHome) {
        Company company = companyRepository.findById(smartHome.getCompany().getId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        validateSmartHomeCreation(company);

        smartHome.setCompany(company);
        return smartHomeRepository.save(smartHome);
    }

    private void validateSmartHomeCreation(Company company) {
        long currentSmartHomeCount = smartHomeRepository.countByCompanyId(company.getId());
        
        switch (company.getSubscriptionPlan()) {
            case FREEMIUM:
                if (currentSmartHomeCount >= 1) {
                    throw new SubscriptionLimitExceededException(
                        "Plano Freemium permite apenas 1 casa inteligente. Atualize seu plano para criar mais casas."
                    );
                }
                break;
            case BASIC:
                if (currentSmartHomeCount >= 5) {
                    throw new SubscriptionLimitExceededException(
                        "Plano Básico permite até 5 casas inteligentes. Atualize para o plano Avançado para criar mais casas."
                    );
                }
                break;
            case ADVANCED:
                // Sem limite para plano avançado
                break;
        }
    }

    @Transactional(readOnly = true)
    public Optional<SmartHome> findById(Long id) {
        return smartHomeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<SmartHome> findByCompanyId(Long companyId) {
        return smartHomeRepository.findByCompanyId(companyId);
    }

    @Transactional(readOnly = true)
    public List<SmartHome> findAll() {
        return smartHomeRepository.findAll();
    }

    public SmartHome updateSmartHome(Long id, SmartHome updatedSmartHome) {
        SmartHome existingSmartHome = smartHomeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Casa inteligente não encontrada"));

        existingSmartHome.setName(updatedSmartHome.getName());
        existingSmartHome.setAddress(updatedSmartHome.getAddress());
        existingSmartHome.setDescription(updatedSmartHome.getDescription());

        return smartHomeRepository.save(existingSmartHome);
    }

    public void deleteSmartHome(Long id) {
        if (!smartHomeRepository.existsById(id)) {
            throw new IllegalArgumentException("Casa inteligente não encontrada");
        }
        smartHomeRepository.deleteById(id);
    }
}

