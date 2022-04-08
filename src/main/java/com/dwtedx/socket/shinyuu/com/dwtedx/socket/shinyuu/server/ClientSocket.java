package com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.server;


import com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.config.BeanContext;
import com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.handler.SocketHandler;
import com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
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
            list.add(new UserModel(1L, "shinyuu", "shinyuu qin"));
            list.add(new UserModel(2L, "qyl", "qyl qin"));
            list.add(new UserModel(3L, "zhang", "zhang qin"));
            list.add(new UserModel(4L, "li", "li qin"));

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

        while (true){
            try {
                //发送数据
                SocketHandler.sendMessage(this, getData());

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
