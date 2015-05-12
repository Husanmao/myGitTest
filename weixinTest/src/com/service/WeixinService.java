package com.service;

import net.sf.json.JSONObject;

public interface WeixinService {

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr);

	/**
	 * 
	 * @param access_token
	 * @param menu_url
	 * @return
	 */
	public String menuCreate(String access_token,String menu_url);

	public String menuDelete(String access_token,String menu_urlDel);

	public String menuGet(String access_token,String  menu_urlGet);

	public String getUser_nickname(String user_url,String access_token,String openid);
	
	public String find(String name);
}