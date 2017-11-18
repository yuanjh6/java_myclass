package com.yuan.common;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MConfig {
	/*
	 * 配置文件：从配置文件路径得到配置文件，读取配置文件的内容得到配置信息，集中系统所有配置信息 文件格式为 key=value
	 */
	private static  String configFile=null;
	private static HashMap<String, String> confMap;

	/*
	 * 加载配置文件，将配置文件读入静态的类变量Map当中
	 */
	
	public MConfig(){
		confMap = new HashMap<String, String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			String line;
			String[] t;
			while ((line = br.readLine()) != null) {
				if (line.startsWith(";"))
					continue;
				if (line==null||line.trim().length()==0)
					continue;

				t = line.split("=",2);
				if (t[0]==null) {
					continue;
				}
				t[0] = t[0].trim();
				if (t[1]==null) {
					t[1] = "";
				}
				confMap.put(t[0], t[1]);
			}
			br.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static class Holder {   
        static MConfig instance = new MConfig();   
    } 

    public static MConfig getInstance() { 
        return Holder.instance;   
    }   

	public static String get(String key) {
		String value = null;
		if (confMap.containsKey(key))
			value = confMap.get(key);
		return value;
	}
	

}
