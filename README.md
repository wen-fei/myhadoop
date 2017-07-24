# myhadoop
## 一、实验环境
#### centos 6.5X64
#### 下载地址：
http://archive.kernel.org/centos-vault/6.5/isos/x86_64/CentOS-6.5-x86_64-minimal.iso
hadoop for partices
## 二、questions list
### 1. 网络问题
1. 主机ping不通虚拟机
2. 虚拟机ping不通主机
3. 虚拟机ping不通网关
#### 解决办法：
1. 虚拟机所有服务是否都正常开启
2. windows的DHCP、net服务是否开启
3. 检查虚拟机和主机的ip地址是否在同一网段，网关是否一致
4. 点击VMWare编辑->虚拟网络编辑器->VMnet8,设置Ip地址和主机同一网段，网关相同
5. 重启服务  service network restart
### 2. 多个虚拟机节点
直接使用系统克隆，无需重新安装多个虚拟机

1. VMware ->虚拟机->管理->克隆
2. 从现有克隆->完整克隆...
3. 克隆之后的操作系统需要重新分配物理地址

	a、删除/etc/sysconfig/network-scripts/ifcfg-eth0 文件中的物理地址

	删除两行：UUID和物理地址

	b、删除文件/etc/udev/rules.d/70-persistent-net.rules
		rm -rf /etc/udev/rules.d/70-persistent-net.rules
4. 修改主机名
   /etc/sysconfig/network文件

5. 重启启动linux: init 6
6. 修改hosts/etc/hosts 文件。

	在文件最后增加一行  ：IP 地址  主机名
### 3. 命令问题
1. man命令不存在：

解决办法： yum install man
## 三、软件安装和管理
### 3.1 安装Nginx
* 下载源码注意下载稳定版[http://nginx.org/en/download.html](das)
* 解压 tar -zxvf 文件名
* 安装编译环境 用 npm grouplist Development tools安装所有编译环境，当然也可以单独安装
* 编译源码 make
* 编译安装 make install
### 3.2 配置yum稳定的源
更换aliyun的源

#### 1. 备份

mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup

#### 2. 下载新的CentOS-Base.repo 到/etc/yum.repos.d/

CentOS 5
 
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-5.repo
	
CentOS 6
	
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-6.repo
	
CentOS 7

wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo

(如果没有wget，也可以直接去后面给的连接去下载下来上传到服务器)
#### 3. 之后运行yum makecache生成缓存

### 3.3 安装java jdk
#### 1. 下载jdk，到官网下载npm的包，方便安装
[http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html]()
#### 2. 用npm命令安装
#### 3. 配置环境变量
* 编辑vi /etc/profile 或者 ~/.bash_profile
* 在文件最后输入

	export JAVA_HOME=/usr/java/jdk1.7.0_71
	
	export PATH=$PATH:$JAVA_HOME/bin
* 生成 source ~/.bash_profile