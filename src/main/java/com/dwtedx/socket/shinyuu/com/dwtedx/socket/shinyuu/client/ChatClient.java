package com.dwtedx.socket.shinyuu.com.dwtedx.socket.shinyuu.client;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * ClassName ChatClient
 * Description TODO
 * Create by shinyuu on 2022/4/7 16:16
 */
@Slf4j
public class ChatClient {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 5071;

        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(false);

        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String uuid = UUID.randomUUID().toString();
        log.info("uuid: {}", uuid);
        outputStream.write(uuid.getBytes());
        DataInputStream inputStream1 = new DataInputStream(socket.getInputStream());
        String content = "";
        while (true){
            byte[] buff = new byte[1024];
            inputStream.read(buff);
            String buffer = new String(buff, "utf-8");
            log.info("info: {}", buffer);
            content += buffer;
            File file = new File("json.txt");
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            out.write(content);
            out.flush();
            //FileWriter fileWriter = new FileWriter(file);
            //fileWriter.write(content);
            //fileWriter.flush();
        }
    }

}
