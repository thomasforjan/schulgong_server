![Logo Schulgong](https://i.gyazo.com/223cf8e9473bfbd2f957b76e44c206f2.png)

# :pushpin: General information

Schulgong is an innovative project aimed at simplifying school notifications and announcements. It bridges the gap between the traditional school bell system and audio announcements, providing a centralized solution for both. With the Schulgong web application, users can easily set the times for the school bell and even broadcast important announcements through network speakers. Just record the message via any device, send it, and it will be played across the school's speakers in no time. The best part? Schulgong is open-source and completely free to use.

## :sparkles: Features

- **Centralized Gong System:** Set the times for the school bell to ring, ensuring everyone knows when it's time for a break.

- **Instant Announcements:** Record and broadcast messages instantly. Whether it's an important update or just a daily announcement, make sure everyone hears it

- **Open Source:** Modify, contribute, or simply explore the code. Schulgong is open to everyone.

## Prerequisites

- [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Gradle](https://gradle.org/install/)

## Getting Started

1. **Clone the Repository**

```bash
git clone https://github.com/thomasforjan/schulgong_server.git
cd schulgong_server
```

2. **Build the application**

```bash
./gradlew build
```

3. **Run the Application**

```bash
./gradlew bootRun
```

The application will start, and by default, you can access it at <http://localhost:8080>.

## :nut_and_bolt: Tech Stack & Libraries

### Build Tool

- [Gradle](https://gradle.org/)

### Language

- Java (source compatibility: Java 17)

### Core Framework

- [Spring Boot](https://spring.io/projects/spring-boot) - version 3.0.6

### Plugins

- [SpotBugs](https://spotbugs.github.io/) - Static analysis tool for Java
- [Spotless](https://github.com/diffplug/spotless) - Code formatter and linter

### Libraries

- Spring Data JPA - version 3.0.6
- [Lombok](https://projectlombok.org/) - version 1.18.22
- [JUnit 5](https://junit.org/junit5/) (jupiter-api & jupiter-engine) - version 5.8.1
- [MariaDB JDBC Client](https://mariadb.com/kb/en/mariadb-connector-j/) - version 3.1.3
- [Hibernate Validator](https://hibernate.org/validator/) - version 8.0.0.Final
- [Jakarta Persistence API](https://jakarta.ee/specifications/persistence/3.1/) - version 3.1.0
- [Springdoc OpenAPI UI](https://springdoc.org/) - version 1.6.15
- [Jackson](https://github.com/FasterXML/jackson) (jackson-datatype-jsr310 & jackson-databind) - version 2.15.0
- [MySQL Connector/J](https://dev.mysql.com/doc/connector-j/en/) - version 8.0.33
- [JAVE (Java Audio Video Encoder) Library](https://github.com/a-schild/jave2) - version 3.3.1
- [Java JWT (JSON Web Token)](https://github.com/jwtk/jjwt) - version 0.11.5
- [Spring Security Crypto](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#crypto) - version 6.1.0
- [Bouncy Castle Java Distribution](https://www.bouncycastle.org/java.html) - version 1.70
- [Spring Boot Starter Security](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-security) - version 3.1.0


## :busts_in_silhouette: Contributing

We welcome contributions! If you find a bug, have a feature request, or want to improve the application in any way, feel free to open an issue or submit a pull request.

## License

This project is licensed under the MIT License. Feel free to use, modify, and distribute the code as you see fit.

---

Happy coding! If you have any questions or feedback, feel free to reach out or open an issue.
