package com.yuan.tools;

import com.yuan.common.MMapo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Function {
	private static Logger logger = LogManager.getLogger(Function.class.getName());
	public static void main(String[] args) {
		String a="1,2,3,4,2,2,2,2";
		String b="a,b,c,d,a,a,a,a";
		System.out.println(Function.correlation(a, b));
	}

	/*
	 * 检查字符串是否合法，（不为空并且除去收尾空白字符后长度大于o），合法返回true
	 */
	public static boolean checkStr(String str) {	
		if (str == null)
			return false;
		if (str.trim().length() <= 0)
			return false;
		return true;
	}
	
	/*
	 * 功能说明：将文件读入到map当中，
	 *文件中数据保存格式为：
	 *;打头为注释内容，
	 *key=value
	 */
	public static Map<String,String> loadMap(String file) throws IOException{
		//说明，注释标志位行首;开头，内容分隔符为=
		BufferedReader rd=new BufferedReader(new FileReader(file));
		HashMap<String,String> map=new HashMap<String,String>();
		
		String line;
		String[] s=new String[2];
		while((line=rd.readLine())!=null){
			if(!line.startsWith(";")){
				s=line.split("=",2);
				if(!checkStr(s[0]))continue;
				map.put(s[0].trim(), s[1].trim());
			}
		}
		rd.close();
		return map;
	}
	
	/*
	 * 将Map转化为string返回的字符数组格式为
	 * s[0]:key1,key2,key3
	 * s[1]:"value1","value2","value3"
	 */
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
			tmp2 += "," + "\"" + entry.getValue().replace("\"","") + "\"";
			
		}
		if (tmp1.length() < 1 || tmp2.length() < 1)
			return null;
		tmpret[0] = tmp1.substring(1);
		tmpret[1] = tmp2.substring(1);

		return tmpret;
	}

	
	public static Map<String, String> toMap(String str) {
		Map<String, String> map = new HashMap<String, String>();
		if (str == null || str.equals(""))
			return null;

		for (String item : str.split("\",\"")) {
			String[] tmpitem = item.split("=", 2);
			if (tmpitem.length < 2)
				continue;
			map.put(tmpitem[0].replaceAll("\"|\\{|\\}", ""),
					tmpitem[1].replaceAll("\"|\\{|\\}", ""));

		}
		return map;
	}

	// 计算两个字符串之间的相关性,以 较短的为结束
	public static float correlation(String stra, String strb) {
		if (!checkStr(stra) || !checkStr(strb)) {
			return (float)0.0;
		}
		stra = stra.trim();
		strb = strb.trim();
		String[] tstra = stra.split(",");
		String[] tstrb = strb.split(",");
		//取得长度较短，以较短的作为标准
		int length = tstra.length < tstrb.length ? tstra.length : tstrb.length;
		//共 现 的次数矩阵 ,n个元素产生的id最大为n由于0被空值占用
		Integer[][] timesMatrix = new Integer[length+1][length+1];
		for (int i = 0; i <=length; i++) {
			for (int j = 0; j <= length; j++) {
				timesMatrix[i][j] = 0;
			}
		}
		//保存每个字串的详细统计信息
		MMapo mapa = new MMapo(), mapb = new MMapo();
	
		// 获取每个元素出现次数，以及各个元素之间的共现 次数
		for (int i = 0; i < length; i++) {
			if (!checkStr(tstra[i]) || !checkStr(tstrb[i])) {
				continue;
			}
			int aid = mapa.getId(tstra[i]);
			int bid = mapb.getId(tstrb[i]);
			timesMatrix[aid][bid]++;
		}
		
		//计算相关性
		float correlation=(float) 0.0;
		for (int i = 0; i <= length; i++) {
			for (int j = 0; j <= length; j++) {
				if(timesMatrix[i][j] != 0){
					correlation+=((float)timesMatrix[i][j]/length)*((float)timesMatrix[i][j]/(mapa.getTimes(i)+mapb.getTimes(j)-timesMatrix[i][j]));
				}
			}
		}
		return correlation;
	}
	
	public static String urlFormat(String url) {

		if (url == null || url.length() == 0)
			return null;
		url = url.trim();
		if (url.startsWith("http://"))
			url = url.substring(7);
		if (url.contains("/"))
			url = url.substring(0, url.indexOf("/"));
		if (url.contains("?"))
			url = url.substring(0, url.indexOf("?"));
		return url;
	}
	
	/**
	 * @param str
	 *            一个由多个数字构成的字符串，数字之间分隔符是逗号,
	 * @return 返回各个数字的统计结果也是字符对的形式 example 15,17,16,17->15:1,16:1,17:2
	 */
	public String numCount(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}
		Map<Integer, Integer> countMap = new TreeMap<Integer, Integer>();
		String[] numStr = str.split(",");
		int num;
		if (numStr != null || numStr.length > 0) {
			for (String numstr : numStr) {
				num = Integer.parseInt(numstr);
				if (countMap.containsKey(num)) {
					countMap.put(num, countMap.get(num) + 1);
				} else {
					countMap.put(num, 1);
				}
			}
		}
		
		String retStr="";
		for(Entry<Integer, Integer> entry:countMap.entrySet()){
			retStr+=entry.getKey()+":"+entry.getValue()+",";
		}
		
		return retStr;
	}
	
	/**计算 两个map之间距离 
	 * @param oldMap 第一个map
	 * @param newMap 第二个map
	 * @return 返回欧拉 距离 
	 */
	public float getDistance(Map<Integer,Float> mapa,Map<Integer,Float> mapb){
		if(mapa==null||mapb==null)return (float)0.0;
		//计算两个map的key的集合
		Set<Integer> keySet=new TreeSet<Integer>();
		keySet.addAll(mapa.keySet());
		keySet.addAll(mapb.keySet());
		// 各个维度的差值 
		List<Float> distanceList=new ArrayList<Float>();
		
		for(Integer key:keySet){
			if(mapa.containsKey(key)){
				if(mapb.containsKey(key)){
					distanceList.add(mapa.get(key)-mapb.get(key));
				}else{
					distanceList.add(mapa.get(key));
				}
				
			}else{
				distanceList.add(mapb.get(key));
			}
			
		}
		
		//保存平方值 
		float xx=0;
		for(Float f:distanceList){
			xx+=f*f;
		}
		
		//返回开根号 
		return (float) Math.sqrt(xx);
	}
	
	/**说明：获取两个map的cos夹角
	 * @param mapa 
	 * @param mapb
	 * @return
	 */
	public float getCos(Map<Integer,Float> mapa,Map<Integer,Float> mapb){
		if(mapa==null||mapb==null)return (float)0.0;
		//计算两个map的key的集合
		Set<Integer> keySet=new TreeSet<Integer>();
		keySet.addAll(mapa.keySet());
		keySet.addAll(mapb.keySet());
		// 各个维度的差值 
		List<Float> cosList=new ArrayList<Float>();
		float aa=(float)0,bb=(float)0,ab=(float)0;	//分别保存a平方，b平方，ab点积
		
		for(Integer key:keySet){
			if(mapa.containsKey(key)){
				if(mapb.containsKey(key)){
					ab+=mapa.get(key)*mapb.get(key);
					aa+=mapa.get(key)*mapa.get(key);
					bb+=mapb.get(key)*mapb.get(key);
				}else{
					aa+=mapa.get(key)*mapa.get(key);
				}
				
			}else{
				bb+=mapb.get(key)*mapb.get(key);
			}
			
		}
		
		//返回开根号 sqrt(aa)*sqrt(bb)=sqrt(aa*bb)
		return (float)(ab/Math.sqrt(aa*bb));
	}
	

}
