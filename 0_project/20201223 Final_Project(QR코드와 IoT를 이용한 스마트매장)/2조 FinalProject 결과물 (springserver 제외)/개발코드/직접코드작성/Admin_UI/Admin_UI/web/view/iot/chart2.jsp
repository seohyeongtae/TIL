<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

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

<figure class="highcharts-figure">
    <div id="container1"></div>
  <!--   <p class="highcharts-description">
		Datasets formatted in CSV or JSON can be fetched remotely using the
		<code>data</code> module. This chart shows how a chart can also be
		configured to poll against the remote source.
    </p> -->
</figure>



<script>

function createChart1() {
    Highcharts.chart('container1', {
        chart: {
            type: 'spline'
        },
        title: {
            text: '실시간 매장 온도'
        },
        accessibility: {
            announceNewData: {
                enabled: true,
                minAnnounceInterval: 15000,
                announcementFormatter: function (allSeries, newSeries, newPoint) {
                    if (newPoint) {
                        return 'New point added. Value: ' + newPoint.y;
                    }
                    return false;
                }
            }
        },
        xAxis: {
        	 title: {
 	            text: 'Date'
 	        }
	    },
	    yAxis: {
	        title: {
	            text: 'Temperature (°C)'
	        }
	    }, 
   	   data: {
           csvURL: 'http://192.168.0.31/Admin_UI/temp.jsp',
           enablePolling: true,
           dataRefreshRate: (1, 2)
       },
      
        series: [{
        	  color: '#f7a35c'    

      
      }],
	
    });

}



createChart1();
 


</script>
