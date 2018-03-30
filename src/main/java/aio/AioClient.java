package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AioClient {
    public static void main(String[] args) {
        String IP = "127.0.0.1";
        int PORT = 9955;
        final AsynchronousSocketChannel client;
        try {
            client = AsynchronousSocketChannel.open();
            SocketAddress serverSocketAddress = new InetSocketAddress(IP, PORT);
            client.connect(serverSocketAddress, "clientAttachment", new CompletionHandler<Void, String>() {
                @Override
                public void completed(Void result, String attachment) {
                    System.out.println(attachment);
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.wrap("hello".getBytes());
                        //wrap后不用byteBuffer.flip()，此时position=0，limit是之前position的位置
                        //read后的postion是写入的界限，limit=capbility,flip做的事情:limit=poition,position=0.
                        //须阻塞写入
                        int writeResult = client.write(byteBuffer).get();
                        System.out.println("写入Byte数：" + writeResult);
                        //须阻塞读取
                        byteBuffer.clear();
                        int readResult = client.read(byteBuffer).get();
                        byteBuffer.flip();
                        System.out.println("server response: " + (new String(byteBuffer.array())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void failed(Throwable exc, String attachment) {
                    exc.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        scanner.nextLine();

    }
}
