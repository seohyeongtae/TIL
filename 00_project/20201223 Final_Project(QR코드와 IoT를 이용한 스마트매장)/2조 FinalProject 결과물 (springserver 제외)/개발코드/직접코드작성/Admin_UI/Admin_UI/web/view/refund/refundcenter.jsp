<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=0,maximum-scale=10,user-scalable=yes">
</head>

<style>

body * {
  box-sizing: border-box;
}

.header {
  background-color: #327a81;
  color: white;
  font-size: 1.5em;
  padding: 1rem;
  text-align: center;
  text-transform: uppercase;
}

img {
  border-radius: 50%;
  height: 60px;
  width: 60px;
}

.table-users {
  border: 1px solid #327a81;
  border-radius: 10px;
  box-shadow: 3px 3px 0 rgba(0, 0, 0, 0.1);
  max-width: calc(100% - 2em);
  margin: 1em auto;
  overflow: hidden;
  width: 800px;
}

#menu {
  width: 100%;
}
#menu td, table th {
  color: #2b686e;
  padding: 10px;
}
#menu td {
  text-align: center;
  vertical-align: middle;
}
#menu td:last-child {
  font-size: 0.95em;
  line-height: 1.4;
}
#menu th {
  background-color: #daeff1;
  font-weight: 300;
  text-align: center;
}
#menu tr:nth-child(2n) {
  background-color: white;
}
#menu tr:nth-child(2n+1) {
  background-color: #edf7f8;
}


@media screen and (max-width: 500px) {

img {
  border-radius: 30%;
  max-height: 50px;
  max-width: 50px;
}

#menu td:last-child {
  font-size: 0.6em;

}

  #menu td, table th {
  font-size: 0.6em;
}

  .table-users {
    border: none;
    box-shadow: none;
    overflow: visible;
  }
}

</style>



<script>
	
	function display(datas){
		
		$('#container').empty();
		$(datas)
				.each(
						function(index, menu) {
							var result = '';
							result += '<tr><td>';
							
							result +=  menu.day + '</td>';
							result += '<td id="td">'+menu.itemname + '</td>'       
							result += '<td>' + menu.userid+'</td><td>'+ menu.quantity + '개 </td>' ;
							result += '</tr>';
							$('#container').append(result);
						});
		
		editready();
		
		
	};	
		
	// productController 로 구현	
	function getData(){
		$.ajax({
			url : 'refundlist.mc',
			async : false,
			dataType : "json",
			data : {
				
			},
			success : function(result) {
				display(result);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(errorThrown);
				alert(textStatus);
			}
		});
	};




	$(document).ready(function(){
		getData(0);
	});


</script>



<body>




 <div class="table-users">
   <div class="header">Refund</div>

   <table id ="menu">
      <tr>
         <th>환불날짜</th>
         <th>제품명</th>
         <th>환불ID</th>
         <th>수량</th>
         
         
      </tr>

      <tbody id = "container" >
      
       </tbody>
        

   </table>
</div>



</body>
</html>