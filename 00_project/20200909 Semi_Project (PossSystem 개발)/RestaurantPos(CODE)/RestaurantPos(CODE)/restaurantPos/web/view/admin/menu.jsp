<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<style>
<style>
/*------center div Start------ */
#center{
	margin-bottom:15px;
}
#center>h1{
	text-align: left;
}
#order> div{
	margin: 0 auto;
	text-align: left;
}
#order>#cate{
	height: 47px;
	padding:0;
}
#order>#menu{
	background: white;
	border: 2px solid gray;
	height: 300px;
	overflow: auto;
}
/*------center div end------ */

/*------category Start------ */
#cate>a{
	padding: 0;
	font-size: 25px;
	color: black;
	margin: 0;
	display:inline-block;
	text-align: center;
	background: white;
	border: 1px solid gray;
}
/*------category end------ */
h3{
	text-align: left;
	margin-top: 15px;
}

/*------menu edit/add start------ */
table td{
	text-align: left;
	color: black;
	font-size: 25px;
	padding-left: 10px;
}
#edit, #add{
	outline: 0;
	border:0;
	height: 50px;
	padding-bottom: 20px;
}
/*------menu edit/add end------ */
#menu>h3{
	display: inline-block;
	border: 1px solid gray;
	margin-left: 10px;
	padding: 5px;
	margin-bottom:0;
}
#menu img{
	height: 100px;
}
</style>

<script>

	/* 수정하기의 value 값을 현재 클릭한 메뉴값으로 바꾸고 출력 한 뒤 input 들어가게함 */
	function editlist(menuid, menuname, price, img1){
	
		$('#edittbody > input').empty();
		$("input[num=editid]").val(menuid);
		$("input[num=editname]").val(menuname);
		$("input[num=editprice]").val(price);
		$("input[num=editimg1]").val(img1);

	};

	/* 불러온 전체메뉴를 카테고리별로 메뉴 form 에 넣어줌 */
	function display(datas) {
		$('#menu').empty();
		$(datas).each(function(index, menu) {
							var result = '';
							result += '<h3> <a type="button" href="javascript:void(0);"  id="'+menu.name+'" value="'+menu.price+'" num ="';
							result += menu.id + '" rel ="'+menu.img1+'" >';
							result += '<img src="img/foods/' + menu.img1 + ' "><p style="display:inline-block;">';
							result += menu.name + '<br>  가격 = ' + menu.price;
							result += '</p></a><a type="button" href="deletemenu.mc?id='+menu.id+'" class="custombtn">del</a></h3>';
							$('#menu').append(result);
	
						});
		/*메뉴 리스트 사진 누르면 order 리스트로 입력되게 하는 함수 시작*/
		editready(); 
	};
	
	
	/*MenuController에 메뉴 리스트 정보 받기*/
	function sendData(id) {
		$.ajax({
			url : 'menulist.mc',
			async : false,
			dataType : "json",
			data : {
				id : id
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
	
	function editready() {
		$('#menu > h3 > a').click(function() {
			var menuid = $(this).attr("num");
			var menuname = $(this).attr("id");
	 		var price = $(this).attr("value");
	 		var img1 = $(this).attr("rel");
			editlist(menuid, menuname, price, img1);
		});
	};
	//추가 처음 눌렀을때에도 화면 뜨게(나영)
	$(document).ready(function(){
		sendData(1);
	});

</script>

<section class="pb_section" style="background-color: #fff5b9" id="section-table-order">
	<div class="container align-items-center justify-content-center"" style="overflow: auto">
		<div class="row">
			<div class="col-sm-12" id="center">
				<h1>메뉴</h1>
				<div id="order">
					<div id="cate" class="row">
						<a class="col-sm-3" type="button" id="cate-food" href="javascript:void(0);" onclick="sendData(1)">전류</a>
						<a class="col-sm-3" type="button" id="cate-fruit" href="javascript:void(0);" onclick="sendData(2)">안주류</a>
						<a class="col-sm-3" type="button" id="cate-anju" href="javascript:void(0);" onclick="sendData(3)">탕류</a>
						<a class="col-sm-3" type="button" id="cate-drink" href="javascript:void(0);" onclick="sendData(4)">주류</a>
					</div>
					<div id="menu">			
					</div>
				</div>
				
				<!-- 	-----------------주의사항 추가하겠습니다----------------- -->
				<div style="background: white;border: 1px solid gray; color:black; padding: 0 20px;margin-top:10px;">
					<h4>주의사항</h4>
					<h5> 각 카테고리는 고유번호(1~4)로 구분됩니다. ID 값은 고유번호를 시작으로 순서대로 입력해주세요</h5>
					<p> ---(전류 1, 안주류 2, 탕류 3, 주류 4)
					<p> Ex) 해물파전 ID = 11, 소세지전 ID = 16, 갑오징어숙회 ID = 26, 고추장찌개 ID = 35, 음료수 ID = 44 </p>
					<h5>   !!  수정/추가시 기존 ID 와 동일한 ID 값을 입력하면 반영 되지 않습니다  !!</h5>
					<h5> 파일 선택은 필수사항이 아니나 수정 시 기존 메뉴사진이, 새로운 메뉴 추가 시 '이미지 등록 ' 사진이 기본값으로 보여집니다.</h5>
					<h5> 수정/추가 후에는 잘 등록이 되었는지 반드시 확인해주세요. </h5>
				</div>
				
				<!-- 	-----------------주의사항 끝----------------- -->
				
				<div>
					<h3>수정하기</h3>
					<form enctype="multipart/form-data" action="editmenu.mc" method="post">
						<table>
							<thead><tr>
								<td>ID</td><td>이름</td><td>카테고리</td><td>가격</td><td></td>
							</tr></thead>
							<tbody><tr>
								<td><input type="text" num ="editid" name="id" size=5></td>
								<td><input type="text" num = "editname" name="name" size=10></td>
								<td><select name="category"><option value="1">전류</option>
										<option value="2">안주류</option>
										<option value="3">탕류</option>
										<option value="4">주류</option>
									</select></td>
								<td><input type="number" num="editprice" name="price" size=10 style="width: 150px"></td>
								<td><input type="file"  style="width:300px;background: white;" name="mf" ></td>
								<td><input type="hidden" num="editimg1"  name="img1" value="1">
								<td><input type="submit" value="edit" class="custombtn" id="edit"></td>
							</tr></tbody>
						</table>
					</form>
					<h3>추가하기</h3>
					<form enctype="multipart/form-data" action="addmenu.mc" method="post">
						<table>
							<thead><tr>
								<td>ID</td><td>이름</td><td>카테고리</td><td>가격</td><td></td>
							</tr></thead>
							<tbody><tr>
								<td><input type="text" name="id" size=5></td>
								<td><input type="text" name="name" size=10></td>
								<td><select name="category"><option value="1">전류</option>
										<option value="2">안주류</option>
										<option value="3">탕류</option>
										<option value="4">주류</option>
									</select></td>
								<td><input type="number" name="price" size=10 style="width: 150px;"></td>
								<td><input type="file" style="width:300px;background: white;" name="mf" ></td>
								<td><input type="hidden" name="img1" value="null.jpg">
								<td><input type="submit" value="add" class="custombtn" id="add"></td>
							</tr></tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>