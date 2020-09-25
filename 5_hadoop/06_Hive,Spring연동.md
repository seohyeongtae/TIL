## HIVE, Spring 연동

>기본적으로 web dynamic project 만든것과 동일하게 진행한다.
>
>web project lib 은 web-inf 밑 lib에다가 넣기 
>
>java 내부에서 test ex(hive 데이터 test)의 경우 Properties 로

Web Dynamic Project -> 

Maven -> pom.xml -> 

Spring nature Add -> 

오라클/하이브 드라이버 web-inf lib 에 복사 -> 

config   spring.xml(필요한부분만 ) <context:component-scan base-package="hive" /> 변경 -> 

Controlle / index 구성 -자세한 사항은 이클립스 참고 Spring  Hive



### Hive 데이터파일 LOAD 시 주의사항

> 되도록 cvs, text 파일로 넣기 csv 는 기본적으로 , 로 구분되어있기 때문에 terminated 가 , 로
> 만약 ""  등 인식하지 못하는 구분자로 되어있는 파일은 전체 바꾸기로 구분자를 바꾸고 넣어야 한다.
>
> 한글로 자료를 받을 경우 hive에 글씨가 깨질 수 있으니 메모장에서 UTF-8 코드로 저장한 다음에 옮겨야 한다. 

* 데이터 로드 및 SELECT문 예시

  ```
  CREATE TABLE PEOPLE (id INT, code INT, man20 FLOAT, man25 FLOAT, man30 FLOAT, woman20 FLOAT, woman25 FLOAT, woman30 FLOAT)
  ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE;
  
  LOAD DATA LOCAL INPATH '/root/people' OVERWRITE INTO TABLE PEOPLE
  
  SELECT code, man25,man30, woman20,woman25 FROM PEOPLE LIMIT 10;
  
  
  
  CREATE TABLE SUBWAY (usedate INT, id STRING, name STRING, station INT, passenger INT)
  ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE;
  
  LOAD DATA LOCAL INPATH '/root/subway' OVERWRITE INTO TABLE SUBWAY;
  
  
  SELECT name, SUM(passenger) FROM SUBWAY 
  WHERE usedate = 20200824
  AND id ='9호선'
  GROUP BY  name
  ;
  ```

  

### Spring 설계

INT STRING FLOAT 잘 구분해서 get 하기
지바내부에서 잘 돌아가는지 확인해보고 차트에 대입하기 

* Hive 데이터 TEST 예시

  ```java
  package d01;
  
  import java.sql.Connection;
  import java.sql.DriverManager;
  import java.sql.PreparedStatement;
  import java.sql.ResultSet;
  
  import org.json.simple.JSONArray;
  import org.json.simple.JSONObject;
  
  public class HiveTest {
  
  	public static void main(String[] args) throws Exception {
  		String url = "jdbc:hive2://192.168.111.120:10000/default";
  		String id = "root";
  		String password = "111111";
  
  		Class.forName("org.apache.hive.jdbc.HiveDriver");
  		Connection con = DriverManager.getConnection(url, id, password);
  		PreparedStatement pstmt = con.prepareStatement("SELECT code, man20,man25, woman20,woman25 FROM PEOPLE LIMIT 10");
  		ResultSet rset = pstmt.executeQuery();
  		
  		
  		// [{name: 'sweden',data[0.94 . 081 11.0]}]
  		JSONArray ja =  new JSONArray();
  		
  		while (rset.next()) {
  
  			JSONObject jo = new JSONObject();
  			jo.put("name", rset.getString(1));
  			JSONArray jo2 = new JSONArray();
  			jo2.add(rset.getString(2));
  			jo2.add(rset.getString(3));
  			jo2.add(rset.getString(4));
  			jo2.add(rset.getString(5));
  			jo.put("data", jo2);   // Object 안에 Array 를 넣는다 . High chart 형식을 맞춰 만들기 위해
  			
  			ja.add(jo);
  		}
  		System.out.println(ja.toJSONString());
  		con.close();
  	}
  
  }
  ```

