package nuoman.com.fragment;

import android.app.Application;

import nuoman.com.framwork.utils.AppConfig;

/**
 * Created by Administrator on 2015/4/18.
 */
public class MineApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.setContext(this);
    }
}
