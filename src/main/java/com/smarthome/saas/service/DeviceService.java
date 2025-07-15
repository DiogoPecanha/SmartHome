package com.smarthome.saas.service;

import com.smarthome.saas.entity.Company;
import com.smarthome.saas.entity.Device;
import com.smarthome.saas.entity.SmartHome;
import com.smarthome.saas.enums.SubscriptionPlan;
import com.smarthome.saas.exception.SubscriptionLimitExceededException;
import com.smarthome.saas.repository.DeviceRepository;
import com.smarthome.saas.repository.SmartHomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final SmartHomeRepository smartHomeRepository;

    public Device createDevice(Device device) {
        SmartHome smartHome = smartHomeRepository.findById(device.getSmartHome().getId())
                .orElseThrow(() -> new IllegalArgumentException("Casa inteligente não encontrada"));

        validateDeviceCreation(smartHome);

        device.setSmartHome(smartHome);
        return deviceRepository.save(device);
    }

    private void validateDeviceCreation(SmartHome smartHome) {
        Company company = smartHome.getCompany();
        long currentDeviceCount = deviceRepository.countBySmartHomeId(smartHome.getId());
        
        switch (company.getSubscriptionPlan()) {
            case FREEMIUM:
                if (currentDeviceCount >= 10) {
                    throw new SubscriptionLimitExceededException(
                        "Plano Freemium permite até 10 dispositivos por casa inteligente. Atualize seu plano para adicionar mais dispositivos."
                    );
                }
                break;
            case BASIC:
                if (currentDeviceCount >= 50) {
                    throw new SubscriptionLimitExceededException(
                        "Plano Básico permite até 50 dispositivos por casa inteligente. Atualize para o plano Avançado para adicionar mais dispositivos."
                    );
                }
                break;
            case ADVANCED:
                // Sem limite para plano avançado
                break;
        }
    }

    @Transactional(readOnly = true)
    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Device> findBySmartHomeId(Long smartHomeId) {
        return deviceRepository.findBySmartHomeId(smartHomeId);
    }

    @Transactional(readOnly = true)
    public List<Device> findByCompanyId(Long companyId) {
        return deviceRepository.findBySmartHomeCompanyId(companyId);
    }

    @Transactional(readOnly = true)
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Device updateDevice(Long id, Device updatedDevice) {
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dispositivo não encontrado"));

        existingDevice.setName(updatedDevice.getName());
        existingDevice.setDeviceType(updatedDevice.getDeviceType());
        existingDevice.setStatus(updatedDevice.getStatus());
        existingDevice.setDescription(updatedDevice.getDescription());
        existingDevice.setLocation(updatedDevice.getLocation());
        existingDevice.setManufacturer(updatedDevice.getManufacturer());
        existingDevice.setModel(updatedDevice.getModel());
        existingDevice.setSerialNumber(updatedDevice.getSerialNumber());

        return deviceRepository.save(existingDevice);
    }

    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new IllegalArgumentException("Dispositivo não encontrado");
        }
        deviceRepository.deleteById(id);
    }
}

