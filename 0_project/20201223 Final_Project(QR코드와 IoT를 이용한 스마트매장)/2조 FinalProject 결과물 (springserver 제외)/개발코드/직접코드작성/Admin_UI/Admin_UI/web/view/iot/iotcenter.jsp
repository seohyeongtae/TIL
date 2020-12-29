<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
  	
	
</head>

<!-- 문, 조명 style -->
<style>

body {
  
  color: #404040;
  
}

.containerbt > table {
  margin: 80px auto;
  max-width: 460px;
  text-align: center;
  
  
}
.containerbt > table > .button {
  margin: 0 12px;
}

.button {
  display: inline-block;
  vertical-align: top;
  height: 48px;
  line-height: 46px;
  padding: 0 25px;
  font-family: inherit;
  font-size: 15px;
  color: #bbb;
  text-align: center;
  text-decoration: none;
  text-shadow: 0 0 2px rgba(0, 0, 0, 0.7);
  background-color: #303030;
  background-clip: padding-box;
  border: 1px solid;
  border-color: #202020 #1a1a1a #111;
  border-radius: 25px;
  background-image: -webkit-linear-gradient(top, #3d3d3d, #272727);
  background-image: -moz-linear-gradient(top, #3d3d3d, #272727);
  background-image: -o-linear-gradient(top, #3d3d3d, #272727);
  background-image: linear-gradient(to bottom, #3d3d3d, #272727);
  -webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.09), 0 1px 3px rgba(0, 0, 0, 0.3);
  box-shadow: inset 0 1px rgba(255, 255, 255, 0.09), 0 1px 3px rgba(0, 0, 0, 0.3);
}
.button:hover {
  background-color: #363636;
  background-image: -webkit-linear-gradient(top, #404040, #2a2a2a);
  background-image: -moz-linear-gradient(top, #404040, #2a2a2a);
  background-image: -o-linear-gradient(top, #404040, #2a2a2a);
  background-image: linear-gradient(to bottom, #404040, #2a2a2a);
}
.button:active, .button.active {
  line-height: 48px;
  color: #ccc;
  background-color: #b42f32;
  border-color: #1c1c1c #202020 #222;
  background-image: -webkit-linear-gradient(top, #a3161a, #b63335 60%, #bf4749);
  background-image: -moz-linear-gradient(top, #a3161a, #b63335 60%, #bf4749);
  background-image: -o-linear-gradient(top, #a3161a, #b63335 60%, #bf4749);
  background-image: linear-gradient(to bottom, #a3161a, #b63335 60%, #bf4749);
  -webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.3), 0 1px rgba(255, 255, 255, 0.09);
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.3), 0 1px rgba(255, 255, 255, 0.09);
}

.button-check {
  position: relative;
  width: 48px;
  padding: 0;
  font: 0/0 serif;
  text-shadow: none;
  color: transparent;
}
.button-check:before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -7px 0 0 -10px;
  height: 5px;
  width: 16px;
  border: solid #bbb;
  border-width: 0 0 5px 5px;
  -webkit-transform: rotate(-45deg);
  -moz-transform: rotate(-45deg);
  -ms-transform: rotate(-45deg);
  -o-transform: rotate(-45deg);
  transform: rotate(-45deg);
  -webkit-box-shadow: -1px 1px 1px rgba(0, 0, 0, 0.4);
  box-shadow: -1px 1px 1px rgba(0, 0, 0, 0.4);
}
.button-check:active:before, .button-check.active:before {
  margin-top: -6px;
  border-color: #ccc;
  -webkit-box-shadow: -1px 1px rgba(0, 0, 0, 0.3);
  box-shadow: -1px 1px rgba(0, 0, 0, 0.3);
}

.lt-ie9 .button {
  line-height: 46px;
}
.lt-ie9 .button-check:before {
  content: none;
}
.lt-ie9 .button-check:after {
  /* a.k.a. the Unicode version */
  content: '\2713';
  font-size: 24px;
  font-weight: bold;
  color: #bbb;
}


</style>

<!-- highcharts 스타일 -->
<style>
.highcharts-figure, .highcharts-data-table table {
    min-width: 360px; 
    max-width: 800px;
    margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #EBEBEB;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 500px;
}
.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}
.highcharts-data-table th {
	font-weight: 600;
    padding: 0.5em;
}
.highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
    padding: 0.5em;
}
.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}
.highcharts-data-table tr:hover {
    background: #f1f7ff;
}

.ld-label {
	width:200px;
	display: inline-block;
}

.ld-url-input {
	width: 500px; 
}

.ld-time-input {
	width: 40px;
}

</style>





<!-- 문, 조명  스크립트-->
<script>
	
	

function getIot(){
	  $.ajax({
          url:'getiot.mc',
          dataType:"json",
        	
      		success:function(data){
      			display(data);
      			console.log(data+"Data");		
      		},
      		error:function(){}
      	});
      };
	var x = 0;
	var y = 0;
	
	function display(data){
	
		$(data).each(function(index,menu){
			var light = menu.light;
			var door = menu.door;
			var temp = menu.temperature;
			
			
			if(door==0){
				$("#door").text('출입문 닫힘');
				$("#door").attr('class','button');
				x = 0;
			}
			if(door==1){
				$("#door").text('출입문 열림');
				$("#door").attr('class','button active');
				x = 1;
			}
			
			if(light==0){
				$("#light").text('조명 꺼짐');
				$("#light").attr('class','button');
				y = 0;
			}
			if(light==1){
				$("#light").text('조명 켜짐');
				$("#light").attr('class','button active');
				y = 1;
			}
			$("#ctemp").text(temp);
			
		});
		
	} // display data end
	
	
	
	function chageclass(num){
		var data = num;
				
		if(num==1){
			var id = "dooron";
			if(x==0){
				
				$("#door").text('출입문 열림');
				$("#door").attr('class','button active');
				x = 1;
		   	    alert("출입문이 열렸습니다.");
				  $.ajax({
		              url:'iotsys.mc',
		      		data : {
						id : id
					},
		              success: function(){
		        
		                  } 
		              
		   			   });
				return;
			}
			if(x==1){
				var id = "dooroff";
				$("#door").text('출입문 닫힘');
				$("#door").attr('class','button');
				x = 0;
	     	    alert("출입문이 닫혔습니다.");
				  $.ajax({
		              url:'iotsys.mc',
		          	data : {
						id : id
					},
		              success: function(){
		      
		                  } 
		              
		   			   });
				return;
			}
		};

		if(num==2){
			if(y==0){
				var id = "lighton";
				$("#light").text('조명 켜짐');
				$("#light").attr('class','button active');
				y = 1;
		   	    alert("조명이 켜졌습니다.");
				  $.ajax({
		              url:'iotsys.mc',
		           	data : {
						id : id
					},
		              success: function(){
		        
		                  } 
		              
		   			   });
				return;
			}
			if(y==1){
				var id = "lightoff";
				$("#light").text('조명 꺼짐');
				$("#light").attr('class','button');
				y = 0;
        	    alert("조명이 꺼졌습니다.");
				  $.ajax({
		              url:'iotsys.mc', 
		           	data : {
						id : id
					},
		              success: function(){
		   
		                  } 
		              
		   			   });
				return;
			}
		}
		
		
		if(num == 3){
			var id = $("#htemp").val();
			   alert(id);
			   
			  $.ajax({
		              url:'iotsys.mc', 
		           	data : {
						id : id
					},
		              success: function(){
		   
		                  } 
		              
		   			   });
				return; 
		}
		
	}; // changeclass end
	
	
	$(document).ready(function(){
		getIot();
	});

</script>


<!-- chart1 (날씨API) 스크립트 -->
<script>
	
	var hourdate = new Array();
	var tempdate = new Array();
	
	
	function getDatach1(){
		  $.ajax({
              // 클릭한 탭 버튼에 XML 데이터 경로 주소를 가져온다.
              url:'https://api.openweathermap.org/data/2.5/onecall?lat=37.509653&lon=127.022339&lang=kr&exclude=minutely&units=metric&appid=d9cb11905741c4f2ce5290f209972f1d',
              // 요청할 데이터의 타입
              dataType:"json",
              data:{

                  // 전송 받을 데이터의 개수
               
              },
              success: function(data){
                  console.log(data.hourly);
                 for(i in data.hourly){
                	 if(i > 35){
                      var dt = data.hourly[i].dt;
                      var temp = data.hourly[i].temp;
                      
                      var date = new Date(dt*1000);
                      var hour = date.getHours();
                      var tday= hour + '시';
                      
                      console.log(tday);
                      hourdate.push(tday);
                      tempdate.push(temp);
                	 }
                
                  } 
         	    console.log(hourdate);
       	     console.log(tempdate);
       		displaych1(hourdate,tempdate);
             }
              
      });
		
	
	};
	
	
	function displaych1(hourdate,tempdate){

	    console.log(hourdate);
	     console.log(tempdate);
	     
		Highcharts.chart('containerch1', {
		    chart: {
		        type: 'line'
		    },
		    title: {
		        text: '시간별 서울 날씨 API'
		    },
		    subtitle: {
		        text: 'Source: openweathermap'
		    },
		    xAxis: {
		        categories: hourdate
		    },
		    yAxis: {
		        title: {
		            text: 'Temperature (°C)'
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
		    series: [{
		        name: '시간별 온도',
		        data: tempdate
		    }]
		});
		};

	
	$(document).ready(function(){
		getDatach1();
	});

</script>



<body>

  <div class="containerbt">
     <table>
     
    <tr>
    	<td colspan ="2"><a id = "door" href="#" class="button" type="button" onclick = "chageclass(1)">출입문 닫힘</a></td>
   		 <td colspan ="2"><a id = "light" href="#" class="button" onclick = "chageclass(2)">조명 꺼짐</a></td>
   		 </tr>
   		 
   		 
    <tr><td></td><td></td><td></td></tr>
    
 
    	<tr><td colspan ="2" rowspan="2"><a id = "temp" href="http://192.168.0.31/Admin_UI/iot.mc" class="button" onclick = "chageclass(3)">온도 설정</a></td><td>희망온도</td><td>설정온도</td></tr>
    	<tr><td><input type="text" id = "htemp" size =10pt></td><td id = "ctemp"></td></tr>
    
    </table>
 	
  </div>
	
	
	<!-- chart1 (날씨API)  -->
	
	<figure class="highcharts-figure">
    <div id="containerch1"></div>
    <p class="highcharts-description">
     
    </p>
</figure>

	
	<div>
		<jsp:include page="chart2.jsp"></jsp:include>
	</div>
	


</body>




</html>