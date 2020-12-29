


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
    
    
    
<style>
	    #container {
		    height: 400px; 
		}
		
		.highcharts-figure, .highcharts-data-table table {
		    min-width: 310px; 
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
		    

</style>
            
        <script>
        
        
        function getmonth2(month){
        	var id = month.value;
        	getData2(id);
        };
        
        function getData2(id){
        	$.ajax({
        		url: 'getorderlist2.mc',
        		dataType : "json",
        		data : {
        			id : id
        		},
        		success:function(data){
        			display2(data);
        		},
        		error:function(){}
        	});
        };

        
   
      function display2(datas){
	    Highcharts.chart('container2', {
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: '�Ϻ� �Ǹ���Ȳ'
	        },
	        subtitle: {
	            text: '(�� ���ɿϷ�)'
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
	                text: '�Ǹűݾ� (��)'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            pointFormat: '�Ǹűݾ� : <b>{point.y} ��</b>'
	        },
	        series: [{
	            name: 'Population',
	            data: datas,
	            dataLabels: {
	                enabled: true,
	                rotation: -90,
	                color: '#FFFFFF',
	                align: 'right',
	                format: '{point.y}', // one decimal
	                y: 10, // 10 pixels down from the top
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	       	 }]
	  	  });
      };
	    
 
        
        function getmonth(month){
        	var id = month.value;
        	getData(id);
        };
        
        function getData(id){
        	$.ajax({
        		url: 'getorderlist.mc',
        		dataType : "json",
        		data : {
        			id : id
        		},
        		success:function(data){
        			display(data);
        		},
        		error:function(){}
        	});
        };

        
   
      function display(datas){
	    Highcharts.chart('container', {
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: '��ǰ�� �Ǹ���Ȳ'
	        },
	        subtitle: {
	            text: '2�� ����Ʈ ����'
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
	                text: '�Ǹŷ� (��)'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            pointFormat: '�Ǹŷ� : <b>{point.y} ��</b>'
	        },
	        series: [{
	            name: 'Population',
	            data: datas,
	            dataLabels: {
	                enabled: true,
	                rotation: -90,
	                color: '#FFFFFF',
	                align: 'right',
	                format: '{point.y}', // one decimal
	                y: 10, // 10 pixels down from the top
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	       	 }]
	  	  });
      };
	    
	  
	  $(document).ready(function(){
		    getData(); 
		    getData2(); 
						
		});

    
    </script>
    
    
    

<section>
	<div id="container"></div>

	
    <table>
      <tbody><tr>
								<td><select name="category" onchange="getmonth(this)" >
										<option>����</option>
										<option value="01" >1��</option>
										<option value="02" >2��</option>
										<option value="03" >3��</option>
										<option value="04" >4��</option>
										<option value="05" >5��</option>
										<option value="06" >6��</option>
										<option value="07" >7��</option>
										<option value="08" >8��</option>
										<option value="09" >9��</option>
										<option value="10">10��</option>
										<option value="11">11��</option>
										<option value="12">12��</option>	
									</select></td>
				
							</tr></tbody>
					
							</table>
							
	  <div id="container2"></div>

    <table>
      
      <tbody><tr>
							
								<td><select name="category2" onchange="getmonth2(this)" >
										<option>����</option>
										<option value="01" >1��</option>
										<option value="02" >2��</option>
										<option value="03" >3��</option>
										<option value="04" >4��</option>
										<option value="05" >5��</option>
										<option value="06" >6��</option>
										<option value="07" >7��</option>
										<option value="08" >8��</option>
										<option value="09" >9��</option>
										<option value="10">10��</option>
										<option value="11">11��</option>
										<option value="12">12��</option>
										
									</select></td>
							
				
							</tr></tbody>
					
							</table>						
							
							
						
</section>