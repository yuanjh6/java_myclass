package com.yuan.web.proxy;

import com.yuan.tools.HttpClientTool;
import com.yuan.tools.JsoupTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProxyTool {
	private static Logger logger = LogManager.getLogger(ProxyTool.class
			.getName());
	int URLTIMEOUT = 5000;
	String TESTURL = "http://www.baidu.com/";
	String TESTURLCHARSET = "utf-8";

	public ProxyTool() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProxyTool toolbox = new ProxyTool();
//		toolbox.getProxys("conf/ip");
		toolbox.testProxy("conf/ip");
		System.out.println("ok");

	}

	private boolean getProxys(String file) {
		ArrayList<Map<String, String>> ALMap = new ArrayList<Map<String, String>>();
		// 参数保存

		Map<String,String> map;
		map = new HashMap<String, String>();
		map.put("url", "http://pachong.org/");
		map.put("charset", "utf-8");
		map.put("eleSelect", "table.tb>tbody>tr");
		map.put("ip", "td:contains(.),text");
		map.put("port", "td:matches(^\\d+$),text");
		ALMap.add(map);
		
		map = new HashMap<String, String>();
		map.put("url", "http://pachong.org/anonymous.html");
		map.put("charset", "utf-8");
		map.put("eleSelect", "table.tb>tbody>tr");
		map.put("ip", "td:contains(.),text");
		map.put("port", "td:matches(^\\d+$),text");
		ALMap.add(map);

		map = new HashMap<String, String>();
		map.put("url", "http://pachong.org/transparent.html");
		map.put("charset", "utf-8");
		map.put("eleSelect", "table.tb>tbody>tr");
		map.put("ip", "td:contains(.),text");
		map.put("port", "td:matches(^\\d+$),text");
		ALMap.add(map);

		map = new HashMap<String, String>();
		map.put("url", "http://51dai.li/http_fast.html");
		map.put("charset", "utf-8");
		map.put("eleSelect", "div#tb>table>tbody>tr");
		map.put("ip", "td:contains(.),text");
		map.put("port", "td:matches(^\\d+$),text");
		ALMap.add(map);
		
//		map = new HashMap<String, String>();
//		map.put("url", "http://51dai.li/http_anonymous.html");
//		map.put("charset", "utf-8");
//		map.put("eleSelect", "div#tb>table>tbody>tr");
//		map.put("ip", "td:contains(.),text");
//		map.put("port", "td:matches(^\\d+$),text");
//		ALMap.add(map);
		
//		map = new HashMap<String, String>();
//		map.put("url", "http://51dai.li/http_non_anonymous.html");
//		map.put("charset", "utf-8");
//		map.put("eleSelect", "div#tb>table>tbody>tr");
//		map.put("ip", "td:contains(.),text");
//		map.put("port", "td:matches(^\\d+$),text");
//		ALMap.add(map);
		
//		map = new HashMap<String, String>();
//		map.put("url", "http://www.proxy360.cn/Region/China");
//		map.put("charset", "utf-8");
//		map.put("eleSelect", "div.proxylistitem");
//		map.put("ip", "div>span.tbBottomLine,text");
//		map.put("port", "div>span.tbBottomLine:matches(^\\d+$),text");
//		ALMap.add(map);

		String url,charset,eleSelect,html;
		ArrayList<Map<String, String>> ItemsInfo = new ArrayList<Map<String, String>>();
		for (Map<String, String> cMap : ALMap) {
			if(!cMap.containsKey("url")||!cMap.containsKey("charset")||!cMap.containsKey("eleSelect")){
				logger.debug("配置map中缺失必须参数");
				continue;
			}
			url=cMap.get("url").trim();
			cMap.remove("url");
			charset=cMap.get("charset").trim();
			cMap.remove("charset");
			eleSelect=cMap.get("eleSelect").trim();
			cMap.remove("eleSelect");
			
			html = HttpClientTool.getMethod(url, charset);
			if (html == null||html.isEmpty()) {
				logger.debug("网页获取失败 " + url);
				continue;
			}

			List<Map<String, String>> list = JsoupTool.itemsInfo(html, eleSelect,
					cMap);
			if (list != null && !list.isEmpty()) {
				ItemsInfo.addAll(list);
				logger.info("网页:"+url+" 获取代理数量:"+list.size());
			}
		}
		//去重复
		Set<String> proxySet=new HashSet<String>();
		for (Map<String, String> tmap : ItemsInfo) {
			if(tmap==null||tmap.isEmpty()||!tmap.containsKey("ip")||!tmap.containsKey("port"))continue;
			proxySet.add(tmap.get("ip").trim() + ":" + tmap.get("port").trim());
		}
	

		//写入文件
		BufferedWriter br;
		int count=0;
		try {
			br = new BufferedWriter(new FileWriter(file));
			for (String s:proxySet) {
				br.write(s+"\n");
				count++;
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("获取总代理数量:"+ItemsInfo.size()+" 去重后数量:"+proxySet.size()+" 写入代理数量:"+count);
		return true;
	}

	private boolean testProxy(String ipFile) {
		Set<String> ipset = new HashSet<String>();
		int maxIp = 1;
		Map<Integer, String> IpMap = new HashMap<Integer, String>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(ipFile));
			String line = null;

			while ((line = br.readLine()) != null) {
				line = line.trim();
				// 过滤注释及不规范内容
				if (!line.startsWith(";") && line.contains(":")
						&& !ipset.contains(line)) {

					IpMap.put(maxIp++, line);
					ipset.add(line);
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		// 已经读入的旧代理文件改名
		File file = new File(ipFile);
		File newfile = new File(ipFile + ".old");
		if (newfile.exists()) {
			newfile.delete();
		}
		file.renameTo(newfile);

		String proxy;

		Iterator<Map.Entry<Integer, String>> it = IpMap.entrySet().iterator();
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(ipFile));

			while (it.hasNext()) {
				// 获取代理服务器
				Map.Entry<Integer, String> entry = it.next();
				proxy = entry.getValue();
				String[] proxyInfo = proxy.split(":", 2);
				if (proxyInfo.length < 2)
					continue;
				SocketAddress addr = new InetSocketAddress(proxyInfo[0],
						Integer.parseInt(proxyInfo[1]));

				try {
					URL url = new URL(TESTURL);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection(new Proxy(Proxy.Type.HTTP, addr));
					conn.setConnectTimeout(URLTIMEOUT);
					conn.setReadTimeout(URLTIMEOUT);
					// 判断是否连接成功
					// BufferedReader in = new BufferedReader(
					// new InputStreamReader(conn.getInputStream(),
					// TESTURLCHARSET));
					//
					// String line, html="";
					// while((line=in.readLine())!=null){
					// html+=line;
					// }
					// if(html.indexOf("百度")>0){
					// bw.write(entry.getValue() + "\n");
					// bw.flush();
					// }

					conn.connect();
					int status = conn.getResponseCode();
					switch (status) {
					case java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT:// 504
						logger.debug("连接网址从超时间 " + proxy);
						break;
					case java.net.HttpURLConnection.HTTP_FORBIDDEN:// 403
						logger.debug("连接网站禁止 " + proxy);
						break;
					case java.net.HttpURLConnection.HTTP_INTERNAL_ERROR:// 500
						logger.debug("连接网址错误或者不存在 " + proxy);
						break;
					case java.net.HttpURLConnection.HTTP_NOT_FOUND:// 404
						logger.debug("连接网址不存在 " + proxy);
						break;
					case java.net.HttpURLConnection.HTTP_OK:
						bw.write(entry.getValue() + "\n");
						bw.flush();
						break;
					}
					conn.disconnect();
				} catch (IOException e1) {
					// 一旦出错认为代理服务器无效
					e1.printStackTrace();
				}

			}
			bw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
