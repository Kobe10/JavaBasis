package nio.mulreactor;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.tools.ant.taskdefs.Sleep;

public class Processor {
	
	private ExecutorService pool=Executors.newFixedThreadPool(2*Runtime.getRuntime().availableProcessors());
	private Selector selector;
	public Processor() {
		try {
			selector=SelectorProvider.provider().openSelector();
			process();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void  addChannel(SocketChannel client) {
		try {
			//会被select()阻塞掉的
			client.register(selector, SelectionKey.OP_READ);
			//selector.wakeup();
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		}
	}
	
	public void wakeup() {
		selector.wakeup();
	}
	
	private void process() {
		pool.execute(()->{
			while(true) {
					try {
						//使用select()会有一个问题，wakeup后如果继续主线程的register如果没有执行
						//，会进入下一个select阻塞状态，register还是无法执行
						if(selector.select(500)<=0)
							continue;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Set<SelectionKey> keys=selector.selectedKeys();
					Iterator<SelectionKey> iterator=keys.iterator();
					while(iterator.hasNext()) {
						SelectionKey key=iterator.next();
						iterator.remove();
						if(key.isReadable()) {
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
								}else {
									client.close();
									key.cancel();
									System.out.println("client close:"+client);
								}
							}catch(IOException e) {
								try {
									client.close();
									key.cancel();
									System.out.println("client close:"+client);
								}catch(IOException e2) {
									e2.printStackTrace();
								}
							}
						}
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
