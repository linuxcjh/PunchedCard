package nuoman.com.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nuoman.com.fragment.database.DBManager;
import nuoman.com.fragment.database.MineDatabase;
import nuoman.com.fragment.entity.PersonInfo;
import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.Task;
import nuoman.com.framwork.network.NMConstants;
import nuoman.com.framwork.utils.AppConfig;
import nuoman.com.framwork.utils.AppTools;
import nuoman.com.framwork.utils.JsonUtil;
import nuoman.com.framwork.utils.Utils;


/**
 * @author Chen
 * @class EnterpriseMemberActivity.java [V 1.0.0]
 * @time Sep 15, 2014 11:42:46 AM
 * @TODO [master登陆]
 */
public class SchoolMastarLoginActivity extends ActivityBase {
    private RequestQueue requestQueue;
    private EditText manager_car_number;
    private Button bt_manager_login;

    String url="http://123.57.34.179/attendence_sys/LoginController?tel=18000000000&machineid=1";//校长登陆

    @Override
    protected int setContentViewResId() {
        // TODO Auto-generated method stub
        return R.layout.activity_schoolmaster_login;
    }

    @Override
    protected void findWigetAndListener() {
        // TODO Auto-generated method stub
        manager_car_number = getViewById(R.id.manager_car_number);
        bt_manager_login = getViewById(R.id.bt_manager_login);
        bt_manager_login.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        requestQueue = Volley.newRequestQueue(this);

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

                List<PersonInfo> list = new ArrayList<PersonInfo>();
                list= (List<PersonInfo>)JsonUtil.getGsonInstance().fromJson(s, new TypeToken<List<PersonInfo>>(){}.getType());
                DBManager.getDbManagerInstance(AppConfig.getContext()).addData(list);
//                DBManager.getDbManagerInstance(AppConfig.getContext()).clearData();
                DBManager.getDbManagerInstance(AppConfig.getContext()).closeDb();

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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_manager_login:
                taskStringRequest();
                break;
            default:
                break;
        }

    }

}
