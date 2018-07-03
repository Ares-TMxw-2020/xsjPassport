package passportdemo.zjrb.com.zjrbpassport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zjrb.passport.net.CallBack;
import com.zjrb.passport.net.FormBody;
import com.zjrb.passport.net.Request;
import com.zjrb.passport.net.Response;
import com.zjrb.passport.net.ZbHttpClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ZbHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new ZbHttpClient.Builder().connTimeOut(10*1000).readTimeOut(10*1000).writeTimeOut(10*1000).build();
    }

    /**
     * 同步get请求
     * @param view
     */
    public void syncGetTest(View view) {
        Request request = null;
        try {
            FormBody body = new FormBody.Builder()
                    .add("menu", "土豆")
                    .add("rn", "15")
                    .add("start", "1")
                    .build();
            request = new Request.Builder()
                    .get(body)
                    .url("http://caipu.yjghost.com/index.php/query/read")
                    .build();
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

    /**
     * 同步post
     * @param view
     */
    public void syncPostTest(View view) {
        Request request = null;
        try {
            request = new Request.Builder()
                    .post(null)
                    .url("https://apibeta.8531.cn/api/account/init")
                    .build();
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

    /**
     * 异步get
     * @param view
     */
    public void aSyncGetTest(View view) {
        Request request = null;
        try {

            FormBody body = new FormBody.Builder()
                    .add("menu", "土豆")
                    .add("rn", "15")
                    .add("start", "1")
                    .build();
            request = new Request.Builder()
                    .get(body)
                    .url("http://caipu.yjghost.com/index.php/query/read")
                    .build();
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

    /**
     * 异步post
     * @param view
     */
    public void aSyncPostTest(View view) {

        Request request = null;
        try {
            FormBody body = new FormBody.Builder()
                    .add("url_scheme", "https://zjbeta.8531.cn/subject.html?id=1475")
                    .add("action", "true")
                    .add("id", "1475")
                    .build();
            request = new Request.Builder()
                    .post(body)
                    .url("https://apibeta.8531.cn/api/favorite/collect")
                    .build();
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

    }
}
