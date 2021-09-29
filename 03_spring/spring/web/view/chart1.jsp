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

	    title: {
	        text: 'Solar Employment Growth by Sector, 2010-2016'
	    },

	    subtitle: {
	        text: 'Source: thesolarfoundation.com'
	    },

	    yAxis: {
	        title: {
	            text: 'Number of Employees'
	        }
	    },

	    xAxis: {
	        accessibility: {
	            rangeDescription: 'Range: 2010 to 2017'
	        }
	    },

	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle'
	    },

	    plotOptions: {
	        series: {
	            label: {
	                connectorAllowed: false
	            },
	            pointStart: 2010
	        }
	    },

	    series: data,

	    responsive: {
	        rules: [{
	            condition: {
	                maxWidth: 500
	            },
	            chartOptions: {
	                legend: {
	                    layout: 'horizontal',
	                    align: 'center',
	                    verticalAlign: 'bottom'
	                }
	            }
	        }]
	    }

	});
};


function getData(){
	$.ajax({
		url: 'getdata1.mc',
		success:function(data){
			display(data);

		},
		error:function(){}
	});
	//display();
};


$(document).ready(function(){
	
	getData();
	
});

</script>


<h1>chart1</h1>
<div id="container">

	


</div>