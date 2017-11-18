package com.yuan.demo.simulationLoginOn;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Goodjobs {
	public Goodjobs() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException,
			IOException {
		// 连接地址（通过阅读html源代码获得，即为登陆表单提交的URL）
		String surl = "http://login.goodjobs.cn/index.php/action/UserLogin";

		/**
		 * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
		 * java.net.URL and //java.net.URLConnection
		 */
		URL url = new URL(surl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		/**
		 * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
		 * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
		 */
		connection.setDoOutput(true);
		/**
		 * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
		 */
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream(), "GBK");
		// 其中的memberName和password也是阅读html代码得知的，即为表单中对应的参数名称
		out.write("memberName=myMemberName&password=myPassword"); // post的关键所在！
		// remember to clean up
		out.flush();
		out.close();

		// 取得cookie，相当于记录了身份，供下次访问时使用
		String cookieVal = connection.getHeaderField("Set-Cookie");

		String s = "http://user.goodjobs.cn/dispatcher.php/module/Resume/action/Preview";
		// 重新打开一个连接
		url = new URL(s);
		HttpURLConnection resumeConnection = (HttpURLConnection) url
				.openConnection();
		if (cookieVal != null) {
			// 发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
			resumeConnection.setRequestProperty("Cookie", cookieVal);
		}
		resumeConnection.connect();
		InputStream urlStream = resumeConnection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(urlStream));
		String ss = null;
		String total = "";
		while ((ss = bufferedReader.readLine()) != null) {
			total += ss;
		}
		IOUtils.write(total, new FileOutputStream("d:/index.html"));
		bufferedReader.close();
	}
}