package com.test.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author huozhicheng@gmail.com
 * @date 2013-3-6下午4:12:19
 * @version 1.0
 */
public class MyClient {

	public static void main(String[] args) throws IOException {
		//向本机的***端口发出请求
		Socket socket = new Socket("localhost",10001);
		
		//由Socket对象得到输入流，并构造相应的BufferedReader对象
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//由Socket对象得到输出流，并构造PrintWriter对象
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		
		//由系统标准输入设备构造BufferedReader对象
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true){
			String msg = reader.readLine();
			out.println(msg);
			out.flush();
			System.out.println("您输入的字符串为："+msg);
			System.out.println("接收到服务器的数据位："+in.readLine());
			if(msg.equals("bye")){
				break;
			}
		}
		out.close();
		in.close();
		socket.close();
	}

}
