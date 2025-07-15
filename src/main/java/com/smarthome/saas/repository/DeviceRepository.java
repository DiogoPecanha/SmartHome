package com.smarthome.saas.repository;

import com.smarthome.saas.entity.Device;
import com.smarthome.saas.enums.DeviceStatus;
import com.smarthome.saas.enums.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    
    List<Device> findBySmartHomeId(Long smartHomeId);
    
    List<Device> findByDeviceType(DeviceType deviceType);
    
    List<Device> findByStatus(DeviceStatus status);
    
    long countBySmartHomeId(Long smartHomeId);
    
    List<Device> findBySmartHomeCompanyId(Long companyId);
}

