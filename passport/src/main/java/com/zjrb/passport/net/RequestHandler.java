package com.zjrb.passport.net;

import com.zjrb.passport.net.interfaces.CallBack;
import com.zjrb.passport.net.interfaces.IRequestHandler;
import com.zjrb.passport.net.interfaces.IResponseHandler;
import com.zjrb.passport.util.SslUtils;
import com.zjrb.passport.util.Logger;

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

    private IResponseHandler responseHandler = IResponseHandler.RESPONSE_HANDLER;

    @Override
    public void handleRequest(HttpCall call, CallBack callBack) {
        if (call == null) {
            throw new NullPointerException("请求的call不能为空");
        }
        HttpURLConnection connection = null;
        try {
            connection = setHttpConfig(call);
            if (!call.request.headers.isEmpty()) { // 添加请求头
                addHeaders(connection, call.request);
            }
            if (call.request.requestBody != null) { // 上传表单
                writeContent(connection, call.request.requestBody);
            }
            if (!connection.getDoOutput()) {
                connection.connect();
            }
        } catch (IOException e) {
            responseHandler.handleFail(callBack, call.request, -1, "请求异常");
        }

        if (call.isCanceled()) {
            Logger.d("取消请求: " + call.request);
            return;
        }

        // 返回内容解析
        Response response = null;
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                byte[] bytes = new byte[1024];
                int length;
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    while ((length = inputStream.read(bytes)) != -1) {
                        baos.write(bytes, 0, length);
                    }
                    response = new Response.Builder().code(responseCode)
                                                     .message(connection.getResponseMessage())
                                                     .body(new ResponseBody(baos.toByteArray()))
                                                     .build();
                } finally {
                    inputStream.close();
                    connection.disconnect();
                }
                responseHandler.handleSuccess(callBack, response);
            } else {
                responseHandler.handleFail(callBack, call.request, responseCode, connection.getResponseMessage());
            }
        } catch (IOException e) {
            responseHandler.handleFail(callBack, call.request, -1, "返回内容解析异常");
        }

        if (response == null) {
            response = new Response.Builder().code(-1).message("请求异常").body(new ResponseBody(null)).build();
        }
        Logger.d(call.request, response);

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
