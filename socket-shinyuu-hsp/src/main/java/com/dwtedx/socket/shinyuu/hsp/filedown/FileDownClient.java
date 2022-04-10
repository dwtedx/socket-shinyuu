package com.dwtedx.socket.shinyuu.hsp.filedown;

import com.dwtedx.socket.shinyuu.hsp.utils.StreamUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Description
 * Created by shinyuu on 2022/4/10 5:42 PM.
 */
public class FileDownClient {

    public static void main(String[] args) throws IOException {
        //接受用户的输入
        System.out.println("请输入要下载的文件名称:");
        Scanner scanner = new Scanner(System.in);
        String downFileName = scanner.next();
        //连接服务端
        Socket socket = new Socket(InetAddress.getLocalHost(), 4070);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(downFileName.getBytes());
        socket.shutdownOutput();

        //读取文件
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtils.InputStreamTOByte(bufferedInputStream);
        //存储到桌面
        String filePath = "/Users/shinyuu/Desktop/" + downFileName + ".mp3";
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream((filePath)));
        bufferedOutputStream.write(bytes);

        bufferedOutputStream.close();
        bufferedInputStream.close();
        outputStream.close();
        socket.close();

    }

}
