#!/bin/bash

# 确保脚本以root权限运行
if [ "$(id -u)" -ne 0 ]; then
    echo -e "\033[0;31m请使用root权限运行此脚本\033[0m" >&2
    exit 1
fi

# 定义颜色输出
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}开始部署应用...${NC}"

# 检查Docker和Docker Compose是否安装
echo -e "${YELLOW}检查Docker环境...${NC}"
if ! command -v docker &> /dev/null; then
    echo -e "${RED}Docker未安装，正在安装...${NC}"
    apt-get update -y
    apt-get install -y docker.io
    systemctl start docker
    systemctl enable docker
    echo -e "${GREEN}Docker安装完成${NC}"
else
    echo -e "${GREEN}Docker已安装，版本: $(docker --version)${NC}"
fi

if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}Docker Compose未安装，正在安装...${NC}"
    # 自动检测系统架构
    ARCH=$(uname -m)
    if [ "$ARCH" = "x86_64" ]; then
        ARCH_SUFFIX="x86_64"
    elif [ "$ARCH" = "aarch64" ] || [ "$ARCH" = "arm64" ]; then
        ARCH_SUFFIX="arm64"
    else
        echo -e "${RED}不支持的架构: $ARCH，请手动安装Docker Compose${NC}"
        exit 1
    fi
    
    curl -L "https://github.com/docker/compose/releases/download/v2.24.5/docker-compose-linux-$ARCH_SUFFIX" -o /usr/local/bin/docker-compose
    chmod +x /usr/local/bin/docker-compose
    ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
    echo -e "${GREEN}Docker Compose安装完成${NC}"
else
    echo -e "${GREEN}Docker Compose已安装，版本: $(docker-compose --version)${NC}"
fi

# 构建并启动容器
echo -e "${YELLOW}构建并启动Docker容器...${NC}"
PROJECT_DIR="/home/zzc/桌面/wuyu/wuyu"

# 检查项目目录是否存在
if [ ! -d "$PROJECT_DIR" ]; then
    echo -e "${RED}项目目录不存在: $PROJECT_DIR${NC}"
    exit 1
fi

cd "$PROJECT_DIR" || exit 1

# 清理旧容器
echo -e "${YELLOW}清理旧容器...${NC}"
docker-compose down

# 构建新容器（带详细日志）
echo -e "${YELLOW}开始构建镜像...${NC}"
docker-compose build --no-cache

# 启动容器
echo -e "${YELLOW}启动服务...${NC}"
docker-compose up -d

# 检查服务启动状态
echo -e "${YELLOW}检查服务状态...${NC}"
SERVICE_STATUS=$(docker-compose ps | grep -v "NAME" | awk '{print $4}')
ALL_RUNNING=true

for STATUS in $SERVICE_STATUS; do
    if [ "$STATUS" != "running" ]; then
        ALL_RUNNING=false
        break
    fi
done

if $ALL_RUNNING; then
    echo -e "${GREEN}所有服务启动成功！${NC}"
else
    echo -e "${RED}部分服务启动失败，查看日志：${NC}"
    docker-compose logs
    exit 1
fi

# 获取IP地址
IP=$(hostname -I | awk '{print $1}')
if [ -z "$IP" ]; then
    IP="127.0.0.1"
    echo -e "${YELLOW}无法获取IP地址，使用本地地址: $IP${NC}"
fi

echo -e "${GREEN}应用部署完成！${NC}"
echo -e "${GREEN}前端访问地址: http://$IP${NC}"
echo -e "${GREEN}后端API地址: http://$IP/api/${NC}"
echo -e "${YELLOW}如需查看日志：docker-compose logs -f${NC}"
