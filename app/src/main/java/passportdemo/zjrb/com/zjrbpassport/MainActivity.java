package passportdemo.zjrb.com.zjrbpassport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zjrb.passport.net.ZbHttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import passportdemo.zjrb.com.zjrbpassport.activity.RegisterActvity;

public class MainActivity extends AppCompatActivity {

    ZbHttpClient client;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_captcha)
    EditText mEtCaptcha;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_password_login)
    TextView mTvPasswordLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_send)
    TextView mTvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        client = new ZbHttpClient.Builder().connTimeOut(10 * 1000)
                .readTimeOut(10 * 1000)
                .writeTimeOut(10 * 1000)
                .build();
    }

    @OnClick({R.id.tv_login, R.id.tv_password_login, R.id.tv_register, R.id.tv_send})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_login:
                break;
            case R.id.tv_password_login:
                break;
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActvity.class);
                startActivity(intent);
                break;
            case R.id.tv_send:
                break;
        }
    }

  /*  public void sendRegisterCaptcha(View view) {
        ZbPassport.sendRegisterCaptcha("13758284975", new ZbListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }

    *//**
     * 同步get请求
     *
     * @param view
     *//*
    public void syncGetTest(View view) {
        Request request = null;
        try {
            FormBody body = new FormBody.Builder().add("menu", "土豆").add("rn", "15").add("start", "1").build();
            request = new Request.Builder().get(body).url("http://caipu.yjghost.com/index.php/query/read").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                Toast.makeText(this, "同步get请求成功  code =" + response.code(), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    *//**
     * 同步post
     *
     * @param view
     *//*
    public void syncPostTest(View view) {
        Request request = null;
        try {
            request = new Request.Builder().post(null).url("https://apibeta.8531.cn/api/account/init").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                Toast.makeText(this, "同步Post请求成功  code =" + response.code(), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    *//**
     * 异步get
     *
     * @param view
     *//*
    public void aSyncGetTest(View view) {
        Request request = null;
        try {
            FormBody body = new FormBody.Builder().add("menu", "土豆").add("rn", "15").add("start", "1").build();
            request = new Request.Builder().get(body).url("http://caipu.yjghost.com/index.php/query/read").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.newCall(request).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Toast.makeText(MainActivity.this, "异步GET请求成功  code =" + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Request call, IOException e) {
                Toast.makeText(MainActivity.this, "异步GET请求失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    *//**
     * 异步post
     *
     * @param view
     *//*
    public void aSyncPostTest(View view) {

        Request request = null;
        try {
            FormBody body = new FormBody.Builder().add("url_scheme", "https://zjbeta.8531.cn/subject.html?id=1475")
                                                  .add("action", "true")
                                                  .add("id", "1475")
                                                  .build();
            request = new Request.Builder().post(body).url("https://apibeta.8531.cn/api/favorite/collect").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.newCall(request).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Toast.makeText(MainActivity.this, "异步Post请求成功  code =" + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Request call, IOException e) {
                Toast.makeText(MainActivity.this, "异步Post请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}
