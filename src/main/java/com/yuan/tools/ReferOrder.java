package com.yuan.tools;

import com.google.common.collect.TreeMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;



public class ReferOrder {
	private static Logger logger = LogManager.getLogger(ReferOrder.class.getName());

	public ReferOrder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	testReferOrder();
		
	}
	static public void testReferOrder(){
		ReferOrder ro=new ReferOrder();
		List<Float> lista=new ArrayList<Float>();
		lista.add((float) 1.0);
		lista.add((float) 0.50);
		lista.add((float) 1.2);
		List<Object> listb=new ArrayList<Object>();
		listb.add(5);
		listb.add(7.23);
		listb.add(12);
		
		listb=ro.referOrder(lista,listb);
		System.out.println(listb);
		
	}
	
	/**
	 * @param ListA 参考数列
	 * @param itemList 待排序的数列 
	 * @return 返回排序后的数列 
	 */
	public List<Object> referOrder(List<Float> ListA,List<Object> itemList){
		if(ListA.size()==0||itemList.size()==0){
			logger.error("排序list中含有空list");
			return null;
		}
		TreeMultimap<Float, Integer> refer = TreeMultimap.create();
		int min=ListA.size()>itemList.size()?itemList.size():ListA.size();
		int i=0;
		while(i<min){
			refer.put(ListA.get(i), i);
			i++;
		}
		List<Object> RetList=new ArrayList<Object>();
		for(Entry<Float,Integer> entry:refer.entries()){
			RetList.add(itemList.get(entry.getValue()));
		}
		return RetList;
	}

}
