package com.yuan.demo;

import jeasy.analysis.MMAnalyzer;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.recognition.NatureRecognition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class SplitWords{
	static MMAnalyzer analyzer=new MMAnalyzer();
	public static void main(String[] args) {
		SplitWords sw=new SplitWords();

		
	}
	public SplitWords(){
			// TODO Auto-generated method stub
	        String text = "  编辑推荐    为当代小学生精心打造的更新换代型　　全新彩图版多功能系列工具书     内容推荐    科学规范：严格按照国家教育部颁布的《小学语文课程标准》的各项规定，专为小学生课内外使用而编写的。内容完备：能够满足小学生课内外学习及阅读需要。快乐学习：版式新颖、图文并茂，使小学生在轻松阅读中获得知识，提高写作能力，并培养学生自主学习的习惯。     目录    词目首字音序索引正文词目笔画索引     书摘与插画       显示全部信息 ";
	        
	        try {
				FileReader rd=new FileReader("d://dic.txt");
				MMAnalyzer.addDictionary(rd);
	        } catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try {
				System.out.println(analyzer.segment(text, " | "));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	        List<Term> paser = BaseAnalysis.paser(text) ;
	        new NatureRecognition(paser).recognition() ;
	        System.out.println(paser);
	        
	        System.out.println(ToAnalysis.paser(text).toString().replace("/n", ""));
	   
	   	
	}
	

}
