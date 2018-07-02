package passportdemo.zjrb.com.zjrbpassport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
                System.out.println("同步执行: msg = " + response.message() + "  body = " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void syncPostTest(View view) {
    }

    public void aSyncGetTest(View view) {
    }

    public void aSyncPostTest(View view) {
    }
}
