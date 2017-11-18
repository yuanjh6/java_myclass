package com.yuan.demo.simulationLoginOn;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class ShowCookie {

	public ShowCookie() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		   // 连接地址（通过阅读html源代码获得，即为登陆表单提交的URL）  
		   HttpClient httpclient = new DefaultHttpClient();
	        // 创建一个本地Cookie存储的实例  
	        CookieStore cookieStore = new BasicCookieStore();
	        //创建一个本地上下文信息  
	        HttpContext localContext = new BasicHttpContext();
	        //在本地上下问中绑定一个本地存储  
	        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	        //设置请求的路径  
	        HttpGet httpget = new HttpGet("https://passport.baidu.com/v2/api/?login");
	        //传递本地的http上下文给服务器  
	        HttpResponse response = httpclient.execute(httpget, localContext);
	        //获取本地信息  
	        HttpEntity entity = response.getEntity();
	        System.out.println(response.getStatusLine());  
	        if (entity != null) {  
	            System.out.println("Response content length: " + entity.getContentLength());  
	        }  
	        //获取cookie中的各种信息  
	        List<Cookie> cookies = cookieStore.getCookies();
	        for (int i = 0; i < cookies.size(); i++) {  
	            System.out.println("Local cookie: " + cookies.get(i));  
	        }  
	        //获取消息头的信息  
	        Header[] headers = response.getAllHeaders();
	        for (int i = 0; i<headers.length; i++) {  
	            System.out.println(headers[i]);  
	        }  
	}

}
