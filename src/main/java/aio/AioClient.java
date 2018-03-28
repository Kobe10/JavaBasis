package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
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
                        byteBuffer.flip();
                        //须阻塞写入
                        Future<Integer> writeFuture = client.write(byteBuffer);
                        System.out.println(writeFuture.get());
                        //须阻塞读取
                        client.read(byteBuffer).get(10, TimeUnit.SECONDS);
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array()));
                    } catch (Exception e) {
                        e.printStackTrace();
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

    }
}
