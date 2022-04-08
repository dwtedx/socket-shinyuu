package com.dwtedx.socket.shinyuu;

import com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.server.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SocketShinyuuApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SocketShinyuuApplication.class, args);
        //在spring容器启动后，取到已经初始化的SocketServer，启动Socket服务
        applicationContext.getBean(SocketServer.class).start();
    }

}
