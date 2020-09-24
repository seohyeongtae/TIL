## Hive 설치 (에코시스템)

> Hive 는 Maria DB를 사용 (오라클은 비싸서)
>
> MariaDB의 구조(컬럼)만 사용하며 모든 데이터는 Datanode에 저장된다.



1. **MariaDb 설치**
   - https://downloads.mariadb.com/MariaDB/mariadb-10.0.15/yum/centos7-amd64/rpms/

     - client, common, server 다운로드



```
yum -y remove mariadb-libs
yum -y localinstall Maria*

systemctl restart mysql
systemctl status mysql
chkconfig mysql on

mysqladmin -u root password '111111'
[root@mainserver 다운로드]# mysql -h localhost -u root -p

MariaDB [mysql]> use mysql;

GRANT ALL ON *.* TO hive@'192.168.111.%' IDENTIFIED BY '111111'; or

GRANT ALL ON *.* TO hive@'127.0.0.1' IDENTIFIED BY '111111';
GRANT ALL ON *.* TO hive@'localhost' IDENTIFIED BY '111111';
GRANT ALL ON *.* TO hive@'192.168.111.120' IDENTIFIED BY '111111';
GRANT ALL ON *.* TO hive@'hadoop' IDENTIFIED BY '111111';
   (127 / local / main  권한 다주기)
   
   
   MariaDB [mysql]> SELECT user,host FROM user;
+------+---------------+
| user | host          |
+------+---------------+
| hive | 127.0.0.1     |
| root | 127.0.0.1     |
| hive | 192.168.111.% |
| root | ::1           |
|      | localhost     |
| hive | localhost     |
| root | localhost     |
|      | mainserver    |
| hive | mainserver    |
| root | mainserver    |
+------+---------------+


MariaDB [mysql]> CREATE DATABASE hive_db CHARACTER SET utf8;
```



2. **HIVE 다운로드**
   
   - wget https://archive.apache.org/dist/hive/hive-1.0.1/apache-hive-1.0.1-bin.tar.gz
   
   - tar xvf apache-hive*
   
   - /usr/local  로 이동 혹은 복사 수업에서 그전에 이름을 hive 로 바꿔서 진행했음
   
   - vi /etc/profile 수정
   
     ```
     JAVA_HOME=/usr/local/jdk1.8.0
     CLASSPATH=/usr/local/jdk1.8.0/lib
     HADOOP_HOME=/usr/local/hadoop-1.2.1
     HIVE_HOME=/usr/local/hive
     export JAVA_HOME CLASSPATH HADOOP_HOME HIVE_HOME
     PATH=$JAVA_HOME/bin:$HADOOP_HOME/bin:$HIVE_HOME/bin:.:$PATH
     ```
   
   - **Mariadb jdbc driver 를 hive 디렉토리 밑 lib에 복사**
   
      cp mariadb-java-client-1.3.5.jar  /usr/local/hive/lib

 