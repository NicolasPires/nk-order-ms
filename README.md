# NK Order Microservice

NK Order Microservice (“nk-order-ms”) is a Spring Boot application designed to manage and process orders. This microservice integrates with PostgreSQL for database management, RabbitMQ for message brokering, and SonarQube for code quality analysis.

---

## Features

- Order creation, update, and deletion.
- Integration with RabbitMQ for asynchronous messaging.
- Persistent storage with PostgreSQL.
- Quality assurance using SonarQube.

---

## Prerequisites

- [Docker](https://www.docker.com/) (for containerized setup)
- [Java 19](https://www.oracle.com/java/technologies/javase-downloads.html) or higher
- [Maven](https://maven.apache.org/) (for building the project)

---

## Setup Instructions

### Local Development

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/NicolasPires/nk-order-ms.git
   cd nk-order-ms
   ```

2. **Configure the Application:**
   Update the `application.yml` file with your database and RabbitMQ configurations if needed.

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

### Containerized Deployment

1. **Build the Application JAR:**
   ```bash
   mvn clean package
   ```

2. **Start Services with Docker Compose:**
   Ensure the `docker-compose.yml` file is correctly configured and run:
   ```bash
   docker-compose up -d
   ```

3. **Access Services:**
    - **Application:** `http://localhost:8088`
    - **PostgreSQL:** `localhost:5432`
    - **RabbitMQ Management UI:** `http://localhost:15672` (default credentials: `guest` / `guest`)
    - **SonarQube:** `http://localhost:9000`
    - **Swagger:** `${Application}/swagger-ui/index.html#/`

---

## Testing

### Running Unit Tests

```bash
mvn test
```

### Testing with Docker

Run integration tests with a complete environment using Docker Compose:
```bash
docker-compose -f docker-compose-test.yml up
```

---

## Project Structure

```
|-- src
    |-- main
        |-- java
            |-- br.com.nksolucoes.nkorderms
                |-- controller
                |-- service
                |-- repository
                |-- model
        |-- resources
            |-- application.yml
    |-- test
        |-- java
            |-- br.com.nksolucoes.nkorderms
```

---

## Configuration

### Environment Variables

| Variable              | Description                      | Default Value           |
|-----------------------|----------------------------------|-------------------------|
| `POSTGRES_USER`       | PostgreSQL username             | `nk-order-user`         |
| `POSTGRES_PASSWORD`   | PostgreSQL password             | `nk-order-pass`         |
| `POSTGRES_DB`         | PostgreSQL database name        | `nk-order-db`           |
| `RABBITMQ_HOST`       | RabbitMQ host                   | `localhost`             |
| `RABBITMQ_PORT`       | RabbitMQ port                   | `5672`                  |
| `SONAR_JDBC_URL`      | SonarQube JDBC URL              | `jdbc:postgresql://db`  |
| `SONAR_JDBC_USERNAME` | SonarQube database username     | `sonar`                 |
| `SONAR_JDBC_PASSWORD` | SonarQube database password     | `sonar`                 |

---

## Contribution Guidelines

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`
3. Commit changes: `git commit -m 'Add new feature'`
4. Push to branch: `git push origin feature-name`
5. Create a Pull Request.

---

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

---

## Contact

For any inquiries or issues, contact the team at [nicolask.pires@gmail.com](mailto:nicolask.pires@gmail.com).

