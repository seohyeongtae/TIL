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