FROM openjdk:8-jdk
ENV JAVA_HOME              /usr/lib/jvm/java-8-openjdk-amd64
ENV JAVA_OPTS              ""
ENV PATH                   $PATH:$JAVA_HOME/bin
WORKDIR /app
ADD target/*.jar /app/FeedbackSystem-0.0.1-SNAPSHOT.jar
CMD ["/bin/sh", "-c", "java $JAVA_OPTS -Dlog.path=/var/log -jar /app/FeedbackSystem-0.0.1-SNAPSHOT.jar"]
