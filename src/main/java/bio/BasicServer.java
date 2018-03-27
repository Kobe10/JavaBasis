package bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.omg.CORBA.SystemException;

public class BasicServer {

	public static void main(String[] args) {
		ServerSocket serverSocket=null;
		try {
			serverSocket=new ServerSocket();
			serverSocket.bind(new InetSocketAddress("127.0.0.1", 8888));
			while(true) {
				Socket client=serverSocket.accept();
				client.setSoTimeout(1000);//客户端read要在1秒内返回，不然认为是IO异常
				System.out.println("get connection:"+client);
				InputStream input=client.getInputStream();
				BufferedReader reader=new BufferedReader(new InputStreamReader(input));
				BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				
				try {
					while(true) {
						String clientWord= reader.readLine();
						client.sendUrgentData(0);//发送心跳包，如果客户端断开连接，则出现IO异常
						if(clientWord==null)
							continue;
						System.out.println(client.getRemoteSocketAddress()+":"+clientWord);
						if("wenqi".equals(clientWord)) {
							writer.write("welcome admin wenqi!\n");
							writer.flush();
						}else {
							writer.write("welcome client "+clientWord+"!\n");
							writer.flush();
						}
					}
				}catch(IOException e) {
					reader.close();
					writer.close();
					client.close();
					System.out.println("断开连接");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		

	}

}
