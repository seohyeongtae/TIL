<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<style>
/*------home btn Start------ */
#homebtn{
	margin-top: 10px;
	display:inline-block;
	text-align: center;
	margin-left: 50%;
}
/*------home btn end------ */
#receiptlist{
	border:1px solid gray;
	background: white;
	min-height: 50px;
	max-height: 400px;
	overflow: auto;
	text-align: right;
	padding: 5px;
}

</style>
<script>

</script>
<section class="pb_cover_v1 text-center" style="background-color: #fff5b9" id="section-table-receipt">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-sm-6" style="margin-top:50px">
				<h1>영수증</h1>
            	<div id="receiptlist">
	            	<h3 style="text-align: left"> ${receiptdata.tab_id} </h3>
	            	<c:forEach var="s" items="${saleslist }">
	            		<h3>${s.menu_id }, ${s.qt }개 ${s.s_price }원</h3>
	            	</c:forEach>
	            	<h3>------------------</h3>
	            	<h3> 총 결제금액 : ${receiptdata.total} 원</h3>
	            	<h3> 결제날짜 : ${receiptdata.regdate} </h3>
	            	<h3> 영수증 ID = ${receiptdata.id}</h3>
            	</div>
            	<p><a class="col-sm-6 custombtn" type="button" id="homebtn" href="tablehome.mc">돌아가기</a></p>
          	</div>  
        </div>
	</div>
</section>