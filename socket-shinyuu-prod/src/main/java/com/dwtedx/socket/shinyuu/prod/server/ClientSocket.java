package com.dwtedx.socket.shinyuu.prod.server;


import com.dwtedx.socket.shinyuu.prod.config.BeanContext;
import com.dwtedx.socket.shinyuu.prod.handler.SocketHandler;
import com.dwtedx.socket.shinyuu.prod.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * ClassName ClientSocket
 * Description 自定义封装的连接的客户端
 * Create by shinyuu on 2022/4/7 16:13
 */
@Slf4j
@Data
@NoArgsConstructor
@Component
public class ClientSocket implements Runnable {

    private ObjectMapper objectMapper;

    /**
     * 不理想的注入方法
     */
    //public static ClientSocket clientSocket;      // 关键
    //@Autowired
    //private ObjectMapper objectMapper;

    // 关键
    //@PostConstruct
    //public void init() {
    //    clientSocket = this;
    //    clientSocket.objectMapper = this.objectMapper;
    //}

    /**
     * socket 属性
     */
    private Socket socket;
    //private DataInputStream inputStream; //字节流
    //private DataOutputStream outputStream; //字节流
    private BufferedReader bufferedReader; //字符流 读
    private BufferedWriter bufferedWriter; //字符流 写
    private String key;
    private String message;

    /**
     * 摸你数据
     * @return
     */
    private String getData(){
        try {
            //摸你数据
            List<UserModel> list = new ArrayList<>();
            list.add(new UserModel(1L, "shinyuu", "shinyuu 秦"));
            list.add(new UserModel(2L, "qyl", "qyl 张"));
            list.add(new UserModel(3L, "zhang", "zhang 王"));
            list.add(new UserModel(4L, "li", "li 李"));

            //转json
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        objectMapper = BeanContext.getApplicationContext().getBean(ObjectMapper.class);
        //发送数据
        for (int i = 0; i < 5; i++) {
            try {
                SocketHandler.sendMessage(this, getData());
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (true){
            try {
                //每5秒进行一次客户端连接，判断是否需要释放资源
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (SocketHandler.isSocketClosed(this)){
                log.info("客户端已关闭,其Key值为：{}", this.getKey());
                //关闭对应的服务端资源
                SocketHandler.close(this);
                break;
            }
        }
    }

}
