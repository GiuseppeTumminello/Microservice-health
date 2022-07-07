FROM gradle:7.4-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:11-jre-slim
EXPOSE 8088
COPY --from=build /home/gradle/src/build/libs/*.jar ./
ENTRYPOINT ["java", "-jar", "Health-0.0.1-SNAPSHOT.jar"]
CMD java $JAVA_OPTIONS -jar Health-0.0.1-SNAPSHOT.jar