package com.controller;

//import java.io.File;
import java.io.FileOutputStream;
//import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

public class Util {
	// 받아온 데이터를 서버에 이미지를 올린다.
	public static void saveFile(MultipartFile mf) {
//		File path = new File("F:\\JavaProject\\git\\RestaurantPos\\restaurantPos\\web\\img\\foods\\");/*절대값 이미지로 변경 (이나영)*/
//		URL path = Util.class.getProtectionDomain().getCodeSource().getLocation();
		String dir = "C:\\Users\\i\\git\\Admin_UI\\Admin_UI\\web\\img\\item\\";//
		// String dir2 = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/img/";
		
		byte [] data;
		String imgname = mf.getOriginalFilename();
		try {
			data = mf.getBytes();
			FileOutputStream fo = 
					new FileOutputStream(dir+imgname);
		//	FileOutputStream fo2 = 
		//			new FileOutputStream(dir2+imgname);
			fo.write(data);
		//	fo2.write(data);
			fo.close();
		//	fo2.close();
		}catch(Exception e) {
			
		}
		
	}
}
