<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>JavaScript Test</title>
		<!-- Layer Popup ?ㅼ젙 ?쒖옉 -->
		<style>
			#popup{
				position:absolute;
				left:50%;
				top:50%;
				margin-left:-150px;
				margin-top:-100px;
				border:2px solid #c4c4c4;
				width:300px;
				height:200px;
			}			
		</style>
		<!-- Layer Popup ?ㅼ젙 ??-->
		
		<script type="text/javascript" src="SessionCheck.js"></script>
	</head>
	<!--counter_init() ?⑥닔???먮룞濡쒓렇?꾩썐?덈궡url???꾨떖  -->
	<body onload="counter_init('../MemberLogoutInfo.me')"> 
	    <!-- 硫붿씤?붾㈃??Timer瑜?蹂댁뿬二쇰뒗 ?ㅼ젙 -->
	    <span id="counter"></span><input type="button" value="?곗옣" onclick="counter_reset()">
		
		<!-- Layer Popup ?ㅼ젙 ?쒖옉, ?몄뀡??醫낅즺???쒖젏???붾㈃??蹂댁씤?? -->
		<!-- div id="popup" style="visibility:hidden" -->
		<div id="popup" style="display:none"> <!-- 珥덇린?붾㈃?먯꽌??蹂댁뿬以??꾩슂媛 ?놁쑝???꾩삁 ?앹꽦???섏? ?딅룄濡??쒕떎. -->
			<div style="width:300px;height:170px" align="center"><br>
				<!-- ?앹뾽?댁슜 -->
				<span id="ncounter"></span> ???먮룞 濡쒓렇?꾩썐 ?⑸땲?? <br><br>
				<input type="button" value="?곗옣?섍린" onclick="counter_reset(), close_window()">
				<input type="button" value="濡쒓렇?꾩썐" onclick="logout('../MemberLogout.me')">
			</div>
		<div>
		<!-- Layer Popup ?ㅼ젙 ??-->
	</body>
</html>