<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	margin-top:70px;
}
h2{
	font-size: 22px;
	margin:0 3px;
}
#srch_r{
	text-align:left;
	margin-top: 10px;
	display: block;
	background: white;
	height: 150px;
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

	// �ֹ���� ���(������)
	function displaySales(datas){
	
		$(datas).each(function(index,menu){
			var result = '';
			result += '<h3> '+menu.menu_id + '  ' + menu.qt+'�� </h3>';
			
			$('#orderdiv').append(result);
	 });
	};
// �˻��� ���� ��� (���縲)
	function searchDisplay(data){
		$(data.documents).each(function(index,add){
			var result = '';
			result += '<p style="color:black;">'+add.title+' '+add.contents+'</p>'; //document name(������ ��! �ٲ� ��X) = title-��������, contents-���� ����
			$('#srch_r').append(result);
			
		});
		
	};
	//�� �˻� ���� �޾ƿ���(���縲)
	function getSearchData(){
		var urlstr = 'https://dapi.kakao.com/v2/search/web';	
		var srch = $('#srch_i[name="srch"]').val();  //srch=search ����
		if(srch!=''){
			$.ajax({
				method:'GET',
				url:urlstr,
				headers:{'Authorization':'KakaoAK 26e6df87e0b209a73bde4bdcb47d3e95'}, // īī�� rest API Ű�� �ٲ��ּ���
				data:{'query':srch},
				success:function(data){
					searchDisplay(data);
				},
				error:function(){
					alert('error g');
				}
			});
		};
	};
	//(���縲)
	function searchbutton(){
		$('#srch_r').empty(); // ������ �˻��ؼ� ���� �˻����� �����
		getSearchData();
	}
	

	// ����� �ֹ���� ������ ������ͼ� ���
	$(document).ready(function(){
		//(������)
		$.ajax({
			url:'waitinglist.mc',
			async:false,
			dataType:"json",
			success:function(result){
				displaySales(result);
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
				<!-- <a href="#" role="button" id="callbtn"><img src="img/bell.png"></a> -->
			</div>
			<div class="col-sm-4" style="padding-top: 150px">
				<p><a href="tableorder.mc" role="button" class="custombtn" id="orderbtn">�ֹ��ϱ�</a></p>
				<p><a href="tablebull.mc" role="button" class="custombtn" id="bullbtn">�����ϱ�</a></p>
				<div id="search">
					<h2 class="row"><input class="col-9" id="srch_i" type="text" name="srch" value=""><button style="border:0; font-size:20px;" class="col-3 custombtn" id="srch_b" onclick="searchbutton()">�˻�</button></h2>
					<div id="srch_r"></div>
				</div>
			</div>
			<div class="col-sm-4"  style="padding-top: 50px">
				<div id="orderdiv">
					<h3>����� �ֹ�</h3>
					<div id="orderlist">
					</div>
				</div>
			</div>
			
		</div> 
	</div>
</section>