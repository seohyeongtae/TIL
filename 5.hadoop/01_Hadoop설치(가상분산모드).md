### Hadoop 설치 (가상분산모드)  교재 42p~

> 가상분산모드란 한 대의 컴퓨터에서 Namenode / 보조Namenode / Datanode  역할 수행
>
> 설치 순서

1.  **Firewall stop**

   - systemctl stop firewalld

   - systemctl disable firewalld



2. **Hostname 변경**

   - hostnamectl set-hostname server1

   - vi /etc/hosts

   192.168.111.101 server1 



3. **Hadoop 설치**

   - wget https://archive.apache.org/dist/hadoop/common/hadoop-1.2.1/hadoop-1.2.1.tar.gz
- tar xvf hadoop*
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



4. **보안설정**

   >SSH(secure Shell)란 네트워크 상의 다른 컴퓨터에 로그인 하거나 원격 시스템에서 명령을 실행하고, 다른 시스템으로 파일을 복사할 수 있게 해주는 응용 프로토콜이나 응용 프로그램 또는 그 프로토콜을 가르킨다.  암호화 기법을 사용하기 때문에 통신이 노출된다 하더라도 이해할 수 없는 암호화된 문자로 보인다.

   - ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa  (Public Key 생성)

   - cat id_dsa.pub >> authorized_keys 
   
    (자기자신에게 Pub 권한 주기-완전분산모드에서는 따로 지정해주어야 한다.) 



5. **Configuration**

   ```
   # cd /usr/local/hadoop-1.2.1/conf 
   
      # vi ~ 로진행
   ```
*  - **core-site.xml**

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
   
- **hadoop.env.sh**

```
9 export JAVA_HOME=/usr/local/jdk1.8.0
10 export HADOOP_HOME_WARN_SUPPRESS="TRUE"
하둡 운영시 하둡 홈 디렉터리에 손쉽게 접근하기 위해 HADOOP_HOME 을 PATH로 설정하는데
하둡을 구동하는 셀 스크립트에서도 HADOOP_HOME을 정의하기 때문에 Warning 발생 이를 방지 하기 위해 위의 설정 추가
(9,10 라인에 추가)
```



   - **Hadoop 실행**
   
     ```
     hadoop namenedo -format
    
     start-all.sh
     
     jps
     ```
   
     

