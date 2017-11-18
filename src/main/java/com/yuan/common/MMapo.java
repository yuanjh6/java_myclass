package com.yuan.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MMapo {
	Map<String, KeyInfo> map;
	int maxId;

	public MMapo() {
		// TODO Auto-generated constructor stub
		map = new HashMap<String, KeyInfo>();
		maxId = 0;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	// 返回String对应的id
	public int getId(String str) {
		if (!checkStr(str)) {
			return 0;
		}
		str = str.trim();
		if (map.containsKey(str)) {
			map.get(str).incCount();
			return map.get(str).getId();
		} else {
			map.put(str, new KeyInfo(++maxId));
			return maxId;
		}
	}


	public int getTimes(String str) {
		if (!checkStr(str)) {
			System.out.println("参数错误，传入空串");
		}
		str = str.trim();
		if (map.containsKey(str)) {
			return map.get(str).getTimes();
		} else {
			System.out.println("没有此单词,返回次数为0");
			return 0;
		}
	}
	public int getTimes(int i){
		Set<Entry<String,KeyInfo>> entrySet=map.entrySet();
		for(Entry<String,KeyInfo> entry:entrySet){
			if(entry.getValue().getId()==i){
				return entry.getValue().getTimes();
			}
		}
		return 0;
	}

	// 保存此map的所有信息到文件
	public void save(String file) throws IOException {
		if(!checkStr(file)){
			System.out.println("参数错误:文件路径非法");
			return ;
		}
		BufferedWriter br=new BufferedWriter(new FileWriter(file));
		Set<Entry<String,KeyInfo>> entrySet=map.entrySet();
		for(Entry<String,KeyInfo> entry:entrySet){
			br.write(entry.getKey()+":"+entry.getValue().getId()+":"+entry.getValue().getTimes()+"\n");
		}
		br.close();
	}

	public static boolean checkStr(String str) {
		if (str == null)
			return false;
		str = str.trim();
		if (str.length() <= 0)
			return false;
		return true;
	}


}

class KeyInfo {
	int id;// 保存key对应的id
	int count;// 保存key出现的次数

	public KeyInfo(int id) {
		this.id = id;
		this.count = 1;
	}

	public void incCount() {
		this.count++;
	}

	public int getId() {
		return this.id;
	}

	public int getTimes() {
		return this.count;
	}
}