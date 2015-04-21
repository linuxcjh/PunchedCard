package nuoman.com.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import nuoman.com.framwork.ActivityBase;
import nuoman.com.framwork.Task;


/**
 * 
 * @class EnterpriseMemberActivity.java [V 1.0.0]
 * 
 * @author Chen
 * 
 * @time Sep 15, 2014 11:42:46 AM
 * 
 * @TODO [教师打卡成功]
 */
public class TeacherPunchSuccessActivity extends ActivityBase {


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
        setImageBitmap(getImageFormBundle());
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
}
