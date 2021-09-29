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