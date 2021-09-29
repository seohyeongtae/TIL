<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
#center{
	text-align: left;
}
#center>h2{
	display: inline-block;
	text-decoration: none;
	border: 1px solid gray;
}
</style>

<section class="pb_cover_v1 text-center" style="background-color: #fff5b9" id="section-table-order">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-sm-11" id="center">
				<h1>테이블 선택</h1>
				<c:forEach var="t" items="${tablelist }">
					<h2><a href="tablehome.mc?id=${t.name }">${t.name }번 테이블</a></h2>
				</c:forEach>
			</div>
		</div>
	</div>
</section>