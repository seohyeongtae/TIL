package com.fcm;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;


@WebServlet({ "/Ftest", "/ftest" })
public class Fcm extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Fcm() {
        super();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			URL url = null;
			try {
				url = new URL("https://fcm.googleapis.com/fcm/send");
			} catch (MalformedURLException e) {
				System.out.println("Error while creating Firebase URL | MalformedURLException");
				e.printStackTrace();
			}
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {
				System.out.println("Error while createing connection with Firebase URL | IOException");
				e.printStackTrace();
			}
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			// set my firebase server key
			conn.setRequestProperty("Authorization", "key="
					+ "AAAAHBSezUM:APA91bFYQil3eNOyKtk780M880TtG2y5c1FgaP346fc49OAnDNuj1NnRRl9r_02Z64t8dMEASes40JxAOKaeBiQvOC7fDA1DxZg5qZj-jkoEoEFgodjMVUETsimil6jKpl2GU2S_dS8B");

			// create notification message into JSON format
			JSONObject message = new JSONObject();
			message.put("to", "/topics/car");
			message.put("priority", "high");
			
			JSONObject notification = new JSONObject();
			notification.put("title", "Hi,there");
			notification.put("body", "This is body text");
			message.put("notification", notification);
			
			JSONObject data = new JSONObject();
			data.put("control", "control1");
			data.put("data", 100);
			data.put("hi", "Hi, there");
			message.put("data", data);


			try {
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				out.write(message.toString());
				out.flush();
				conn.getInputStream();
				System.out.println("OK...............");

			} catch (IOException e) {
				System.out.println("Error while writing outputstream to firebase sending to ManageApp | IOException");
				e.printStackTrace();
			}	
				
	}

}
