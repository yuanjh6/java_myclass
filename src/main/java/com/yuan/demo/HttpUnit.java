package com.yuan.demo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;




public class HttpUnit {

	public HttpUnit() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.CHROME_16);    
		webClient.setCssEnabled(false);
		webClient.setJavaScriptEnabled(true);
		webClient.setThrowExceptionOnScriptError(false);
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
	
		//获取某网站页面  
		HtmlPage page = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		 
		
//		 page = webClient.getPage("http://www.sogou.com");
//		 page = webClient.getPage("http://www.baidu.com");
		 page = webClient.getPage("http://list.lufax.com/list/listing");
		 
		 String pageAsXml = page.asXml();
		 webClient.closeAllWindows();
		 System.out.println(pageAsXml.toString());
	}

}
