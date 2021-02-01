FROM tomcat:9.0-jdk11-openjdk
MAINTAINER talgat.bkk@gmail.com
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY /out/artifacts/AdPlatform/AdPlatform.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]