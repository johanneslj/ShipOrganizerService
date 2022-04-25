FROM openjdk:17-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8080
ARG DEPENDENCY=/workspace/app/target/dependency
ARG MAINAPP=src/main/java/no.ntnu.idata.shiporganizer.shiporganizerservice.ShipOrganizerServiceApplication
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8080
ENTRYPOINT ["./wait-for-it.sh", "mysqldb:3306","java","-cp","app:app/lib/*", "no.ntnu.idata.shiporganizer.shiporganizerservice.ShipOrganizerServiceApplication"]