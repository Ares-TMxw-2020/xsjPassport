package passportdemo.zjrb.com.zjrbpassport.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import passportdemo.zjrb.com.zjrbpassport.R;

/**
 * Date: 2018/7/9 上午11:39
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 注册页面
 */
public class RegisterActvity extends AppCompatActivity {

    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_send)
    TextView mTvSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_register, R.id.tv_login, R.id.tv_send})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_login:
                break;
            case R.id.tv_send:
                break;
        }
    }
}
