package com.test;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @Title: HttpConnection.java
 * @Package com.unionpay.upomp.api.util
 * @Description: TODO 通信类
 * @author huozhicheng@gmail.com
 * @date 2012-8-24 下午3:01:58
 * @version V1.0
 */
public class HttpConnection {

	public static String SubmitHttpRequest(String urlstr, String method, String data) {
		String responseString = "";
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlstr);
			connection = (HttpURLConnection) url.openConnection();
			if (connection != null) {
				//设置连接超时时间
				connection.setConnectTimeout(30000);
				connection.setRequestProperty("Content-Type", "text/xml");
				//设定请求的方法为"POST"，默认是GET
				connection.setRequestMethod(method);
				//设置是否从httpUrlConnection读入，默认情况下是true
				connection.setDoInput(true);
				//setDoOutput设置是否向httpUrlConnection输出，为true时，参数要放在 http正文内，默认情况下是false
				connection.setDoOutput(true);
				//连接到服务器
				connection.connect();
				if (data != null && data.length() > 0) {
					//现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象
					DataOutputStream dataOutput = new DataOutputStream(connection.getOutputStream());
					//向对象输出流写出数据
					dataOutput.writeBytes(data);
					//刷新对象输出流，将任何字节都写入潜在的流中
					dataOutput.flush();
					//关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中
					dataOutput.close();
				}
				// 获得响应状态
				int responseCode = connection.getResponseCode();
				System.out.println("响应码:"+responseCode);
				if (HttpURLConnection.HTTP_OK == responseCode) {
					InputStream is = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					String line;
					while ((line = reader.readLine()) != null){
						if (line.length() > 0){
							responseString += line.trim();
						}
					}
					reader.close();
				} else {
					System.out.println("Error:" + responseCode);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(connection != null){
				connection.disconnect();
			}
		}
		return responseString;
	}
}
/* session设置 */
//connection.setRequestProperty( "Cookie", "AB2A385B99F80092A314D36C4879D7AA");
//connection.setRequestProperty( "Cookie",  "JSESSIONID=00EFB692FCC647F4A5D322B7C767C850");