<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#center>form>p>input, textarea{
	width: 100%;
}
input, textarea{
	font-size: 20px;
	padding: 0 10px;
}
#content{
	height: 400px;
	text-align: 
}
.custombtn{
	border:0;
	padding: 0 15px;
	margin-left: 5%;
}
form{
	text-align: right;
}
</style>
<section class="pb_cover_v1 text-center" style="background-color: #fff5b9" id="section-table-order">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			<div class="col-sm-11" id="center">
				<form enctype="multipart/form-data" action="savebull.mc" method="post">
					<p><input type="text" name="title" placeholder="제목을 입력하세요."></p>
					<p><textarea rows="10" name="content" placeholder="내용을 입력하세요." id="content"></textarea></p>
					<input type="text" name="author" placeholder="익명">
					<input type="submit" value="저 장" class="custombtn">
				</form>
			</div>
		</div>
	</div>
</section>