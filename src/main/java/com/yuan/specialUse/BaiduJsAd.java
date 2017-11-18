package com.yuan.specialUse;

import com.yuan.tools.HtmlUnitTool;
import com.yuan.tools.HttpClientTool;
import com.yuan.tools.JsoupTool;
import jeasy.analysis.MMAnalyzer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaiduJsAd {
	static MMAnalyzer analyzer = new MMAnalyzer();
	private static Logger logger = LogManager.getLogger(BaiduJsAd.class
			.getName());
//	static String basicUrl = "http://cpro.baidu.com/cpro/ui/uijs.php?rs=2&u=http://www.tgbus.com/shop/beijing/&p=baidu&c=news&n=10&t=tpclicked3&q=tgbus_cpr&k=#KEYWORD#&sid=28bc699faf19b0ce&ch=0&tu=639143&jk=ede70d062d9f44c6&cf=1&fv=11&stid=0&urlid=0";
//	static String basicUrl="http://cpro.baidu.com/cpro/ui/uijs.php?u=http%3A%2F%2Fwww%2Etgbus%2Ecom%2F&n=20&t=tpclicked3&q=tgbus_cpr&k=#KEYWORD#";
	static String basicUrl="http://cpro.baidu.com/cpro/ui/uijs.php?rs=4&u=http%3A%2F%2Fwww%2Eyingjiesheng%2Ecom%2Fcommend-fulltime-1%2Ehtml&p=baidu&c=news&n=10&t=tpclicked3_hc&q=yingjiesheng_cpr&k0=&k1=&k2=&k3=&k4=&k5=&sid=93904c6f62e5058f&ch=0&tu=u1168282&jk=2995dec35a0c06d4&cf=4&fv=11&stid=0&urlid=0&k=#KEYWORD#";
	static String tableName = "adver_baidujs";
	static String dbDriver = "com.mysql.jdbc.Driver";
	static String dbUrl = "jdbc:mysql://localhost:3306/adver?useUnicode=true&characterEncoding=utf8&user=root&password=";
	
	public static void main(String[] args) {
		//test part
//		String html=null;
//		html = HtmlUnitTool.getPageAsXml("http://cpro.baidu.com/cpro/ui/uijs.php?rs=4&u=http%3A%2F%2Fwww%2Eyingjiesheng%2Ecom%2Fcommend-fulltime-1%2Ehtml&p=baidu&c=news&n=10&t=tpclicked3_hc&q=yingjiesheng_cpr&k0=&k1=&k2=&k3=&k4=&k5=&sid=93904c6f62e5058f&ch=0&tu=u1168282&jk=2995dec35a0c06d4&cf=4&fv=11&stid=0&urlid=0&k=%E5%BA%94%E5%B1%8A%E7%94%9F");
//		System.out.println(	Jsoup.parse(html).text());
//		String eleSelect = "div.adBlockL";
//		Map<String,String> attrMap=new HashMap<String,String>();
//		attrMap.put("pic", "div.adBlockLMulti>a>img,attr,src");
//		attrMap.put("txt", "div.adBlockLInfo>div>a,text");
//		attrMap.put("turl", "div.adBlockLInfo>div.adBlockLSurl>a,text");
//		// attrMap.put("info", "div.aspdesc,text");
//		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
//		results = JsoupTool.itemsInfo(html, eleSelect, attrMap);
//		eleSelect ="div.asp>div.aspblock";
//		attrMap.clear();
//		attrMap.put("txt", "a.asptit,text");
//		attrMap.put("turl", "a.aspurl,text");
//		List<Map<String, String>> result=JsoupTool.itemsInfo(html, eleSelect, attrMap);
//		if(result!=null){
//			results.addAll(result);
//		}
//		for(Map<String,String> map:results){
//			System.out.println(map);
//		}
		
	
		// 构造词表
		String dicFile = "otheruse/baidujsad.word.txt";
		String usedDicFile="otheruse/usedbaidujsad.word.txt";
		Set<String> usedWordSet=getWordSet(usedDicFile);
		Set<String> wordSet =getWordSet(dicFile);
		if(wordSet==null){
			logger.error("获取待处理词表为空,请检查");
			return ;
		}
		logger.info("allword:"+wordSet.size());
		logger.info("usedWord:"+usedWordSet.size());
		if(!wordSet.isEmpty()&&usedWordSet!=null){
			wordSet.removeAll(usedWordSet);
		}
		logger.info("待处理wordSet:"+wordSet.size());
		
		// 依次获取结果以及结果保存
		try {
			Class.forName(dbDriver);
			Connection conn = DriverManager.getConnection(dbUrl);
			Statement sta = conn.createStatement();
			BufferedWriter bw=new BufferedWriter(new FileWriter(usedDicFile,true));

			for (String word : wordSet) {
				if (word=="") {
					continue;
				}
				bw.write(word+",\n");
				bw.flush();
				
				List<Map<String, String>> result = getAds(word);
				// 结果保存
				if (result == null || result.isEmpty())
					continue;
				String sql = null;
				int e = 0, r = 0;
				for (Map<String, String> item : result) {
					if (item == null || item.isEmpty())
						continue;
					item.put("mtype", word);
					String[] s = mapToStr(item);
					if (s == null)
						continue;
					sql = "insert into " + tableName + "(" + s[0] + ") values "
							+ "(" + s[1] + ");";
					try {
						if (sta.executeUpdate(sql) > 0) {
							r++;
						} else {
							logger.debug("sql语句执行失败:" + sql);
							e++;
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						e++;
					}
					
				}
				logger.info("word:" + word + " 成功插入数据:" + r + " 插入数据失败:" + e);
			}
			bw.close();

			sta.close();
			conn.close();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			logger.error("数据库连接出错，请检查");
		}
		logger.info("ok!");
	}
	
	public static Set<String> getWordSet(String fileName){
		File file=new File(fileName);
		if(!file.exists()){
			logger.error("文件不存在 请检查 fileName:"+fileName);
			return null;
		}
		
		StringBuffer content = new StringBuffer();
		String wordStr =null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
			br.close();
			
			if (content.length() == 0) {
				logger.error("文件内容为空,请检查");
				return null;
			}
			wordStr = analyzer.segment(content.toString(), ",");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (wordStr == null) {
			logger.error("词文件为空,亲检查");
			return null;
		}
		Set<String> wordSet = new HashSet<String>();
		for (String word : wordStr.split(",")) {
			if (word != null && !(word.equals("")) ) {
				wordSet.add(word.trim());
			}
		}
		return wordSet;
	}

	public static List<Map<String, String>> getAds(String keyword) {
		String url = null;
		try {
			url = basicUrl.replace("#KEYWORD#",
					URLEncoder.encode(keyword, "gbk"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (url == null) {
			logger.error("url编码后为空");
			return null;
		} else {
			logger.debug("url encode:" + url);
		}
		String html=null;
		html = HtmlUnitTool.getPageAsXml(url);
	
		
		
		// System.out.println(html);
		String eleSelect;
		Map<String, String> attrMap;
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		List<Map<String, String>> result;
		attrMap = new HashMap<String, String>();
//		eleSelect= "div.asp_new>div.aspblock";
//		attrMap.put("txt", "a.asptit,text");
//		attrMap.put("turl", "a.aspurl,text");
//		// attrMap.put("info", "div.aspdesc,text");
//
//		result = JsoupTool.itemsInfo(html, eleSelect, attrMap);
//		if (result == null) {
//			logger.error("获取结果失败");
//		} else {
//			results.addAll(result);
//		}

		eleSelect ="div.asp>div.aspblock";
		attrMap.clear();
		attrMap.put("txt", "a.asptit,text");
		attrMap.put("turl", "a.aspurl,text");
		result=JsoupTool.itemsInfo(html, eleSelect, attrMap);
		if(result!=null){
			results.addAll(result);
		}
		
		eleSelect = "div.adBlockL";
		attrMap.clear();
		attrMap.put("pic", "div.adBlockLMulti>a>img,attr,src");
		attrMap.put("txt", "div.adBlockLInfo>div>a,text");
		attrMap.put("turl", "div.adBlockLInfo>div.adBlockLSurl>a,text");
		result = JsoupTool.itemsInfo(html, eleSelect, attrMap);
		if (result == null) {
			logger.error("获取结果失败");
		} else {
			results.addAll(result);
		}
		// 补充所在页面的信息
		Map<String, String> pageInfo = getUrlInfo(url);
		if (pageInfo != null && !pageInfo.isEmpty()) {
			if (pageInfo.containsKey("title")) {
				attrMap.put("ltitle", pageInfo.get("title"));
			}
			if (pageInfo.containsKey("keywords")) {
				attrMap.put("lkeywords", pageInfo.get("keywords"));
			}
			if (pageInfo.containsKey("description")) {
				attrMap.put("ldescription", pageInfo.get("description"));
			}
		}

		// 补充指向连接地址信息
		for (Map<String, String> item : results) {
			if (item != null && !item.isEmpty() && item.containsKey("turl")) {
				String turl = item.get("turl");
				if (turl == null || turl=="")
					continue;
				turl = turl.trim();
				Map<String, String> turlInfo = getUrlInfo(turl);
				if (turlInfo == null || turlInfo.isEmpty())
					continue;
				if (turlInfo.containsKey("title")) {
					item.put("ttitle", turlInfo.get("title"));
				}
				if (turlInfo.containsKey("keywords")) {
					item.put("tkeywords", turlInfo.get("keywords"));
				}
				if (turlInfo.containsKey("description")) {
					item.put("tdescription", turlInfo.get("description"));
				}
			}
		}
		return results;

	}

	private static String[] mapToStr(Map<String, String> map) {
		String[] tmpret = new String[2];
		if (map == null) {
			logger.error("参数Map为空指针");
			return null;
		}

		Set<Map.Entry<String, String>> entrySet = map.entrySet();
		String tmp1 = "", tmp2 = "";
		for (Map.Entry<String, String> entry : entrySet) {

			if (entry.getKey().trim().length() == 0 || entry.getValue() == null
					|| entry.getValue().trim().length() == 0)
				continue;
			tmp1 += "," + entry.getKey();
			tmp2 += "," + "\"" + entry.getValue().replace("\"", "'") + "\"";
		}
		if (tmp1.length() < 1 || tmp2.length() < 1)
			return null;

		tmpret[0] = tmp1.substring(1);
		tmpret[1] = tmp2.substring(1);

		return tmpret;
	}

	private static Map<String, String> getUrlInfo(String url) {
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		Map<String, String> tmap = new HashMap<String, String>();
		// 获取网页内容
		String html = null;
		try {
			html = HttpClientTool.getMethod(url, null);
		} catch (Exception  e) {
			e.printStackTrace();
		}
		if (html == null)
			return null;

		// jsoup解析网页
		Document doc = Jsoup.parse(html);

		Element ele;
		ele = doc.select("title").first();
		if (ele != null)
			tmap.put("title", ele.text().trim());

		ele = doc.select("meta[name=keywords]").first();
		if (ele != null)
			tmap.put("keywords", ele.attr("content").trim());

		ele = doc.select("meta[name=description]").first();
		if (ele != null)
			tmap.put("description", ele.attr("content").trim());
		return tmap;

	}
}
