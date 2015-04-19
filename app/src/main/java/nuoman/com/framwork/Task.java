package nuoman.com.framwork;

import android.os.AsyncTask;

/**
 * @TODO : 【框架中所有AsyncTask任务的基类】
 */
public class Task<R, S> extends AsyncTask<R, S, Object[]> {
	
	private TaskListener m_listener;

	private int m_id;
	
	public interface TaskListener {
		
		public void taskOnPreExecute(Task<?, ?> task);

		public void  taskOnPostExecute(Task<?, ?> task, Object[] result);

		public void  taskOnProgressUpdate(Task<?, ?> task, String... values);
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
		m_listener. taskOnPreExecute(this);
	}

	@Override
	protected void onPostExecute(Object[] result) {
		m_listener. taskOnPostExecute(this, result);
	}

	@Override
	protected void onProgressUpdate(S... values) {
		m_listener. taskOnProgressUpdate(this, values[0].toString());
	}

	@Override
	protected Object[] doInBackground(R... values) {
		return null;
	}

}
