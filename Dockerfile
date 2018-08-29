FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
WORKDIR /app
ADD /target/chuck-client-0.1.0.jar /app/app.jar
RUN sh -c 'touch /app/app.jar'
EXPOSE 9090
ENV JAVA_OPTS=""
CMD [ "sh", "-c", "java $JAVA_OPTS -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]