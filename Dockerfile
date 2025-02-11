FROM maven:3-amazoncorretto-21 as build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests \
    && rm -rf /root/.m2

FROM ubuntu:latest

RUN apt-get update -qq && apt-get install -y \
    openjdk-21-jdk \
    ffmpeg \
    bash \
    libx264-dev \
    libmp3lame-dev \
    libopus-dev \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /app/target/clipshot-video-processor.jar .

CMD ["java", "-jar", "clipshot-video-processor.jar"]