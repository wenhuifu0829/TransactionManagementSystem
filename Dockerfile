# 使用官方的Java 17运行环境作为基础镜像（比JDK镜像更小巧）
FROM eclipse-temurin:17-jre-alpine

# 在容器内创建一个目录来存放应用
WORKDIR /app

# 将Maven打包好的JAR文件复制到容器内
# 注意：这里的文件名需要与你pom.xml中的artifactId和version匹配
COPY target/transaction-management-0.0.1-SNAPSHOT.jar app.jar

# 创建一个非root用户来运行应用（增强安全性）
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# 暴露应用运行的端口
EXPOSE 8080

# 指定容器启动时运行的命令
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# 可以添加一些JVM参数优化（可选）
# ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Xmx512m", "-jar", "/app/app.jar"]