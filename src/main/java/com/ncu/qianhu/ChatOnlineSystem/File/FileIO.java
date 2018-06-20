package com.ncu.qianhu.ChatOnlineSystem.File;

import com.ncu.qianhu.ChatOnlineSystem.Bean.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/14
 * @Modified by
 */
public class FileIO {
    public static String charOutStream(ArrayList<Message> messages, String user) throws Exception{
        // 1：利用File类找到要操作的对象
        File file = new File("E:" + File.separator + "history" + File.separator + user +".txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        //2：准备输出流
        Writer out = new FileWriter(file);
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            out.write(message.getMessageId()+"\r\n");
            out.write(message.getMessageTime()+"\r\n");
            out.write(message.getMessageSenderId()+"\r\n");
            out.write(message.getMessageReceiverId()+"\r\n");
            out.write(message.getMessageContent()+"\r\n");
            out.write("\r\n");
        }
        out.close();
        return file.getAbsolutePath();
    }

}
