<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<style type="text/css">	 
/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>


<script>
	function fetchdata() {
		$.ajax({
			url : 'statusToBrower.mc',
			type : 'post',
			success : function(response) {
				// Perform operation on the return value
				$('#iotStatus').html(response);
			}
		});
	}
	function fetchcmd() {
		$.ajax({
			url : 'itemToBrower.mc',
			type : 'post',
			success : function(response) {
				// Perform operation on the return value
				$('#lastScan').html("Last scanned : " + response);
			}
		});
	}
	$(document).ready(function() {
		$('#iot_s').click(function() {
			$.ajax({
				url : 'iotStart.mc',
				success : function(data) {
					alert('Send Complete...');
				}
			});
		});
		setInterval(fetchdata, 500);
		setInterval(fetchcmd, 500);
		$('#iot_t').click(function() {
			$.ajax({
				url : 'iotStop.mc',
				success : function(data) {
					alert('Send Complete...');
				}
			});
		});

		$('#phone').click(function() {
			$.ajax({
				url : 'phone.mc',
				success : function(data) {
					alert('Send Complete...');
				}
			});
		});

	});
</script>


</head>
<body>

	<h1>Main Page</h1>
	<h2>IoT장비 IP설정</h2>
	<form id="IoTsubmit" action="IoTsubmit.mc" method="post">
		<div>
			<input type="text" name="id" placeholder="/xxx.xxx.xxx.xxx">
		</div>
		<div>
			<input type="submit" value="IP등록">
		</div>
	</form>
	<h2 id="iotIP"></h2>
	<h2>
		<a id="iot_s" href="#">Start IoT(TCP/IP)</a>
	</h2>
	<h2>
		<a id="iot_t" href="#">Stop IoT(TCP/IP)</a>
	</h2>
	<h2>
		<a id="phone" href="#">Send Phone(FCM)</a>
	</h2>
	<h2>냉난방 제어</h2>
	<form action="">
		<label for="vol">세기(0 에서 5):</label> 
		<input type="range" id="vol" name="vol" min="0" max="5">
		<input type="submit">
	</form>
	<h2>전등제어</h2>
	<label class="switch">
		<input type="checkbox">
		<span class="slider"></span>
	</label>
	<h2>IoT Status</h2>
	<h3 id="iotStatus"></h3>
	<h3 id="lastScan"></h3>




</body>
</html>