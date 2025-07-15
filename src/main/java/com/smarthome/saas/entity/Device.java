package com.smarthome.saas.entity;

import com.smarthome.saas.enums.DeviceStatus;
import com.smarthome.saas.enums.DeviceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do dispositivo é obrigatório")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Tipo do dispositivo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType deviceType;

    @NotNull(message = "Status do dispositivo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status;

    @Column(length = 500)
    private String description;

    @NotBlank(message = "Localização é obrigatória")
    @Column(nullable = false)
    private String location;

    @Column
    private String manufacturer;

    @Column
    private String model;

    @Column
    private String serialNumber;

    @NotNull(message = "Casa inteligente é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smart_home_id", nullable = false)
    private SmartHome smartHome;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

