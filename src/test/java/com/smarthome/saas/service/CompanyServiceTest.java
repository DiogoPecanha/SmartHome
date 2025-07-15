package com.smarthome.saas.service;

import com.smarthome.saas.entity.Company;
import com.smarthome.saas.enums.SubscriptionPlan;
import com.smarthome.saas.enums.SubscriptionType;
import com.smarthome.saas.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setName("Empresa Teste");
        company.setEmail("teste@empresa.com");
        company.setCnpj("12345678000199");
        company.setSubscriptionPlan(SubscriptionPlan.FREEMIUM);
        company.setSubscriptionType(SubscriptionType.NONE);
    }

    @Test
    void createCompany_Success() {
        // Given
        when(companyRepository.existsByEmail(company.getEmail())).thenReturn(false);
        when(companyRepository.existsByCnpj(company.getCnpj())).thenReturn(false);
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        // When
        Company result = companyService.createCompany(company);

        // Then
        assertNotNull(result);
        assertEquals(company.getName(), result.getName());
        assertEquals(company.getEmail(), result.getEmail());
        verify(companyRepository).save(company);
    }

    @Test
    void createCompany_EmailAlreadyExists_ThrowsException() {
        // Given
        when(companyRepository.existsByEmail(company.getEmail())).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.createCompany(company)
        );
        assertEquals("Email já está em uso", exception.getMessage());
        verify(companyRepository, never()).save(any());
    }

    @Test
    void createCompany_CnpjAlreadyExists_ThrowsException() {
        // Given
        when(companyRepository.existsByEmail(company.getEmail())).thenReturn(false);
        when(companyRepository.existsByCnpj(company.getCnpj())).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.createCompany(company)
        );
        assertEquals("CNPJ já está em uso", exception.getMessage());
        verify(companyRepository, never()).save(any());
    }

    @Test
    void updateSubscriptionPlan_Success() {
        // Given
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        // When
        Company result = companyService.updateSubscriptionPlan(1L, SubscriptionPlan.BASIC, SubscriptionType.MONTHLY);

        // Then
        assertNotNull(result);
        assertEquals(SubscriptionPlan.BASIC, result.getSubscriptionPlan());
        assertEquals(SubscriptionType.MONTHLY, result.getSubscriptionType());
        verify(companyRepository).save(company);
    }

    @Test
    void updateSubscriptionPlan_CompanyNotFound_ThrowsException() {
        // Given
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.updateSubscriptionPlan(1L, SubscriptionPlan.BASIC, SubscriptionType.MONTHLY)
        );
        assertEquals("Empresa não encontrada", exception.getMessage());
        verify(companyRepository, never()).save(any());
    }

    @Test
    void findById_Success() {
        // Given
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // When
        Optional<Company> result = companyService.findById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(company.getId(), result.get().getId());
    }

    @Test
    void deleteCompany_Success() {
        // Given
        when(companyRepository.existsById(1L)).thenReturn(true);

        // When
        companyService.deleteCompany(1L);

        // Then
        verify(companyRepository).deleteById(1L);
    }

    @Test
    void deleteCompany_CompanyNotFound_ThrowsException() {
        // Given
        when(companyRepository.existsById(1L)).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> companyService.deleteCompany(1L)
        );
        assertEquals("Empresa não encontrada", exception.getMessage());
        verify(companyRepository, never()).deleteById(any());
    }
}

