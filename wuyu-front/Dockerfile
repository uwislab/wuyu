# 构建阶段 - 使用Docker Hub官方Node.js镜像
FROM node:18 AS build
WORKDIR /app

# 先复制package.json和package-lock.json，利用缓存加速依赖安装
COPY package*.json ./

# 配置npm使用淘宝镜像源并安装依赖（添加错误处理）
RUN npm config set registry https://registry.npmmirror.com && \
    npm install --production=false --no-fund --no-audit || \
    (echo "npm安装失败，检查package.json" && exit 1) && \
    npm cache clean --force

# 复制源代码并构建
COPY . .
RUN npm run build || (echo "前端构建失败，检查构建脚本" && exit 1)

# 运行时阶段 - 使用Docker Hub官方Nginx镜像
FROM nginx:alpine

# 复制构建产物到Nginx文档根目录
COPY --from=build /app/dist /usr/share/nginx/html

# 替换默认Nginx配置（可选，若有自定义配置）
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露端口
EXPOSE 80

# 使用更安全的启动方式
CMD ["nginx", "-g", "daemon off;"]
