/**
 * UpdateVersionDialog.java [V 1.0.0]
 * com.mingzhi.samattendance.ui.utils.UpdateVersionDialog
 * ChenJianhui  create at 2013-9-23 上午8:52:28
 */
package nuoman.com.framwork.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nuoman.com.fragment.R;
import nuoman.com.framwork.network.NMConstants;


/**
 * @file : UpdateVersionDialog.java [V 1.0.0]
 * 
 * @author: 陈建辉
 * 
 * @time : CREAT AT 2013-9-23 上午8:52:28
 * 
 * @TODO : 【版本升级提示对话框】
 * 
 */
public class UpdateVersionDialog extends Dialog implements
		View.OnClickListener {
	/**
	 * 提示内容
	 */
	private String content;
	private Handler mHandler;
	private TextView contentTextView;
	private Button confirmButton, cancelButton;

	public UpdateVersionDialog(Context context, Handler handler, String content) {
		super(context, R.style.category_dialog);
		this.content = content;
		this.mHandler = handler;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_version_dialog);
		this.init();
	}

	private void init() {

		contentTextView = (TextView) findViewById(R.id.content);
		confirmButton = (Button) findViewById(R.id.confirm);
		confirmButton.setOnClickListener(this);
		cancelButton = (Button) findViewById(R.id.cancle);
		cancelButton.setOnClickListener(this);

		contentTextView.setText(content);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == confirmButton) {
			mHandler.sendMessage(mHandler
					.obtainMessage(NMConstants.NOTICE_DOWNLOAD));
			dismiss();
		} else if (v == cancelButton) {
			dismiss();
		}
	}
}
