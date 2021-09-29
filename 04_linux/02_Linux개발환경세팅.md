##  Linux 개발환경 세팅

* **JDK**

  * download jdk x64

    tar xvf jdk...tar.gz

    mv jdk1.8.0.11.11 jdk1.8.0

    cp -r jdk1.8.0 /usr/local

    java

    /usr/bin/java x

    cd /usr/bin

    ln -s /usr/local/jdk1.8.0/bin/java java

    ls -l java

    

    vi /etc/profile

    

    52 JAVA_HOEM=/usr/local/jdk1.8.0

    CLASSPATH=/usr/local/jdk1.8.0/lib

    export JAVA_HOME CLASSPATH

    PATH=$JAVA_HOME/bin:$PATH:.



* **Eclipse**

  * eclipse download 

    tar xvf eclipse-xxxx.gz

    cp -r eclipse /usr/local

    cd /usr/bin

    ln -s /usr/local/eclipse/eclipse eclipse

    

* **Tomcat**

  * tomcat.apache.org

    tar xvf apachxxx.gz

    cp -r apache-tomcat-xx /usr/local

    cd /usr/local/apache-tomcat-xx

    cd conf

    vi server.xml - 69 -> 80 port

    cd /usr/bin

    ln -s /usr/local/apache-tomcat-9.0.37/bin/startup.sh starttomcat

    ln -s /usr/local/apache-tomcat-9.0.37/bin/shutdown.sh stoptomcat

    

* **Oracle**

  * oracle express edition 11g - download

    unzip oraclexxxx.zip

    cd Disk1

    yum -y localinstall oraclxxx.rpm

    service oracle-xe configure

    p618 

    systemctl restart oracle-xe

    systemctl status oracle-xe

    p619 2-4, 2-5

    

* MariaDB

  * download

    [링크](https://downloads.mariadb.com/MariaDB/mariadb-10.0.15/yum/centos7-amd64/rpms/)

    \- client, common, server  다운

    

    yum -y remove mariadb-libs

    yum -y localinstall Maria*

    

    systemctl restart mysql

    systemctl status mysql

    chkconfig mysql on

    

    mysqladmin -u root password '111111'

    

    mysql -h localhost  -u  root  -p

    

    use mysql;

    

    SELECT  user, host  FROM  user;

    

    GRANT   ALL   ON   *.*  TO   user1@'192.168.111.%'  IDENTIFIED  BY  '111111';

    

    CREATE   DATABASE   shopdb   CHARACTER   SET   utf8;

    

    use   shoppdb

    

  * mariadb Driver Download  파일폴더에있음 (이클립스에 넣어야함 Oracle jar 처럼)

    * Class.forName("org.mariadb.jdbc.Driver");

      String dbUrl = "jdbc:mariadb://localhost:3306/shopdb";

      string dbUser = "username";

      string dbPasswd = "passwd";

      Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);

