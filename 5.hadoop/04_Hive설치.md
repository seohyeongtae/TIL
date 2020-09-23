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

GRANT ALL ON *.* TO hive@'192.168.111.%' IDENTIFIED BY '111111';
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