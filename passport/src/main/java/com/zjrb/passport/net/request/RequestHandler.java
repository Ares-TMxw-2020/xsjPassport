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
            connection.connect();
        } catch (IOException e) {
            responseHandler.handleFail(callBack, call.request, ErrorCode.ERROR_HTTP, "网络请求异常,请检查网络连接状态");
            return;
        }

        if (call.isCanceled()) {
            Logger.d("取消请求: " + call.request);
            return;
        }

        long currentTime = SystemClock.elapsedRealtime();
        long lastTime = ZbPassport.getZbConfig().getSpUtil().getLong(ZbConstants.PASSPORT_NETTIME);
        ZbPassport.getZbConfig().getSpUtil().putLong(ZbConstants.PASSPORT_NETTIME, SystemClock.elapsedRealtime());
        long deltaTime = currentTime - lastTime;
        if (!TextUtils.equals(ApiManager.EndPoint.INIT, call.request.getApi()) && lastTime != 0 && deltaTime >= ZbConstants.PASSPORT_SIGN_EXPIRED) { // 当前请求非init接口请求,且两次请求间隔大于30分钟,重新请求init接口
            ZbPassport.initApp(new ZbInitListener() {
                @Override
                public void onSuccess(ClientInfo info) {
                    if (info != null) {
                        ZbPassport.getZbConfig().setSignatureKey(info.getSignature_key()); // 设置签名密钥,30分钟有效期
                        // 因为init接口重新请求后sign会重新下发,所以重新请求需要刷新header中sign及uuid及cookie信息,重新进行当前请求
                        call.request.refreshHeader(info.getSignature_key());
                        call.enqueue(callBack); // 重新进行当前请求
                    }
                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {

                }
            });
            return; // 取消当前网络请求
        }

        // 返回内容解析
        Response response = null;
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] bytes = new byte[1024];
                int length;
                if (TextUtils.equals(ApiManager.EndPoint.INIT, call.request.getApi())) { // init接口,获取cookie持久化
                    // Cookie处理 只获取init接口下发的Cookie,添加到后续请求的请求头中
                    ZbPassport.getZbConfig().setCookie(connection.getHeaderField("Set-Cookie"));
                }
                if (TextUtils.isEmpty(ZbPassport.getZbConfig().getCookie())) { // 容错,如果init接口下发的cookie为空,则取当前请求的cookie
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
                String jsonString = response.body().string();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(jsonString);
                    int code = jsonObject.optInt("code");
                    if (code == ErrorCode.ERROR_SESSION_NEED_INIT) {
                        ZbPassport.initApp(new ZbInitListener() {
                            @Override
                            public void onSuccess(ClientInfo info) {
                                if (info != null) {
                                    ZbPassport.getZbConfig().setSignatureKey(info.getSignature_key()); // 设置签名密钥,30分钟有效期
                                    // 因为init接口重新请求后sign会重新下发,所以重新请求需要刷新header中sign及uuid及cookie信息,重新进行当前请求
                                    call.request.refreshHeader(info.getSignature_key());
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
            responseHandler.handleFail(callBack, call.request, ErrorCode.ERROR_HTTP, "返回内容解析异常");
        }

        if (response == null) {
            response = new Response.Builder().code(-1).message("网络请求异常").body(new ResponseBody(null)).build();
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
        // connection.setReadTimeout not mean read complete, it mean when wait for timeout, when there're no more data read in, will throw a timeoutexception
        connection.setReadTimeout(call.config.readTimeout);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        if (call.request.requestBody != null && Request.HttpMethod.checkNeedBody(call.request.method)) {
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
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
