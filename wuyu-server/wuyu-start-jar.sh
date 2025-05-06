#/bin/bash
#Author weiss
#jar包启动脚本

#目录变量定义区
#echo $url_first
#变量：项目基本路径
url_base='/var/www/wuyu'
#变量：jar包存放路径
url_jar=$url_base/jar
#变量：日志文件存放路径
url_log=$url_base/log
# 进程号暂存文件(写死，无需传参)
tmp_file="pid.tmp"
mkdir $url_log


  #进入jar包存放目录
  cd $url_jar
  #获取jar包名
  name_jar=`ls *.jar`	

  #1  检查是否有jar包正在运行
  #服务器上启动的服务可能会有多个
  #变量pids，转换成数组，存放所有服务的pid
  #将自带换行符的数据暂存进文件
  ps aux | grep -e '.jar$' |awk '{print $2}' > $tmp_file
  #变量暂存空格字符串，下面的命令可以将换行符替换成空分隔
  tmp=`cat $tmp_file | xargs`
  #删除临时文件
  rm -rf "$tmp_file"

  if [ "$tmp" != "" ]; then
    #对IFS变量 进行替换处理
    OLD_IFS="$IFS"
    IFS=" "
    pids=($tmp)
    IFS="$OLD_IFS"

    for var in ${pids[@]}
    do
      kill -s 9 $var
      echo "关闭服务: $var"
    done
    echo "重启服务..."
  else
    echo "第一次启动服务"
  fi

  #启动服务
  nohup java -jar $url_jar/$name_jar>$url_log/${name_jar%-0*}.log 2>&1 &
  
  #检测服务是否启动成功
  #再次获取进程pid
  #变量pids，转换成数组，存放所有服务的pid
  #将自带换行符的数据暂存进文件
  ps aux | grep -e '.jar$' |awk '{print $2}' > $tmp_file
  #变量暂存空格字符串，下面的命令可以将换行符替换成空分隔
  tmp=`cat $tmp_file | xargs`
  #删除临时文件
  rm -rf "$tmp_file"

  if [ "$tmp" != "" ]; then
    #对IFS变量 进行替换处理
    OLD_IFS="$IFS"
    IFS=" "
    pids=($tmp)
    IFS="$OLD_IFS"

    echo "服务启动成功。进程号为 = "$pids
    for var in ${pids[@]}
    do
      echo "服务进程号: $var"
    done
  else
    echo $tmp
    echo "服务启动失败。请于 "$url_log/${name_jar%-0*}".log 查看日志信息"
  fi
