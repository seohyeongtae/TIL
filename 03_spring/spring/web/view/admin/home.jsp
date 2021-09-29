<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<style>

h1{
	margin-top: 50px;
	text-align: left;
}
#tables{
	height: 400px;
	border: 1px solid gray;
	overflow: auto;
}
#menu{
	margin-top: 10px;
}
#menu>a{
	display: inline-block;
	margin:5px;
	padding: 0 20px;
}
#wait-order{
	height: 474px;
	overflow: auto;
}
/*--------order list table start----------*/
#list-table{
	border-collapse: collapse;
}
#list-table td{
	text-align: center;
	color: black;
	border: 1px solid gray;
}

#list-table>tbody>tr:hover {
	background: #d3cb98;
	font-weight: bold;
}

/*--------order list table end----------*/

</style>
<script>

	// 우측 주문목록 데이터를 함께 사용하여 Table div 영역에 출력. 주문목록 데이터 삭제시 함께 연동됨.
	function dispalytable(datas){
		$(datas).each(function(index,menu){

				var result = '';
					result += '<h3> '+menu.menu_id+' '+menu.qt+' <h3> '
					$('#tables > #'+menu.receipt_id+'').append(result);	
		});
	};


	// 우측 주문목록에 데이터 세팅  
	function display(datas) {
		$(datas).each(
				function(index, menu) {
					var result = '';
					result += '<tr id=' + menu.id + ' onclick="delwaiting(this.id)"> <td> '
							+ menu.tab_id + '</td>';
					result += '<td> ' + menu.menu_id + '</td>';
					result += '<td> ' + menu.qt + '</td> </tr>';

					$('#list-table > tbody').append(result);
				});
	};

	
	// 주문대기목록 출력
	function delwaiting(data) {
		var result = confirm('대기목록에서 지우시겠습니까?');
		if (result) {
			$.ajax({
				url : 'waitingdeladmin.mc',
				async : false,
				data : {id:data},
				success : function(result) {
					if(result){
						alert("삭제되었습니다.");
						location.reload();
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(errorThrown);
					alert(textStatus);
				}
			});
		} else {

		}
		;

	};
	
	// ajax로 주문목록 데이터 요청 sales 데이터 menucontroller에 있음.
	function listwaiting(){
		$.ajax({
			url : 'waitinglistadmin.mc',
			async : false,
			dataType : "json",
			success : function(result) {
				display(result);
				dispalytable(result);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(errorThrown);
				alert(textStatus);

			}
		});
	}
	
	// 화면 켜지자마자 데이터 요청 시작
	$(document).ready(function() {
		listwaiting();
	});
</script>

<section class="pb_cover_v1 text-center" style="background-color: #fff5b9" id="section-table-order">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-md-9">
				<h1>Table</h1>
				<div id="tables"></div>
				<div id="menu" class="row align-items-center justify-content-center">
					<a class="custombtn" href="sales.mc">매출 관리</a>
					<a class="custombtn" href="menu.mc">메뉴 관리</a>
					<a class="custombtn" href="table.mc">테이블 관리</a>
					<a class="custombtn" href="adminbull.mc">게시판 관리</a>
				</div>
			</div>
			<div class="col-md-3" style="padding-left:0">
				<h1>주문 목록</h1>
				
				<div id="wait-order" class="row">
					<table id="list-table" class="col-md-12">
						<thead>
							<tr>
								<td>테이블<br>번호</td>
								<td>메뉴 이름</td>
								<td>개수</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</section>