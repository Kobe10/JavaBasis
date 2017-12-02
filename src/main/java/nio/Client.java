package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
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
			SocketChannel client=SocketChannel.open();
			client.connect(new InetSocketAddress("127.0.0.1", 8888));
			
			ByteBuffer writeBuffer=ByteBuffer.allocate(128);
			ByteBuffer readBuffer=ByteBuffer.allocate(128);
			
			writeBuffer.clear();
			writeBuffer.put("wenqi".getBytes());
			writeBuffer.flip();
			client.write(writeBuffer);
			
			readBuffer.clear();
			int size=client.read(readBuffer);
			readBuffer.flip();
			String clientWord=new String(readBuffer.array(),0,size);
			System.out.println("server response to "+num+"-"+clientWord);
			
			writeBuffer.clear();

			writeBuffer.put(("good-"+num).getBytes());
			writeBuffer.flip();
			client.write(writeBuffer);
			
			readBuffer.clear();
			int size2=client.read(readBuffer);
			readBuffer.flip();
			System.out.println("server response to "+num+"-"+new String(readBuffer.array(),0,size2));
			client.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
