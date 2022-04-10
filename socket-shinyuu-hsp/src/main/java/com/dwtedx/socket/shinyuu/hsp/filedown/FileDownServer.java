package com.dwtedx.socket.shinyuu.hsp.filedown;

import com.dwtedx.socket.shinyuu.hsp.utils.StreamUtils;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description socket文件下载服务端
 * Created by shinyuu on 2022/4/10 4:49 PM.
 */
public class FileDownServer {
    public static void main(String[] args) throws IOException {
        int port = 4070;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("下载的文件服务启动成功，端口:" + port);
        //等待客户端链接
        Socket socket = serverSocket.accept();
        //读取客户端发送的文件名
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        String downloadFileName = ""; //循环读取文件名，兼容后期文件名很长的情况
        while ((len = inputStream.read(bytes)) != -1) {
            downloadFileName += new String(bytes, 0, len);
        }
        System.out.println("下载的文件名:" + downloadFileName);

        String resFileName = null;
        if (downloadFileName.contains("拥抱")) {
            resFileName = "files/隔壁老樊-多想在平庸的生活拥抱你 (Live).mp3";
        } else {
            resFileName = "files/儿歌-一只哈巴狗.mp3";
        }
        InputStream resourceAsStream = SecuritySupport.getResourceAsStream(resFileName);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);
        byte[] fileBytes = StreamUtils.InputStreamTOByte(bufferedInputStream);
        //写出数据
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        bufferedOutputStream.write(fileBytes);
        socket.shutdownOutput();

        bufferedInputStream.close();
        bufferedOutputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();

    }
}
