# DESAFIO PARA DESENVOLVEDORES SÊNIOR

## Estória do Usuário: Sistema de Automação e Monitoramento Inteligente

Como um usuário do sistema de gerenciamento de casas inteligentes SaaS,
Eu quero ter diferentes opções de planos de assinatura (Freemium, Básico, Avançado) com funcionalidades de automação e monitoramento,
Para que eu possa escolher o plano que melhor se adapta às minhas necessidades de controle e automação residencial.

### Critérios de Aceitação:

*   **Plano Freemium:**
    *   Permite o gerenciamento de 1 casa inteligente.
    *   Permite até 10 dispositivos por casa.
    *   Funcionalidades básicas de controle manual dos dispositivos.
    *   Histórico de dados limitado a 7 dias.
    *   Não requer pagamento.

*   **Plano Básico:**
    *   Permite o gerenciamento de até 5 casas inteligentes.
    *   Permite até 50 dispositivos por casa.
    *   Funcionalidades de automação simples (horários programados).
    *   Histórico de dados de 30 dias.
    *   Notificações básicas por email.
    *   Requer assinatura mensal ou anual.

*   **Plano Avançado:**
    *   Casas inteligentes ilimitadas.
    *   Dispositivos ilimitados por casa.
    *   Automação avançada com regras condicionais e cenários.
    *   Histórico de dados ilimitado.
    *   Notificações em tempo real (email, SMS, push).
    *   Integração com assistentes virtuais (Alexa, Google Assistant).
    *   Dashboard analítico com relatórios de consumo.
    *   Requer assinatura mensal ou anual.

### Funcionalidades Principais a Implementar:

#### 1. Sistema de Automação
Implementar um sistema que permita criar regras de automação baseadas em:
- **Horários**: Ligar/desligar dispositivos em horários específicos
- **Sensores**: Ações baseadas em leituras de sensores (temperatura, movimento, luminosidade)
- **Cenários**: Conjuntos de ações que podem ser executadas simultaneamente

#### 2. Monitoramento em Tempo Real
- **Status dos Dispositivos**: Monitoramento contínuo do status (online/offline/erro)
- **Histórico de Dados**: Armazenamento e consulta de dados históricos dos dispositivos
- **Alertas**: Sistema de notificações para eventos importantes

#### 3. Dashboard Analítico (Plano Avançado)
- **Consumo de Energia**: Relatórios de consumo por dispositivo e período
- **Padrões de Uso**: Análise de padrões de utilização dos dispositivos
- **Eficiência Energética**: Sugestões para otimização do consumo

### Detalhes de Implementação Sugeridos:

#### 1. Novas Entidades:

**AutomationRule**
```java
@Entity
public class AutomationRule {
    private Long id;
    private String name;
    private String description;
    private RuleType type; // SCHEDULE, SENSOR_BASED, SCENARIO
    private String conditions; // JSON com condições
    private String actions; // JSON com ações
    private boolean active;
    private SmartHome smartHome;
    private LocalDateTime createdAt;
}
```

**DeviceData**
```java
@Entity
public class DeviceData {
    private Long id;
    private Device device;
    private String dataType; // TEMPERATURE, HUMIDITY, POWER_CONSUMPTION, etc.
    private Double value;
    private String unit;
    private LocalDateTime timestamp;
}
```

**Notification**
```java
@Entity
public class Notification {
    private Long id;
    private Company company;
    private NotificationType type; // EMAIL, SMS, PUSH
    private String title;
    private String message;
    private boolean sent;
    private LocalDateTime createdAt;
}
```

#### 2. Lógica de Negócio nos Serviços:

**AutomationService:**
- Validar criação de regras baseada no plano de assinatura
- Plano Freemium: Apenas controle manual
- Plano Básico: Até 10 regras de automação simples
- Plano Avançado: Regras ilimitadas e condicionais complexas

**MonitoringService:**
- Coletar e armazenar dados dos dispositivos
- Aplicar retenção de dados baseada no plano
- Gerar alertas baseados em thresholds configurados

**AnalyticsService (Plano Avançado):**
- Calcular métricas de consumo e eficiência
- Gerar relatórios personalizados
- Identificar padrões e anomalias

#### 3. Endpoints da API:

```
POST /api/automation-rules - Criar regra de automação
GET /api/automation-rules/smart-home/{id} - Listar regras por casa
PUT /api/automation-rules/{id}/toggle - Ativar/desativar regra

POST /api/devices/{id}/data - Registrar dados do dispositivo
GET /api/devices/{id}/data - Consultar histórico de dados
GET /api/devices/{id}/status - Status em tempo real

GET /api/analytics/consumption/{smartHomeId} - Relatório de consumo
GET /api/analytics/efficiency/{smartHomeId} - Análise de eficiência
```

#### 4. Testes Unitários Específicos:

**Cenários de Teste para AutomationService:**
- Criar regra com plano Freemium (deve falhar)
- Criar regra com plano Básico (sucesso até limite)
- Criar regra com plano Avançado (sucesso ilimitado)
- Executar automação baseada em horário
- Executar automação baseada em sensor

**Cenários de Teste para MonitoringService:**
- Armazenar dados de dispositivo
- Aplicar retenção baseada no plano
- Gerar alerta por threshold excedido
- Consultar histórico respeitando limite do plano

### Desafios Técnicos Adicionais:

#### 1. Processamento em Tempo Real
Implementar um sistema de processamento de eventos em tempo real para:
- Monitoramento contínuo dos dispositivos
- Execução de regras de automação
- Geração de alertas instantâneos

#### 2. Otimização de Performance
- Implementar cache para consultas frequentes de status
- Otimizar queries de histórico de dados
- Implementar paginação para grandes volumes de dados

#### 3. Segurança e Privacidade
- Implementar autenticação JWT
- Criptografia de dados sensíveis
- Auditoria de ações críticas
- Rate limiting por plano de assinatura

#### 4. Integração Externa (Plano Avançado)
- Webhook para integração com assistentes virtuais
- API para integração com dispositivos IoT reais
- Sistema de notificações push

### Critérios de Avaliação:

1. **Arquitetura e Design**: Qualidade do código, padrões utilizados, separação de responsabilidades
2. **Lógica de Negócio**: Implementação correta das regras de planos de assinatura
3. **Testes**: Cobertura e qualidade dos testes unitários e de integração
4. **Performance**: Otimizações implementadas para cenários de alta carga
5. **Documentação**: Qualidade da documentação da API e do código
6. **Criatividade**: Soluções inovadoras para os desafios propostos

### Entregáveis Esperados:

- [ ] Implementação completa das novas entidades e relacionamentos
- [ ] Serviços com lógica de negócio para automação e monitoramento
- [ ] Controllers REST com todos os endpoints especificados
- [ ] Testes unitários cobrindo cenários críticos
- [ ] Documentação da API (Swagger/OpenAPI)
- [ ] Script de dados de exemplo para demonstração
- [ ] README com instruções de execução e teste

### Considerações Adicionais:

- Utilizar padrões de design apropriados (Strategy, Observer, Factory)
- Implementar tratamento robusto de exceções
- Considerar escalabilidade para milhares de dispositivos
- Implementar logs estruturados para monitoramento
- Seguir princípios SOLID e Clean Code

### Tempo Estimado:
**5-7 dias** para um desenvolvedor sênior implementar todas as funcionalidades principais com qualidade de produção.

---

**Boa sorte e demonstre sua expertise em desenvolvimento de sistemas IoT e SaaS!** 🏠🤖

