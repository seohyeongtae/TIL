## Hive 설치 (에코시스템)

> Hive 는 Maria DB를 사용 (오라클은 비싸서)
>
> MariaDB의 구조(컬럼)만 사용하며 모든 데이터는 Datanode에 저장된다.



### MariaDb

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



### HIVE설치 및 MariaDB 연동

2. **HIVE 다운로드 및 설치**

   - wget https://archive.apache.org/dist/hive/hive-1.0.1/apache-hive-1.0.1-bin.tar.gz

   - tar xvf apache-hive*

   - /usr/local  로 이동 혹은 복사 수업에서 그전에 이름을 hive 로 바꿔서 진행했음

   - vi /etc/profile 수정

     > 환경변수 설정 이후에는 reboot 을 해야 적용이 된다.

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

     

   - **hive-site.xml  생성**

     > cd /usr/local/hive/conf  
     >
     > vi hive-site.xml

     ```
     <?xml version="1.0"?>
     <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
     <configuration>
         <property>
             <name>hive.metastore.local</name>
             <value>false</value>
             <description>controls whether to connect to remove metastore server or open a new metastore server in Hive Client JVM</description>
         </property>
         <property>
             <name>javax.jdo.option.ConnectionURL</name>
             <value>jdbc:mariadb://localhost:3306/hive_db?createDatabaseIfNotExist=true</value>
             <description>JDBC connect string for a JDBC metastore</description>
         </property>
         <property>
             <name>javax.jdo.option.ConnectionDriverName</name>
             <value>org.mariadb.jdbc.Driver</value>
             <description>Driver class name for a JDBC metastore</description>
         </property>
         <property>
             <name>javax.jdo.option.ConnectionUserName</name>
             <value>hive</value>
             <description>username to use against metastore database</description>
         </property>
         <property>
             <name>javax.jdo.option.ConnectionPassword</name>
             <value>111111</value>
             <description>password to use against metastore database</description>
         </property>
     </configuration>
     
     # hive 및 111111 은 해당 ID 및 PWD 에 맞게 변경
     ```

     

   * Hive 디렉토리 세팅

     > Hive 실행 전 반드시 Hadoop 세팅을 완료 해야 한다.

     ```
     start-all.sh
     
     hadoop fs -mkdir /tmp
     hadoop fs -mkdir /user/root/warehouse
     hadoop fs -mkdir /tmp/hive
     hadoop fs -chmod 777 /tmp
     hadoop fs -chmod 777 /user/root/warehouse
     hadoop fs -chmod 777 /tmp/hive
     
     ```

     