FROM adoptopenjdk/openjdk11:alpine-jre as builder

WORKDIR /app

COPY target/svc-ventas-0.0.1-SNAPSHOT.jar /app/svc-ventas.jar

ENTRYPOINT ["java", "-jar", "svc-ventas.jar"]