FROM gradle:8.10.0-jdk21-jammy AS build-env

WORKDIR /src/

COPY ./ ./
RUN gradle :dhim2228-spring:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

WORKDIR /usr/app

COPY --from=build-env /src/dhim2228-spring/build/libs/dhim2228-spring.jar ./

CMD java -jar dhim2228-spring.jar