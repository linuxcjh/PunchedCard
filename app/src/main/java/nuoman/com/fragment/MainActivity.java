package nuoman.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//    String url = "http://123.57.34.179/attendence_sys/SendInfoController?schlid=8";//获取人员信息
    private   String url="http://123.57.34.179/attendence_sys/SendNewsController?schlid=2";//获取新闻
    private Camera camera; //相机
    private Camera.Parameters parameters;//相机参数
    @Override
    protected void findWigetAndListener() {
        viewFlow = getViewById(R.id.view_flow);
         indic =getViewById(R.id.view_flow_in);
        teacher_login_bt=getViewById(R.id.teacher_login_bt);
        teacher_login_bt.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setCamera( );
        requestQueue = Volley.newRequestQueue(this);
        viewFlow.setAdapter(new ImageAdapter(this));
        viewFlow.setmSideBuffer(3); // 实际图片张数， 我的ImageAdapter实际图片张数为3
        viewFlow.setFlowIndicator(indic);
        viewFlow.setSelection(3 * 1000); // 设置初始位置
        viewFlow.startAutoFlowTimer(); // 启动自动播放
        taskStringRequest();

    }

    /**
     * 设置相机
     */
    private void setCamera( ){
        SurfaceView surfaceView = (SurfaceView) this
                .findViewById(R.id.surfaceView);
        surfaceView.getHolder()
                .setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 144); //设置Surface分辨率
        surfaceView.getHolder().setKeepScreenOn(true);// 屏幕常亮
        surfaceView.getHolder().addCallback(new SurfaceCallback());//为SurfaceView的句柄添加一个回调函数
    }
    @Override
    protected int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case   R.id.teacher_login_bt:
                camera.takePicture(null, null, new MyPictureCallback());//拍照
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


    /**
     * 拍照监听
     */
    private final class MyPictureCallback implements Camera.PictureCallback {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                Bundle   bundle = new Bundle();
                bundle.putByteArray("bytes", data); //将图片字节数据保存在bundle当中，实现数据交换
                saveToSDCard(data); // 保存图片到sd卡中
                Toast.makeText(getApplicationContext(),"成功",
                        Toast.LENGTH_SHORT).show();
                camera.startPreview(); // 拍完照后，重新开始预览
                Intent intent=new Intent(MainActivity.this,TeacherPunchSuccessActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将拍下来的照片存放在SD卡中
     * @param data
     * @throws java.io.IOException
     */
    public static void saveToSDCard(byte[] data) throws IOException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
        String filename = format.format(date) + ".jpg";
        File fileFolder = new File(Environment.getExternalStorageDirectory()
                + "/finger/");
        if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
            fileFolder.mkdir();
        }
        File jpgFile = new File(fileFolder, filename);
        FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
        outputStream.write(data); // 写入sd卡中
        outputStream.close(); // 关闭输出流
    }


    private final class SurfaceCallback implements SurfaceHolder.Callback {
        // 拍照状态变化时调用该方法
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

            AppTools.getToast(""+width+" :"+height);
            parameters = camera.getParameters(); // 获取各项参数
            parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
            parameters.setPreviewSize(width, height); // 设置预览大小
            parameters.setPreviewFrameRate(5);  //设置每秒显示4帧
            parameters.setPictureSize(width, height); // 设置保存的图片尺寸
            parameters.setJpegQuality(80); // 设置照片质量
        }

        // 开始拍照时调用该方法
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera = Camera.open(); // 打开摄像头
                camera.setPreviewDisplay(holder); // 设置用于显示拍照影像的SurfaceHolder对象
                camera.setDisplayOrientation(getPreviewDegree(MainActivity.this));
                camera.startPreview(); // 开始预览
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 停止拍照时调用该方法
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.release(); // 释放照相机
                camera = null;
            }
        }
    }

    // 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
    public static int getPreviewDegree(Activity activity) {
        // 获得手机的方向
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degree = 0;
        // 根据手机的方向计算相机预览画面应该选择的角度
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }
}
