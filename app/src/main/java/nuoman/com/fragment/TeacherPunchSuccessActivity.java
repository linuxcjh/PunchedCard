package nuoman.com.fragment;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.network.RequestTask;
import nuoman.com.framwork.utils.DateUtil;
import nuoman.com.framwork.utils.Utils;


/**
 * @author Chen
 * @class EnterpriseMemberActivity.java [V 1.0.0]
 * @time Sep 15, 2014 11:42:46 AM
 * @TODO [Teacher punch card success]
 */
public class TeacherPunchSuccessActivity extends ActivityBase {

            String url = "http://123.57.34.179/attendence_sys/WriteAttLogController";
//    String url = "http://192.168.1.23/attendence_sys/WriteAttLogController";
    private ImageView image_view;


    @Override
    protected int setContentViewResId() {
        // TODO Auto-generated method stub
        return R.layout.activity_teacher_punchcard_success;
    }

    @Override
    protected void findWigetAndListener() {
        // TODO Auto-generated method stub
        image_view = getViewById(R.id.image_view);
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        image_view.setImageBitmap(BitmapFactory.decodeFile(getIntent().getExtras().getString("path")));

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("cardno", "0004216378");
        params.put("machineno", Utils.getImei());
        params.put("timestamp", DateUtil.getDateTimeForsys(DateUtil.yyyy_MM_dd_HH_mm_ss, System.currentTimeMillis()));
        taskShareInfo(params, getIntent().getExtras().getString("path"));

    }

    private void taskShareInfo(HashMap<String, String> params, String uploadImagePath) {
        RequestTask task = new RequestTask(this);
        task.setId(0);
        task.execute(url, params, uploadImagePath, new TypeToken<String>() {
        });
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







}
