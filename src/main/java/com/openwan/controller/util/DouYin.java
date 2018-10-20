package com.openwan.controller.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;



public class DouYin {
	//楠哥
	//private static String URL = "https://aweme.snssdk.com/aweme/v1/aweme/favorite/?iid=44478516381&device_id=12229011297&os_api=18&app_name=aweme&channel=App%20Store&idfa=5FDF9129-D8F4-4594-96FE-6EC18AD136B9&device_platform=iphone&build_number=26006&vid=AB52ACAA-1E73-44D1-9BC2-BB1D74A6F682&openudid=282a2be914adb26833e880d38ee671e45da49829&device_type=iPhone7,2&app_version=2.6.0&version_code=2.6.0&os_version=12.0&screen_width=750&aid=1128&ac=WIFI&count=21&max_cursor=0&min_cursor=0&user_id=92966675808&mas=017229968ba20848c942069b1dc81b62d2293429ed1e007c2b4eb8&as=a1e5cbfaeeb37bcb957601&ts=1537588031";
	
	//沒有錢
	//private static String URL = "https://aweme.snssdk.com/aweme/v1/aweme/favorite/?iid=44478516381&device_id=12229011297&os_api=18&app_name=aweme&channel=App%20Store&idfa=5FDF9129-D8F4-4594-96FE-6EC18AD136B9&device_platform=iphone&build_number=26006&vid=AB52ACAA-1E73-44D1-9BC2-BB1D74A6F682&openudid=282a2be914adb26833e880d38ee671e45da49829&device_type=iPhone7,2&app_version=2.6.0&version_code=2.6.0&os_version=12.0&screen_width=750&aid=1128&ac=WIFI&count=21&max_cursor=0&min_cursor=0&user_id=103667629283&mas=01997fd4775be2e177d61709646cb10aaae887c3f793e2e154072e&as=a1a5dd9a6a254ba3f52293&ts=1537594202";
	private static String URL = "https://api.amemv.com/aweme/v1/aweme/favorite/?iid=44478516381&device_id=12229011297&os_api=18&app_name=aweme&channel=App%20Store&idfa=5FDF9129-D8F4-4594-96FE-6EC18AD136B9&device_platform=iphone&build_number=26006&vid=AB52ACAA-1E73-44D1-9BC2-BB1D74A6F682&openudid=282a2be914adb26833e880d38ee671e45da49829&device_type=iPhone7,2&app_version=2.6.0&version_code=2.6.0&os_version=12.0&screen_width=750&aid=1128&ac=WIFI&count=21&max_cursor=0&min_cursor=0&user_id=97435649990&mas=01dade842f8c152151ae8f3d3f26240914496940f44005a91254e1&as=a1257d3a2164ebb5257659&ts=1537594689";
		
	public static String sendGet(String url){
		 String entity = null;
		  try {
				 //创建HttpClient
			    CloseableHttpClient httpClient = HttpClients.createDefault();
			    
			    //创建请求方法
			    HttpGet httpGet = new HttpGet(url);
		
			    //设置Header模拟浏览器行为
			    httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		
			  
		        //发送请求，收取响应
		        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		
		        if(httpResponse.getStatusLine().getStatusCode() == 200){
		            //解析响应
		            entity = EntityUtils.toString(httpResponse.getEntity());
		           // System.out.println(entity);
		        }
	
		        EntityUtils.consume(httpResponse.getEntity());
		        httpResponse.close();
		  	} catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
		  return entity;
	 }

	
	
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	
	public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3000);
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		InputStream inputStream = conn.getInputStream();


		byte[] getData = readInputStream(inputStream);
		java.io.File saveDir = new java.io.File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		java.io.File file = new java.io.File(saveDir + java.io.File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getData);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}
	}

	public static void main(String[] args) throws Exception {
		
		
		String json = sendGet("https://www.iesdouyin.com/share/video/6604043815181372679/?region=CN&mid=6575353762485897998&u_code=3ddmjfe8&titleType=title&timestamp=1537693813&utm_campaign=client_share&app=aweme&utm_medium=ios&tt_from=copy&utm_source=copy&iid=44478516381");
		
		//System.out.println(json);
		 // System.out.println(getRedirectInfo("http://v.douyin.com/dC8Xw9/"));
		
		
		
		/*Gson g = new Gson();
		DouClass dc = g.fromJson(json, DouClass.class);
		System.out.println(dc.getAweme_list());
		System.out.println(g.fromJson(json, HashMap.class).get("aweme_list"));
		
		for(AwemeList al : dc.getAweme_list()){
			Video v = al.getVideo() ;
			PlayAddr pa = v.getPlay_addr();
			 
			
			System.out.println(pa.getUrl_list().get(0) );
			
			String urlStr = pa.getUrl_list().get(0) ;
			
			long imageTitile = System.currentTimeMillis();
			
			String fileName = null ;
			
			if( !"".equals(al.getDesc() )){
				  fileName = al.getDesc() + "." + "mp4";
			}else{
				  fileName = imageTitile + "." + "mp4";
			}
			String savePath = "F:\\douyin";
			try {
				downLoadFromUrl(urlStr, fileName, savePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
  
	}
 
	
	//地址重定向
	public static String getRedirectInfo(String url) throws  Exception{
		String result = null ;
		HttpHost targetHost = null ;
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(url);
		 
			//将HttpContext对象作为参数传给execute()方法,则HttpClient会把请求响应交互过程中的状态信息存储在HttpContext中
			HttpResponse response = httpClient.execute(httpGet, httpContext);
			//获取重定向之后的主机地址信息,即"http://127.0.0.1:8088"
			targetHost = (HttpHost)httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
			//获取实际的请求对象的URI,即重定向之后的"/blog/admin/login.jsp"
			HttpUriRequest realRequest = (HttpUriRequest)httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
			System.out.println("主机地址:" + targetHost);
			System.out.println("URI信息:" + targetHost+realRequest.getURI());
			HttpEntity entity = response.getEntity();
			if(null != entity){
				//System.out.println("响应内容:" + EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset()));
				EntityUtils.consume(entity);
			}
			 result = targetHost.toString() + realRequest.getURI();
			 targetHost.clone();
			 httpGet.clone();
			 
		 
			httpClient.getConnectionManager().shutdown();
		return result;
	}

 
	
}

 