package aio;

import guavafuture.ThreadTask;
import nio.NIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AioEchoServer {
    private int PORT = 9955;
    private String IP = "127.0.0.1";
    private AsynchronousServerSocketChannel server;

    public AioEchoServer() {
        try {
            server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(IP, PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startServer() {
        //attachment参数可以被CompletionHandler接收

        server.accept("attachment", new CompletionHandler<AsynchronousSocketChannel, String>() {
            @Override
            public void completed(AsynchronousSocketChannel client, String attachment) {
                //获取了对应的客户端socket
                try {
                    System.out.println("attachment: " + attachment);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //这里是非阻塞读取，需要使用get等待客户端返回结果
                    int readResult = client.read(byteBuffer).get();
                    System.out.println("读入数据量：" + readResult);
                    byteBuffer.flip();
                    System.out.println("Get from client:" + (new String(byteBuffer.array())));
                    int writeResult = client.write(byteBuffer).get();
                    if (writeResult > 0) {
                        System.out.println(client.getRemoteAddress() + ": " + "response success! write length: " + writeResult);
                    } else {
                        System.out.println("write length <0");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    server.accept(null,this);
                }
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                exc.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        AioEchoServer aioEchoServer = new AioEchoServer();
        aioEchoServer.startServer();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
