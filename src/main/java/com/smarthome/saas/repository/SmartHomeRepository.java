package com.smarthome.saas.repository;

import com.smarthome.saas.entity.SmartHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartHomeRepository extends JpaRepository<SmartHome, Long> {
    
    List<SmartHome> findByCompanyId(Long companyId);
    
    long countByCompanyId(Long companyId);
}

