package passportdemo.zjrb.com.zjrbpassport;

import android.app.Application;

import com.zjrb.passport.ZbPassport;

/**
 * Function: App
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZbPassport.init(this);
//        ZbPassport.init(this,
//                        new ZbConfigBuilder().setAppId(2).setAppKey("").setAppSecret("World").setEnvType(2));
    }
}
