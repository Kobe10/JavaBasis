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
				client.setSoTimeout(1000);//�ͻ���readҪ��1���ڷ��أ���Ȼ��Ϊ��IO�쳣
				System.out.println("get connection:"+client);
				InputStream input=client.getInputStream();
				BufferedReader reader=new BufferedReader(new InputStreamReader(input));
				BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				
				try {
					while(true) {
						String clientWord= reader.readLine();
						client.sendUrgentData(0);//����������������ͻ��˶Ͽ����ӣ������IO�쳣
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
					System.out.println("�Ͽ�����");
				}
				reader.close();
				writer.close();
				client.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		

	}

}
