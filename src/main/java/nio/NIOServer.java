package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
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
			
			//准备好读写的缓冲区
			ByteBuffer readBuffer=ByteBuffer.allocate(128);
			ByteBuffer writeBuffer=ByteBuffer.allocate(128);
			
			while(true) {
				//阻塞直到有通道被选择
				selector.select();
				Set<SelectionKey> keys=selector.selectedKeys();
				Iterator<SelectionKey>iterator=keys.iterator();
				while(iterator.hasNext()) {
					SelectionKey key=iterator.next();
					//将当前key从selector的中删除,这样在下一次这个key就不会出现
					iterator.remove();
					if(key.isAcceptable()) {
						SocketChannel client=server.accept();
						System.out.println("accept connection from "+client);
						//设置client socket对都不阻塞
						client.configureBlocking(false);
						//将client注册为可读，放入selector
						client.register(selector, SelectionKey.OP_READ);
					}else if(key.isReadable()) {
						SocketChannel client=(SocketChannel) key.channel();
						readBuffer.clear();
						int size=client.read(readBuffer);
						if(size!=-1) {
							readBuffer.flip();//不可少
							String clientWord=new String(readBuffer.array(),0,size);
							//改变key为写信号
							key.interestOps(SelectionKey.OP_WRITE);
							//根据客户传送的信息附带不同的信息
							if("wenqi".equals(clientWord)) {
								key.attach("welcome admin wenqi!");
							}else {
								key.attach("welcome client "+clientWord+"!");
							}
						}else {
							System.out.println("client closed:"+client);
							key.cancel();
							client.close();
						}
					} else if(key.isWritable()) {//isWritable有是否准备好&SelectionKey决定
						SocketChannel client=(SocketChannel) key.channel();
						String backword=(String) key.attachment();
						writeBuffer.clear();
						writeBuffer.put(backword.getBytes());
						writeBuffer.flip();
						client.write(writeBuffer);
						key.interestOps(SelectionKey.OP_READ);
						//不允许多次读写用cancel
						//key.cancel();
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
