FROM openjdk:18-jdk-alpine


RUN apk add --no-cache tomcat-native
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH
RUN mkdir -p "$CATALINA_HOME"
WORKDIR $CATALINA_HOME

COPY guerin_conf/tomcat /usr/local/tomcat

EXPOSE 8080

CMD ["catalina.sh", "run"]