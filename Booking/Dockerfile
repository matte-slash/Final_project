FROM openjdk:17 as builder
WORKDIR Booking
ARG JAR_FILE=target/*.jar
EXPOSE 8080
COPY ${JAR_FILE} Booking.jar
RUN java -Djarmode=layertools -jar Booking.jar extract     #estrae layers

FROM openjdk:17
WORKDIR Booking
COPY --from=builder Booking/dependencies/ ./
COPY --from=builder Booking/spring-boot-loader ./
COPY --from=builder Booking/internal-dependencies ./
COPY --from=builder Booking/snapshot-dependencies ./
COPY --from=builder Booking/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]