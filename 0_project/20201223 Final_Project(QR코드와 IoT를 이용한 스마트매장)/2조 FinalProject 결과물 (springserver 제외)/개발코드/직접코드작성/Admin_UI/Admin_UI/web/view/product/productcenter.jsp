<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=0,maximum-scale=10,user-scalable=yes">


	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

</head>

<!-- 메뉴 탭 스타일 -->
<style>


 #topMenu { 
 	height: 30px; 
	max-width: 850px; 
	 text-align: center;
	 margin: 0 auto;
}
#container{
	height : 550px;
	max-width : 850px;
	border : 1px solid gray;
	overflow: auto;
	text-align: center;
	margin: 0 auto;
}

#container .img{
	width : 100px;
	height : 100px;
	border : 1px solid gray;
	text-align: center;
	margin: 0 auto;
}

#container2{
	max-width : 850px;
	border : 1px solid gray;
	text-align: center;
	margin: 0 auto;
	
}

.edittable{
	display : inline-block;
	flex-wrap : nowrap;
		margin: 0 auto;
}


 #topMenu ul li { 
	 list-style: none; 
	 color: white; 
	background-color: #2d2d2d; 
	float: left; 
	line-height: 30px; 
	vertical-align: middle; 
	text-align: center;
} 

#topMenu .menuLink { 
	text-decoration:none; 
	color: white; 
	display: block;
	max-width: 150px;
	min-width: 100px;
	font-size: 12px;
	font-weight: bold; 
	font-family: "Trebuchet MS", Dotum, Arial; 
 } 
#topMenu .menuLink:hover { 
	color: red; 
	background-color: #4d4d4d; 
 } 
 
 @media screen and (max-width: 700px) {
  #topMenu { 
 	height: 30px; 
	max-width: 700px; 
	 text-align: center;
	 margin: 0 auto;
}
#container{
	height : 550px;
	max-width : 850px;
	border : 1px solid gray;
	overflow: auto;
	text-align: center;
	margin: 0 auto;
}

#container .img{
	width : 100px;
	height : 100px;
	border : 1px solid gray;
	text-align: center;
	margin: 0 auto;
}

#container2{
	max-width : 700px;
	border : 1px solid gray;
	text-align: center;
	margin: 0 auto;
	
}


 #topMenu ul li { 
	 list-style: none; 
	 color: white; 
	background-color: #2d2d2d; 
	float: left; 
	line-height: 30px; 
	vertical-align: middle; 
	text-align: center;
} 

#topMenu .menuLink { 
	text-decoration:none; 
	color: white; 
	display: block;
	max-width: 70px;
	min-width: 60px;
	font-size: 10px;
	font-weight: nomal; 
	font-family: "Trebuchet MS", Dotum, Arial; 
 } 
 
  @media screen and (max-width: 500px) {
  #topMenu { 
 	height: 30px; 
	max-width: 500px; 
	 text-align: center;
	 margin: 0 auto;
}
#container{
	height : 550px;
	max-width : 500px;
	border : 1px solid gray;
	overflow: auto;
	text-align: center;
	margin: 0 auto;
}

#container .img{
	width : 100px;
	height : 100px;
	border : 1px solid gray;
	text-align: center;
}

#container2{
	max-width : 500px;
	border : 1px solid gray;
	text-align: center;
	margin: 0 auto;
	
}


 #topMenu ul li { 
	 list-style: none; 
	 color: white; 
	background-color: #2d2d2d; 
	float: left; 
	line-height: 30px; 
	vertical-align: middle; 
	text-align: center;
} 

#topMenu .menuLink { 
	text-decoration:none; 
	color: white; 
	display: block;
	max-width: 50px;
	min-width: 40px;
	font-size: 6px;
	font-weight: nomal; 
	font-family: "Trebuchet MS", Dotum, Arial; 
 } 
 
 
 }
 
 
</style>

<!-- 메뉴 리스트 스타일 -->
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


<style>

.rectangle {
	background: #7f9db9;
	height: 50px;
	max-width: 380px;
	margin: 0 auto;
	left:-15px;
	top: 30px;
	
	-moz-box-shadow: 0px 0px 4px rgba(0,0,0,0.55);
  -khtml-box-shadow: 0px 0px 4px rgba(0,0,0,0.55);
  -webkit-box-shadow: 0px 0px 4px rgba(0,0,0,0.55);
	z-index: 100; /* the stack order: foreground */
}

.rectangle h2 {
	font-size: 30px;
	color: #fff;
	padding-top: 6px;
	text-shadow: 1px 1px 2px rgba(0,0,0,0.2);
	text-align: center;
}



</style>


<script>

function editlist(itemname, stock, price, image,category){
	
	$('#edittbody > input').empty();
	$("input[num=editname]").val(itemname);
	$("input[num=editstock]").val(stock);
	$("input[num=editprice]").val(price);
	$("input[num=editimage]").val(image);
	// $("#category > option[@value="+음료수+"]").attr("seleted","true");
	var cate = category
	$(".category").val(cate);
};


function editready() {
	$('#container > tr > #td > a').click(function() {
		var itemname = $(this).attr("num");
		var stock = $(this).attr("id");
 		var price = $(this).attr("value");
 		var image = $(this).attr("rel");
 		var category = $(this).attr("name");
		editlist(itemname, stock, price, image,category);
	});
};


