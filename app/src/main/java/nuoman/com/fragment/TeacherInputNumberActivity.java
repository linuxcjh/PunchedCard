package nuoman.com.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.Task;
import nuoman.com.framwork.utils.AppConfig;
import nuoman.com.framwork.utils.AppTools;


/**
 * @author Chen
 * @class EnterpriseMemberActivity.java [V 1.0.0]
 * @time Sep 15, 2014 11:42:46 AM
 * @TODO [输入卡号打卡]
 */
public class TeacherInputNumberActivity extends ActivityBase {
    private RequestQueue requestQueue;
    private TextView car_number;
    private Button number_0, number_1, number_2, number_3, number_4, number_5, number_6, number_7, number_8, number_9, number_back, number_confirm;
    String url = "http://123.57.34.179/attendence_sys/WriteAttLogController";

    @Override
    protected int setContentViewResId() {
        // TODO Auto-generated method stub
        return R.layout.activity_input_car_number;
    }

    @Override
    protected void findWigetAndListener() {
        // TODO Auto-generated method stub
        car_number = getViewById(R.id.car_number);
        number_0 = getViewById(R.id.number_0);
        number_0.setOnClickListener(this);
        number_1 = getViewById(R.id.number_1);
        number_1.setOnClickListener(this);
        number_2 = getViewById(R.id.number_2);
        number_2.setOnClickListener(this);
        number_3 = getViewById(R.id.number_3);
        number_3.setOnClickListener(this);
        number_4 = getViewById(R.id.number_4);
        number_4.setOnClickListener(this);
        number_5 = getViewById(R.id.number_5);
        number_5.setOnClickListener(this);
        number_6 = getViewById(R.id.number_6);
        number_6.setOnClickListener(this);
        number_7 = getViewById(R.id.number_7);
        number_7.setOnClickListener(this);
        number_8 = getViewById(R.id.number_8);
        number_8.setOnClickListener(this);
        number_9 = getViewById(R.id.number_9);
        number_9.setOnClickListener(this);
        number_back = getViewById(R.id.number_back);
        number_back.setOnClickListener(this);
        number_confirm = getViewById(R.id.number_confirm);
        number_confirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        requestQueue = Volley.newRequestQueue(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.number_0:
                car_number.setText(car_number.getText() + "0");
                break;
            case R.id.number_1:
                car_number.setText(car_number.getText() + "1");
                break;
            case R.id.number_2:
                car_number.setText(car_number.getText() + "2");
                break;
            case R.id.number_3:
                car_number.setText(car_number.getText() + "3");
                break;
            case R.id.number_4:
                car_number.setText(car_number.getText() + "4");
                break;
            case R.id.number_5:
                car_number.setText(car_number.getText() + "5");
                break;
            case R.id.number_6:
                car_number.setText(car_number.getText() + "6");
                break;
            case R.id.number_7:
                car_number.setText(car_number.getText() + "7");
                break;
            case R.id.number_8:
                car_number.setText(car_number.getText() + "8");
                break;
            case R.id.number_9:
                car_number.setText(car_number.getText() + "9");
                break;
            case R.id.number_back:
                if (car_number.getText().length() > 0) {
                    car_number.setText(car_number.getText().toString().substring(0, car_number.getText().toString().length() - 1));
                }
                break;
            case R.id.number_confirm:
                Intent intent = new Intent(TeacherInputNumberActivity.this, TeacherPunchSuccessActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

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
    private void taskRequest() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("kind", "value1");
        map.put("cardno", "value2");
        map.put("schlid", "value2");
        map.put("machineno", "value2");
        map.put("timestamp", "value2");
        map.put("photo", "value2");
        JSONObject jsonObject = new JSONObject(map);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "response -> " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }


}