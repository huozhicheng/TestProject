package com.test.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author huozhicheng@gmail.com
 * @date 2013-3-6下午4:00:57
 * @version 1.0
 */
public class MyServer {

	public static void main(String[] args) throws IOException {
		//创建一个ServerSocket在端口***监听客户请求
		ServerSocket server = new ServerSocket(10001);
		//使用accept()阻塞等待客户请求，有客户请求到来则产生一个Socket对象，并继续执行
		Socket socket = server.accept();
		
		//由Socket对象得到输入流，并构造相应的BufferedReader对象
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//由Socket对象得到输出流，并构造PrintWriter对象
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		while(true){
			String msg = in.readLine();
			System.out.println(msg);
			out.println("服务器返回数据");
			out.flush();
			if(msg.equals("bye")){
				break;
			}
		}
		out.close();
		in.close();
		socket.close();
		server.close();
	}

}
