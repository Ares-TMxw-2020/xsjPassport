package com.zjrb.passport.net;

/**
 * Date: 2018/6/28 下午6:21
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 参考OkHttpClent
 */
public class ZbHttpClient {

    public Config config;

    public ZbHttpClient(Builder builder) {
        this.config = new Config(builder);
    }

    public Call newCall(Request request) {
        return new HttpCall(config, request);
    }

    static class Config {
        final int connTimeout;
        final int readTimeout;
        final int writeTimeout;

        public Config(Builder builder) {
            this.connTimeout = builder.connTimeout;
            this.readTimeout = builder.readTimeout;
            this.writeTimeout = builder.writeTimeout;
        }
    }

    public static final class Builder {
        private int connTimeout;
        private int readTimeout;
        private int writeTimeout;

        public Builder() {
            // 参考浙江新闻超时时长的设置
            this.connTimeout = 5 * 1000;
            this.readTimeout = 20 * 1000;
            this.writeTimeout = 20 * 1000;
        }


        public Builder readTimeOut(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder connTimeOut(int connTimeout) {
            this.connTimeout = connTimeout;
            return this;
        }

        public Builder writeTimeOut(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public ZbHttpClient build() {
            return new ZbHttpClient(this);
        }

    }
    
}
