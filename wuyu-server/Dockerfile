# 构建阶段
FROM maven:3.8.6-jdk-8 AS build
WORKDIR /app
COPY . .
# 添加阿里云镜像加速构建
RUN mvn clean package -DskipTests -s /usr/share/maven/ref/settings-docker.xml

# 运行时阶段
FROM openjdk:8-jdk
COPY --from=build /app/target/*.jar app.jar
# 添加时区配置（解决时区相关异常）
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
EXPOSE 9080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
