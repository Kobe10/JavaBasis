package nio.mulreactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class NIOServerMulReactor {

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
			
			//获取多Reactor模式下的子Reactor处理器
			int coreNum=Runtime.getRuntime().availableProcessors();
			System.out.println("得到"+coreNum+"处理器单元");
			Processor[] processors=new Processor[coreNum];
			int index=0;
			for(int i=0;i<coreNum;i++) {
				processors[i]=new Processor();
			}
			
			while(true) {
				//阻塞直到有通道被选择
				selector.select();
				Set<SelectionKey> keys=selector.selectedKeys();
				Iterator<SelectionKey>iterator=keys.iterator();
				while(iterator.hasNext()) {
					SelectionKey  key=iterator.next();
					//将当前key从selector的中删除,这样在下一次这个key就不会出现
					iterator.remove();
					if(key.isAcceptable()) {
						SocketChannel client=server.accept();
						System.out.println("accept connection from "+client.getRemoteAddress());
						//设置client socket对都不阻塞
						client.configureBlocking(false);
						Processor processor=processors[index%coreNum];
						index=(index+1)%coreNum;
						processor.wakeup();
						processor.addChannel(client);
						
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
