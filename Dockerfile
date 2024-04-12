FROM eclipse-temurin:17-alpine

COPY ./target/kouwik-0.0.1-SNAPSHOT.jar .

EXPOSE 8443

CMD ["sh","-c","java -XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=70  -XshowSettings $JAVA_OPTS -jar kouwik-0.0.1-SNAPSHOT.jar"]