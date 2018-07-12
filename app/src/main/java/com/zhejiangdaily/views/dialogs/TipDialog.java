package com.zhejiangdaily.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhejiangdaily.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    protected final View mView;

    public TipDialog(@NonNull Context context) {
        super(context);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_tip, null);
        if (mView != null) {
            ButterKnife.bind(this, mView);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
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
                dismiss();
                break;
            case R.id.tv_right:
                if (listener != null) {
                    listener.onRight();
                }
                dismiss();
                break;
        }
    }

    public interface Listener {

        void onLeft();

        void onRight();
    }

}
