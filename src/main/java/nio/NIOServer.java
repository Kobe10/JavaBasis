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
			//����һ��server
			ServerSocketChannel server = ServerSocketChannel.open();
			server.bind(new InetSocketAddress("127.0.0.1", 8888));
			//����server��acceptΪ������ģʽ
			server.configureBlocking(false);
			
			//����Selector
			Selector selector=Selector.open();
			//Ϊserverע��selector����Ȥ���¼�
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			//׼���ö�д�Ļ�����
			ByteBuffer readBuffer=ByteBuffer.allocate(128);
			ByteBuffer writeBuffer=ByteBuffer.allocate(128);
			
			while(true) {
				//����ֱ����ͨ����ѡ��
				selector.select();
				Set<SelectionKey> keys=selector.selectedKeys();
				Iterator<SelectionKey>iterator=keys.iterator();
				while(iterator.hasNext()) {
					SelectionKey key=iterator.next();
					//����ǰkey��selector����ɾ��,��������һ�����key�Ͳ������
					iterator.remove();
					if(key.isAcceptable()) {
						SocketChannel client=server.accept();
						System.out.println("accept connection from "+client);
						//����client socket�Զ�������
						client.configureBlocking(false);
						//��clientע��Ϊ�ɶ�������selector
						client.register(selector, SelectionKey.OP_READ);
					}else if(key.isReadable()) {
						SocketChannel client=(SocketChannel) key.channel();
						readBuffer.clear();
						int size=client.read(readBuffer);
						if(size!=-1) {
							readBuffer.flip();//������
							String clientWord=new String(readBuffer.array(),0,size);
							//�ı�keyΪд�ź�
							key.interestOps(SelectionKey.OP_WRITE);
							//���ݿͻ����͵���Ϣ������ͬ����Ϣ
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
					} else if(key.isWritable()) {//isWritable���Ƿ�׼����&SelectionKey����
						SocketChannel client=(SocketChannel) key.channel();
						String backword=(String) key.attachment();
						writeBuffer.clear();
						writeBuffer.put(backword.getBytes());
						writeBuffer.flip();
						client.write(writeBuffer);
						key.interestOps(SelectionKey.OP_READ);
						//�������ζ�д��cancel
						//key.cancel();
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
