<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<style>
#login{
	font-size: 25px;
	text-decoration: none;
	border: 0;
	padding: 5px 20px;
}
#field{
	border: 3px solid #e9856e;
	padding: 20px;
}
#field>p{
	font-size: 20px;
	text-align: right;
	color: black;
}
#field>legend{
	color: black;
	font-size: 40px;
	text-align: left;
	padding-left: 10px
}
h1{
	display:inline;
	text-align: left;
}

</style>

<section class="pb_cover_v1 text-center" style="background-color: #fff5b9" id="section-table-order">
	<div class="container">
		<div class="row align-items-center justify-content-center">
			
			<form action="loginimpl.mc" method="post">
				<fieldset id="field">
					<legend>Login</legend>
					<p>ID  <input type="text" name="id" value="admin01">
					<p>PWD  <input type="password" name="pwd" value="pwd01"><br></p>
					<input type="submit" value="LOGIN" class="custombtn" id="login"><br>
				</fieldset>
			</form>
		</div>
	</div>
</section>