package nuoman.com.framwork.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @file : JsonUtil.java [V 1.0.0]
 * 
 * @author: 陈建辉
 * 
 * @time : CREAT AT 2013-9-22 上午10:19:38
 * 
 * @TODO : 【Json创建】
 * 
 */
public abstract class JsonUtil {

	private static GsonBuilder builder = new GsonBuilder();

	public static Gson getGsonInstance() {
		Gson gson = builder.setDateFormat(DateUtil.yyyy_MM_dd_HH_mm_ss).create();
		return gson;
	}

}
