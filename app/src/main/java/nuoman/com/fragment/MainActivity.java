package nuoman.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.Toast;

import com.nuoman.widget.CircleFlowIndicator;
import com.nuoman.widget.ViewFlow;

import nuoman.com.fragment.adapter.ImageAdapter;


public class MainActivity extends Activity {
    private ViewFlow viewFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlow = (ViewFlow) findViewById(R.id.view_flow);
        viewFlow.setAdapter(new ImageAdapter(this));
        viewFlow.setmSideBuffer(3); // 实际图片张数， 我的ImageAdapter实际图片张数为3

        CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.view_flow_in);
        viewFlow.setFlowIndicator(indic);

        viewFlow.setSelection(3 * 1000); // 设置初始位置
        viewFlow.startAutoFlowTimer(); // 启动自动播放

        getResolution();
        getDisplay( );
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
     * 获取屏幕分辨率
     */
    private void getResolution() {
// TODO Auto-generated method stub
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float density = displayMetrics.density; //得到密度
        float width = displayMetrics.widthPixels;//得到宽度
        float height = displayMetrics.heightPixels;//得到高度

//        Toast.makeText(MainActivity.this,"density:"+density+" width: "+width+" height:"+height,Toast.LENGTH_LONG).show();
    }


    private void getDisplay( ){
        DisplayMetrics mDis=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDis);
        Toast.makeText(MainActivity.this," width: "+mDis.widthPixels+" height:"+mDis.heightPixels,Toast.LENGTH_LONG).show();
    }
}
