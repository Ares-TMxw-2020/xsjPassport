package com.zjrb.passport.net.request;

import android.os.SystemClock;
import android.text.TextUtils;

import com.zjrb.passport.Entity.ClientInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ErrorCode;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbInitListener;
import com.zjrb.passport.net.ApiManager;
import com.zjrb.passport.net.interfaces.CallBack;
import com.zjrb.passport.net.interfaces.IRequestHandler;
import com.zjrb.passport.net.interfaces.IResponseHandler;
import com.zjrb.passport.net.response.Response;
import com.zjrb.passport.net.response.ResponseBody;
import com.zjrb.passport.util.Logger;
import com.zjrb.passport.util.SslUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void handleRequest(final HttpCall call, final CallBack callBack) {
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
            long currentTime = SystemClock.elapsedRealtime();
            long lastTime = ZbPassport.getZbConfig().getSpUtil().getLong(ZbConstants.PASSPORT_NETTIME);
            System.out.println("RequestHandler lastTime:" + lastTime);
            System.out.println("RequestHandler currentTime:" + currentTime);
            ZbPassport.getZbConfig().getSpUtil().putLong(ZbConstants.PASSPORT_NETTIME, SystemClock.elapsedRealtime());
            long deltaTime = currentTime - lastTime;
            if (lastTime != 0 && deltaTime >= ZbConstants.PASSPORT_SIGN_EXPIRED) { // 大于30分钟,重新请求init接口
                ZbPassport.initApp(ZbPassport.getZbConfig().getAppId() + "", new ZbInitListener() {
                    @Override
                    public void onSuccess(ClientInfo info) {
                        if (info != null) {
                            ZbPassport.getZbConfig().setSignatureKey(info.getSignature_key()); // 设置签名密钥,30分钟有效期
                            //todo 当前网络请求重试  处理 同步
                            call.enqueue(callBack);
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {

                    }
                });
                return; // 取消当前网络请求
            }
            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] bytes = new byte[1024];
                int length;
                if (call.request != null && TextUtils.equals(ApiManager.EndPoint.INIT, call.request.getApi())) { // init接口,获取cookie持久化
                    // TODO: 2019/3/4 Cookie处理 只获取init接口下发的Cookie,添加到后续请求的请求头中
                    String cookie = connection.getHeaderField("Set-Cookie");
                    System.out.println("ssss INIT cookie: " + cookie);
                    ZbPassport.getZbConfig().setCookie(connection.getHeaderField("Set-Cookie"));
                }
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
                // code 1002
                String jsonString = response.body().string();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(jsonString);
                    int code = jsonObject.optInt("code");
                    if (code == ErrorCode.ERROR_SIGNATURE) {
                        ZbPassport.initApp(ZbPassport.getZbConfig().getAppId() + "", new ZbInitListener() {
                            @Override
                            public void onSuccess(ClientInfo info) {
                                if (info != null) {
                                    ZbPassport.getZbConfig().setSignatureKey(info.getSignature_key()); // 设置签名密钥,30分钟有效期
                                    //todo 当前网络请求重试  处理 同步
                                    call.enqueue(callBack);
                                }
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {

                            }
                        });
                        return; // 取消当前网络请求
                    }
                } catch (JSONException e) {
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

    private HttpURLConnection setHttpConfig(HttpCall call) throws IOException {
        URL url = new URL(call.request.url);
        if ("https".equalsIgnoreCase(url.getProtocol())) {
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

    private void writeContent(HttpURLConnection connection, RequestBody body) throws IOException {
        if (connection != null) {
            OutputStream outputStream = connection.getOutputStream();
            body.writeTo(outputStream);
        }
    }

    /**
     * 添加请求头
     *
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
