Time,Temp
<%=reqStr %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "org.json.simple.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.IOException" %>



<%!
String reqStr = null;
%>
<%
	StringBuilder stringBuilder = new StringBuilder();
	BufferedReader bufferedReader = null;

	try {
	InputStream inputStream = request.getInputStream();
	if (inputStream != null) {
	bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	char[] charBuffer = new char[128];
	int bytesRead = -1;
	while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	stringBuilder.append(charBuffer, 0, bytesRead);
	}
	} else {
	stringBuilder.append("");
	}
	} catch (IOException ex) {
	throw ex;
	} finally {
	if (bufferedReader != null) {
	try {
	bufferedReader.close();
	} catch (IOException ex) {
	throw ex;
		}
	}
	}
	
	reqStr = stringBuilder.toString();
	Thread.sleep(3000);
	
	
	JSONParser jsonparser = new JSONParser();
	
	//스트링 템프 데이터가 들어있을꺼다 temp 안에 sysout 프린트 jo.put parsing  

	//JSONArray ja = new JSONArray();
	
	
%>


