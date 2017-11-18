package com.yuan.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MMap {

	Map<String, Integer> IdMap; // 保存string与id的对应信息
	Map<Integer, Integer> CountMap; // 保存id与次数的对应信息
	boolean autoCount; // 是否开启计数自动计数(getId),true时开启计数,在调用getid方法时增加计数。false时不增加计数
	int sumCount;
	int maxId;

	public MMap(String name) {
		// TODO Auto-generated constructor stub
		maxId = 0;
		sumCount=0;
		IdMap = new HashMap<String, Integer>();
		CountMap = new HashMap<Integer, Integer>();
		autoCount = true;
	}
	public int getSumCount(){
		return this.sumCount;
	}

	public void setAutoCount(boolean bl) {
		this.autoCount = bl;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	// 返回String对应的id
	public int put(String str) {
		if (str==null||str.equals("")) {
			return 0;
		}
		str = str.trim();
		if (IdMap.containsKey(str)) {
			// 已经含有这个词
			int id = IdMap.get(str);
			
			// 计数器加1
			if (this.autoCount) {
				if (!CountMap.containsKey(id)) {
					return 0;
				}
				CountMap.put(id, CountMap.get(id) + 1);
				sumCount++;
			}
			
			return id;
		} else {
			// 尚未包含这个词，添加
			IdMap.put(str, ++maxId);
			if (this.autoCount) {
				CountMap.put(maxId, 1);
				sumCount++;
			} else {
				CountMap.put(maxId, 0);
			}
			return maxId;
		}
	}

	public int getTimes(String str) {
		if (str==null||str.equals("")) {
			return 0;
		}
		str = str.trim();
		if (IdMap.containsKey(str)) {
			int id = IdMap.get(str);
			if (!CountMap.containsKey(id)) {
				return 0;
			}
			return CountMap.get(id);
		} else {
			return 0;
		}
	}

	public int getTimes(int i) {
		if (CountMap.containsKey(i)) {
			return CountMap.get(i);
		}
		return 0;
	}

	// 保存此map的所有信息到文件
	/**
	 * @param file 保存文件文件路径/文件名称
	 * @return 成功写入否
	 * example:写入格式
	 * 词,id,计数
	 */
	public boolean save(String file) {
		if (file==null||file.equals("")) {
			return false;
		}

		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			Set<Entry<String, Integer>> entrySet = IdMap.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				br.write(entry.getKey() + "," + entry.getValue() + ","
						+ CountMap.get(entry.getValue()) + "\n");
			}
			br.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * @param fileName 读取文件文件路径/文件名称，
	 * @return 成功读入文件 ，参考写入方法
	 * example:读入格式
	 * 词,id,计数
	 */
	public boolean load(String fileName) {
		if (fileName==null||fileName.equals("")) {
			return false;
		}

		if (!new File(fileName).exists()) {
			return false;
		}
		
		// 清空旧数据
		if (IdMap != null)
			IdMap.clear();
		else
			IdMap = new HashMap<String, Integer>();

		if (CountMap != null)
			CountMap.clear();
		else
			CountMap = new HashMap<Integer, Integer>();
		
		this.maxId=0;
		this.sumCount=0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String fileLine;
			String[] line;
			while ((fileLine = br.readLine()) != null) {
				line = fileLine.split(",", 3);
				if (line.length < 3)
					continue;
				IdMap.put(line[0], Integer.parseInt(line[1]));
				CountMap.put(Integer.parseInt(line[1]),
						Integer.parseInt(line[2]));
				//更新maxid和sumcount计数器
				if(Integer.parseInt(line[1])>this.maxId)this.maxId=Integer.parseInt(line[1]);
				this.sumCount+=Integer.parseInt(line[2]);
			}
			br.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public void clear(){
		this.CountMap.clear();
		this.IdMap.clear();
		this.maxId=0;
		this.autoCount=true;
	}
	
	/**
	 * @param id 需要增加计数的id
	 * @return 最后的计数
	 */
	public int addCount(int id){
		if(id<0||!this.CountMap.containsKey(id)){
			return 0;
		}
		int count=this.CountMap.get(id);
		this.CountMap.put(id,++count);
		this.sumCount++;
		return count;
	}
	
	/**
	 * @param str 增加计数的项目
	 * @return 最后的计数结果
	 */
	public int addCount(String str){
		if(str==null||str.equals("")||!this.IdMap.containsKey(str.trim())){
			return 0;
		}
		str=str.trim();
		return this.addCount(this.IdMap.get(str));
	}

}
