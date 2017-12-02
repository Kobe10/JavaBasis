package bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

	public static void main(String[] args) {
		for(int i=1;i<=10;i++) {
			new myThread(i).start();
		}
	}

}

class myThread extends Thread{
	private int num;
	public myThread(int i) {
		num=i;
	}
	@Override
	public void run() {
		try {
			Socket client=new Socket("127.0.0.1", 8888);
			OutputStream out=client.getOutputStream();
			InputStream input=client.getInputStream();
			
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
			BufferedReader reader=new BufferedReader(new InputStreamReader(input));
			writer.write("wenqi\n");
			writer.flush();
			String clientWord=reader.readLine();
			System.out.println("server response to "+num+"-"+clientWord);
			
			writer.write("good-"+num+"\n");
			writer.flush();
			String clientWord2=reader.readLine();
			System.out.println("server response to "+num+"-"+clientWord2);
		
			writer.close();
			reader.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
