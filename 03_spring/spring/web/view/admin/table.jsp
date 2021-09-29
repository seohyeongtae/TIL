<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<style>
/*------center div Start------ */
#center{
	margin-bottom:15px;
}
#center>h1{
	text-align: left;
}
#center> div{
	margin: 10px auto;
	text-align: left;
}

#tables{
	background: white;
	padding:5px;
	border: 2px solid gray;
	height: 250px;
	overflow: auto;
}
.custombtn{
	border:0;
	padding: 5px;
}
table td{
	text-align: left;
	color: black;
	font-size: 20px;
	padding-left: 10px;
}
/*------center div end------ */

</style>
<script>
 function choose(id,pwd,name,num){
	 $('#edittable>tbody>tr>td>#tid').val(id);
	 $('#edittable>tbody>tr>td>#tpwd').val(pwd);
	 $('#edittable>tbody>tr>td>#tname').val(name);
	 $('#edittable>tbody>tr>td>#tnum').val(num);
 };
</script>
<section class="pb_section" style="background-color: #fff5b9" id="section-table-order">
	<div class="container align-items-center justify-content-center"" style="overflow: auto">
		<div class="row">
			<div class="col-sm-12" id="center">
				<h1>테이블</h1>
				<div id="tables">
					<c:forEach var="t" items="${tabledatas }">
						<h3><a type="button" href="javascript:void(0);" onclick="choose('${t.id }','${t.pwd }',${t.name },${t.num });" >ID: ${t.id }, PWD: ${t.pwd }, 이름(번호): ${t.name }, 수용인원: ${t.num }</a></h3>
					</c:forEach>
				</div>
				<div>
					<h3>수정하기</h3>
					<form action="edittable.mc" method="post">
						<table id="edittable">
							<thead><tr>
								<td>ID</td><td>PWD</td><td>이름(번호)</td><td>수용인원</td><td></td>
							</tr></thead>
							<tbody><tr>
								<td><input type="text" name="id" size=10 readonly="readonly" id="tid"></td>
								<td><input type="text" name="pwd" size=10 id="tpwd"></td>
								<td><input type="number" name="name" size=10 style="width: 100px" id="tname"></td>
								<td><input type="number" name="num" size=5 style="width: 100px" id="tnum"></td>
								<td><input type="text" name="admin_id" value="${logintable.id }" style="display:none">
									<input type="submit" value="edit" class="custombtn" id="edit"></td>
							</tr></tbody>
						</table>
					</form>
					<h3>추가하기</h3>
					<form action="addtable.mc" method="post">
						<table id="addtable">
							<thead><tr>
								<td>ID</td><td>PWD</td><td>이름(번호)</td><td>수용인원</td><td></td>
							</tr></thead>
							<tbody><tr>
								<td><input type="text" name="id" size=10 ></td>
								<td><input type="text" name="pwd" size=10></td>
								<td><input type="number" name="name" size=10 style="width: 100px"></td>
								<td><input type="number" name="num" size=5 style="width: 100px"></td>
								<td><input type="text" name="admin_id" value="${logintable.id }" style="display:none">
									<input type="submit" value="add" class="custombtn" id="add"></td>
							</tr></tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>