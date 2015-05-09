/**
 * UpdateVersion.java [V 1.0.0]
 * com.mingzhi.samattendance.framework.utils.UpdateVersion
 * ChenJianhui  create at 2013-9-5 下午4:06:25
 */
package nuoman.com.framwork.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import nuoman.com.fragment.MineApplication;
import nuoman.com.fragment.R;
import nuoman.com.fragment.entity.UpdataVersionInfo;
import nuoman.com.framwork.network.NMConstants;
import nuoman.com.framwork.network.NetWorkConnectionException;

/**
 * @file : UpdateVersion.java [V 1.0.0]
 * 
 * @author: 陈建辉
 * 
 * @time : CREAT AT 2013-9-5 下午4:06:25
 * 
 * @TODO : 【版本升级】
 * 
 */
public class UpdateVersion {
	/*
	 * @param 0 后台静默检测
	 * 
	 * @param 1 前台显示检测
	 */
	private int updataType = 0;
	public static final int UPDATA_APPLICATION_RESULT = 8;
	private CustomProgressDialog m_progressDialog;
	private Context context;
	private UpdataVersionInfo appLicationModel;
	private Handler mHandler;

	public UpdateVersion(Context context, UpdataVersionInfo appLicationModel,
			int updateType) {

		this.context = context;
		this.appLicationModel = appLicationModel;
		this.updataType = updateType;
		init();
	}

	public UpdateVersion(Context context, Handler mHandler) {

		this.mHandler = mHandler;
		this.context = context;
		init();
	}

	private void init() {
		m_progressDialog = Utils.getProgressDialog(context);
	}

	/**
	 * 判断是否更新
	 * 
	 * @param appLicationModel
	 * @return
	 */
	private boolean judgeIsUpdata(UpdataVersionInfo appLicationModel) {
		if(!appLicationModel.getVersion().equals(AppTools.getVersionName())){
			MineApplication.isUpdate=true;
			return true;
		}
		MineApplication.isUpdate = false;
		return false;
	}

	public void showIsUpdata() {
		if (!judgeIsUpdata(appLicationModel)) {
			if (updataType == 0) {
				return;
			}
			showNotUpdataDialog();
		} else {
			showUpdataDialog(appLicationModel);
		}
	}

	/**
	 * 显示不更新dialog提示
	 */
	private void showNotUpdataDialog() {
		Builder dialog = new Builder(context);
		dialog.setTitle("更新检测");
		dialog.setMessage("已是最新版本！");
		dialog.setNegativeButton("确定", null);
		dialog.create().show();
	}
	
	

	/**
	 * 显示是否更新提示
	 * 
	 * @param appLicationModel
	 */
	private void showUpdataDialog(final UpdataVersionInfo appLicationModel) {
		String content = appLicationModel.getIntro();
		if (!TextUtils.isEmpty(content) && content.contains("\\n")) {
			content = appLicationModel.getIntro().replace("\\n", "\n");
		}
		new UpdateVersionDialog(context, handler, content).show();
	}

	/**
	 * 新版本下载
	 * 
	 */
	private void downloadAppLication(Context context, String url) {
		Builder builder = new Builder(context);
		AlertDialog dialog = builder.create();
		dialog.setIcon(R.mipmap.ic_launcher);
		dialog.setTitle("正在下载最新版本……");
		DownLoadAppThread appThread = new DownLoadAppThread(url, context,
				dialog);
		appThread.start();
	}

	/**
	 * 数据库文件下载
	 * 
	 */
	public void downloadSQLfile(final String sqlUrl) {
		m_progressDialog.show();
		m_progressDialog.setMessage("初始化，请稍候……");
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					try {
						URL url = new URL(sqlUrl);
						URLConnection connection = url.openConnection();
						connection.connect();
						InputStream inputStream = connection.getInputStream();
						FileOutputStream fileOutputStream = context
								.openFileOutput("area.txt",
										Context.MODE_WORLD_READABLE);
						byte[] buf = new byte[128];
						do {
							int numread = inputStream.read(buf);
							if (numread <= 0) {
								break;
							}
							fileOutputStream.write(buf, 0, numread);
						} while (true);
						m_progressDialog.dismiss();
						mHandler.sendMessage(mHandler
								.obtainMessage(NMConstants.NOTICE_DOWNLOAD_FILE));
					} catch (IOException e) {
					}
				} catch (NetWorkConnectionException e) {
				}
			}
		}).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATA_APPLICATION_RESULT:
				AppTools.inistallAPKFile(context, (String) msg.obj);
				break;
			case NMConstants.NOTICE_DOWNLOAD:
				File file = new File(
						context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
								+ "/sam.apk");
				if (file.exists()) {
					file.delete();
				}
				downloadAppLication(context, appLicationModel.getDurl());
				break;
			}
		};
	};
}
