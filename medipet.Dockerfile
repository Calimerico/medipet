FROM openjdk:13-alpine
RUN apk update && apk add bash
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ADD ./target/medipet-0.0.1-SNAPSHOT.jar medipet.jar
EXPOSE 8080
CMD ["/wait-for-it.sh", "postgres:5432", "camunda:8081","elasticsearch:9200", "--", "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /medipet.jar"]