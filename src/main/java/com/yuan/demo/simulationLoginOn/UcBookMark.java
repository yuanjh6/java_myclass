package com.yuan.demo.simulationLoginOn;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/** 
*JDK默认没有org.apache.http包，需要先去http://hc.apache.org/downloads.cgi下载 
*下载HttpClient，解压，在Eclipse中导入所有JAR 
*/  
public class UcBookMark {  
    /** 
     * @param args 
     * @throws UnsupportedEncodingException  
     * 这个例子为了简单点，没有捕捉异常，直接在程序入口加了异常抛出声明 
     */  
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
        String url="http://fav.uc.cn/favoman/Favoman/waplogin";  
        //POST的URL  
        HttpPost httppost=new HttpPost(url);
        //建立HttpPost对象  
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        //建立一个NameValuePair数组，用于存储欲传送的参数  
        params.add(new BasicNameValuePair("uid","15815539733"));
        params.add(new BasicNameValuePair("pwd","2544"));
        params.add(new BasicNameValuePair("submit","true"));
        //添加参数  
        httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        //设置编码  
        HttpResponse response=new DefaultHttpClient().execute(httppost);
        //发送Post,并返回一个HttpResponse对象  
                //Header header = response.getFirstHeader("Content-Length");  
        //String Length=header.getValue();  
                // 上面两行可以得到指定的Header  
        if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回  
            String result= EntityUtils.toString(response.getEntity());
            //得到返回的字符串  
            System.out.println(result);  
            //打印输出  
                       //如果是下载文件,可以用response.getEntity().getContent()返回InputStream  
        }  
    }  
}  