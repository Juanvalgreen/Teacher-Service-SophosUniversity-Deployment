FROM openjdk:17-alpine

LABEL author="Juan Valverde"

WORKDIR /app

COPY target/SophosUniversity-0.0.1-SNAPSHOT.jar sophos-uni-teacher-ms.jar

EXPOSE 9004

ENTRYPOINT ["java", "-jar", "sophos-uni-teacher-ms.jar"]
