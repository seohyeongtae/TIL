<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
#center{
	text-align: left;
	overflow: auto;
}
h1{
	margin-bottom: 15px;
}
#tables>a{
	border: 1px solid gray;
	text-decoration: none;
	display: inline-block;
	background: none;
	font-size: 40px;
	padding: 50px;
	margin: 10px;
}
#tables>a:hover{
	background: #d3cb98;
}
</style>
<section class="pb_cover_v1 text-center" style="background-color: #fff5b9" id="section-table-order">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-sm-11" id="center">
				<h1>테이블 번호를 선택해주세요.</h1>
				<div id="tables">
					<c:forEach var="t" items="${tablelist }">
						<a href="tableloginimpl.mc?id=${t.id }"><h3>${t.name } 번 테이블</h3>
						</a>
					</c:forEach>
				</div>
				
			</div>
		</div>
	</div>
</section>