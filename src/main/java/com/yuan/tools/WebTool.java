package com.yuan.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebTool {

	private static final int URLTIMEOUT = 5000;
	private static Logger logger = LogManager
			.getLogger(WebTool.class.getName());


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		Map<String, String> map = new HashMap<String, String>();
//		map.put("urlFilterReg", "www.baidu.com/s\\?wd=");
//		map.put("charset", "utf-8");
//		map.put("eleSelect", "td.f");
//		map.put("constant", "ptitle:$title,pt:pt");
//		map.put("attrSelect",
//				"url:{a,attr,href},title:{a,text},info:{font,text},time:{span.g,text}");
//		for (Map<String, String> tmap : getWebpageElementInfo(
//				"http://www.baidu.com/s?wd=123", map)) {
//			System.out.println(tmap);
//		}

		
	}

//	/*
//	 * 提取网页中的元信息 参数说明： url：待提取网页的url信息
//	 * param:{urlFilterReg,charset,eleSelect,attrSelect,constant}
//	 * urlFilterReg:本规则适用的url的正则表达式，可空 charset:网页编码，可空
//	 * eleSelect:元素选择规则，参照jsoup的元素选择器 attrSelect:提取内容名称:{子元素原则参数,函数名称[,参数]}
//	 * constant:常量部分$开头表网页常量，否则为普通常量 举例:百度搜索结果页面： map.put("urlFilterReg",
//	 * "www.baidu.com/s\\?wd="); map.put("charset", "utf-8");
//	 * map.put("eleSelect", "td.f"); map.put("constant", "ptitle:$title,pt:pt");
//	 * map.put("attrSelect",
//	 * "url:{a,attr,href},title:{a,text},info:{font,text},time:{span.g,text}");
//	 */
//	public static List<Map<String, String>> getWebpageElementInfo(String url,
//			Map<String, String> param) {
//		// 参数检查赋值
//		String urlFilter = null, charset = null, eleSelect = null, attrSelect = null, constant = null;
//
//		if (!param.containsKey("eleSelect") || !param.containsKey("attrSelect")) {
//			logger.error("缺少必须参数,返回Null");
//			return null;
//		} else {
//			eleSelect = param.get("eleSelect");
//			attrSelect = param.get("attrSelect");
//		}
//		if (param.containsKey("urlFilterReg")) {
//			urlFilter = param.get("urlFilterReg");
//		}
//		if (param.containsKey("charset")) {
//			charset = param.get("charset");
//		} else {
//			charset = getCharset(url);
//		}
//		if (param.containsKey("constant")) {
//			constant = param.get("constant");
//		}
//
//		// 判断是否符合使用条件
////		String tmpurl = url;
//		if (urlFilter != null) {
//			Pattern p = Pattern.compile(urlFilter);
//			Matcher m = p.matcher(url);
//			if (!m.find()) {
//				logger.error("url格式不匹配，返回null");
//				return null;
//			}
//		}
//
//		// 获取网页
//		Document doc = null;
//		try {
//			doc = Jsoup.connect(url).userAgent("I ’ m jsoup") // 设置 User-Agent
//					.cookie("auth", "token") // 设置 cookie
//					.timeout(URLTIMEOUT) // 设置连接超时时间
//					.get();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//
//		// 常量赋值
//		Map<String, String> constantMap = null;
//		if (constant != null) {
//			constantMap = new HashMap<String, String>();
//			String[] tmpt;
//			String value;
//			for (String tmps : constant.split(",")) {
//				tmpt = tmps.split(":", 2);
//				if (tmpt.length < 2 || tmpt[0] == null || tmpt[1] == null) {
//					continue;
//				}
//				switch (tmpt[1]) {
//				case "$title":
//					value = doc.title();
//					break;
//				default:
//					value = tmpt[1];
//
//				}
//				constantMap.put(tmpt[0].trim(), value.trim());
//
//			}
//		}
//
//		// 将attrSelect保存为map格式
//		Map<String, String> attrMap = new HashMap<String, String>();
//		String[] tmpAttr;
//		for (String attrStr : attrSelect.split("},")) {
//			if (attrStr != null && attrStr.contains(":")) {
//				tmpAttr = attrStr.split(":", 2);
//				tmpAttr[1] = tmpAttr[1].replaceAll("\\{|\\}", "");
//
//				if (tmpAttr.length == 2 && tmpAttr[0] != null
//						&& tmpAttr[1] != null) {
//					attrMap.put(tmpAttr[0].trim(), tmpAttr[1].trim());
//				}
//			}
//		}
//		// System.out.println(attrMap);
//
//		// 获取元素
//		Elements elements = doc.select(eleSelect);
//		Map<String, String> item;
//		List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
//		String key = null, value = null;
//		String[] tmpvalue;
//		Element tmpElement;
//		for (Element element : elements) {
//			item = new HashMap<String, String>();
//			if (constantMap != null)
//				item.putAll(constantMap);
//
//			for (Map.Entry<String, String> entry : attrMap.entrySet()) {
//				key = entry.getKey();
//				tmpvalue = entry.getValue().split(",");
//				if (tmpvalue.length < 2 || tmpvalue[0] == null
//						|| tmpvalue[1] == null) {
//					continue;
//				}
//				tmpElement = element.select(tmpvalue[0]).first();
//				if (tmpElement == null)
//					continue;
//				// 长度为二第二项意义为函数,长度为第三项意义为函数参数
//
//				switch (tmpvalue[1]) {
//				case "text":
//					value = tmpElement.text();
//					break;
//				case "outerHtml":
//					value = tmpElement.outerHtml();
//					break;
//				case "html":
//					value = tmpElement.html();
//					break;
//				case "attr":
//					value = tmpElement.attr(tmpvalue[2]);
//					break;
//				default:
//					;
//				}
//				if (value != null) {
//					item.put(key.trim(), value.trim());
//				}
//			}
//
//			itemList.add(item);
//		}
//
//		return itemList;
//	}
//	
//	
//	public static List<Map<String, String>> getElementInfo(String html,
//			String eleSelect,String attrSelect) {
//		// 获取网页对象
//		Document doc = Jsoup.parse(html);
//
//		// 将attrSelect保存为map格式
//		Map<String, String> attrMap = new HashMap<String, String>();
//		String[] tmpAttr;
//		for (String attrStr : attrSelect.split("},")) {
//			if (attrStr != null && attrStr.contains(":")) {
//				tmpAttr = attrStr.split(":", 2);
//				tmpAttr[1] = tmpAttr[1].replaceAll("\\{|\\}", "");
//
//				if (tmpAttr.length == 2 && tmpAttr[0] != null
//						&& tmpAttr[1] != null) {
//					attrMap.put(tmpAttr[0].trim(), tmpAttr[1].trim());
//				}
//			}
//		}
//		// System.out.println(attrMap);
//
//		// 获取元素
//		Elements elements = doc.select(eleSelect);
//		Map<String, String> item;
//		List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
//		String key = null, value = null;
//		String[] tmpvalue;
//		Element tmpElement;
//		for (Element element : elements) {
//			item = new HashMap<String, String>();
//
//			for (Map.Entry<String, String> entry : attrMap.entrySet()) {
//				key = entry.getKey();
//				tmpvalue = entry.getValue().split(",");
//				if (tmpvalue.length < 2 || tmpvalue[0] == null
//						|| tmpvalue[1] == null) {
//					continue;
//				}
//				tmpElement = element.select(tmpvalue[0]).first();
//				if (tmpElement == null)
//					continue;
//				// 长度为二第二项意义为函数,长度为第三项意义为函数参数
//
//				switch (tmpvalue[1]) {
//				case "text":
//					value = tmpElement.text();
//					break;
//				case "outerHtml":
//					value = tmpElement.outerHtml();
//					break;
//				case "html":
//					value = tmpElement.html();
//					break;
//				case "attr":
//					value = tmpElement.attr(tmpvalue[2]);
//					break;
//				default:
//					;
//				}
//				if (value != null) {
//					item.put(key.trim(), value.trim());
//				}
//			}
//
//			itemList.add(item);
//		}
//
//		return itemList;
//	}

	private static String getCharset(String purl) {
		int URLTIMEOUT=5000;
		String content = null, line, charset = null;
	
		URL url = null;
		URLConnection turl = null;
		try {
			url = new URL(purl);
			turl = url.openConnection();
			turl.setConnectTimeout(URLTIMEOUT);
			turl.setReadTimeout(URLTIMEOUT);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					turl.getInputStream()));
			while ((line = in.readLine()) != null) {
				content += line;
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (content != null) {
			int j = content.indexOf("charset=") + 8;
			if (content.length() > j) {
				String Scharset = content.substring(j, j + 8);
				if (Scharset.startsWith("utf-8")
						|| Scharset.startsWith("UTF-8")) {
					charset = "utf-8";
				} else if (Scharset.startsWith("gbk")
						|| Scharset.startsWith("GBK")) {
					charset = "gbk";
				} else if (Scharset.startsWith("gb2312")
						|| Scharset.startsWith("GB2312")) {
					charset = "gb2312";
				}
	
			}
		}
	
		// 如果还没得到编码，则使用header信息
		if (charset == null) {
			// map存放的是header信息(url页面的头信息)
			Map<String, List<String>> map = turl.getHeaderFields();
			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
	
			// 遍历,查找字符编码
			String key = "";
			String tmp = "";
			while (iterator.hasNext()) {
				key = iterator.next();
				tmp = map.get(key).toString().toLowerCase();
				// 获取content-type charset
				if (key != null && key.equals("Content-Type")) {
					int m = tmp.indexOf("charset=");
					if (m != -1) {
						charset = tmp.substring(m + 8).replace("]", "");
					}
				}
			}
		}
	
		return charset;
	}

}
