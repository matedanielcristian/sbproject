FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=/build/lib/eskills-0.0.1-SNAPSHOT.jar
RUN ls
COPY build/libs/eskills-0.0.1-SNAPSHOT.jar eskills-0.0.1-SNAPSHOT.jar
RUN ls
ENTRYPOINT ["java","-jar","/eskills-0.0.1-SNAPSHOT.jar"]