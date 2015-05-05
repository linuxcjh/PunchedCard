package nuoman.com.framwork.network;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nuoman.com.framwork.Task;

/**
 * @file : RequestTask.java [V 1.0.0]
 * @author: 陈建辉
 * @time : CREAT AT 2013-8-26 下午3:26:50
 * @TODO : 【异步任务类】
 */
public class RequestTask extends Task<Object, Object> {

    public RequestTask(
            Task.TaskListener listener) {
        super(listener);
    }

    @Override
    protected Object[] doInBackground(Object... values) {
        Object object = null;
        try {
            switch (values.length) {
                case 4:
                    object = new WebserviceRequest().sendPost(
                            (String) values[0],
                            (Map<String, String>) values[1], (String) values[2], (TypeToken<?>) values[3]);
                    break;
            }
        } catch (Exception e) {
            return new Object[]{e};
        }
        return new Object[]{object};
    }

}
