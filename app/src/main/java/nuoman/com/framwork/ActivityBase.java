package nuoman.com.framwork;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

import nuoman.com.framwork.utils.AppTools;
import nuoman.com.framwork.utils.CustomProgressDialog;
import nuoman.com.framwork.utils.Utils;



/**
 * 
 * @FILE : FragmentActivity.java [V 1.0.0]
 * 
 * @AUTHOR: 陈建辉 CREAT AT 2013-8-12 上午11:26:57
 * 
 * @NOTE :[框架中Activity的基类，通过实现TaskListener接口达到封装任务的目的]
 * 
 */
// 在这里定义方法名
public abstract class ActivityBase extends FragmentActivity implements
        Task.TaskListener, OnClickListener {
	private static final String UNDEFINE_ERROR = "网络不给力啊！";
	private static final String UNDEFINE_ERROR1 = "数据异常！";
	private ArrayList<Task<?, ?>> m_tasks;
	protected CustomProgressDialog m_progressDialog;
	protected boolean progressDialogFlag;
	protected String prompt = "加载中……";



	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        m_progressDialog = Utils.getProgressDialog(this);
		setContentView(setContentViewResId());
		findWigetAndListener();
		initData();

	}

	/**
	 * 获取View控件
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends View> T getViewById(int id) {
		return (T) findViewById(id);
	}

	/**
	 * Activity layout
	 * 
	 * @return
	 */
	protected abstract int setContentViewResId();

	/**
	 * 初始化view
	 */
	protected abstract void findWigetAndListener();

	/**
	 * 初始化Data
	 */
	protected abstract void initData();

	@Override
	public void  taskOnPreExecute(Task<?, ?> task) {

		if (progressDialogFlag) {
			m_progressDialog.setMessage(prompt);
			m_progressDialog.show();
		}
	}

	@Override
	public void  taskOnPostExecute(Task<?, ?> task, Object[] result) {
		if (m_tasks != null) {
			m_tasks.remove(task);
			if (m_tasks.size() == 0) {
				if (m_progressDialog.isShowing()) {
					progressDialogFlag = false;
					prompt = "加载中……";
					m_progressDialog.dismiss();
				}
			}
		}
	}

	@Override
	public void  taskOnProgressUpdate(Task<?, ?> task, String... values) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * 添加需要执行的任务 添加任务必须继承 com.cepri.epmmis.framework.android.Task<?, ?>
	 * 
	 * @param task
	 */
	public void addTask(Task<?, ?> task) {
		if (m_tasks == null) {
			m_tasks = new ArrayList<Task<?, ?>>();
		}
		m_tasks.add(task);
	}

	/**
	 * 结束当前任务
	 * 
	 * @param task
	 */
	public void cancelTask(Task<?, ?> task) {
		task.cancel(true);
	}

	/**
	 * 结束当前所有任务
	 */
	public void cancelAllTasks() {
		if (m_tasks != null) {
			for (int i = 0; i < m_tasks.size(); i++) {
				m_tasks.get(i).cancel(true);
			}
		}
	}

	/**
	 * 
	 * @return 返回当前正在执行任务
	 */

	public boolean getTaskStatus() {
		for (int i = 0; i < m_tasks.size(); i++) {
			if (m_tasks.get(i).getStatus() == AsyncTask.Status.RUNNING) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onPause() {
		super.onPause();
		cancelAllTasks();
	}

	/**
	 * 验证返回数据的正确型
	 * 
	 * @param result
	 *            参数为需要验证的返回数据
	 * @return 返回boolean型验证结果
	 */
	public boolean checkTaskResult(Object[] result) {
		progressDialogFlag = false;
		m_progressDialog.dismiss();
		prompt = "加载中……";
		if (result == null || result[0] == null
				|| result[0] instanceof Exception) {
			if (!AppTools.netWorkJuder()) {
				Toast.makeText(ActivityBase.this, UNDEFINE_ERROR,
						Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(ActivityBase.this, UNDEFINE_ERROR1,
						Toast.LENGTH_SHORT).show();
			return false;

		}


		return true;
	}


	
}
