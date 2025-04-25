# 📦 ms_scalable

Proyecto de arquitectura de microservicios escalable utilizando tecnologías modernas como Spring Boot, Java 17, contenedores Docker, autenticación con Keycloak y observabilidad con herramientas como Grafana y Prometheus.

---

## 🚀 Stack Tecnológico

- **Java:** 17
- **Spring Boot:** 3.4.4
- **Spring Security (MVC)**
- **Bases de datos:** Relacionales (SQL) y NoSQL
- **Docker:** 
- **Kafka:** 
- **Keycloak:** 
- **Observabilidad:** Prometheus, Grafana, Zipkin

---

## 🧩 Microservicios y Componentes

| Servicio          | Puerto | Descripción                            |
|-------------------|--------|----------------------------------------|
| `ms_products`     | 8081   | Gestión de productos                   |
| `ms_orders`       | 8082   | Gestión de pedidos                     |
| `ms_inventory`    | 8083   | Gestión de inventario                  |
| `api-gateway`     | 8084   | Puerta de enlace a los microservicios |
| `eureka-server`   | 8761   | Servicio de descubrimiento            |
| `adminer`         | 8080   | Interfaz web para bases de datos       |
| `keycloak`        | 8181   | Servidor de autenticación y autorización |
| `kafka`           | 9092   | Broker de mensajería                   |
| `zookeeper`       | 2181   | Coordinador de Kafka                   |
| `zipkin`          | 9411   | Trazabilidad de peticiones             |
| `grafana`         | 3000   | Visualización de métricas              |
| `prometheus`      | 9090   | Recolección de métricas                |

---

## 📝 Notas

- Todos los servicios están orquestados mediante Docker y configurados para facilitar la escalabilidad y el monitoreo.
- Incluye autenticación centralizada con Keycloak y seguimiento distribuido con Zipkin.

---


## ⚙️ Init project

```bash
docker-compose up --build



