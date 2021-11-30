FROM heroku/heroku:20

RUN apt-get update && apt-get install -y maven

ADD . /app
WORKDIR /app

RUN mvn dependency:resolve