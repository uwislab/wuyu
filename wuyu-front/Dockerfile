# FROM node:18 as build
# WORKDIR /app
# COPY package*.json ./
# RUN npm install
# COPY . .
# RUN npm run build

# FROM nginx:alpine
# COPY --from=build /app/dist /usr/share/nginx/html
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]

# 构建阶段
FROM node:18-alpine AS build
WORKDIR /app

# 缓存依赖安装
COPY package*.json ./
#COPY .npmrc ./  # 自定义npm配置
RUN npm install --no-fund --no-audit

# 复制源码并构建
COPY . .
RUN npm run build

# 运行时阶段
FROM nginx:alpine
#LABEL maintainer="your-email@example.com"

# 复制构建产物
COPY --from=build /app/dist /usr/share/nginx/html

# 复制自定义Nginx配置（创建wuyu-front/nginx.conf）
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 添加时区支持
RUN apk add --no-cache tzdata
ENV TZ=Asia/Shanghai

# 暴露端口
EXPOSE 80

# 添加健康检查
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD curl -f http://localhost/ || exit 1

# 使用自定义启动脚本
#COPY docker-entrypoint.sh /
#RUN chmod +x /docker-entrypoint.sh
#ENTRYPOINT ["/docker-entrypoint.sh"]
#CMD ["nginx", "-g", "daemon off;"]
