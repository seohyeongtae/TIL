### Hadoop 설치 (완전분산모드) 교재 42p~

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

   - scp  /etc/hosts  root@secondserver:/etc/hosts  (hosts 설정한 뒤 복사전송)

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
- tar xvf hadoop*
- cp -r hadoop-1.2.1  /usr/local       에 복사
- vi /etc/profile   (HADOOP_HOME 지정)

```
 /52번 line 밑에다가 추가 (자바와 함께 지정함)
 
JAVA_HOME=/usr/local/jdk1.8.0
CLASSPATH=/usr/local/jdk1.8.0/lib
HADOOP_HOME=/usr/local/hadoop-1.2.1
export JAVA_HOME CLASSPATH  HADOOP_HOME
PATH=$JAVA_HOME/bin:$HADOOP_HOME/bin:.:$PATH
```

- scp  /etc/profile  root@secondserver:/etc/profile   (설정한 profile 복사전송)



6. **Configuration**

```
# cd /usr/local/hadoop-1.2.1/conf 
# vi ~ 로 진행
```

- **core-site.xml**

  ```
  <property>
  
  <name>fs.default.name</name>
  
  <value>hdfs://mainserver:9000</value>
                (Namenode server)
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
  
  <value>2</value> 
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
  
  <value>mainserver:9001</value>
  
  </property>
  ```

- **marsters   (보조Namenode 서버설정)**

  ```
  secondsever (로 수정)
  ```

- **slaves  (Datanode 서버설정)**

  ```
  secondserver
  dataserver (로 수정)
  ```

- **hadoop.env.sh**  (설정안해도 실행은 된다.)

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
  
  jps  - 각 서버별로 지정된 jsp 가 나온다
  ex)
main server
  4327 JobTracker
  4460 Jps
  4158 NameNode
  
  secondserver
  3476 SecondaryNameNode
  3655 Jps
  3550 TaskTracker
  3407 DataNode
  
  
  dataserver
  3313 DataNode
  3393 TaskTracker
  3559 Jps
  
  192.168.111.120:50070  으로 확인
  ```
  



## 가상분산모드로 전환

> 완전분산모드에서 가상분산모드로 전환시키기

```
기존 .ssh authorized_keys 삭제하고 다시 설정하기
ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa
cat id_dsa.pub >> authorized_keys 
#ssh dataserver 확인하기

# cd /usr/local/hadoop-1.2.1/
# rm -rf name tmp  다시 format 하기 위해 삭제 
(data가 없는 이유는 second/dataserver 에만 데이터가 들어가기 때문에)
vi /etc/hosts  - main만 남기고 삭제 

conf 로 들어가기
vi masters
vi slaves  두개 localhost 로 바꾸기 
vi hdfs-site.xml   replication  1로 바꾸기

# hadoop namenode -format
하둡1.2.1 디렉터리 안에  name 생성되었는지 확인

start-all.sh
tmp, data 생성되었는지 확인

jps
8193 SecondaryNameNode
8088 DataNode
8392 TaskTracker
7950 NameNode
8286 JobTracker
8511 Jps

jps 다 생성되었는지 확인

```



### 문제 발생시!!

> Stop 먼저 한뒤 폴더들을 삭제하고 어디에 문제가 있는지 확인 후 다시 처음부터 진행

```
stop-all.sh
name, data, tmp 삭제
trouble shooting

hadoop namenode -format
start-all.sh

```



## 맵리듀스 실행 예시

```

# hadoop fs -mkdir /air
[root@mainserver 다운로드]# hadoop fs -put 2007.csv  /air
[root@mainserver 다운로드]# cd /usr/local/hadoop-1.2.1/  
      (hadoop-examples-1.2.1.jar 있는곳으로 이동) 
[root@mainserver hadoop-1.2.1]# hadoop jar hadoop-examples-1.2.1.jar  wordcount /air   /output
      (wordcount 맵리듀스 동작)   air 폴더 안에있는 것을 output 에 뿌려라

```

