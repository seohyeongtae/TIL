<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<style>

/*------whole div Start------ */

#right-div,#left-div{
	padding-top:50px;
}
.row{
	text-align: center;
}
/*------whole div end------ */

/*------left div Start------ */
#left-div>h1{
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
	height: 450px;
	overflow: auto;
}
/*------left div end------ */

/*------left div category Start------ */
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
/*------left div category end------ */

/*------right div Start------ */
#right-div>div{
	border: 1px solid gray;
	height: 375px;
	overflow: auto;
}
#right-div>h1{
	margin-top:5px;
	margin-bottom:5px;
}
#right-div>#orderbtn{
	display:block;
}
#order-list{
	text-align: right;
	padding:5px;
}
#odrder-list>h3{
	margin-top: 0;
	padding-top:5px;
}
#order-list>h3>.custombtn{
	border:0;
	padding:0 5px;
	margin: 2px;
}
/*------right div end------ */
#menu>h3{
	display: inline-block;
	padding: 5px;
	margin: 5px;
	border:1px solid gray;
	text-align: center;
}
#menu>h3>a>img{
	height: 180px;
}
</style>

<script>
	var menulist = new Array();
	var datanum = 0;
	var valuenum = 0;

	/* �ֹ��ϱ� ��ư Ŭ���� RECEIPT ������ ��û */
	function reciptlist() {
		$.ajax({
			type : "POST",
			url : 'orderreceipt.mc',
			dataType : "text",
			data : JSON.stringify(menulist),
			contentType : 'application/json; charset=UTF-8',
			async : false,
			success : function(data) {
				alert("�ֹ��� �Ϸ�Ǿ����ϴ�.");
			},
			error : function(request, status, error) {
				console.log("AJAX_ERROR");
			}
		});
	};


	/*MenuController�� �޴� ����Ʈ ���� �ޱ�*/
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

	/*MenuController�� �޴� ����Ʈ ���� ������ ȭ�鿡 ǥ���ϱ�*/
	function display(datas) {
		$('#menu').empty();
		$(datas)
				.each(
						function(index, menu) {
							var result = '';
							result += '<h3 id="'+menu.id+'"> <a type="button" href="javascript:void(0);" num ="';
							result += menu.id + '">';
							result += '<img src="img/foods/' + menu.img1 + ' "><br>';
							result += menu.name + ', ' + menu.price+'��</a>';
							result += '</h3>';
							$('#menu').append(result);

						});
		/*�޴� ����Ʈ ���� ������ order ����Ʈ�� �Էµǰ� �ϴ� �Լ� ����*/
		orderready();
	};
	

	/* �ֹ���� �ش� �� ����, ���հ�ݾ� ���� dispalyorder Button���� ���� */
	function deleteRow(ths) {
	
		var deletelist = new Array();
	
		
		var $ths = $(ths);
		$ths.parents("h3").remove();

		var price = $(ths).attr('name');
		var deletemenu = $(ths).attr('num');
		var qt = $(ths).attr('value');
	
		var total = Number(price) * Number(qt);
		
	
		deletetotal = $('#right-div > h1 > span').html() - total;
		$('#right-div > h1 > span').html(deletetotal);
	
		
			/* menulist �� toStringȭ �ؼ� ���� ���̵��� �ִ� ���� ã��  */
		for (var i=0; i <= menulist.length; i++) {
			var stmenulist = JSON.stringify(menulist[i]);
			var checklist = stmenulist.indexOf(deletemenu);	
	
			if(checklist != -1 ){
			menulist.splice(i, 1);
			datanum--;
				}
			};
	}

	
	/*+ - ���� ���� �� �� �ݾ� ���� */
	function change(num, id) {

		var x = (document.getElementById("qtcount" + id).value); // �ʱⰪ = 1 
		var y = (Number(x) + Number(num));
		
		var z = $("#delete" + id).attr('value'); //��ư������ value �߰� total ����� ���Ͽ� �ʱⰪ = 1
		var bz = (Number(z) + Number(num));

		var chageid = $("#delete" + id).attr('num'); // menulist tsale = �Ǹż��� üũ�ϱ� ����  munu.id ���� �Ѿ�´�.
		
	
		if (y >= 1) {
			document.getElementById("qtcount" + id).value = y;
			$("#delete" + id).attr('value', bz);         // X ��ư�� value -> tsale  �� ��������

			/* menulist �� toStringȭ �ؼ� ���� ���̵��� �ִ� ���� ã��  */
			for (var i=0; i <= menulist.length; i++) {
				var stmenulist = JSON.stringify(menulist[i]);
				var checklist = stmenulist.indexOf(chageid);	
				
						if(checklist != -1 ){
							menulist[i].tsales = y; // �ش� �޴��� tsales �� ���� = �Ǹż���
						
							var delqt = $('#qtcount' + id).attr('name');    // menu ����
							deleteqt = Number($('#right-div > h1 > span').html())
									+ Number(delqt) * Number(num);
							$('#right-div > h1 > span').html(deleteqt);
							
							}else{	
						
								};
			} 
		}else{
			document.getElementById("qtcount" + id).value = 1;
			$('#plus' + id).attr('value') = 1;
			menulist[i].tsales = 1;
			}
	};

	/*�̹��� ������ ��ü �ֹ���϶��� ǥ��*/

	function displayorder(datas) {

		$(datas).each(function(index, menu) {
			function total() {
				var total = '';
				total = Number(menu.price)
						+ Number($('#right-div > h1 > span')
								.html());
				$('#right-div > h1 > span').html(total);
			};
			
			
			
			// �ߺ� �ִ��� �˻�
			for ( var i in menulist) {
				if (menu.name != menulist[i].name) {
				
				} else {
					alert("�̹� �����Ͻ� �޴��Դϴ�..")
					return displayorder();
				}
			};
			
			menulist.push(menu);
			menulist[datanum].tsales = 1;
			
			var result = '';
			result += '<h3 id="'+menu.id+'">';
			result += menu.name + '  ' + menu.price+' ';
	
			result += '<button type="button" class="custombtn" onclick="change(1,' + menu.id + ');" id="plus'+ menu.id + '" value="1" ">+</button>'
			result += '<input type="text" num ="'+datanum+'" id="qtcount'+menu.id+'" value="1" size = 1 name ="'+menu.price+'">'
			result += '<button type="button" class="custombtn"  onclick="change(-1,' + menu.id + ');" id="minus' + menu.id + '">-</button>'
	
			result += '<button type="button" class="custombtn"  num ="' + menu.id +  '" id="delete' + menu.id + '" value="1" name="' + menu.price + '" onclick = "deleteRow(this);">X</button>';
			result += '</h3>';
/* 			 menu.id +menu.name + menu.price+ + menu.tsale + menu.category + menu.img1 + menu.img2 + menu.img3 */
			datanum++;
			valuenum++;

			$('#order-list').append(result);
			
			var price = '';
			price += parseInt(menu.price);
			total();
		});
	};
	
	/*�ֹ���Ͽ�  display���� �̹��� Ŭ���ϸ� Id ���� �Ѿ�´� ������ �������*/
	function orderlist(id) {
		$.ajax({
			url : 'orderlist.mc',
			async : false,
			dataType : "json",
			data : {
				id : id
			},
			success : function(result) {
				displayorder(result);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(errorThrown);
				alert(textStatus);
			}
		});
	};

	
	/*�޴���Ͽ��� (display)  �޴���������� �̹��� Ŭ���� ���� �Լ� �����غ�*/
	function orderready() {
		$('#menu > h3 > a').click(function() {
			var num = $(this).attr("num");
			orderlist(num);
		});
	};
	
	$(document).ready(function(){
		sendData(1);
	});
</script>

<section class="pb_cover_v1 text-center" style="background-color: #fff5b9" id="section-table-order">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-sm-8" id="left-div">
				<h1>�ֹ��ϱ�</h1>
				<div id="order">
					<div id="cate" class="row">
						<a class="col-sm-3" type="button" id="cate-food" href="javascript:void(0)" onclick="sendData(1)">����</a>
						<a class="col-sm-3" type="button" id="cate-fruit" href="javascript:void(0)" onclick="sendData(2)">���ַ�</a>
						<a class="col-sm-3" type="button" id="cate-anju" href="javascript:void(0)" onclick="sendData(3)">����</a>
						<a class="col-sm-3" type="button" id="cate-drink" href="javascript:void(0)" onclick="sendData(4)">�ַ�</a>
					</div>
					<div id="menu">			
					</div>
				</div>
			</div>
			<div class="col-sm-4" id="right-div">
				<h1>�ֹ� ���</h1>
				<div id="order-list"></div>
				<h1>Total : <span>0</span> ��</h1>
				<a type="button" id="orderbtn" href="tablereceipt.mc" onclick="reciptlist()" class="custombtn">�ֹ��ϱ�</a>
			</div>
		</div>
	</div>
</section>