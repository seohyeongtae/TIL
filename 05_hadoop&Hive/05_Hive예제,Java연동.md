## Hive 예제 및 Java에서 실행

### **Hive에 데이터 테이블 작성 및 파일 업로드**

> Hive 에 데이터를 넣기 전에는 구조(테이블)을 먼저 생성해야 한다.

```
hive> CREATE TABLE HDI(id INT, country STRING, hdi FLOAT, lifeex INT, mysch INT, eysch INT, gni INT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE;
    (HDI 테이블 생성)
    
hive>load data local inpath '/root/hdi.txt' OVERWRITE into table HDI;
	(HDI 테이블(구조)에 데이터 load)
	
hive> select * from hdi limit 5;
	(쿼리문 실행)
```

1. **Java Application 연동**

   ```
   [root]#hive --service hiveserver2
   	(Oracle의 1521 port와 같이 항상 열려있는 것이 아니기 때문에 대기상태를 만들어 주어야 한다.)
   ```

   * 필요 라이브러리 Java Project에 추가

     > 관련 jar 파일은 상위 폴더에 있다.

     ```
     1. /usr/local/hive/lib에 있는 몇가지 jar
     
     1) commons-logging-X.jar
     
     2) hive-exec-X.jar
     
     3) hive-jdbc-X.jar
     
     4) hive-jdbc-X-standalone.jar
     
     5) hive-metastore-X.jar
     
     6) hive-service-X.jar
     
     7) libfb303-X.jar
     
     8) log4j-X.jar
     
     2. /usr/local/hadoop-1.2.1/hadoop-core-1.2.1.jar
     ```

     

## Air Data로 보는 쿼리문 예제

```
CREATE TABLE airline_delay(Year INT,
MONTH INT,
DayofMonth INT,
DayofWeek INT,
DepTime INT,
CRSDepTime INT,
ArrTime INT,
CRSArrTime INT,
UniqueCarrier STRING,
FlightNum INT,
TailNum STRING,
ActualElapsedTime INT,
CRSElapsedTime INT,
AirTime INT,
ArrDelay INT,
DepDelay INT,
Origin STRING,
Dest STRING,
Distance INT,
TaxiIn INT,
TaxiOut INT,
Cancelled INT,
CancellationCode STRING
COMMENT 'A = carrier, B = weather, C = NAS, D = security',
Diverted INT COMMENT '1 = yes, 0 = no',
CarrierDelay STRING,
WeatherDelay STRING,
NASDelay STRING,
SecurityDelay STRING, 
LateAircraftDelay STRING) 
COMMENT 'TEST DATA' 
PARTITIONED BY (delayYear INT) 
	(연도별로 파티션을 구분한다. 데이터 조회 및 관리 용의)

ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
	(데이터별 구분을 어떤것으로 하는지 ex)id01,pwd01,1000 )
LINES TERMINATED BY '\n' 
	(Line은 어떤걸로 구분하는지 )
STORED AS TEXTFILE;


hive> LOAD DATA LOCAL INPATH '/root/2006.csv' OVERWRITE INTO TABLE airline_delay PARTITION (delayYear='2006');
hive> LOAD DATA LOCAL INPATH '/root/2007.csv' OVERWRITE INTO TABLE airline_delay PARTITION (delayYear='2007');
hive> LOAD DATA LOCAL INPATH '/root/2008.csv' OVERWRITE INTO TABLE airline_delay PARTITION (delayYear='2008');	
  (overwrite) 를 안쓰면 카피본이 계속 들어간다. 덮어쓰기를 해야함.

hive> SELECT year, month, deptime, arrtime, flightnum FROM airline_delay
    > WHERE delayYear='2006'
    > LIMIT 10;


년도 별 출발지연시간, 도착지연 시간의 평균을 구하시오.
SELECT Year, avg(ArrDelay), avg(DepDelay) FROM airline_delay GROUP BY Year;

2008년 월별 출발 도착 지연시간의 평균을 구하시오
SELECT Year, Month, avg(ArrDelay), avg(DepDelay) FROM airline_delay 
WHERE delayYear='2008' GROUP BY Year, Month ORDER BY Year,Month ;

SELECT Year, Month, avg(ArrDelay), avg(DepDelay) FROM airline_delay 
WHERE delayYear='2008' OR delayYear=2007 GROUP BY Year, Month ORDER BY Year,Month;

SELECT Year, Month, avg(ArrDelay), avg(DepDelay) FROM airline_delay 
WHERE delayYear='2006' GROUP BY Year, Month HAVING Month >= 10;



SELECT Year, avg(ArrDelay), avg(DepDelay) FROM airline_delay 
WHERE delayYear='2006' GROUP BY Year
```

