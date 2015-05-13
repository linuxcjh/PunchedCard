package nuoman.com.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import nuoman.com.fragment.database.DBManager;
import nuoman.com.fragment.entity.PersonInfo;
import nuoman.com.fragment.entity.UpdataVersionInfo;
import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.Task;
import nuoman.com.framwork.network.NMConstants;
import nuoman.com.framwork.utils.AppConfig;
import nuoman.com.framwork.utils.AppTools;
import nuoman.com.framwork.utils.JsonUtil;
import nuoman.com.framwork.utils.UpdateVersion;


/**
 * @author Chen
 * @class EnterpriseMemberActivity.java [V 1.0.0]
 * @time Sep 15, 2014 11:42:46 AM
 * @TODO [设置]
 */
public class SettingActivity extends ActivityBase {

    private Button back_main_page, admin_check_update, admin_reset, admin_exit;
    private RequestQueue requestQueue;
    private DownloadCompleteReceiver receiver;

    @Override
    protected int setContentViewResId() {
        // TODO Auto-generated method stub
        return R.layout.activity_manager_setting;
    }

    @Override
    protected void findWigetAndListener() {
        // TODO Auto-generated method stub
        back_main_page = getViewById(R.id.back_main_page);
        admin_check_update = getViewById(R.id.admin_check_update);
        admin_reset = getViewById(R.id.admin_reset);
        admin_exit = getViewById(R.id.admin_exit);
        back_main_page.setOnClickListener(this);
        admin_check_update.setOnClickListener(this);
        admin_reset.setOnClickListener(this);
        admin_exit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        requestQueue = Volley.newRequestQueue(this);
        receiver = new DownloadCompleteReceiver();
        initRegistBroadCast();// 注册广播

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_main_page:
                break;
            case R.id.admin_check_update:
                taskStringRequest();
                break;
            case R.id.admin_reset:
                break;
            case R.id.admin_exit:
                break;
            default:
                break;
        }

    }


    /**
     * request person info.
     */
    private void taskStringRequest() {
        String urlUpdateInfo = "http://123.57.34.179/attendence_sys/UpdateController?type=2";
        m_progressDialog.show();
        StringRequest sr = new StringRequest(Request.Method.GET, urlUpdateInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                m_progressDialog.dismiss();
                if (!TextUtils.isEmpty(s)) {
                    List<UpdataVersionInfo> list = (List<UpdataVersionInfo>) JsonUtil.getGsonInstance().fromJson(s, new TypeToken<List<UpdataVersionInfo>>() {
                    }.getType());
                    if (list != null && list.size() > 0) {
                        UpdateVersion updateVersion = new UpdateVersion(SettingActivity.this,
                                list.get(0), 0);
                        updateVersion.showIsUpdata();
                    }
                } else {
                    AppTools.getToast(R.string.request_error);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                m_progressDialog.dismiss();
                AppTools.getToast(R.string.data_error);
            }
        });

        sr.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(sr);
    }

    /**
     * 静态注册广播
     */
    private void initRegistBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(NMConstants.BROADCAST_DISAPPEAR_SIGN_DOWNLOADAPP);
        registerReceiver(receiver, filter);

    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(
                    NMConstants.BROADCAST_DISAPPEAR_SIGN_DOWNLOADAPP)) { // apk下载
                AppTools.inistallAPKFile(context,
                        AppTools.getAppSavePath(context));
            }
        }
    }
}
