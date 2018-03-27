package bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServerWithPool {
	public static ExecutorService pool=Executors.newFixedThreadPool(16);

	public static void main(String[] args) {
		ServerSocket server=null;
		try {
			server=new ServerSocket();
			server.bind(new InetSocketAddress("127.0.0.1", 8888));
			
			while(true) {
				Socket client=server.accept();
				client.setSoTimeout(10000);
				pool.submit(()->{
					try {
						BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
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
							System.out.println("断开连接");
						}
						reader.close();
						writer.close();
						client.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
