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
				<h1>������</h1>
            	<div id="receiptlist">
	            	<h3 style="text-align: left"> ${receiptdata.tab_id} </h3>
	            	<c:forEach var="s" items="${saleslist }">
	            		<h3>${s.menu_id }, ${s.qt }�� ${s.s_price }��</h3>
	            	</c:forEach>
	            	<h3>------------------</h3>
	            	<h3> �� �����ݾ� : ${receiptdata.total} ��</h3>
	            	<h3> ������¥ : ${receiptdata.regdate} </h3>
	            	<h3> ������ ID = ${receiptdata.id}</h3>
            	</div>
            	<p><a class="col-sm-6 custombtn" type="button" id="homebtn" href="tablehome.mc">���ư���</a></p>
          	</div>  
        </div>
	</div>
</section>