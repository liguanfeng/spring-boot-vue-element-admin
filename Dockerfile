FROM openjdk:8-jdk-alpine
MAINTAINER li.guan.feng@qq.com
VOLUME /var/logs
RUN mkdir -p /var/logs
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
