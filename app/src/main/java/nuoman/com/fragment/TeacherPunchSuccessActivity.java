package nuoman.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.Task;
import nuoman.com.framwork.network.RequestTask;
import nuoman.com.framwork.utils.AppTools;
import nuoman.com.framwork.utils.DateUtil;


/**
 * 
 * @class EnterpriseMemberActivity.java [V 1.0.0]
 * 
 * @author Chen
 * 
 * @time Sep 15, 2014 11:42:46 AM
 * 
 * @TODO [Teacher punch card success]
 */
public class TeacherPunchSuccessActivity extends ActivityBase {

    String url = "http://123.57.34.179/attendence_sys/WriteAttLogController";
    private RequestQueue requestQueue;
    private ImageView image_view;


	@Override
	protected int setContentViewResId() {
		// TODO Auto-generated method stub
		return R.layout.activity_teacher_punchcard_success;
	}

	@Override
	protected void findWigetAndListener() {
		// TODO Auto-generated method stub
        image_view=getViewById(R.id.image_view);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
        requestQueue = Volley.newRequestQueue(this);
//        setImageBitmap(getImageFormBundle());
        image_view.setImageBitmap(BitmapFactory.decodeFile(getIntent().getExtras().getString("path")));

        HashMap<String,String> params=new HashMap<String,String>();
        params.put("kind", "0"); // 0:student   1:teacher
        params.put("cardno", "0004216378");
        params.put("schlid", MineApplication.loginInfo.getSchoolId());
        params.put("machineno",  getImei());
        params.put("timestamp", System.currentTimeMillis()+"");
        taskShareInfo(params,getIntent().getExtras().getString("path"));

	}
    private void taskShareInfo(HashMap<String,String> params,String uploadImagePath){
        RequestTask task=new RequestTask(this);
        task.setId(0);
        task.execute(url,params,uploadImagePath,new TypeToken<String>(){});
    }

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
            case R.id.bt_manager_login:
                break;
		default:
			break;
		}

	}


    /**
     * 将MainActivity传过来的图片显示在界面当中
     *
     * @param bytes
     */
    public void setImageBitmap(byte[] bytes) {
        Bitmap cameraBitmap = byte2Bitmap();
        // 根据拍摄的方向旋转图像（纵向拍摄时要需要将图像选择90度)
        Matrix matrix = new Matrix();
        matrix.setRotate(MainActivity.getPreviewDegree(this));
        cameraBitmap = Bitmap
                .createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(),
                        cameraBitmap.getHeight(), matrix, true);
        image_view.setImageBitmap(cameraBitmap);
    }

    /**
     * 从Bundle对象中获取数据
     *
     * @return
     */
    public byte[] getImageFormBundle() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        byte[] bytes = data.getByteArray("bytes");
        return bytes;
    }

    /**
     * 将字节数组的图形数据转换为Bitmap
     *
     * @return
     */
    private Bitmap byte2Bitmap() {
        byte[] data = getImageFormBundle();
        // 将byte数组转换成Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }


    /**
     * 获取手机IMEI
     *
     * @return
     */
    private String getImei() {
        TelephonyManager mTm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTm.getDeviceId();
        // String imsi = mTm.getSubscriberId();
        return imei;

    }



    /**
     * commit data
     * kind	打卡类型
     * cardno	打卡的卡号
     * schlid	学校ID
     * machineno	机器号
     * timestamp	打卡的时间戳
     * photo	照片的二进制流
     */
//    private void taskRequest() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("kind", "0"); // 0:student   1:teacher
//        map.put("cardno", "0004216378");
//        map.put("schlid", MineApplication.loginInfo.getSchoolId());
//        map.put("machineno",  getImei());
//        map.put("timestamp", System.currentTimeMillis()+"");
//        try {
////            map.put("photo", new String(getImageFormBundle(),"UTF-8"));
////            map.put("photo",Base64.encodeToString(getImageFormBundle(), Base64.DEFAULT));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        JSONObject jsonObject = new JSONObject(map);
//        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        AppTools.getToast("SUCCESS!");
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                AppTools.getToast("ERROR!");
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Accept", "application/json");
//                headers.put("Content-Type", "application/json; charset=UTF-8");
//                return headers;
//            }
//        };
//        requestQueue.add(jsonRequest);
//    }
}
