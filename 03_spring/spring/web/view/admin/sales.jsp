<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
.month{
	background: none;
	outline: 0;
	border: 0;
}
#p_chart{
	background: white;
	height: 300px;
}
h3{
	margin-top:20px;
}
</style>

<section class="pb_section" style="background-color: #fff5b9" id="section-home">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-sm-12" style="text-align: left">
				<h1>매출 관리</h1>
				<h3>월 별 <button class="month" id="month-b">◀</button><span> 8</span>월 <button class="month" id="month-a">▶</button></h3>
				<div id="p_chart"></div>
				<h3>금일 매출액<br> : <span>0</span>원</h3>
			</div>
		</div>
	</div>
</section>