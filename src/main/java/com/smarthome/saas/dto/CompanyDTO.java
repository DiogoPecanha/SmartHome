package com.smarthome.saas.dto;

import com.smarthome.saas.enums.SubscriptionPlan;
import com.smarthome.saas.enums.SubscriptionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private Long id;

    @NotBlank(message = "Nome da empresa é obrigatório")
    private String name;

    @Email(message = "Email deve ter um formato válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    @NotNull(message = "Plano de assinatura é obrigatório")
    private SubscriptionPlan subscriptionPlan;

    @NotNull(message = "Tipo de assinatura é obrigatório")
    private SubscriptionType subscriptionType;
}

