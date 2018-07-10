package passportdemo.zjrb.com.zjrbpassport.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import passportdemo.zjrb.com.zjrbpassport.R;

/**
 * Function: BindPhoneActivity
 * <p>
 * Author: chen.h
 * Date: 2018/7/5
 */
public class BindPhoneActivity extends AppCompatActivity {

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_bind)
    TextView tvBind;

    int status = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.tv_back, R.id.tv_send, R.id.tv_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_send:
                break;
            case R.id.tv_bind:
                break;
        }
    }
}
