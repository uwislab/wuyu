# 构建阶段
FROM maven:3.8.6-openjdk-8 AS build
#FROM registry.cn-hangzhou.aliyuncs.com/maven:3.8.6-openjdk-8 AS build
#FROM hub-mirror.c.163.com/maven:3.8.6-openjdk-8 AS build
#FROM swr.cn-north-4.myhuaweicloud.com/public/maven:3.8.6-openjdk-8 AS build
WORKDIR /app
COPY . .
# 添加阿里云镜像加速构建
RUN mvn clean package -DskipTests -s /usr/share/maven/ref/settings-docker.xml

# 运行时阶段
FROM openjdk:8-jdk
#FROM registry.cn-hangzhou.aliyuncs.com/openjdk:8-jdk
#FROM hub-mirror.c.163.com/openjdk:8-jdk
#FROM swr.cn-north-4.myhuaweicloud.com/public/openjdk:8-jdk
COPY --from=build /app/target/*.jar app.jar

# 添加时区配置（解决时区相关异常）
# RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# EXPOSE 9080
# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' > /etc/timezone

# 设置环境变量
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Dspring.profiles.active=prod"

# 暴露端口（与application.yml中的server.port一致）
EXPOSE 9080

# 添加健康检查
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:9080/actuator/health || exit 1

# 优化启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]

## 构建阶段
#FROM maven:3.8.6-jdk-8 AS build
#WORKDIR /app
#COPY . .
## 添加阿里云镜像加速构建
#RUN mvn clean package -DskipTests -s /usr/share/maven/ref/settings-docker.xml
#
## 运行时阶段
#FROM openjdk:8-jdk
#COPY --from=build /app/target/*.jar app.jar
#
## 添加时区配置（解决时区相关异常）
#RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
#EXPOSE 9080
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
