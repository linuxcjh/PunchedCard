package nuoman.com.fragment;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import nuoman.com.fragment.entity.LoginInfo;
import nuoman.com.framwork.utils.AppConfig;

/**
 * Created by Administrator on 2015/4/18.
 */
public class MineApplication extends Application {

    public static LoginInfo loginInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.setContext(this);
    }



}