function display(datas) {
	$('#container').empty();
	$(datas)
			.each(
					function(index, menu) {
						var result = '';
						result += '<tr><td>';
						
						result += '<img class="img" src="img/item/' + menu.image + ' "></td>';
						result += '<td id="td"> <a href="javascript:void(0);" name="'+menu.category+'" id="'+menu.stock+'" value="'+menu.price+'" num ="'+menu.itemname + '" rel ="'+menu.image+'" >'+menu.itemname + '</td></a>'       
						result += '<td>' + menu.price+'원'+'</td><td>'+ menu.stock + '개 </td>' ;
						result += '</tr>';
						$('#container').append(result);
					});
	
	editready();
	
};

	function getData(cate){
		$.ajax({
			url : 'menulist.mc',
			async : false,
			dataType : "json",
			data : {
				cate : cate
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


<nav id="topMenu" > 
<ul> 
 <li><a class="menuLink" href="javascript:void(0)" onclick = "getData(0)">All</a></li>
 <li><a class="menuLink" href="javascript:void(0)" onclick = "getData(1)">과자</a></li> 
 <li><a class="menuLink" href="javascript:void(0)" onclick = "getData(2)">음료수</a></li> 
 <li><a class="menuLink" href="javascript:void(0)" onclick = "getData(3)">생필품</a></li> 
 <li><a class="menuLink" href="javascript:void(0)" onclick = "getData(4)">신선식품</a></li>
 <li><a class="menuLink" href="javascript:void(0)" onclick = "getData(5)">부족재고</a></li>  
 </ul> <br> 
 </nav>



  
 <div class="table-users">
   <div class="header">Product</div>

   <table id ="menu">
      <tr>
         <th>제품사진</th>
         <th>제품명</th>
         <th>가격</th>
         <th>재고</th>
         
         
      </tr>

      <tbody id = "container" >
      
       </tbody>
         
         
     


   </table>
</div>
 
 
 		<div id = container2>
 
  
 <div class="rectangle"><h2>수정하기</h2></div>
<br>

 
 	<div class = "edittable">
 	
					<form enctype="multipart/form-data" action="editmenu.mc" method="post">
				
						<table>
							
							<tbody>
								<tr>
								<td>제품명</td>
								<td><input type="text" num ="editname" name="itemname"></td></tr>
								<tr>
								<td>재고</td>
								<td><input type="text" num = "editstock" name="stock"></td></tr>
								<tr>
								<td>카테고리</td>
								<td><select class="category" name="category"><option value="과자">과자</option>
										<option value="음료수">음료수</option>
										<option value="생필품">생필품</option>
										<option value="신선식품">신선식품</option>
									</select></td></tr>
								<tr>
								<td>가격</td>
								<td><input type="number" num="editprice" name="price" ></td>
								</tr>
							
						
								<tr>
								
								<td colspan ="2"><input type="file"  style="background: white;" name="mf"  ></td></tr>
								<tr>
								<td><input type="hidden" num="editimage"  name="image" value="1">
								<td><input type="submit" value="edit" class="custombtn" id="edit" ></td>
							</tr>
						</tbody>
					</table>
						<br>
					</form>
					</div>
					
			<div class="rectangle"><h2>추가하기</h2></div>
			<br>
		
		
		<div class = "edittable">
					
					<form enctype="multipart/form-data" action="addmenu.mc" method="post">
						<table>
						
							<tbody>
								<tr>
								<td>제품명</td>
								<td><input type="text" name="itemname"></td></tr>
								<tr>
								<td>재고</td>
								<td><input type="text" name="stock"></td></tr>
								<tr>
								<td>카테고리</td>
								<td><select name="category"><option value="과자">과자</option>
										<option value="음료수">음료수</option>
										<option value="생필품">생필품</option>
										<option value="신선식품">신선식품</option>
									</select></td></tr>
								<tr>
								<td>가격</td>
								<td><input type="number" name="price" ></td> </tr>
							
								<tr>
								<td colspan ="2"><input type="file" style="background: white;" name="mf" ></td></tr>
								<tr>
								<td><input type="hidden" ></td>
								<td><input type="submit" value="add" class="custombtn" id="add"></td>
								
							</tr></tbody>
						</table>
						<br>
					</form>
			
				</div>
				
				<div class="rectangle"><h2>삭제하기</h2></div>
				<br>
		
			<div class = "edittable">
				 
					<form enctype="multipart/form-data" action="delmenu.mc" method="post">
						<table>
					
							<tbody id = "edittbody">
								<tr>
								<td>제품명</td>
								<td><input type="text" num ="editname" name="itemname"></td></tr>
								<tr>
								<td>재고</td>
								<td><input type="text" num = "editstock" name="stock"></td></tr>
								<tr>
								<td>카테고리</td>
								<td><select class="category" name="category"><option value="과자">과자</option>
										<option value="음료수">음료수</option>
										<option value="생필품">생필품</option>
										<option value="신선식품">신선식품</option>
									</select></td></tr>
								<tr>
								<td>가격</td>
								<td><input type="number" num="editprice" name="price"></td>
								</tr>
							
						
								<tr>
								<td colspan ="2"><input type="file"  style="background: white;" name="mf"  ></td> </tr>
								<tr>
								<td><input type="hidden" num="editimage"  name="image" value="1">
								<td><input type="submit" value="delete" class="custombtn" id="delete" ></td>
							</tr>
						</tbody>
					</table>
						<br>
					</form>
					</div>
					
								<div class="rectangle"><h2>발주하기</h2></div>
				<br>
		
			<div class = "edittable">
				 
					<form enctype="multipart/form-data" action="stockorder.mc" method="post">
						<table>
					
							<tbody id = "edittbody">
								<tr>
								<td>제품명</td>
								<td><input type="text" num ="editname" name="itemname"></td></tr>
								<tr>
								<td>발주량</td>
								<td><input type="text" num = "stock" name="stock"></td></tr>
												
						
								<tr>
								<td><input type="hidden">
								<td><input type="submit" value="order" class="custombtn" id="order" ></td>
							</tr>
						</tbody>
					</table>
						<br>
					</form>
					</div>
					
					
			</div>
