## Selmag-app v 1.0
Учебный-проект для демонстрации навыков в проектировании и разработке полного цикла микросервисной архитектуры с
использованием Spring framework.

stack:

- spring boot (web, validation, webflux)
- spring cloud netflix eureka, config, api gateway
- security (oauth2, keycloak)
- thymeleaf
- spring data jpa, hibernate
- postgres + flyway, mongoDB
- lombok
- junit, mockito, wiremock, testcontainers
- spring doc, swagger
- grafana, loki, tempo, prometheus
- ?

features:
- 
services:
- catalogue-service (rest)
- manager-app (mvc)
- customer-app (webflux)
- feedback-service (rest-webflux)
- admin-server (infrastructure)
- eureka-server (infrastructure)
- config-server (infrastructure)
- api-gateway (infrastructure)
