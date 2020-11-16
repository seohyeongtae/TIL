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
		String dir = "F:\\JavaProject\\git\\RestaurantPos\\restaurantPos\\web\\img\\foods\\";//
		
		byte [] data;
		String imgname = mf.getOriginalFilename();
		try {
			data = mf.getBytes();
			FileOutputStream fo = 
					new FileOutputStream(dir+imgname);
			fo.write(data);
			fo.close();
		}catch(Exception e) {
			
		}
		
	}
	
}




