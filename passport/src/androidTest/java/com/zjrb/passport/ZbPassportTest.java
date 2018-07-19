package com.zjrb.passport;

import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;
import com.zjrb.passport.listener.ZbLoginListener;
import com.zjrb.passport.listener.ZbLogoutListener;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Function: ZbPassportTest
 * <p>
 * Author: chen.h
 * Date: 2018/7/19
 */
public class ZbPassportTest {

    @BeforeClass
    public static void init() {
        ZbPassport.init(null,
                        new ZbConfigBuilder().setAppId(1)
                                             .setAppKey("fQNZBzR9ej")
                                             .setAppSecret("E1v9TSMLLit2VHTTl0i9")
                                             .setEnvType(1)
                                             .setAppVersion("1.0")
                                             .setAppUuid("uuid"));
    }


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void sendCaptcha() {
        ZbPassport.sendCaptcha(ZbConstants.SMS_BIND, phoneNumber, new ZbCaptchaSendListener() {
            @Override
            public void onSuccess() {
                assertTrue(true);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                fail(errorMessage);
            }
        });
    }

    @Test
    public void verifyCaptcha() {
    }

    private String phoneNumber = "13758284975";
    private String password = "123";

    @Test
    public void register() {
//        ZbPassport.register(phoneNumber, );
    }

    @Test
    public void login() {
        ZbPassport.login(phoneNumber, "123", new ZbLoginListener() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                assertTrue(true);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                fail(errorMessage);
            }
        });
    }

    @Test
    public void loginCaptcha() {
    }

    @Test
    public void loginThird() {
    }

    @Test
    public void getInfo() {
    }

    @Test
    public void changePassword() {
    }

    @Test
    public void checkPassword() {
    }

    @Test
    public void findPassword() {
    }

    @Test
    public void bindPhone() {
    }

    @Test
    public void checkBindState() {
        ZbPassport.checkBindState(phoneNumber, new ZbCheckPhoneListener() {
            @Override
            public void onSuccess(boolean isBind) {
                assertTrue(isBind);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                fail(errorMessage);
            }
        });
    }

    @Test
    public void logout() {
        ZbPassport.logout(new ZbLogoutListener() {
            @Override
            public void onSuccess() {
                assertTrue(true);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                fail(errorMessage);
            }
        });
    }

    @Test
    public void bindThird() {
    }

    @Test
    public void unbindThird() {
    }
}