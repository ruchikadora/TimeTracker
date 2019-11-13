FROM tomcat:9.0.1-jre8-alpine
ARG DEPENDENCY=target/dependency
ADD /target/employeetracker1-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]
EXPOSE 8081
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/tomcat/webapps/employeetracker1-0.0.1-SNAPSHOT.war" ]
