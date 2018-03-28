package aio;

import guavafuture.ThreadTask;
import nio.NIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
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
                    //非阻塞读取，需要使用get等待读取结果返回
                    client.read(byteBuffer).get(10, TimeUnit.SECONDS);
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array()));
                    Future<Integer> future = client.write(byteBuffer);
                    int flag = 0;
                    flag = future.get();
                    if (flag > 0) {
                        System.out.println(client.getRemoteAddress() + ": " + "response success! flag: "+flag);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
