
From tomcat:8.0.51-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/chatmessageservice-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/chatmessageservice-0.0.1-SNAPSHOT.jar
CMD ["catalina.sh","run"]
