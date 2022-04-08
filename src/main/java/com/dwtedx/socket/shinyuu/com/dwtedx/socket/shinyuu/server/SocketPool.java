package com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName SocketPool
 * Description SocketPool是Socket连接的池，存放着所有已连接的socket对象
 * Create by shinyuu on 2022/4/7 16:12
 */
public class SocketPool {

    private static final ConcurrentHashMap<String, ClientSocket> ONLINE_SOCKET_MAP = new ConcurrentHashMap<>();


    public static void add(ClientSocket clientSocket){
        if (clientSocket != null && !clientSocket.getKey().isEmpty())
            ONLINE_SOCKET_MAP.put(clientSocket.getKey(), clientSocket);
    }

    public static void remove(String key){
        if (!key.isEmpty())
            ONLINE_SOCKET_MAP.remove(key);
    }
}
