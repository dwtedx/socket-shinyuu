package com.dwtedx.socket.shinyuu.prod.server;


import com.dwtedx.socket.shinyuu.prod.handler.SocketHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ClassName SocketService
 * Description Socket服务端实现
 * Create by shinyuu on 2022/4/7 16:08
 */
@Slf4j
@Data
@Component
@PropertySource("classpath:socket.properties")
@NoArgsConstructor
public class SocketServer {

    @Value("${port}")
    private Integer port;
    private boolean started;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        new SocketServer().start(5071);
    }

    public void start() {
        start(null);
    }

    //@Autowired
    //private UserService userService;//测试使用


    public void start(Integer port) {
        log.info("port: {}, {}", this.port, port);
        try {
            serverSocket = new ServerSocket(port == null ? this.port : port);
            started = true;
            log.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());
        } catch (IOException e) {
            log.error("端口冲突,异常信息：{}", e);
            System.exit(0);
        }

        while (started) {
            try {
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(true);
                ClientSocket register = SocketHandler.register(socket);
                if (register != null) {
                    log.info("客户端已连接，其Key值为：{}", register.getKey());
                    executorService.submit(register);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}