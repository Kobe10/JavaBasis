package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class NIOServerConcurrent {

	public static void main(String[] args) {
		try {
			//开启一个server
			ServerSocketChannel server = ServerSocketChannel.open();
			server.bind(new InetSocketAddress("127.0.0.1", 8888));
			//设置server的accept为非阻塞模式
			server.configureBlocking(false);
			
			//设置Selector
			Selector selector=Selector.open();
			//为server注册selector感兴趣的事件
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			//读写操作处理器
			IWorker readProcessor=new Worker();
			
			//进入读写状态的队列
			Queue<SelectionKey> operateQueue=new LinkedList<>();
			
			while(true) {
				//阻塞直到有通道被选择
				selector.select();
				Set<SelectionKey> keys=selector.selectedKeys();
				Iterator<SelectionKey>iterator=keys.iterator();
				while(iterator.hasNext()) {
					SelectionKey  key=iterator.next();
					//将当前key从selector的中删除,这样在下一次这个key就不会出现
					iterator.remove();
					if(operateQueue.contains(key))
						continue;
					if(key.isAcceptable()) {
						SocketChannel client=server.accept();
						System.out.println("accept connection from "+client.getRemoteAddress());
						//设置client socket对都不阻塞
						client.configureBlocking(false);
						//将client注册为可读，放入selector
						client.register(selector, SelectionKey.OP_READ);
					}else if(key.isReadable()) {
						//分发给Read线程池处理(从内核空间拷贝到用户空间)
						operateQueue.offer(key);
						readProcessor.process(key,operateQueue);
					} 
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
