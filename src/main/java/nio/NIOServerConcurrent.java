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
			//����һ��server
			ServerSocketChannel server = ServerSocketChannel.open();
			server.bind(new InetSocketAddress("127.0.0.1", 8888));
			//����server��acceptΪ������ģʽ
			server.configureBlocking(false);
			
			//����Selector
			Selector selector=Selector.open();
			//Ϊserverע��selector����Ȥ���¼�
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			//��д����������
			IWorker readProcessor=new Worker();
			
			//�����д״̬�Ķ���
			Queue<SelectionKey> operateQueue=new LinkedList<>();
			
			while(true) {
				//����ֱ����ͨ����ѡ��
				selector.select();
				Set<SelectionKey> keys=selector.selectedKeys();
				Iterator<SelectionKey>iterator=keys.iterator();
				while(iterator.hasNext()) {
					SelectionKey  key=iterator.next();
					//����ǰkey��selector����ɾ��,��������һ�����key�Ͳ������
					iterator.remove();
					if(operateQueue.contains(key))
						continue;
					if(key.isAcceptable()) {
						SocketChannel client=server.accept();
						System.out.println("accept connection from "+client.getRemoteAddress());
						//����client socket�Զ�������
						client.configureBlocking(false);
						//��clientע��Ϊ�ɶ�������selector
						client.register(selector, SelectionKey.OP_READ);
					}else if(key.isReadable()) {
						//�ַ���Read�̳߳ش���(���ں˿ռ俽�����û��ռ�)
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
