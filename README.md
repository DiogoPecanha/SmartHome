# Smart Home SaaS - API para Gerenciamento de Casas Inteligentes

## Descrição

Este é um projeto base para uma API SaaS de gerenciamento de casas inteligentes, desenvolvido com Spring Boot, JPA/Hibernate e H2 Database. O sistema permite o gerenciamento de empresas, casas inteligentes e dispositivos IoT com diferentes planos de assinatura.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (para desenvolvimento e testes)
- **Lombok**
- **JUnit 5**
- **Mockito**
- **Maven**

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/smarthome/saas/
│   │   ├── controller/          # Controllers REST
│   │   ├── entity/              # Entidades JPA
│   │   ├── enums/               # Enumerações
│   │   ├── exception/           # Exceções customizadas
│   │   ├── repository/          # Repositórios JPA
│   │   ├── service/             # Serviços de negócio
│   │   └── SmartHomeSaasApplication.java
│   └── resources/
│       └── application.yml      # Configurações da aplicação
└── test/
    └── java/com/smarthome/saas/
        └── service/             # Testes unitários
```

## Funcionalidades Implementadas

### Entidades Principais

1. **Company**: Representa as empresas clientes
   - Planos de assinatura (FREEMIUM, BASIC, ADVANCED)
   - Tipos de assinatura (MONTHLY, ANNUAL, NONE)

2. **SmartHome**: Representa as casas inteligentes
   - Vinculadas a uma empresa
   - Limitações baseadas no plano de assinatura

3. **Device**: Representa os dispositivos IoT
   - Diferentes tipos (LIGHT, THERMOSTAT, SECURITY_CAMERA, etc.)
   - Status (ONLINE, OFFLINE, MAINTENANCE, ERROR)
   - Limitações baseadas no plano de assinatura

### Regras de Negócio por Plano

#### Plano Freemium
- 1 casa inteligente
- Até 10 dispositivos por casa
- Gratuito

#### Plano Básico
- Até 5 casas inteligentes
- Até 50 dispositivos por casa
- Assinatura paga

#### Plano Avançado
- Casas inteligentes ilimitadas
- Dispositivos ilimitados
- Assinatura paga

## Como Executar

### Pré-requisitos
- Java 21
- Maven 3.6+

### Passos

1. Clone o repositório
```bash
git clone <repository-url>
cd smart-home-saas
```

2. Execute a aplicação
```bash
mvn spring-boot:run
```

3. Acesse a aplicação
- API: http://localhost:8080
- Console H2: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## Endpoints da API

### Companies
- `POST /api/companies` - Criar empresa
- `GET /api/companies` - Listar empresas
- `GET /api/companies/{id}` - Buscar empresa por ID
- `PUT /api/companies/{id}` - Atualizar empresa
- `PUT /api/companies/{id}/subscription` - Atualizar plano de assinatura
- `DELETE /api/companies/{id}` - Deletar empresa

### Smart Homes
- `POST /api/smart-homes` - Criar casa inteligente
- `GET /api/smart-homes` - Listar casas
- `GET /api/smart-homes/{id}` - Buscar casa por ID
- `GET /api/smart-homes/company/{companyId}` - Listar casas por empresa
- `PUT /api/smart-homes/{id}` - Atualizar casa
- `DELETE /api/smart-homes/{id}` - Deletar casa

### Devices
- `POST /api/devices` - Criar dispositivo
- `GET /api/devices` - Listar dispositivos
- `GET /api/devices/{id}` - Buscar dispositivo por ID
- `GET /api/devices/smart-home/{smartHomeId}` - Listar dispositivos por casa
- `GET /api/devices/company/{companyId}` - Listar dispositivos por empresa
- `PUT /api/devices/{id}` - Atualizar dispositivo
- `DELETE /api/devices/{id}` - Deletar dispositivo

## Testes

Execute os testes unitários:
```bash
mvn test
```

## Desafio para Desenvolvedores

Consulte o arquivo [CHALLENGE.md](CHALLENGE.md) para detalhes sobre o desafio de implementação de funcionalidades avançadas de automação e monitoramento.

## Próximos Passos

O projeto base está pronto para extensão com as seguintes funcionalidades:

1. **Sistema de Automação**
   - Regras baseadas em horários
   - Automação baseada em sensores
   - Cenários personalizados

2. **Monitoramento em Tempo Real**
   - Coleta de dados dos dispositivos
   - Histórico de dados
   - Sistema de alertas

3. **Dashboard Analítico**
   - Relatórios de consumo
   - Análise de padrões
   - Métricas de eficiência

4. **Integrações Externas**
   - Assistentes virtuais
   - Dispositivos IoT reais
   - Sistemas de notificação

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

