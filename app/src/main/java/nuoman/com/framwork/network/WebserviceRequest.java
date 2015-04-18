package nuoman.com.framwork.network;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nuoman.com.framwork.utils.JsonUtil;

/**
 * 
 * @file : AbstractWebservice.java [V 1.0.0]
 * 
 * @author: 陈建辉
 * 
 * @time : CREAT AT 2013-8-16 下午4:39:35
 * 
 * @TODO : 【Interface calls】
 * 
 */
public class WebserviceRequest {

    private final static String LOG_TAG="WebserviceRequest";
	public Object requestServer(String methodName, Object model,
			String exception, TypeToken<?> typeToken) {

		Object object = hprose(methodName, typeToken, JsonUtil
				.getGsonInstance().toJson(model),exception);
		if (object != null) {
			return object;
		}
		return null;
	}

	/**
	 * Interface calls
	 * 
	 * @param model
	 * @param methodName
	 * @param typeToken
	 * @return
	 */
	public Object requestServer(String methodName, Object model,
			TypeToken<?> typeToken) {

		Object object = hprose(methodName, typeToken, JsonUtil
				.getGsonInstance().toJson(model));
		if (object != null) {
			return object;
		}
		return null;
	}

	/**
	 * No parameter
	 * 
	 * @param methodName
	 * @param typeToken
	 * @return
	 */
	public Object requestServer(String methodName, TypeToken<?> typeToken) {
		Object object = hprose(methodName, typeToken);
		if (object != null) {
			return object;
		}
		return null;
	}




	/**
	 * Print result log
	 * 
	 * @param methodName
	 * @param params
	 */
	private static void outLog(String methodName, String result,
			Object... params) {
		if (params != null && params.length > 0) {
			Log.d(LOG_TAG, "METHODNAME:---" + methodName + "---"
					+ " POST_JSON:  " + params[0] + "\n\r RESULT : " + result);
		} else {
			Log.d(LOG_TAG, "METHODNAME:---" + methodName + " --- "
					+ " POST_JSON:  " + "No parameters" + "\n\r RESULT : "
					+ result);
		}
	}

	public Object hprose(String methodName, TypeToken<?> returnType,
			String params) {
		Object result = null;
		HttpURLConnection urlConnection = null;
		try {
			// IgnoreSSLToHttps.ignoreSSL();//https请求
			URL url = new URL(ServerWebService.SERVICE_URL + methodName);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.getOutputStream().write(params.getBytes());
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			String result1 = convertStreamToString(in);
			result = JsonUtil.getGsonInstance().fromJson(result1,
					returnType.getType());
			outLog(methodName, result1, params);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				urlConnection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Object hprose(String methodName, TypeToken<?> returnType) {
		Object result = null;
		HttpURLConnection urlConnection = null;
		try {
			// IgnoreSSLToHttps.ignoreSSL();//https请求
			URL url = new URL(ServerWebService.SERVICE_URL + methodName);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			// urlConnection.getOutputStream().write(
			// params.getBytes());
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			String result1 = convertStreamToString(in);
			result = JsonUtil.getGsonInstance().fromJson(result1,
					returnType.getType());
			outLog(methodName, result1, "");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}
		return result;
	}

	public String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public Object hprose(String methodName, TypeToken<?> returnType,
			String params, String excetion) {
		Object result = null;
		HttpURLConnection urlConnection = null;
		try {
			// IgnoreSSLToHttps.ignoreSSL();//https请求
			URL url = new URL(ServerWebService.SERVICE_URL + methodName);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.getOutputStream().write(params.getBytes());
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			// String result1 = convertStreamToString(in);
			// result = JsonUtil.getGsonInstance().fromJson(result1,
			// returnType.getType());
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
			// outLog(methodName, result1, params);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				urlConnection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