* ChartController

  > 3가지 차트로 high chart를 이용했으며 각 데이터 형식에 맞게 JSONArray 를 만들어야한다. 
  >
  > Chart1 = HDI TABEL
  >
  > Chart2 = PEOPLE TABEL
  >
  > Chart4 = SUBWAY 
  >
  > 데이터를 이용했다
  >
  > TEST 와 다르게 con.close 를 하기 위해 Try/ Catch를 강제로 만들어서 진행

```java
package hive;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ChartController {
	String url = "jdbc:hive2://192.168.111.120:10000/default";
	String id = "root";
	String password = "111111";
	
	public ChartController() {
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	// 하이차트 데이터 모양 = [{name: 'sweden',data[0.94 . 081 11.0]}] 따라서 ,Object 1개, Array 2개를 이용해야 한다.
	@RequestMapping("/getdata1.mc")
	@ResponseBody
	public void getdata1(HttpServletResponse res) throws Exception {
		Connection con = null;
		JSONArray ja =  new JSONArray();
		
		try {
			con = DriverManager.getConnection(url, id, password);
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM HDI limit 10");
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {

				JSONObject jo = new JSONObject();
				jo.put("name", rset.getString(2));
				JSONArray jo2 = new JSONArray();
				jo2.add(rset.getFloat(3));
				jo2.add(rset.getFloat(4));
				jo2.add(rset.getFloat(5));
				jo.put("data", jo2);
				
				ja.add(jo);
			}
			
		}catch(Exception e) {
			throw e;
		}finally {
			con.close();
		}
	        res.setCharacterEncoding("UTF-8");
	        res.setContentType("application/json");
	        PrintWriter out = res.getWriter();
	        out.print(ja.toJSONString());
	        out.close();
	};
	
	@RequestMapping("/getdata2.mc")
	@ResponseBody
	public void getdata2(HttpServletResponse res) throws Exception {
		Connection con = null;
		JSONArray ja =  new JSONArray();
		try {
			con = DriverManager.getConnection(url, id, password);
			PreparedStatement pstmt = con.prepareStatement("SELECT code, man25,man30, woman20,woman25 FROM PEOPLE LIMIT 10");
			ResultSet rset = pstmt.executeQuery();

			while (rset.next()) {

				JSONObject jo = new JSONObject();
				jo.put("name", rset.getString(1));
				JSONArray jo2 = new JSONArray();
				jo2.add(rset.getFloat(2));
				jo2.add(rset.getFloat(3));
				jo2.add(rset.getFloat(4));
				jo2.add(rset.getFloat(5));
				jo.put("data", jo2);
				
				ja.add(jo);
			}
			
		}catch(Exception e) {
			throw e;
		}finally {
			con.close();
		}
	        res.setCharacterEncoding("UTF-8");
	        res.setContentType("application/json");
	        PrintWriter out = res.getWriter();
	        out.print(ja.toJSONString());
	        out.close();
	};
	
	// 이 하이차트 데이터 모양은 [역이름,탑승객수],[역이름,탑승객수],[역이름,탑승객수],[역이름,탑승객수] 이 모양이므로 아래와 같이  JSONArray 를 JSONArray 안으로 더해야한다.
	@RequestMapping("/getdata3.mc")
	@ResponseBody
	public void getdata3(HttpServletResponse res) throws Exception {
		Connection con = null;
		JSONArray ja =  new JSONArray();
		
		try {
			con = DriverManager.getConnection(url, id, password);
			PreparedStatement pstmt = con.prepareStatement("SELECT name, SUM(passenger) FROM SUBWAY WHERE usedate = 20200824 AND id ='9호선' GROUP BY  name");
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {

				JSONArray jo2 = new JSONArray();
				jo2.add(rset.getString(1));
				jo2.add(rset.getInt(2));
				
				ja.add(jo2);
			}
		}catch(Exception e) {
			throw e;
		}finally {
			con.close();
		}
		System.out.println(ja.toJSONString());
	        res.setCharacterEncoding("UTF-8");
	        res.setContentType("application/json");
	        PrintWriter out = res.getWriter();
	        out.print(ja.toJSONString());
	        out.close();
	};
}

```

