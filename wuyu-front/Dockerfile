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

# 使用Node.js基础镜像
FROM node:16-alpine
# 设置工作目录
WORKDIR /app
# 复制项目的package.json和package-lock.json文件
COPY package*.json ./
# 安装依赖
RUN npm install
# 复制项目源代码
COPY . .
# 构建前端项目
RUN npm run build:prod
# 使用Nginx作为静态文件服务器
FROM nginx:1.21-alpine
# 复制配置文件
COPY nginx-conf/default.conf /etc/nginx/conf.d/
# 复制构建好的前端文件到Nginx的默认静态文件目录
COPY --from=0 /app/dist /usr/share/nginx/html
# 暴露前端服务端口
EXPOSE 80
# 启动Nginx服务
CMD ["nginx", "-g", "daemon off;"]
