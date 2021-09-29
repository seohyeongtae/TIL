/**
 * 
 */

	function weatherDisplay(data){
		$(data.weather).each(function(index,val){
			var result = '';
			result += val.icon; // 날씨 아이콘 코드 
			
			if (result.indexOf('01d')!=-1){
				$(".weather").attr("src","img/weather/sunny.png");
			} else if (result.indexOf('02d')!=-1){
				$(".weather").attr("src","img/weather/sunny.png");
			} else if (result.indexOf('03d')!=-1){
				$(".weather").attr("src","img/weather/cloudy.png");
			} else if (result.indexOf('04d')!=-1){
				$(".weather").attr("src","img/weather/cloudy.png");
			} else if (result.indexOf('05d')!=-1){
				$(".weather").attr("src","img/weather/rain.png");
			} else if (result.indexOf('10d')!=-1){
				$(".weather").attr("src","img/weather/rain.png");
			} else if (result.indexOf('11d')!=-1){
				$(".weather").attr("src","img/weather/storm.png");
			} else if (result.indexOf('13d')!=-1){
				$(".weather").attr("src","img/weather/snow.png");
			} else {
				$(".weather").attr("src","img/weather/cloudy.png");
			}
		});
		
	};
	
	// 날씨 내용 받아오기 *키 값 바꾸기
	function getweatherData(){
		var urlstr = 'https://api.openweathermap.org/data/2.5/weather';	
		var key = '78d1d5190dbeb7c99f48441f87367235';
		
		$.ajax({
			method:'GET',
			url:urlstr,
			dataType: 'json', 
			data:{
				'q':'seoul',  // 서울 날씨로 고정 
				'appid':key,  // *키 값
				'units':'metric'
				},
			success:function(data){
				weatherDisplay(data);
			},
			error:function(){
				alert('error g');
			}
		});
	};
	$(document).ready(function(){
		getweatherData();
	}); 
	
	