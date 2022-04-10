package com.dwtedx.socket.shinyuu.prod;

import com.dwtedx.socket.shinyuu.prod.server.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SocketShinyuuProdApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SocketShinyuuProdApplication.class, args);
        //在spring容器启动后，取到已经初始化的SocketServer，启动Socket服务
        applicationContext.getBean(SocketServer.class).start();
    }

}
