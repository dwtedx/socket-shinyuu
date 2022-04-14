package com.dwtedx.socket.shinyuu.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * ClassName NioSelectorServer
 * Description NIO Demo
 * Create by shinyuu on 2022/4/14 13:48
 */
public class NioSelectorServer {

    public static void main(String[] args) throws IOException {

        int port = 9070;
        //创建NIO Server
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        //设置Socket非阻塞
        serverSocketChannel.configureBlocking(false);
        //打开Selector处理Channel，即创建epoll
        Selector selector = Selector.open();
        //把serverSocketChannel注册到Selector上
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NIO ServerSocket已启动，端口：" + port);

        while (true) {
            //阻塞等待需要处理的事件发生
            selector.select();
            //获取selector中注册的全部事件的 selectionKey 实例，selectionKey就是监听有事件发生（数据通信）的客户端
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //处理发生事件
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                //OP_ACCEPT事件
                if (key.isAcceptable()){
                    ServerSocketChannel serverSocket = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocket.accept();
                    socketChannel.configureBlocking(false);
                    //这里注册了读事件，如果需要给客户端发送数据，可以注册写事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    //socketChannel.register(selector, SelectionKey.OP_WRITE);
                    System.out.println("客户端连接成功");
                }
                //读客户端的数据
                else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int len = socketChannel.read(byteBuffer);
                    if(len > 0){
                        System.out.println("接收到消息" + new String(byteBuffer.array()));
                    }
                    else if(len == -1){
                        System.out.println("客户端已断开");
                        socketChannel.close();
                    }

                }
                //从事件集合中删除本次事件，防止下次select重复
                //iterator.remove();
            }

        }

    }

}
