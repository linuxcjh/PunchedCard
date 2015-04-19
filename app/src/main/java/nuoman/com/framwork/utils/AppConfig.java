package nuoman.com.framwork.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import nuoman.com.framwork.network.NMConstants;


/**
 * 
 * com.mingzhi.samattendance.framework.utils.AppConfig
 * 
 * @author 陈建辉 <br/>
 * @time 2013-6-18 下午2:52:06
 * @note 功能说明：用于存储应用的配置参数
 */
public class AppConfig {

	private static Context context;

	/**
	 * 方法名: 保存Context
	 * <p>
	 * 功能说明： 保存当前activity的Context
	 * </p>
	 * 
	 * @param myContext
	 *            当前context
	 */
	public static void setContext(Context myContext) {

		AppConfig.context = myContext;

	}

	/**
	 * 方法名: 获取Context
	 * <p>
	 * 功能说明： 获取Context
	 * </p>
	 * 
	 * @return 返回当前context
	 */
	public static Context getContext() {
		return AppConfig.context;
	}

	/**
	 * 设置String类型程序参数
	 * 
	 * @param key
	 * @param value
	 */
	public static void setStringConfig(String key, String value) {
		SharedPreferences preferences = context.getSharedPreferences(
				NMConstants.NUO_MAN_DOC, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 得到String类型程序参数
	 *
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getStringConfig(String key, String defValue) {
		SharedPreferences preferences = context.getSharedPreferences(
				NMConstants.NUO_MAN_DOC, Context.MODE_PRIVATE);
		String site = preferences.getString(key, defValue);
		return site;
	}
/*
*
* 初始化登陆
* */
    public static void setIsInit( String value) {
        setStringConfig("INIT_LOGIN",value);
    }

    public static String getIsInit() {

        return getStringConfig("INIT_LOGIN","");
    }
}
