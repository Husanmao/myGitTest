package com.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.dao.WeixinDAO;
import com.model.Weixin;
import com.service.WeixinService;

@Component
public class WeixinServiceImpl implements WeixinService {

	private WeixinDAO dao;

	public WeixinDAO getDao() {
		return dao;
	}

	@Resource
	public void setDao(WeixinDAO dao) {
		this.dao = dao;
	}

	public class MyX509TrustManager implements X509TrustManager {
		// 检查客户端证书
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
		// 检查服务器端证书
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
		// 返回受信任的X509证书数组
		public X509Certificate[] getAcceptedIssuers() {return null;}
	}

	public JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			System.out.println("连接超时");
		} catch (Exception e) {
			System.out.println("https请求异常");
		}
		return jsonObject;
	}

	public String menuCreate(String access_token,String  menu_url){
		//System.out.println("input menuCreate");
		String errmsg = "";
		String requestUrl = menu_url.replace("ACCESS_TOKEN",access_token);
		String outputStr = "{\"button\":["
				+ "{\"name\":\"优惠活动\","
				+ "\"sub_button\":["
				+ "{\"type\":\"click\",\"name\":\"最新活动\",\"key\":\"huodong_zuixing\"},"
				+ "{\"type\":\"click\",\"name\":\"优惠券\",\"key\":\"huodong_youhuiquan\"},"
				+ "{\"type\":\"click\",\"name\":\"优惠套餐\",\"key\":\"huodong_youhuitaocan\"}"
				+ "]},"
				+ "{\"type\":\"view\",\"name\":\"我要点餐\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe5b630ae58e5df9f&redirect_uri=http%3A%2F%2Fwww%2Eieater%2Ecn%2Fwm%2Findex&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"},"
				+ "{\"name\":\"我的服务\","
				+ "\"sub_button\":["
				+ "{\"type\":\"view\",\"name\":\"我的订单\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe5b630ae58e5df9f&redirect_uri=http%3A%2F%2Fwww%2Eieater%2Ecn%2Fwm%2Ffront%2DmyOrder%2Dlist&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"},"
				+ "{\"type\":\"view\",\"name\":\"我的地址\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe5b630ae58e5df9f&redirect_uri=http%3A%2F%2Fwww%2Eieater%2Ecn%2Fwm%2Ffront%2DmyAddr%2Dshow&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"},"
				+ "{\"type\":\"click\",\"name\":\"客服电话\",\"key\":\"phone\"}"
				+ "]}"
				+ "]}";
		//System.out.println("\trequestUrl = " + requestUrl);
		//System.out.println("\toutputStr = " + outputStr);
		JSONObject jsonObject = httpsRequest(requestUrl, "POST", outputStr);
		if (null != jsonObject) {
			try {
				errmsg = jsonObject.getString("errcode") + jsonObject.getString("errmsg");
			} catch (JSONException e) {
				errmsg = "";
			}
		}
		//System.out.println("input menuCreate errmsg = " + errmsg);
		return errmsg;
	}

	public String menuDelete(String access_token,String  menu_urlDel){
		//System.out.println("input menuDelete");
		String errmsg = "";
		String requestUrl = menu_urlDel.replace("ACCESS_TOKEN",access_token);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				errmsg = jsonObject.getString("errcode") + jsonObject.getString("errmsg");
			} catch (JSONException e) {
				errmsg = "";
			}
		}
		//System.out.println("\terrmsg = " + errmsg);
		return errmsg;
	}

	public String menuGet(String access_token,String  menu_urlGet){
		//System.out.println("input menuGet");
		String requestUrl = menu_urlGet.replace("ACCESS_TOKEN",access_token);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			System.out.println("！！！jsonObject = " + jsonObject);
			return jsonObject.toString();
		}
		//System.out.println("\terrmsg = " + errmsg);
		return "";
	}

	public String getUser_nickname(String user_url, String access_token, String openid){
		String nickname = "";
		String requestUrl = user_url.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				nickname = jsonObject.getString("nickname");
			} catch (JSONException e) {
				nickname = "";
			}
		}
		return nickname;
	}

	public String find(String name){
		Weixin o = new Weixin();
		if("access_token".equals(name)){
			Date today = new Date();
			String access_token_Time = dao.findUniqueByProperty("name", "access_token_Time").getValue();
			if (access_token_Time == null || "".equals(access_token_Time)) {
				return saveAccess_Token();
			} else {
				Date time = new Date(Long.parseLong(access_token_Time));
				if (today.compareTo(time) > 0) {//已过期
					return saveAccess_Token();
				} else {
					return dao.findUniqueByProperty("name", name).getValue();
				}
			}
		} else {
			o = dao.findUniqueByProperty("name", name);
		}
		return o.getValue();
	}
	
	String saveAccess_Token(){
		Weixin o = dao.findUniqueByProperty("name", "access_token");
		String token_url = dao.findUniqueByProperty("name", "token_url").getValue();
		String appid = dao.findUniqueByProperty("name", "appid").getValue();
		String appsecret = dao.findUniqueByProperty("name", "appsecret").getValue();
		String requestUrl = token_url.replace("APPID",appid).replace("APPSECRET",appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				String access_token = jsonObject.getString("access_token");
				String access_token_Time = Long.toString((((new Date()).getTime() - 100) + new Long(jsonObject.getInt("expires_in")*1000)));

				Weixin weixin = dao.findUniqueByProperty("name", "access_token_Time");
				//weixin.setName("access_token_Time");
				weixin.setValue(access_token_Time);
				dao.merge(weixin);
				
				//o.setName("access_token");
				o.setValue(access_token);
				dao.merge(o);
				
			} catch (JSONException e) {
			
			}
		}
		return o.getValue();
	}
}