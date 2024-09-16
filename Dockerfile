FROM openjdk:21

ARG FILE_JAR=target/trendtySchool-0.0.1-SNAPSHOT.jar

ADD ${FILE_JAR} trendty-school.jar

ENTRYPOINT ["java", "-jar", "trendty-school.jar"]

EXPOSE 8080