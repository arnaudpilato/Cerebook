FROM heroku/heroku:20

RUN apt-get update
RUN apt-get install -y maven

RUN mkdir /app
ADD pom.xml /app/pom.xml
WORKDIR /app

RUN mvn dependency:resolve

ADD . /app
RUN mvn install

CMD mvn spring-boot:run