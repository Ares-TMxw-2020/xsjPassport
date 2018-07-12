package com.zhejiangdaily.presenters;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhejiangdaily.contracts.UmLoginContract;

import java.util.Map;

/**
 * Function: UmLoginPresenter
 * <p>
 * Author: chen.h
 * Date: 2018/7/9
 */
public class UmLoginPresenterImpl implements UmLoginContract.Presenter {

    private final UmLoginContract.View view;

    private UMShareAPI shareAPI;

    public UmLoginPresenterImpl(UmLoginContract.View view) {
        this.view = view;
        shareAPI = UMShareAPI.get(this.view.getIActivity());
    }

    @Override
    public void umLogin(SHARE_MEDIA platform) {
        shareAPI.getPlatformInfo(view.getIActivity(), platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if (map != null) {
                    String uid = map.get("uid");
                    String name = map.get("name");
                    String gender = map.get("gender");
                    String url = map.get("iconurl");
                    view.umLogin(true, share_media, uid);
                } else {
                    view.umLogin(false, null, null);
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                view.umLogin(false, null, null);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                view.umLogin(false, null, null);
            }
        });
    }

}
