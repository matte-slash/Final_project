FROM openjdk:17 as builder
WORKDIR Room
ARG JAR_FILE=target/*.jar
EXPOSE 8080
COPY ${JAR_FILE} Room.jar
RUN java -Djarmode=layertools -jar Room.jar extract     #estrae layers

FROM openjdk:17
WORKDIR Room
COPY --from=builder Room/dependencies/ ./
COPY --from=builder Room/spring-boot-loader ./
COPY --from=builder Room/internal-dependencies ./
COPY --from=builder Room/snapshot-dependencies ./
COPY --from=builder Room/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]