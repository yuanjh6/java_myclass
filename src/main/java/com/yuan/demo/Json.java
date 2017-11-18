package com.yuan.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {

	public Json() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test();
	}

	private static void test() {
		String s = "{\"person\":{\"name\":\"张三\",\"age\":20}}";
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(s);

			JSONObject result = jsonObj.getJSONObject("person");
			System.out.println("姓名:" + result.getString("name") + " 年龄:"
					+ result.getInt("age"));
			// 解析object形式
			s = "{\"persons\":[\"张三\",\"李四\",\"王五\"]}";
			jsonObj = new JSONObject(s);
			JSONArray jsonarr = jsonObj.getJSONArray("persons");
			for (int i = 0; i < jsonarr.length(); i++) {
				System.out.println(jsonarr.getString(i));
			}// 解析array形式

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
