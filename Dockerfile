FROM gradle:7.4.2-jdk17 as gradle
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/gradle/src/build/libs/twitter-0.0.1-SNAPSHOT.jar"]
