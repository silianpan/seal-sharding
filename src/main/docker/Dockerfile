FROM livingobjects/jre8
VOLUME /tmp
ADD sharding-0.0.1-SNAPSHOT.jar app.jar
ADD wait-for-it.sh /wait-for-it.sh
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
