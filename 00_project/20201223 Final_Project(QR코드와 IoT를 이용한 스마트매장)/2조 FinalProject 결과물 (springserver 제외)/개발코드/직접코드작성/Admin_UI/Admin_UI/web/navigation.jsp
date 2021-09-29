<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<%

	request.setCharacterEncoding("UTF-8");
	Thread.sleep(3000);

%>   
      

<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<body>
<h1 class="cateid" id= <%=request.getParameter("id") %>><%=request.getParameter("id") %></h1>
</body>



<script>
		var category;
		var id =  $('.cateid').attr("id");
		
		if(id =="cate1"){
			category = "cate1";
		}
		
		if(id =="cate2"){
			category = "cate2";
		}
		
		if(id =="cate3"){
			category = "cate3";
		}
		
		if(id =="cate4"){
			category = "cate4";
		}
		
	if(id =="cate1"){
		 $.ajax({
             url:'iotnavi.mc',
          	data : {
          		category : category
			},
             success: function(){
       
                 } 
             
  			   });
		
	};
	if(id =="cate2"){
		 $.ajax({
             url:'iotnavi.mc',
          	data : {
          		category : category
			},
             success: function(){
       
                 } 
             
  			   });
	};
	if(id =="cate3"){
		 $.ajax({
             url:'iotnavi.mc',
          	data : {
          		category : category
			},
             success: function(){
       
                 } 
             
  			   });
	};
	if(id =="cate4"){
		 $.ajax({
             url:'iotnavi.mc',
          	data : {
          		category : category
			},
             success: function(){
       
                 } 
             
  			   });
	};	

</script>

</html>
