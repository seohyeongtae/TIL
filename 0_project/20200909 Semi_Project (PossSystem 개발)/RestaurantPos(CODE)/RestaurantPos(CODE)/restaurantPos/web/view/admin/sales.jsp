<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script src="https://code.highcharts.com/modules/export-data.js"></script>
	<script src="https://code.highcharts.com/modules/accessibility.js"></script> 
<style>
.month{
	background: none;
	outline: 0;
	border: 0;
}
#p_chart{
	background: white;
	height: 300px;
}
h3{
	margin-top:20px;
}
</style>
<script>
	function gchart(gdata) {  
		Highcharts.chart('p_chart', {
			chart : {
				type : 'line'
			},
			title : {
				text : '매출/수익 차트'  // 차트 속 제목
			},
			subtitle : {
				text : '2019년'  // 차트 속 부제목
			},
			xAxis : {
				categories : [ '1일', '2일', '3일', '4일', '5일', '6일', '7일',
						'8일', '9일', '10일', '11일', '12일', '13일', '14일', '15일', '16일', '17일', '18일', '19일',
						'20일', '21일', '22일', '23일', '24일', '25일', '26일', '27일', '28일', '29일',
						'30일', '31일']  
			},
			yAxis : {
				title : {
					text : '단위:원(10,000)'
				}
			},
			plotOptions : {
				line : {
					dataLabels : {
						enabled : true
					},
					enableMouseTracking : false
				}
			},
			series : gdata		
			});

	};
	
	function changearraydate(getdata, month) {
		var newarr = new Array;
		$(getdata).each(function (index, data){
			newarr.push(data.data/10000);
		});
		
		var gdata = [{
			name : month+'월 매출',
			data :newarr
		}];
		gchart(gdata); 
	}
	
	
	
	
	
	function getDatagChart(month) { 
		/* var rev = {daysaleslist.data} */
		
		$.ajax({
			url:'salesgraph.mc',
			type:'GET',
			async:false,
			data: {
				'month':month
			},success:function(result){
				changearraydate(result, month);
				
			}
		});
		 
	};
	//금일매출액
	function total_display(datat) {
		$(datat).each(function(index,daysaleslist){
			var result = '';
			
			result += '<h4>'+' '+  +' '+'</h4>';
			$('').append(result);
		});
		
	};
	
	
	$(document).ready(function() {
		var today = new Date();
		var tnmonth = today.getMonth()+1;
		var tmonth = new String(tnmonth);
		if(tmonth.length == 1){ 
			tmonth = "0" + tmonth; 
		} 
		getDatagChart(tmonth);
		
		$('#month-b').click(function(){
			var mo = $('#monthval').text();
			var m= Number(mo);
			m = m-1;
			var month = new String(m);
			if(month.length == 1){ 
				month = "0" + month; 
			}
			$('#monthval').empty();
			$('#monthval').append(month);
			getDatagChart(month);
		});
		$('#month-a').click(function(){
			var mo = $('#monthval').text();
			var m= Number(mo);
			m = m+1;
			if(m>tnmonth) {
				m=tnmonth;
			};
			var month = new String(m);
			if(month.length == 1){ 
				month = "0" + month; 
			}
			$('#monthval').empty();
			$('#monthval').append(month);
			getDatagChart(month);
		});
	});
	
	
</script>
<section class="pb_section" style="background-color: #fff5b9" id="section-home">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-sm-12" style="text-align: left">
				<h1>매출 관리</h1>
				<!-- 현재년도 -->
				<c:set var="now" value="<%=new java.util.Date()%>" />
				<c:set var="sysMonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set> 
				<h3>월 별 <button class="month" id="month-b">◀</button><span id="monthval"> <c:out value="${sysMonth}" /></span>월 <button class="month" id="month-a">▶</button></h3>
				<div id="p_chart"></div>
				<h3>금일 매출액<br> : <span>${sales }</span>원</h3>
			</div>
		</div>
	</div>
</section>