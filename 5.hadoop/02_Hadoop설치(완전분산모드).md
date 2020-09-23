### Hadoop 설치 (가상분산모드) 교재 42p~

> 완전분산모드란 각 컴퓨터에 Namenode / 보조Namenode / Datanode 를 지정하여 처리
>
> 설치 순서

1.  **Firewall stop**

   - systemctl stop firewalld

   - systemctl disable firewalld



2. **Hostname 변경**

   - hostnamectl set-hostname server1

   - vi /etc/hosts

   192.168.111.120 mainserver

   192.168.111.121 secondserver

   192.168.111.122 dataserver

   

3. **SSH 세팅  교재 51p~**

   > SSH(secure Shell)란 네트워크 상의 다른 컴퓨터에 로그인 하거나 원격 시스템에서 명령을 실행하고, 다른 시스템으로 파일을 복사할 수 있게 해주는 응용 프로토콜이나 응용 프로그램 또는 그 프로토콜을 가르킨다. 암호화 기법을 사용하기 때문에 통신이 노출된다 하더라도 이해할 수 없는 암호화된 문자로 보인다.

   - ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa  (Public Key 생성)

   - ssh-copy-id -i /root/.ssh/id_dsa.pub  root@dataserver
   
    (모든 서버에 authorized.keys 를 전송해야한다 - 자기 자신서버도 포함) 



4. **SSH 를 이요한 파일 전송 및 Jdk 원격설치**

   - scp  /etc/hosts  root@secondserver:/etc/hosts  (hosts 설정한 뒤 복사)

     ​	scp  ./ (현재있는폴더를 나타냄)

     ```
     scp ./jdk*tar.gz root@secondserver:/root
     (자바파일을 복사)
     ssh root@secondserver  tar xvf  /root/jdk*
     (ssh - 원격실행)
     ssh root@secondserver mv jdk1.8.0_261  jdk1.8.0
     ssh root@secondserver cp -r  /rood/jdk1.8.0   /usr/local
     ```

     

5. **Hadoop 설치**

- wget https://archive.apache.org/dist/hadoop/common/hadoop-1.2.1/hadoop-1.2.1.tar.gz
  
- /usr/local에 복사
  
- vi /etc/profile
  
- HADOOP_HOME 지정

```
 /52번 line 밑에다가 추가 (자바와 함께 지정함)
 
JAVA_HOME=/usr/local/jdk1.8.0
CLASSPATH=/usr/local/jdk1.8.0/lib
HADOOP_HOME=/usr/local/hadoop-1.2.1
export JAVA_HOME CLASSPATH  HADOOP_HOME
PATH=$JAVA_HOME/bin:$HADOOP_HOME/bin:.:$PATH
```





5. **Configuration**

   - **core-site.xml**

     ```
     <property>
     
     <name>fs.default.name</name>
     
     <value>hdfs://localhost:9000</value>
                   (namenode server)
     </property>
     
     <property>
     
     <name>hadoop.tmp.dir</name>
     
     <value>/usr/local/hadoop-1.2.1/tmp</value>
     
     </property>
     ```

   - **hdfs-site.xml**

     ```
     <property>
     
     <name>dfs.replication</name>
     
     <value>1</value> 
       (몇개를 복제할 것인지 - 파일의 신뢰성 확보 파일 및 컴퓨터 손상 대비)
     </property>
     
     <property>
     
     <name>dfs.webhdfs.enabled</name>
     
     <value>true</value>
     
     </property>
     
     <property>
     
     <name>dfs.name.dir</name>
     
     <value>/usr/local/hadoop-1.2.1/name</value>
     
     </property>
     
     <property>
     
     <name>dfs.data.dir</name>
     
     <value>/usr/local/hadoop-1.2.1/data</value>
     
     </property>
     ```

   - **mapred-site.xml**

     ```
     <property>
     
     <name>mapred.job.tracker</name>
     
     <value>localhost:9001</value>
     
     </property>
     ```

   - **Hadoop 실행**

     ```
     hadoop namenedo -format
     
     start-all.sh
     
     jps
     ```

     

