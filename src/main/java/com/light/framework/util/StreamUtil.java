package com.light.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 流操作工具类
 * Created by wanganyu on 2018/03/28.
 */
public class StreamUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     * @return
     */
    public static String getString(InputStream is){
        StringBuilder sb=new StringBuilder();
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
         LOGGER.error("get string failure",e);
         throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 将输入流复制到输出流
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream){

        try {
            int length;
            byte[] buffer=new byte[4*1024];
            while((length=inputStream.read(buffer,0,buffer.length))!=-1){
              outputStream.write(buffer,0,length);
            }
            outputStream.flush();
        } catch (Exception e) {
            LOGGER.error("copy stream failure",e);
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
              LOGGER.error("close stream failure",e);
            }
        }
    }
}
