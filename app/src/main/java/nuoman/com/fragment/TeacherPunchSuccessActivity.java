package nuoman.com.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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




	@Override
	protected int setContentViewResId() {
		// TODO Auto-generated method stub
		return R.layout.activity_teacher_punchcard_success;
	}

	@Override
	protected void findWigetAndListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub


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



	@SuppressWarnings("unchecked")
	@Override
	public void  taskOnPostExecute(Task<?, ?> task, Object[] result) {
		// TODO Auto-generated method stub
		super. taskOnPostExecute(task, result);



	}


}
