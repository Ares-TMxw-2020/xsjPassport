package com.zjrb.passport.net;

import com.zjrb.passport.net.interfaces.IRequestHandler;
import com.zjrb.passport.net.util.SslUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 * Date: 2018/6/30 下午9:36
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 请求处理封装
 */
public class RequestHandler implements IRequestHandler {
    
    @Override
    public Response handleRequest(HttpCall call) throws IOException {
        if (call == null) {
            throw new NullPointerException("请求的call不能为空");
        }
        HttpURLConnection connection = setHttpConfig(call);
        if (!call.request.headers.isEmpty()) { // 添加请求头
            addHeaders(connection, call.request);
        }
        if (call.request.requestBody != null) { // 上传表单
            writeContent(connection, call.request.requestBody);
        }
        if (!connection.getDoOutput()) {
            connection.connect();
        }
        
        // 返回内容解析
        int responseCode = connection.getResponseCode();
        // TODO: 2018/7/2  
        if (responseCode == 200) {
            Response response;
            byte[] bytes = new byte[1024];
            int length = 0;
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                while ((length = inputStream.read(bytes)) != -1) {
                    baos.write(bytes, 0, length);
                }
                response = new Response.Builder().code(responseCode).message(connection.getResponseMessage())
                        .body(new ResponseBody(baos.toByteArray())).build();
            } finally {
                inputStream.close();
                connection.disconnect();
            }
            return response;
        }
        throw new IOException(String.valueOf(connection.getResponseCode()));
    }
    
    private HttpURLConnection setHttpConfig(HttpCall call) throws IOException{
        URL url = new URL(call.request.url);
        if("https".equalsIgnoreCase(url.getProtocol())){
            try {
                SslUtils.ignoreSsl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(call.config.connTimeout);
        connection.setReadTimeout(call.config.readTimeout);
        connection.setDoInput(true);
        if (call.request.requestBody != null && Request.HttpMethod.checkNeedBody(call.request.method)) {
            connection.setDoOutput(true);
        }
        return connection;
    }
    
    private void writeContent(HttpURLConnection connection, RequestBody body) throws IOException{
        if (connection != null) {
            OutputStream outputStream = connection.getOutputStream();
            body.writeTo(outputStream);
        }
    }

    /**
     * 添加请求头
     * @param connection
     * @param request
     */
    private void addHeaders(HttpURLConnection connection, Request request) {
        if (request == null) {
            return;
        }
        if (request.headers != null && request.headers.size() != 0) {
            Set<String> set = request.headers.keySet();
            for (String key : set) {
                connection.addRequestProperty(key, request.headers.get(key));
            }
        }
    }
}
