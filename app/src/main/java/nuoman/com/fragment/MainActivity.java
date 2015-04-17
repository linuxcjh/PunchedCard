package nuoman.com.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.nuoman.widget.CircleFlowIndicator;
import com.nuoman.widget.ViewFlow;

import nuoman.com.fragment.adapter.ImageAdapter;


public class MainActivity extends Activity {
    private ViewFlow viewFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        viewFlow.setAdapter(new ImageAdapter(this));
        viewFlow.setmSideBuffer(3); // 实际图片张数， 我的ImageAdapter实际图片张数为3

        CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        viewFlow.setFlowIndicator(indic);

        viewFlow.setSelection(3 * 1000); // 设置初始位置
        viewFlow.startAutoFlowTimer(); // 启动自动播放
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
