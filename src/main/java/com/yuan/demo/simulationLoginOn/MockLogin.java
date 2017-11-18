package com.yuan.demo.simulationLoginOn;
/*
 * 模拟登录中国文学网
 * 如果先是乱码则是字符问题，将项目的默认环境字符设为gbk后输出的内容复制到html中打开即可。
 */

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class MockLogin {

	private HttpClient client;
	private PostMethod method;

	public MockLogin() {
		this.client = new HttpClient();
	}

	public void setPostMethod(String url, NameValuePair[] pairs) { // 根据配置中的参数NameValuePair数组设置PostMethod
		this.method = new PostMethod(url);
		this.method.setRequestBody(pairs);
	}

	public void print(String encoding) { // 打印响应状态和响应的主体信息
		String response = "";
		try {
			this.client.executeMethod(this.method);
			System.out.println("||->HTTP Status:");
			System.out.println(this.method.getStatusLine());
			response = new String(this.method.getResponseBodyAsString()
					.getBytes(encoding));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("||->Response content:");
		System.out.println(response);
	}

	public static void main(String[] args) throws HttpException, IOException {

		MockLogin login = new MockLogin();
		String url = "http://www.china-wenxue.net/bbs/logging.php?action=login&loginsubmit=yes";
		NameValuePair[] pairs = new NameValuePair[] {
				new NameValuePair("username", "kapa2009"),
				new NameValuePair("password", "123456"), };
		login.setPostMethod(url, pairs);
		login.print("iso-8859-1");
	}
}