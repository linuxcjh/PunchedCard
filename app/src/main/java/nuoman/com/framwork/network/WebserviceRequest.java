package nuoman.com.framwork.network;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nuoman.com.framwork.utils.JsonUtil;

/**
 * @file : AbstractWebservice.java [V 1.0.0]
 * @author: 陈建辉
 * @time : CREAT AT 2013-8-16 下午4:39:35
 * @TODO : 【Interface calls】
 */
public class WebserviceRequest {

    private final static String LOG_TAG = "WebserviceRequest";

    public Object requestServer(String methodName, Object model,
                                String exception, TypeToken<?> typeToken) {

        Object object = hprose(methodName, typeToken, JsonUtil
                .getGsonInstance().toJson(model), exception);
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


    protected String sendPost(String url, Map<String, String> params, String uploadFilePath, TypeToken<?> returnType) {
        Iterator iterator = params.entrySet().iterator();
        Object object = null;

        String result = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置HTTP POST请求参数必须用NameValuePair对象
        try {
            org.apache.http.entity.mime.MultipartEntity entity = new org.apache.http.entity.mime.MultipartEntity();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                entity.addPart(key, new StringBody(value, Charset.forName("UTF-8")));
            }
            FileInputStream fin = new FileInputStream(uploadFilePath);
            InputStreamBody isb = new InputStreamBody(fin, "image");
            entity.addPart("file", isb);
            httpPost.setEntity(entity);
            HttpResponse httpResponse;
            httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                result = EntityUtils.toString(httpResponse.getEntity());
//                result = JsonUtil.getGsonInstance().fromJson( EntityUtils.toString(httpResponse.getEntity()),
//                        returnType.getType());
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
