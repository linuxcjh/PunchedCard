package nuoman.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.nuoman.widget.CircleFlowIndicator;
import com.nuoman.widget.ViewFlow;

import java.util.ArrayList;
import java.util.List;

import nuoman.com.fragment.adapter.ImageAdapter;
import nuoman.com.fragment.database.DBManager;
import nuoman.com.fragment.entity.NewsInfo;
import nuoman.com.fragment.entity.PersonInfo;
import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.utils.AppConfig;
import nuoman.com.framwork.utils.AppTools;
import nuoman.com.framwork.utils.JsonUtil;


public class MainActivity extends ActivityBase {
    private RequestQueue requestQueue;
    private ViewFlow viewFlow;
    private CircleFlowIndicator indic;
    private Button teacher_login_bt;
    private   String url="http://123.57.34.179/attendence_sys/SendNewsController?schlid=2";

    @Override
    protected void findWigetAndListener() {
        viewFlow = getViewById(R.id.view_flow);
         indic =getViewById(R.id.view_flow_in);
        teacher_login_bt=getViewById(R.id.teacher_login_bt);
        teacher_login_bt.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        requestQueue = Volley.newRequestQueue(this);
        viewFlow.setAdapter(new ImageAdapter(this));
        viewFlow.setmSideBuffer(3); // 实际图片张数， 我的ImageAdapter实际图片张数为3
        viewFlow.setFlowIndicator(indic);
        viewFlow.setSelection(3 * 1000); // 设置初始位置
        viewFlow.startAutoFlowTimer(); // 启动自动播放
        taskStringRequest();
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case   R.id.teacher_login_bt:
                Intent intent=new Intent(MainActivity.this,TeacherInputNumberActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
    /**
     * 人员信息请求
     */
    private void taskStringRequest() {
        m_progressDialog.show();
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                m_progressDialog.dismiss();
                AppTools.getToast(s);
                s=s.replace("[[","[");
                s=s.replace("]]","]");
                List<NewsInfo> list = new ArrayList<NewsInfo>();
                list= (List<NewsInfo>) JsonUtil.getGsonInstance().fromJson(s, new TypeToken<List<NewsInfo>>(){}.getType());
//                DBManager.getDbManagerInstance(AppConfig.getContext()).addData(list);
//                DBManager.getDbManagerInstance(AppConfig.getContext()).closeDb();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                m_progressDialog.dismiss();
                AppTools.getToast("volleyError");
            }
        });

        sr.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(sr);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_CALL:
                return true;
            case KeyEvent.KEYCODE_SYM:
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
            case KeyEvent.KEYCODE_STAR:
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
