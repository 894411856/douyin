package com.openwan.controller;

import com.google.gson.Gson;
import com.openwan.controller.util.DouYin;
import com.openwan.model.DouYinUrl;
import com.openwan.service.DouYinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *
 */
@RestController
@RequestMapping(value = "/api")
public class CityController {

	@Autowired
	private DouYinService douYinService;

 	private static Gson g = new Gson();

	@RequestMapping("/")
	public ModelAndView home(HttpServletResponse response) throws IOException{
		return new ModelAndView("index");
	}
	@RequestMapping("/test")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("test");
	}
	@RequestMapping("/v")
	public String dow(HttpServletResponse response) throws IOException{
		return "hello world";
	}

	@RequestMapping(value = "/dows", method = {RequestMethod.POST,RequestMethod.GET})
	public void resq( String url ,  String name,HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("下载地址："+url );
		System.out.println("文件名："+name );
		downloadFile(url,name,request,response);
	}

	@ResponseBody
	@RequestMapping("/reqInfo")
	public String reqInfo(String u , HttpServletRequest request) {
		String ip  = getIpAddr(request) ;
		System.out.println("IP地址："+ip);
		System.out.println("解析地址"+u);

		try {
			String url = DouYin.getRedirectInfo(u);
			String api =  "https://api.berryapi.net/get/video?AppKey=RGro8tBg1l&url="; // berryAPI 接口
			System.out.println("api转接口地址："+api);
			String json = DouYin.sendGet(api + url);

			//getNopin(json,fileName,imageTitile);
			System.out.println("解析结果："+json);

			DouYinUrl dyu = new DouYinUrl();
			dyu.setIp(ip);
			dyu.setUrl(u);
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			dyu.setDateTime(sdf.format(new Date()));
			douYinService.insertEmailCode(dyu);

			return json ;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
	

	/*
	* 单无水印视频
	* */
	private  static  void getNopin(String json ,String fileName ,long imageTitile) throws IOException {
		HashMap<String, Object> map = g.fromJson(json, HashMap.class);

		if("ok".equals(map.get("msg")) && !"".equals(map.get("url").toString())){
			HashMap<String, Object> m = g.fromJson(g.toJson(map.get("userinfo")), HashMap.class);
			String urlStr = map.get("url").toString();

			String nickName = m.get("nickname").toString();

			if( !"".equals(nickName )){
				fileName = nickName + "." + "mp4";
			}else{
				fileName = imageTitile + "." + "mp4";
			}
			DouYin.downLoadFromUrl(urlStr, fileName, "C://");
		}
	}


	/**
	 * @Description: 获取客户端IP地址
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if(ip.equals("127.0.0.1")){
				//根据网卡取本机配置的IP
				InetAddress inet=null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ip= inet.getHostAddress();
			}
		}
		// 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ip != null && ip.length() > 15){
			if(ip.indexOf(",")>0){
				ip = ip.substring(0,ip.indexOf(","));
			}
		}
		return ip;
	}


	public static void downloadFile(String urlStr, String name,HttpServletRequest request,HttpServletResponse response) {
		OutputStream out = null;
		System.out.println(request.getHeader("User-Agent").toLowerCase());
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			System.out.println(name.substring(name.indexOf("."),name.length()));

			if(".mp4".equals(name.substring(name.indexOf("."),name.length()))){
				response.setContentType("video/mpeg4;");
			}else if (".jpg".equals(name.substring(name.indexOf("."),name.length()))){
				response.setContentType("image/x-citrix-jpeg;");
			}
			response.addHeader("Content-Disposition", "attachment;filename="+name);
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			InputStream inputStream = conn.getInputStream();

			out = response.getOutputStream();

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}



}
