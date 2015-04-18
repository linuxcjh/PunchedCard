package nuoman.com.framwork;

import android.os.AsyncTask;

/**
 * 
 * @file : Task.java [V 1.0.0]
 * 
 * @author: 陈建辉
 * 
 * @time : CREAT AT 2013-6-18 上午10:59:10
 * 
 * @TODO : 【框架中所有AsyncTask任务的基类】
 * 
 */
public class Task<R, S> extends AsyncTask<R, S, Object[]> {
	
	private TaskListener m_listener;

	private int m_id;
	
	public interface TaskListener {
		
		public void onPreExecute(Task<?, ?> task);

		public void onPostExecute(Task<?, ?> task, Object[] result);

		public void onProgressUpdate(Task<?, ?> task, String... values);
	}

	public Task(TaskListener listener) {
		m_listener = listener;
	}

	protected TaskListener getListener() {
		return m_listener;
	}
	/**
	 * 设置任务ID
	 * 
	 * @param id
	 */
	public void setId(int id) {
		m_id = id;
	}

	/**
	 * 获取任务ID
	 * 
	 * @return
	 */
	public int getId() {
		return m_id;
	}
	@Override
	protected void onPreExecute() {
		m_listener.onPreExecute(this);
	}

	@Override
	protected void onPostExecute(Object[] result) {
		m_listener.onPostExecute(this, result);
	}

	@Override
	protected void onProgressUpdate(S... values) {
		m_listener.onProgressUpdate(this, values[0].toString());
	}

	@Override
	protected Object[] doInBackground(R... values) {
		return null;
	}

}
