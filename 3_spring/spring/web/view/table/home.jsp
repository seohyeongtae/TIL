<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<style>

/*------whole div Start------ */
#section-table-home{
	padding-top: 150px;
	height: 770px;
}
#section-table-home>div{
	display:block;
	margin:0 auto;
}
.row{
	text-align: center;
}
/*------whole div end------ */

/*------center div Start------ */
.custombtn{
	font-size: 35px;
	text-decoration:none;
	display: block;
	padding:0;
}

#search{
	display: block;
	margin-top:120px;
}
h2{
	font-size: 22px;
	margin:0 3px;
}
#srch_r{
	margin-top: 10px;
	display: block;
	background: white;
	height: 100px;
	border: 2px solid gray;
	overflow: auto;
}
/*------center div end------ */

/*------left div Start------ */
#callbtn{
	display:block;
	margin:70px;
	background-color: #e9856e;
	padding:10px;
}
#callbtn:hover{
	background-color: #bc6754;
}
#callbtn>img{
	width: 80px;
}
/*------left div end------ */

/*------right div Start------ */
#orderdiv{
	display: block;
	background: white;
	border: 2px solid gray;
	height: 500px;
	overflow: auto;
}
#orderlist{
	display:block;
}
#orderdiv>h3{
	padding: 5px;
}
/*------right div Start------ */

</style>
<script>

	// 주문목록 출력
function display(datas){

	$(datas).each(function(index,menu){
		var result = '';
		result += '<h3> '+menu.menu_id + '  ' + menu.qt+'개 </h3>';
		
		$('#orderdiv').append(result);
 });
};


	// 사용자 주문목록 데이터 가지고와서 출력
$(document).ready(function(){

	$.ajax({
		url:'waitinglist.mc',
		async:false,
		dataType:"json",
		success:function(result){
			display(result);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert(errorThrown);
			alert(textStatus);

		}
	});
});

</script>

<section class="pb_cover_v1 text-center" style="background-color: #fff5b9" id="section-table-order">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-sm-4" style="padding-top: 430px">
				<a href="#" rol="button" id="callbtn"><img src="img/bell.png"></a>
			</div>
			<div class="col-sm-4" style="padding-top: 150px">
				<p><a href="tableorder.mc" role="button" class="custombtn" id="orderbtn">주문하기</a></p>
				<p><a href="tablebull.mc" role="button" class="custombtn" id="bullbtn">낙서하기</a></p>
				<div id="search">
					<h2 class="row"><input class="col-9" id="srch_i" type="text" name="srch"><button class="col-3" id="srch_b">검색</button></h2>
					<div id="srch_r"></div>
				</div>
			</div>
			<div class="col-sm-4"  style="padding-top: 50px">
				<div id="orderdiv">
					<h3>대기중 주문</h3>
					<div id="orderlist">
					</div>
				</div>
			</div>
			
		</div> 
	</div>
</section>