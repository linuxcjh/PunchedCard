package nuoman.com.framwork.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import nuoman.com.fragment.R;


/**
 * 
 * com.cepri.epmmis.common.widget.CustomProgressDialog
 * 
 * @author 张强 <br/>
 *         create at 2012-11-21 下午6:50:11 等待进度条
 */
public class CustomProgressDialog extends Dialog {

	public CustomProgressDialog(Context context) {
		super(context, R.style.progress_dialog);
		getWindow().getAttributes().alpha = 0.6f;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);

		getWindow().getAttributes().alpha = 0.6f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_progress_dialog);
		setCanceledOnTouchOutside(false);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

	}

	/**
	 * 
	 * [Summary] setTitile 标题
	 * 
	 * @param strTitle
	 * @return
	 * 
	 */

	public void setTitile(String strTitle) {

	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public void setMessage(String strMessage) {
		TextView tvMsg = (TextView) this.findViewById(R.id.tv_dialog);
		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

	}
}
