package nuoman.com.framwork.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * com.mingzhi.samattendance.framework.Utils
 * 
 * @author 陈建辉 <br/>
 *         create at 2013-6-18 上午10:59:36
 * @note: 框架工具类
 */
public class Utils {



	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int toPx(Context context, int dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int px = (int) (dp * scale + 0.5f);
		return px;
	}

	/**
	 * 通过资源ID获得Drawable对象
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public static Drawable getResourceImage(Context context, int id) {
		return context.getResources().getDrawable(id);
	}

	/**
	 * dip值转px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);

	}

	/**
	 * px值转dip
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获得设备屏幕宽度 单位像素
	 * 
	 * @param context
	 * @return
	 */
	public static int getDeviceWidthPixels(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param context
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获得设备屏幕高度 单位像素
	 * 
	 * @param context
	 * @return
	 */
	public static int getDeviceHeightPixels(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到一个进度提示对话框
	 * 
	 * @param context
	 * @param msgStr
	 * @return
	 */
	public static ProgressDialog getProgressDialog(Context context,
			String msgStr) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		progressDialog.setMessage(msgStr);// 进度条显示内容
		progressDialog.setIndeterminate(true);// 设置进度条是否为不明?
		progressDialog.setCancelable(true);// 设置进度条是否可以按取消键取消
		return progressDialog;
	}

	/**
	 * 得到一个进度提示对话框
	 * 
	 * @param context
	 * @return
	 */
	public static CustomProgressDialog getProgressDialog(Context context) {
		CustomProgressDialog progressDialog = new CustomProgressDialog(context);
		progressDialog.setCancelable(true);// 设置进度条是否可以按键取消
		return progressDialog;
	}

	/**
	 * 获得一个消息提示框
	 * 
	 * @param context
	 * @param msg
	 * @param listener
	 * @return
	 */
	public static Builder getAlertDialog(Context context, String msg,
			DialogInterface.OnClickListener listener) {
		Builder builder = new Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(msg);// 设置标题

		builder.setPositiveButton("确定", listener);
		builder.setNegativeButton("取消", listener);
		return builder;
	}



	/**
	 * 获得一个消息提示框
	 * 
	 * @param context
	 * @param resId
	 *            提示内容以资源形式传入
	 * @param listener
	 * @return
	 */
	public static Builder getAlertDialog(Context context, int resId,
			DialogInterface.OnClickListener listener) {
		String msg = context.getResources().getString(resId);
		return getAlertDialog(context, msg, listener);
	}

	public static Builder getAlertDialog(Context context, int resId) {
		String msg = context.getResources().getString(resId);
		return getAlertDialog(context, msg,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// nothing to do
					}
				});
	}

	public static Builder getAlertDialog(Context context, String msg) {
		return getAlertDialog(context, msg,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// nothing to do
					}
				});
	}

	/**
	 * @param dateString
	 *            <p>
	 *            日期字符串转化成日期类型
	 *            </p>
	 */
	public static Date stringToDate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param date
	 *            <p>
	 *            日期转化成字符串
	 *            </p>
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(date);
		return dateString;

	}

	/**
	 * @param currentDate
	 *            当前日期
	 * @return <p>
	 *         当前日期前一天时期字符串
	 *         </p>
	 */
	public static String getPreviousDayString(String currentDate) {
		if (TextUtils.isEmpty(currentDate)) {
			return null;
		}
		return dateToString(getPreviousDay(stringToDate(currentDate)));

	}

	/**
	 * @param currentDate
	 *            当前日期
	 * @return <p>
	 *         当前日期后一天时期字符串
	 *         </p>
	 */
	public static String getNextDayString(String currentDate) {
		if (TextUtils.isEmpty(currentDate)) {
			return null;
		}
		return dateToString(getNextDay(stringToDate(currentDate)));

	}

	/**
	 * 前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getPreviousDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		return date;
	}

	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}




}
