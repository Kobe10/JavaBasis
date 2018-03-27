package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Worker implements IWorker {
	//开10个读的线程池
	public static final ExecutorService pool=Executors.newFixedThreadPool(36);

	@Override
	public void process(SelectionKey key, Queue<SelectionKey>operateQueue) {
		//执行读的逻辑
		pool.submit(()->{ 
			SocketChannel client=null;
			try {
					ByteBuffer readBuffer=ByteBuffer.allocate(128);
					client=(SocketChannel)key.channel();
					int size=client.read(readBuffer);
					if(size!=-1) {
						readBuffer.flip();
						String clientWord=new String(readBuffer.array(),0,size);
						System.out.println("clientWord:"+clientWord);
						
						if("wenqi".equals(clientWord)) {
							key.attach("welcome admin wenqi!");
							writeProcess(key);
						}else {
							key.attach("welcome client "+clientWord+"!");
							writeProcess(key);
						}
						operateQueue.remove(key);
						
					}else {
						client.close();
						key.cancel();
						System.out.println("client close:"+client);
					}
			
			}catch(IOException e) {
				e.printStackTrace();
				try {
					client.close();
					key.cancel();
					System.out.println("client close:"+client);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	public static void writeProcess(SelectionKey key) {
		try {
			ByteBuffer writeBuffer=ByteBuffer.allocate(128);
			SocketChannel client=(SocketChannel) key.channel();
			String backword=(String) key.attachment();
			System.out.println("writing:"+backword);
			writeBuffer.put(backword.getBytes());
			writeBuffer.flip();
			client.write(writeBuffer);
		}catch(IOException e) {
			e.printStackTrace();
		}
}

}
