# Ship Organizer Service for

This project is the result of a bachelor thesis at NTNU Ålesund. <br>
The Ship Organizer Service is the backend system for the Sea Storage application developed for Giske Kystfiske.

## Deployment

The Spring Boot application is meant to run using the included docker compose file. <br>
You can run the project locally by running the docker compose file.
This creates the server and MySQL database. 

The server runs on port 6868
## Requirements
Computer with functionality to run a Docker server.<br>
Docker can be on Windows, macOS or Linux

Installment of docker found here: https://docs.docker.com/get-docker/

## Technologies
<ul>
<li>Docker</li>
<li>Spring Boot</li>
<li>Maven</li>
<li>Spring Security</li>
<li>MySQL</li>
</ul>

## Src folder

```
├── src
│   ├── Auth <-- Jwt Authentication filter
│   ├── Config <-- Configuration files
│   ├── Controller <-- Contains all controller classes 
│   ├── Model <-- Contains all entity classes
│   ├── Repository <-- Contains all repository interfaces
│   ├── Service <-- Contains all service classes
│   ├── UserPrincipal <-- Contains different user principals
│   └── ShipOrganizerServiceApplication.java <-- main class for project
```

## Application Properties

### These application properties are needed to configure the server.

spring.datasource.url=<br>
spring.datasource.username=<br>
spring.datasource.password=<br>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver<br>
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect<br>
spring.jpa.show-sql=true<br>
spring.jpa.hibernate.ddl-auto=none<br>
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl<br>

### Web properties

spring.servlet.multipart.max-file-size=5MB<br>
spring.servlet.multipart.max-request-size=5MB<br>

### Server properties

server.tomcat.max-http-form-post-size=50000000<br>
server.tomcat.max-swallow-size=50000000<br>

### Mail

spring.mail.host=<br>
spring.mail.port=<br>
spring.mail.username=<br>
spring.mail.password=<br>
spring.mail.properties.mail.smtp.auth=true<br>
spring.mail.properties.mail.smtp.ssl.enable=true<br>

### Expiration time 3 weeks

jwt.properties.secretCode=<br>
jwt.properties.expirationTime=1814400000<br>
jwt.properties.tokenPrefix=Bearer<br>
jwt.properties.headerString=Authorization<br>

### Digital Ocean Space properties

do.spaces.access.key=<br>
do.spaces.access.secret=<br>
do.spaces.access.endpoint=<br>
do.spaces.access.region=<br>
do.spaces.access.bucket=<br>

### Superuser Properties

user.properties.name=<br>
user.properties.username=<br>
user.properties.password=<br>

## Authors

<ul>
<li>Hans Andreas Lindgård</li>
<li>Simon Duggal</li>
<li>Johannes Løvold Josefsen</li>
</ul>