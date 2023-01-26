FROM openjdk:17 as builder
WORKDIR Desk
ARG JAR_FILE=target/*.jar
EXPOSE 8080
COPY ${JAR_FILE} Desk.jar
RUN java -Djarmode=layertools -jar Desk.jar extract     #estrae layers

FROM openjdk:17
WORKDIR Desk
COPY --from=builder Desk/dependencies/ ./
COPY --from=builder Desk/spring-boot-loader ./
COPY --from=builder Desk/internal-dependencies ./
COPY --from=builder Desk/snapshot-dependencies ./
COPY --from=builder Desk/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]