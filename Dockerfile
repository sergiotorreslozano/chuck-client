FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD /target/chuck-client-0.1.0.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 9090
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]