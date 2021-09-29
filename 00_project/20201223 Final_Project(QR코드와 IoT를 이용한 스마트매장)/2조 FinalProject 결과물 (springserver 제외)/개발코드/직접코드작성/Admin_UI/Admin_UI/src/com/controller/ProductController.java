package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dao.OrderlistDao;
import com.dao.OrdersDao;
import com.frame.Biz;
import com.vo.Items;
import com.vo.Orderlist;
import com.vo.Orders;

@Controller
public class ProductController {
	

	// 메뉴 리스트 데이터 (items)
	@RequestMapping("/menulist.mc")
	@ResponseBody
	public void getmenulist(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String cate = request.getParameter("cate").toString();
		String url = "http://192.168.0.119:8080/items";
		HttpConnect httpconnect = new HttpConnect();
		
		String result = httpconnect.getString(url);
		JSONArray ja = new JSONArray();	
		
		// String  JSON 형식으로 변환
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		ja = (JSONArray) obj;


        // 전체 메뉴를 선택했을 경우 / 처음 화면
		if (cate.equals("0")) {
				res.setContentType("text/json;charset=utf-8");
				PrintWriter out;
		        out = res.getWriter();
		        out.print(ja.toJSONString());
		        out.close();
		// 과자를 선택했을 경우 
		}else if(cate.equals("1")) {
			 JSONArray ja2 = new JSONArray(); 
			 ArrayList<Items> items = new ArrayList();
				try {
	                for(int i =0; i <ja.size(); i++){
	                    JSONObject jo = (JSONObject) ja.get(i);
	                    String name = (String) jo.get("itemname");
	                    int price = (int) Integer.parseInt(jo.get("price").toString());
	                    int stock = (int) Integer.parseInt(jo.get("stock").toString());
	                    String category = (String) jo.get("category");
	                    String img = (String) jo.get("image");
	                    
	                    if(category.equals("과자")) {
	                    	JSONObject jo2 = (JSONObject) ja.get(i);
	                    	ja2.add(jo2);
	                    	}
	                    
	                	}
	                	res.setCharacterEncoding("UTF-8");
	    		        res.setContentType("application/json");
	    		        PrintWriter out = res.getWriter();
	    		        out.print(ja2.toJSONString());
	    		        out.close();
	                    	                
	            } catch (Exception e) {
	                e.printStackTrace();
			
	            }
				// 음료수 선택했을 경우
		}else if(cate.equals("2")) {
			 JSONArray ja2 = new JSONArray(); 
			 ArrayList<Items> items = new ArrayList();
				try {
	                for(int i =0; i <ja.size(); i++){
	                    JSONObject jo = (JSONObject) ja.get(i);
	                    String name = (String) jo.get("itemname");
	                    int price = (int) Integer.parseInt(jo.get("price").toString());
	                    int stock = (int) Integer.parseInt(jo.get("stock").toString());
	                    String category = (String) jo.get("category");
	                    String img = (String) jo.get("image");
	                    
	                    if(category.equals("음료수")) {
	                    	JSONObject jo2 = (JSONObject) ja.get(i);
	                    	ja2.add(jo2);
	                    	}
	                    
	                	}
	                	res.setCharacterEncoding("UTF-8");
	    		        res.setContentType("application/json");
	    		        PrintWriter out = res.getWriter();
	    		        out.print(ja2.toJSONString());
	    		        out.close();
	                    	                
	            } catch (Exception e) {
	                e.printStackTrace();
			
	            }
			// 생필품 선택했을 경우
		}else if(cate.equals("3")) {
			 JSONArray ja2 = new JSONArray(); 
			 ArrayList<Items> items = new ArrayList();
				try {
	                for(int i =0; i <ja.size(); i++){
	                    JSONObject jo = (JSONObject) ja.get(i);
	                    String name = (String) jo.get("itemname");
	                    int price = (int) Integer.parseInt(jo.get("price").toString());
	                    int stock = (int) Integer.parseInt(jo.get("stock").toString());
	                    String category = (String) jo.get("category");
	                    String img = (String) jo.get("image");
	                    
	                    if(category.equals("생필품")) {
	                    	JSONObject jo2 = (JSONObject) ja.get(i);
	                    	ja2.add(jo2);
	                    	}
	                    
	                	}
	                	res.setCharacterEncoding("UTF-8");
	    		        res.setContentType("application/json");
	    		        PrintWriter out = res.getWriter();
	    		        out.print(ja2.toJSONString());
	    		        out.close();
	                 	                
	            } catch (Exception e) {
	                e.printStackTrace();
			
	            }
			// 신선식품 선택했을 경우
		}else if(cate.equals("4")) {
			 JSONArray ja2 = new JSONArray(); 
			 ArrayList<Items> items = new ArrayList();
				try {
	                for(int i =0; i <ja.size(); i++){
	                    JSONObject jo = (JSONObject) ja.get(i);
	                    String name = (String) jo.get("itemname");
	                    int price = (int) Integer.parseInt(jo.get("price").toString());
	                    int stock = (int) Integer.parseInt(jo.get("stock").toString());
	                    String category = (String) jo.get("category");
	                    String img = (String) jo.get("image");
	                    
	                    if(category.equals("신선식품")) {
	                    	JSONObject jo2 = (JSONObject) ja.get(i);
	                    	ja2.add(jo2);
	                    	}
	                    
	                	}
	                	res.setCharacterEncoding("UTF-8");
	    		        res.setContentType("application/json");
	    		        PrintWriter out = res.getWriter();
	    		        out.print(ja2.toJSONString());
	    		        out.close();
	                    	                
	            } catch (Exception e) {
	                e.printStackTrace();
			
	            }
				
			// 부족재고 선택했을 경우
		}else if(cate.equals("5")) {
			 JSONArray ja2 = new JSONArray(); 
			 ArrayList<Items> items = new ArrayList();
				try {
	                for(int i =0; i <ja.size(); i++){
	                    JSONObject jo = (JSONObject) ja.get(i);
	                    String name = (String) jo.get("itemname");
	                    int price = (int) Integer.parseInt(jo.get("price").toString());
	                    int stock = (int) Integer.parseInt(jo.get("stock").toString());
	                    String category = (String) jo.get("category");
	                    String img = (String) jo.get("image");
	                    
	                    if(stock <= 10) {
	                    	JSONObject jo2 = (JSONObject) ja.get(i);
	                    	ja2.add(jo2);
	                    	}
	                    
	                	}
	                	res.setCharacterEncoding("UTF-8");
	    		        res.setContentType("application/json");
	    		        PrintWriter out = res.getWriter();
	    		        out.print(ja2.toJSONString());
	    		        out.close();
	                    	                
	            } catch (Exception e) {
	                e.printStackTrace();
			
	            }
		}
		
		
		
	} // getmenulist end
	
