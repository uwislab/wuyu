FROM maven:3.8.6-openjdk-8 as build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# 使用单引号包围EOF，避免IDEA误解析
RUN mkdir -p /usr/share/maven/ref && \
    cat > /usr/share/maven/ref/settings-docker.xml << 'EOF' && \
    cat /usr/share/maven/ref/settings-docker.xml  # 可选：验证文件内容
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
  <mirrors>
    <mirror>
      <id>aliyunmaven</id>
      <mirrorOf>*</mirrorOf>
      <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>
</settings>
EOF

RUN mvn clean package -DskipTests -s /usr/share/maven/ref/settings-docker.xml

FROM openjdk:8-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

EXPOSE 9080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]