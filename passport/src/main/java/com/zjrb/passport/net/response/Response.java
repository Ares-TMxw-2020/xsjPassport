package com.zjrb.passport.net.response;

import com.zjrb.passport.util.Logger;

/**
 * Date: 2018/6/28 下午3:19
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 封装响应信息
 */
public class Response {

    final int code;
    final String message;
    final ResponseBody body;

    public Response(Builder builder) {
        this.body = builder.body;
        this.message = builder.message;
        this.code = builder.code;
    }

    public ResponseBody body() {
        return this.body;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static class Builder {

        private ResponseBody body;
        private String message;
        private int code;

        public Builder body(ResponseBody body) {
            this.body = body;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Response build() {
            if (message == null) Logger.e("response message == null");
            if (body == null) Logger.e("response data == null");
            return new Response(this);
        }

    }
}