	@RequestMapping("/editmenu.mc")
	public String editmenu(HttpServletRequest request, Items editmenudata)  {
		
		
		HttpConnectionPut httpconnectionput = new HttpConnectionPut();
		HttpConnectionUtil httpconnectionutil = new HttpConnectionUtil();
		

		String itemname = editmenudata.getItemname();
		int stock = editmenudata.getStock();
		int price = editmenudata.getPrice();
		String category = editmenudata.getCategory();
		String image= editmenudata.getImage();

		String newimgname = editmenudata.getMf().getOriginalFilename();
		
		String url = "http://192.168.0.119:8080/items/"+itemname;

		if(image != newimgname && newimgname.equals("") ) {
	
			 JSONObject jsonObject = new JSONObject();
			try {
					// jsonObject.put("itemname", itemname);
		            jsonObject.put("price", price);
		            jsonObject.put("stock", stock);
		            jsonObject.put("category", category);
		            jsonObject.put("image", image);
//		            System.out.println(jsonObject.toJSONString()+"editeditjsonehrwoejrwoeirjewoiq");
		            //httpconnectionput.postRequest(url, jsonObject.toJSONString());
		            httpconnectionput.postRequest(url, jsonObject.toJSONString());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			 JSONObject jsonObject = new JSONObject();
			try {
					jsonObject.put("itemname", itemname);
		            jsonObject.put("price", price);
		            jsonObject.put("stock", stock);
		            jsonObject.put("category", category);
		            jsonObject.put("image", image);
		            httpconnectionput.postRequest(url, jsonObject.toJSONString());
		            Util.saveFile(editmenudata.getMf());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:product.mc";
	}; //edit menu end
		
	@RequestMapping("/addmenu.mc")
	public String addmenu(HttpServletRequest request, Items editmenudata)  {
		
		
		HttpConnectionPut httpconnectionput = new HttpConnectionPut();
		HttpConnectionUtil httpconnectionutil = new HttpConnectionUtil();
		
		String itemname = editmenudata.getItemname();
		int stock = editmenudata.getStock();
		int price = editmenudata.getPrice();
		String category = editmenudata.getCategory();
		String image= editmenudata.getImage();

		String newimgname = editmenudata.getMf().getOriginalFilename();
		
		
		String url = "http://192.168.0.119:8080/items/";

		if(! newimgname.equals("") ) {
	
			 JSONObject jsonObject = new JSONObject();
			try {
					jsonObject.put("itemname", itemname);
		            jsonObject.put("price", price);
		            jsonObject.put("stock", stock);
		            jsonObject.put("category", category);
		            jsonObject.put("image", newimgname);
		            
		            //httpconnectionput.postRequest(url, jsonObject.toJSONString());
		            httpconnectionutil.postRequest(url, jsonObject.toJSONString());
		            Util.saveFile(editmenudata.getMf());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			 JSONObject jsonObject = new JSONObject();
			try {
					jsonObject.put("itemname", itemname);
		            jsonObject.put("price", price);
		            jsonObject.put("stock", stock);
		            jsonObject.put("category", category);
		            jsonObject.put("image", "null.jpg");
		            httpconnectionutil.postRequest(url, jsonObject.toJSONString());
		            
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:product.mc";
	}; //edit menu end
	
	@RequestMapping("/delmenu.mc")
	public String deletemenu(HttpServletRequest request, Items editmenudata)  {
		
		
		HttpConnectionDel httpconnectiondel = new HttpConnectionDel();
		
		

		String itemname = editmenudata.getItemname();
		int stock = editmenudata.getStock();
		int price = editmenudata.getPrice();
		String category = editmenudata.getCategory();
		String image= editmenudata.getImage();

		String newimgname = editmenudata.getMf().getOriginalFilename();
		
		String url = "http://192.168.0.119:8080/items/"+itemname;

		            //httpconnectionput.postRequest(url, jsonObject.toJSONString());
		httpconnectiondel.postRequest(url);
	
		return "redirect:product.mc";
	}; //edit menu end
	
	
	@RequestMapping("/stockorder.mc")
	public String stockorder(HttpServletRequest request, Items editmenudata)  {
		
		
		HttpConnectionPut httpconnectionput = new HttpConnectionPut();
		
		String itemname = editmenudata.getItemname();
		int stock = editmenudata.getStock();
		
		JSONObject jsonObject = new JSONObject();
		 
		jsonObject.put("orderStock", stock);
		
		
		String url = "http://192.168.0.119:8080/stockorder/"+itemname;
//		System.out.println(url+"urlrulrusljfljlksadf");
//		System.out.println(jsonObject.toJSONString()+"jsonehrwoejrwoeirjewoiq");
		            //httpconnectionput.postRequest(url, jsonObject.toJSONString());
		httpconnectionput.postRequest(url,jsonObject.toJSONString());
	
		return "redirect:product.mc";
	}; //edit menu end
	
	
	
	// 메뉴 리스트 데이터 (items)
	@RequestMapping("/refundlist.mc")
	@ResponseBody
	public void refundlist(HttpServletRequest request, HttpServletResponse res) throws Exception {

		String url = "http://multicampus.xyz:8080/demo/refund/";
		HttpConnect httpconnect = new HttpConnect();
		
		String result = httpconnect.getString(url);
		JSONArray ja = new JSONArray();	
		
		// String  JSON 형식으로 변환
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		ja = (JSONArray) obj;

		res.setContentType("text/json;charset=utf-8");
		PrintWriter out;
        out = res.getWriter();
        out.print(ja.toJSONString());
        out.close();
	
	}
	
	

} // Class end
