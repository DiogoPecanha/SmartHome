package com.smarthome.saas.controller;

import com.smarthome.saas.entity.Device;
import com.smarthome.saas.exception.SubscriptionLimitExceededException;
import com.smarthome.saas.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public ResponseEntity<?> createDevice(@Valid @RequestBody Device device) {
        try {
            Device savedDevice = deviceService.createDevice(device);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDevice);
        } catch (SubscriptionLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.findAll();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        return deviceService.findById(id)
                .map(device -> ResponseEntity.ok(device))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/smart-home/{smartHomeId}")
    public ResponseEntity<List<Device>> getDevicesBySmartHome(@PathVariable Long smartHomeId) {
        List<Device> devices = deviceService.findBySmartHomeId(smartHomeId);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Device>> getDevicesByCompany(@PathVariable Long companyId) {
        List<Device> devices = deviceService.findByCompanyId(companyId);
        return ResponseEntity.ok(devices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable Long id, @Valid @RequestBody Device device) {
        try {
            Device updatedDevice = deviceService.updateDevice(id, device);
            return ResponseEntity.ok(updatedDevice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        try {
            deviceService.deleteDevice(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().body(e.getMessage());
        }
    }
}

