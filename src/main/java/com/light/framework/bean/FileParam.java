package com.light.framework.bean;

import java.io.InputStream;

/**
 * Created by wanganyu on 2018/04/17.
 */
public class FileParam {
    private String fieldName ;//文件表单的字段名
    private String fileName ;//上传文件的文件名
    private long fileSize ;//上传文件的文件大小
    private String contentType;//上传文件的类型
    private InputStream inputStream;//上传文件的字节输入流
    private String realPath;//上传文件到服务器的路径

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream, String realPath) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;

        this.contentType = contentType;
        this.inputStream = inputStream;
        this.realPath = realPath;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


}
