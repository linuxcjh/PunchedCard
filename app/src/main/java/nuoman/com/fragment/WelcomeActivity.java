package nuoman.com.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.utils.AppConfig;


/**
 * 
 * chen.pop.framework.launcher.WelcomeActivity
 * 
 * @author 陈建辉 <br/>
 * @time 2013-6-18 上午11:25:31
 * @note 功能说明：欢迎界面
 */
public class WelcomeActivity extends ActivityBase {

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void findWigetAndListener() {

    }

    @Override
    protected void initData() {
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (!AppConfig.getIsInit().equals("1")) {
                    AppConfig.setIsInit("1");
                    Intent intent = new Intent(WelcomeActivity.this,
                            SchoolMastarLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(WelcomeActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

        mHandler.sendMessageDelayed(mHandler.obtainMessage(), 300);
    }

    @Override
    public void onClick(View v) {

    }
}
