package com.dwtedx.socket.shinyuu.nio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * ClassName NioSelectorClient
 * Description Nio客户端
 * Create by shinyuu on 2022/4/14 14:28
 */
public class NioSelectorClient {


    public static void main(String[] args) throws Exception {
        // 1. 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9070));
        // 2. 切换非阻塞模式
        sChannel.configureBlocking(false);
        // 3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 4. 发送数据给服务端
        String str = new Date() + " " + "shinyuu 秦\r\n";
        buf.put(str.getBytes(StandardCharsets.UTF_8));
        buf.flip();
        sChannel.write(buf);
        sChannel.shutdownOutput();
        // 5. 关闭通道
        sChannel.close();
    }

}

