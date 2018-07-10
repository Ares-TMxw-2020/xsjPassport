package passportdemo.zjrb.com.zjrbpassport.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import passportdemo.zjrb.com.zjrbpassport.R;

/**
 * Function: TipDialog
 * <p>
 * Author: chen.h
 * Date: 2018/7/9
 */
public class TipDialog extends Dialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private Listener listener;

    public TipDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_tip);
        ButterKnife.bind(this);
    }

    public TipDialog setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public TipDialog setMessage(String notice) {
        tvMessage.setText(notice);
        return this;
    }

    public TipDialog setLeft(String text) {
        tvLeft.setText(text);
        return this;
    }

    public TipDialog setRight(String text) {
        tvRight.setText(text);
        return this;
    }

    public TipDialog setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    @OnClick({R.id.tv_left, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                if (listener != null) {
                    listener.onLeft();
                }
                break;
            case R.id.tv_right:
                if (listener != null) {
                    listener.onRight();
                }
                break;
        }
    }

    public interface Listener {

        void onLeft();

        void onRight();
    }

}
