package com.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.service.WeixinService;

@Component
@Scope("prototype")
public class WeixinAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6145686025185770362L;
	private WeixinService objService;

	public WeixinService getObjService() {
		return objService;
	}

	@Resource
	public void setObjService(WeixinService objService) {
		this.objService = objService;
	}

	public void index() {
		String echostr = ServletActionContext.getRequest().getParameter("echostr");
		if (null == echostr || echostr.isEmpty()) {
			responseMsg();//回复内容
		} else {
			if (this.checkSignature()) {
				//System.out.println("服务器Token配置");
				this.print(echostr);
			} else {
				this.print("error");
			}
		}
	}

	//回复内容
	@SuppressWarnings("static-access")
	public void responseMsg() {
		String postStr = null;
		try {
			postStr = this.readStreamParameter(ServletActionContext.getRequest().getInputStream());
			postStr = new String(postStr.getBytes("gbk"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != postStr && !postStr.isEmpty()) {
			Document document = null;
			try {
				document = DocumentHelper.parseText(postStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				return;
			}
			Element root = document.getRootElement();
			String fromUsername = root.elementText("FromUserName");
			String toUsername = root.elementText("ToUserName");
			String event = root.elementText("Event");
			String key = root.elementText("EventKey");
			String keyword = root.elementTextTrim("Content");
			String time = new Date().getTime() + "";
			String textTpl = "<xml>"
					+ "<ToUserName><![CDATA[%1$s]]></ToUserName>"
					+ "<FromUserName><![CDATA[%2$s]]></FromUserName>"
					+ "<CreateTime>%3$s</CreateTime>"
					+ "<MsgType><![CDATA[%4$s]]></MsgType>"
					+ "<Content><![CDATA[%5$s]]></Content>"
					+ "<FuncFlag>0</FuncFlag>" + "</xml>";

			String msgType = "text";//只写了文字回复，代扩展
			String contentStr = "Input someThing";
			if (null != keyword && !keyword.equals("")) {
				//聊天软件，可以做，代扩展
				//System.out.println("keyword = " + keyword);
				contentStr = "请通过菜单使用服务！";
			} else {
				if (event != null && event.equals("CLICK")) {//CLICK点击查询时间
					//这里可以在数据库中生成关键字库
					if (key.equals("phone")) {// 联系电话
						contentStr = "请拨打4000-000-000！";
					} else {
						contentStr = "123";
					}
					
				} else if (event != null && event.equals("subscribe")) {//订阅
					contentStr = "您好，欢迎关注！体验生活，从这里开始！";
				}  else if (event != null && event.equals("SCAN")) {//用户已关注时的事件推送
					contentStr = "您好，您已关注！";
				} else if (event != null && event.equals("unsubscribe")) {//取消订阅
					contentStr = "您好，您已取消关注！";
				}else if (event != null && event.equals("VIEW")) {//跳转事件
					//System.out.println(toUsername);
				}
			}
			String resultStr = textTpl.format(textTpl, fromUsername,toUsername, time, msgType, contentStr);
			try {
				resultStr = new String(resultStr.getBytes("UTF-8"), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			this.print(resultStr);
		} else {
			this.print("请通过菜单使用网址导航服务！");
		}
	}

	//菜单更新
	public String menuUpdate(){
		//String menu = objService.menuGet(objService.find("access_token"), objService.find("menu_urlGet"));
		objService.menuDelete(objService.find("access_token"), objService.find("menu_urlDel"));
		String msg = objService.menuCreate(objService.find("access_token"), objService.find("menu_url"));
		if("0ok".equals(msg)){
			return SUCCESS;
		} else {
			return ERROR;
		}
		//return SUCCESS;
	}

	// 微信接口验证
	public boolean checkSignature() {
		String signature = ServletActionContext.getRequest().getParameter("signature");
		String timestamp = ServletActionContext.getRequest().getParameter("timestamp");
		String nonce = ServletActionContext.getRequest().getParameter("nonce");
		String token = objService.find("TOKEN");
		String[] tmpArr = { token, timestamp, nonce };
		Arrays.sort(tmpArr);
		String tmpStr = this.ArrayToString(tmpArr);
		tmpStr = this.SHA1Encode(tmpStr);
		if (tmpStr.equalsIgnoreCase(signature)) {
			return true;
		} else {
			return false;
		}
	}

	// 向请求端发送返回数据
	public void print(String content) {
		try {
			ServletActionContext.getResponse().getWriter().print(content);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (Exception e) {

		}
	}

	// 数组转字符串
	public String ArrayToString(String[] arr) {
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			bf.append(arr[i]);
		}
		return bf.toString();
	}

	// sha1加密
	public String SHA1Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}

	// 从输入流读取post参数
	public String readStreamParameter(ServletInputStream in) {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
}