package com.smarthome.saas.controller;

import com.smarthome.saas.entity.Company;
import com.smarthome.saas.entity.SmartHome;
import com.smarthome.saas.exception.SubscriptionLimitExceededException;
import com.smarthome.saas.service.SmartHomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/smart-homes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SmartHomeController {

    private final SmartHomeService smartHomeService;

    @PostMapping
    public ResponseEntity<?> createSmartHome(@Valid @RequestBody SmartHome smartHome) {
        try {
            SmartHome savedSmartHome = smartHomeService.createSmartHome(smartHome);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSmartHome);
        } catch (SubscriptionLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SmartHome>> getAllSmartHomes() {
        List<SmartHome> smartHomes = smartHomeService.findAll();
        return ResponseEntity.ok(smartHomes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmartHome> getSmartHomeById(@PathVariable Long id) {
        return smartHomeService.findById(id)
                .map(smartHome -> ResponseEntity.ok(smartHome))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<SmartHome>> getSmartHomesByCompany(@PathVariable Long companyId) {
        List<SmartHome> smartHomes = smartHomeService.findByCompanyId(companyId);
        return ResponseEntity.ok(smartHomes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSmartHome(@PathVariable Long id, @Valid @RequestBody SmartHome smartHome) {
        try {
            SmartHome updatedSmartHome = smartHomeService.updateSmartHome(id, smartHome);
            return ResponseEntity.ok(updatedSmartHome);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSmartHome(@PathVariable Long id) {
        try {
            smartHomeService.deleteSmartHome(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().body(e.getMessage());
        }
    }
}

