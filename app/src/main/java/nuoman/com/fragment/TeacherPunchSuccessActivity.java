package nuoman.com.fragment;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.HashMap;

import nuoman.com.fragment.database.DBManager;
import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.Task;
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
    private TextView puncher_status,puncher_name,puncher_card,puncher_time;

    private File file;

    @Override
    protected int setContentViewResId() {
        // TODO Auto-generated method stub
        return R.layout.activity_teacher_punchcard_success;
    }

    @Override
    protected void findWigetAndListener() {
        // TODO Auto-generated method stub
        image_view = getViewById(R.id.image_view);
        puncher_status=getViewById(R.id.puncher_status);
        puncher_name=getViewById(R.id.puncher_name);
        puncher_card=getViewById(R.id.puncher_card);
        puncher_time=getViewById(R.id.puncher_time);
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        String path=getIntent().getExtras().getString("path");
        String cardNo=getIntent().getExtras().getString("cardNo");
        String time=DateUtil.getDateTimeForsys(DateUtil.yyyy_MM_dd_HH_mm_ss, System.currentTimeMillis());
        if(!TextUtils.isEmpty(path)){
            file=new File(path);
            image_view.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        if(!TextUtils.isEmpty(cardNo)){
            puncher_name.setText(DBManager.getDbManagerInstance(this).queryNameByCardNo(cardNo));
            puncher_card.setText(cardNo);
        }
        puncher_time.setText(time);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("cardno", cardNo);
        params.put("machineno", Utils.getImei());
        params.put("timestamp", time);
        taskShareInfo(params, path);
    }

    private void taskShareInfo(HashMap<String, String> params, String uploadImagePath) {
        RequestTask task = new RequestTask(this);
        task.setId(0);
        task.execute(url, params, uploadImagePath, new TypeToken<String>() {
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            TeacherPunchSuccessActivity.this.finish();
        }
    };
    @Override
    public void taskOnPostExecute(Task<?, ?> task, Object[] result) {
        super.taskOnPostExecute(task, result);
        if(file.exists()){ //´ò¿¨³É¹¦É¾³ýÕÕÆ¬
            file.delete();
        }
        handler.sendEmptyMessageDelayed(0x11, 1000);

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
