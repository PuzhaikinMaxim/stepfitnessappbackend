FROM openjdk:11
ADD 'stepfitnessapp-SNAPSHOT.jar' 'stepfitnessappbackend.jar'
ENTRYPOINT ["java", "-jar", "stepfitnessappbackend.jar"]