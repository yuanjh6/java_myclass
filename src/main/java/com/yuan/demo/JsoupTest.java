package com.yuan.demo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Example program to list links from a URL.
 */
public class JsoupTest {
	private static int URLTIMEOUT=5000;
    public static void main(String[] args) throws IOException {
        String url ="http://www.oschina.net";
        List<String> urls=new ArrayList<String>();

        Document doc =  Jsoup.connect(url).userAgent("I ’ m jsoup") // 设置 User-Agent
				.cookie("auth", "token") // 设置 cookie
				.timeout(URLTIMEOUT) // 设置连接超时时间
				.get();
       
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        Elements links = doc.select("a[href]");

        for (Element src : media) {
            urls.add(src.tagName()+":"+src.attr("abs:src"));
        }

        for (Element link : imports) {
            urls.add(link.tagName()+":"+link.attr("abs:href"));
        }

        for (Element link : links) {
            urls.add("a:"+link.attr("abs:href"));
        }
        for(String s:urls){
        	System.out.println(s);
        }
    }
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}