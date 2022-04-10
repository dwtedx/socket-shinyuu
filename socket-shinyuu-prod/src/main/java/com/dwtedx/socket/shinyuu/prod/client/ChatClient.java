package com.dwtedx.socket.shinyuu.prod.client;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * ClassName ChatClient
 * Description Socket客户端
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
        //DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        //DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String uuid = UUID.randomUUID().toString();
        log.info("uuid: {}", uuid);
        //outputStream.write(uuid.getBytes());
        bufferedWriter.write(uuid);
        bufferedWriter.newLine(); //插入一个换行符，表示写入结束
        bufferedWriter.flush(); //字符流需要手动刷新，否则数据不会写入数据通道
        String content = "";
        while (true){
            //byte[] buff = new byte[1024];
            //inputStream.read(buff, 0, buff.length);
            //String buffer = new String(buff, StandardCharsets.UTF_8);
            String buffer = bufferedReader.readLine();
            log.info("info: {}", buffer);
            content += buffer;
            File file = new File("json.txt");
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),StandardCharsets.UTF_8);
            out.write(content);
            out.flush();
            //FileWriter fileWriter = new FileWriter(file);
            //fileWriter.write(content);
            //fileWriter.flush();
        }
    }

}