* view > main.jsp

  > jstl 를 쓰기 위해 추가
  >
  > highcart 쓰기위해 script 추가
  >
  > Ajax 쓰기위해 추가

  ```
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
      
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
  <!DOCTYPE html>
  <html>
  
  
  <head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  <script src="https://code.highcharts.com/highcharts.js"></script>
  <script src="https://code.highcharts.com/modules/series-label.js"></script>
  <script src="https://code.highcharts.com/modules/exporting.js"></script>
  <script src="https://code.highcharts.com/modules/export-data.js"></script>
  <script src="https://code.highcharts.com/modules/accessibility.js"></script>
  <script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  
  </head>
  <body>
  <h1>Main Page</h1>
  <h3><a href="chart1.mc">Chart1</a></h3>
  <h3><a href="chart2.mc">Chart2</a></h3>
  <h3><a href="chart3.mc">Chart3</a></h3>
  
  	<c:choose>
  		<c:when test="${centerpage == null }">
  			<jsp:include page="center.jsp"></jsp:include>
  		</c:when>
  		<c:otherwise>
  			<jsp:include page="${centerpage}.jsp"></jsp:include>
  		</c:otherwise>
  		
  	</c:choose>
  </body>
  </html>
  ```

* char2 /char3  jsp

  > 데이터 입력에 해당하는  series: data, 이 부분만 우선 변경하였다. (형식에 맞게 데이터를 불러온 뒤)

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#container{
	width:800px;
	height:500px;
	border:2px solid red;
}
</style>

<script>
function display(data){
	Highcharts.chart('container', {
	    chart: {
	        type: 'line'
	    },
	    title: {
	        text: '행정동 별 서울생활 인구'
	    },
	    subtitle: {
	        text: 'Source: data.seoul.go.kr'
	    },
	    xAxis: {
	        categories: ['남자20~24세', '남자25~29세', '여자20~24세', '여자25~29세', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	    },
	    yAxis: {
	        title: {
	            text: '생활인구 (백명)'
	        }
	    },
	    plotOptions: {
	        line: {
	            dataLabels: {
	                enabled: true
	            },
	            enableMouseTracking: false
	        }
	    },
	    series: data,
	});
};

function getData(){
	$.ajax({
		url: 'getdata2.mc',
		success:function(data){
			display(data);
		},
		error:function(){}
	});
};

$(document).ready(function(){
	display();
    getData(); 
	
});
</script>

<h1>chart2</h1>
<div id="container">

</div>
```

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script>

function display(datas){
	Highcharts.chart('container', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: '20200824 9호선 역 별 총 탑승객 수'
	    },
	    subtitle: {
	        text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
	    },
	    xAxis: {
	        type: 'category',
	        labels: {
	            rotation: -45,
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '총 탑승객 수'
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    tooltip: {
	        pointFormat: 'Population in 2017: <b>{point.y:.1f} millions</b>'
	    },
	    series: [{
	        name: 'Population',
	        data:datas,
	        
	        dataLabels: {
	            enabled: true,
	            rotation: -90,
	            color: '#FFFFFF',
	            align: 'right',
	            format: '{point.y:.1f}', // one decimal
	            y: 10, // 10 pixels down from the top
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    }]
	});
};


 function getData(){
	$.ajax({
		url: 'getdata3.mc',
		success:function(data){
			display(data);
			alert(data);
		},
		error:function(){}
		
	});
};

$(document).ready(function(){
	display();
    getData(); 
	
});

</script>    
    
<style>
#container{
	width:800px;
	height:500px;
	border:2px solid red;
}
</style>
<h1>chart3</h1>
<div id="container">

</div>
```



