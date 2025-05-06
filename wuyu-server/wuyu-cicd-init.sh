#
# 定义nginx的html与jar的存放路径
#

wuyu_site_path=/var/www/wuyu

DEPOT_NAME=$1
BRANCH_NAME=$2

echo DEPOT_NAME:$1
echo BRANCH_NAME:$2

# clone一份代码，并且安装node编译需要的包在代码目录中。便于以后可以快速编译。

# 进入cicd下的wuyu工作目录
cd /cicd/wuyu

if [ $DEPOT_NAME == "wuyu-front" ] ; then

# 初始化运行，删除原来的目录
rm -f -r ./wuyu-front

# 重新下载最新的源代码
git clone -b "$BRANCH_NAME" https://e.coding.net/uwiswork/devops/wuyu-front.git

#
## 为了初次编译front前端，安装工作环境与需要的node包
#

cd ./wuyu-front
git pull

# wget -nc "https://coding-public-generic.pkg.coding.net/public/downloads/node-linux-x64.tar.xz?version=v16.13.0" -O node-v16.13.0-linux-x64.tar.xz | true
# tar -xf node-v16.13.0-linux-x64.tar.xz -C /usr --strip-components 1
node -v

#npm下载依赖包
npm install --legacy-peer-deps

# 安装编译需要的包。第一次需要安装，以后可以不再需要
npm install --legacy-peer-deps

npm run build:prod

# 拷贝dist下的所有文件到nginx的static发布目录

# 删除nginx中的前端发布的文件
rm -f -r $wuyu_site_path/html
mkdir $wuyu_site_path/html
cp -r ./dist/* $wuyu_site_path/html
echo "Copy HTML files to: $wuyu_site_path/html OK!"

fi

#######################################################################################################

if [ $DEPOT_NAME == "wuyu-server" ] ; then

# 初始化运行，删除原来的目录
rm -f -r ./wuyu-server

# 重新下载最新的源代码
git clone -b "$BRANCH_NAME" https://e.coding.net/uwiswork/devops/wuyu-server.git

#
## 为了初次编译server后端，安装工作环境与需要的Java包
#

cd /cicd/wuyu

cd ./wuyu-server
git pull
mvn package

# 删除nginx中的前端发布的文件
rm -f -r $wuyu_site_path/jar
mkdir $wuyu_site_path/jar
cp ./target/fiveup-core-0.0.1-SNAPSHOT.jar $wuyu_site_path/jar

echo "Copy jar files to: $wuyu_site_path/jar OK!"

# 重启jar后端服务
/cicd/wuyu/wuyu-start-jar.sh

fi
