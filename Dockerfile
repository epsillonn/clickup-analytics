FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace
COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle gradle
RUN --mount=type=cache,target=/root/.gradle ./gradlew dependencies --no-daemon > /dev/null
COPY src src
RUN --mount=type=cache,target=/root/.gradle ./gradlew bootJar --no-daemon
RUN java -Djarmode=tools -jar build/libs/app.jar extract --layers --destination extracted

FROM eclipse-temurin:25-jre
WORKDIR /app
RUN useradd --system --no-create-home spring
USER spring
COPY --from=build /workspace/extracted/dependencies/ ./
COPY --from=build /workspace/extracted/snapshot-dependencies/ ./
COPY --from=build /workspace/extracted/application/ ./
EXPOSE 8080
HEALTHCHECK --interval=10s --timeout=3s --retries=3 --start-period=30s \
    CMD ["bash", "-c", "exec 3<>/dev/tcp/localhost/8080 && printf 'GET /actuator/health HTTP/1.0\\r\\n\\r\\n' >&3 && head -1 <&3 | grep -q ' 200 '"]
ENTRYPOINT ["java", "-jar", "app.jar"]
