package nuoman.com.framwork.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * com.mingzhi.samattendance.framework.utils.AppTools
 * 
 * @author 陈建辉 <br/>
 * @time 2013-6-18 下午2:54:20
 * @note 功能说明：应用公用工具
 */
public class AppTools {


	/**
	 * 方法名: 获取系统当前时间
	 * <p>
	 * 功能说明： 获取系统当前时间
	 * </p>
	 * 
	 * @param pattern
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getTime(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date());
	}

	public static String getTime(String pattern, long data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(data);
	}

	public static String getTime(String pattern, String data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.format(dateFormat.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;

	}

	public static String getTime(String pattern, Date data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.format(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


	/**
	 * 方法名:将uri 转换为绝对路径
	 * <p>
	 * 功能说明： 将uri 转换为绝对路径
	 * </p>
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getAbsolutePath(Context context, Uri uri) {
		if (uri == null) {
			return null;
		}
		if (uri.toString().contains("file://")) {

			String path = uri.toString().replace("file://", "");

			return Uri.decode(path);
		}
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, proj, null,
				null, null);
		if (cursor == null) {
			return null;
		}
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		cursor.moveToFirst();
		return cursor.getString(column_index);
	}



	/**
	 * 方法名:获取图片文件存取路径
	 * <p>
	 * 功能说明：获取图片文件存取路径
	 * </p>
	 * 
	 * @return
	 */
	public static String getImageSavePath(Activity activity) {
		if (AppTools.getSDPath().equals("")) {
			return activity.getFilesDir().getPath();
		}
		File file = new File(AppTools.getSDPath() + "/XXB/profession/dcim");
		if (!file.exists()) {
			if (file.mkdirs()) {
				return file.getPath();
			}
			return "";
		}
		return file.getPath();
	}

	/**
	 * 获取压缩图片文件存取路径
	 * 
	 * @return
	 */
	public static String getImageCompresPath(Activity activity) {
		if (AppTools.getSDPath().equals("")) {
			return activity.getFilesDir().getPath();
		}
		File file = new File(AppTools.getSDPath()
				+ "/XXB/profession/dcim/compres");
		if (!file.exists()) {
			if (file.mkdirs()) {
				return file.getPath();
			}
			return "";
		}
		return file.getPath();
	}

	/**
	 * 方法名: 判断SD卡是否存在
	 * <p>
	 * 功能说明： 判断SD卡是否存在, 如果存在返回SD卡路径 , 如果不存在返回路径为空
	 * </p>
	 * 
	 * @return
	 */
	public static String getSDPath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			File sdDir = Environment.getExternalStorageDirectory();
			return sdDir.toString();
		}
		return "";
	}

	/**
	 * 判断网络连接是否连通
	 * 
	 * @return
	 */
	public static boolean netWorkJuder() {
		ConnectivityManager manager = (ConnectivityManager) AppConfig
				.getContext().getSystemService(
						AppConfig.getContext().CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 图片无损压缩
	 * 
	 * @param fromPath
	 *            图片来源路径
	 * @param toPath
	 *            目標存儲路徑
	 * @param width
	 *            最長邊壓縮到的長度
	 */
	public static String compressImage(String toPath, int width, String fromPath) {
		// String fromPath = CameraManager.intance().getCameraPath();
		Log.i("test1", "fromPath  : " + fromPath + " ; toPath : " + toPath);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(fromPath, options);
		int be;
		// 缩放比
		if (options.outWidth > options.outHeight) {
			be = (int) (options.outWidth / width);
			be = be + ((options.outWidth % width == 0) ? 0 : 1);
		} else {
			be = (int) (options.outHeight / (float) width);
			be = be + ((options.outHeight % width == 0) ? 0 : 1);
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(fromPath, options);
		File file = new File(toPath);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toPath;
	}

	public static Bitmap compressImage2(int width, String fromPath) {
		// String fromPath = CameraManager.intance().getCameraPath();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(fromPath, options);
		int be;
		// 缩放比
		if (options.outWidth > options.outHeight) {
			be = (int) (options.outWidth / width);
			be = be + ((options.outWidth % width == 0) ? 0 : 1);
		} else {
			be = (int) (options.outHeight / (float) width);
			be = be + ((options.outHeight % width == 0) ? 0 : 1);
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(fromPath, options);
		return bitmap;
	}

	/**
	 * 图片无损压缩
	 * 
	 * @param fromPath
	 *            图片来源路径
	 * @param toPath
	 *            目標存儲路徑
	 * @param width
	 *            最長邊壓縮到的長度
	 */
	public static String zipImage(String fromPath, String toPath, int width) {
		Log.i("test1", "fromPath  : " + fromPath + " ; toPath : " + toPath);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(fromPath, options);
		// if (bitmap == null)
		// {
		// return;
		// }
		int be;
		// 缩放比
		if (options.outWidth > options.outHeight) {
			be = (int) (options.outWidth / width);
			be = be + ((options.outWidth % width == 0) ? 0 : 1);
		} else {
			be = (int) (options.outHeight / (float) width);
			be = be + ((options.outHeight % width == 0) ? 0 : 1);
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(fromPath, options);
		File file = new File(toPath);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toPath;
	}

	/**
	 * 
	 * 方法名:getFileByte
	 * <p>
	 * 功能说明：将字节数转换成文件流
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileByte(String filePath) {
		int count;
		int num = 0;
		File file = new File(filePath);
		long len = file.length();
		if (file.exists()) {
			FileInputStream fis = null;
			StringBuffer sb = new StringBuffer();
			try {
				fis = new FileInputStream(file);
				byte[] buffer = new byte[(int) len];
				while ((count = fis.read(buffer)) != -1) {
					sb.append(Base64.encodeToString(buffer, Base64.DEFAULT));
					num = count++;
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		return "";
	}





	/**
	 * 按照日期格式转化为系统时间按戳中午12点
	 * 
	 * @param dateString
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getDataToTimeInMillis(String dateString) {
		long timestamp = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = df.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			timestamp = cal.getTimeInMillis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timestamp;
	}



	/**
	 * 方法名: getAppSavePath
	 * <p>
	 * 功能说明： 获取更新应用存储路径
	 * </p>
	 * 
	 * @return
	 */
	public static String getAppSavePath(Context context) {
		return context.getFilesDir().getPath() + "/sam.apk";
	}

	/**
	 * 方法名: inistallAPKFile
	 * <p>
	 * 功能说明：安装APK文件
	 * </p>
	 * 
	 * @param path
	 */
	public static void inistallAPKFile(Context context, String path) {
		String pathString = context.getDir("shared_prefs", Context.MODE_PRIVATE).toString();
		pathString=pathString.replace("app_shared_prefs", "shared_prefs");
		deletefile(context,pathString);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 4.0以后的系统必须有这句，否则安装完成直接退出不显示安装成功界面
		context.startActivity(intent);
//		SharedPreferences sp = context.getSharedPreferences(
//		MineAppliction.user.getUserId(), Context.MODE_PRIVATE);
//		sp.edit().clear().commit();

	}

	public static void deletefile(Context context, String delpath) {
		File file = new File(delpath);
		String[] filelist = file.list();
		for (int i = 0; i < filelist.length; i++) {
			String str=filelist[i].substring(0, filelist[i].lastIndexOf("."));
			SharedPreferences sp = context.getSharedPreferences(
					str, Context.MODE_WORLD_READABLE);
			Editor ed = sp.edit();
			ed.clear();
			ed.commit();
		}

	}


	/**
	 * 方法名: getVersionCode
	 * <p>
	 * 功能说明： 返回当前应用的版本号
	 * </p>
	 * 
	 * @return
	 */
	public static int getVersionCode() {
		int verCode = 0;
		try {
			verCode = AppConfig.getContext().getPackageManager()
					.getPackageInfo(AppConfig.getContext().getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
		}
		return verCode;
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap outBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return outBitmap;
	}

	/**
	 * 对图片的等比缩放
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap imageSize(Bitmap bitmap) {
		int bmpWidth = 80;
		int bmpHeight = 80;
		Matrix matrix = new Matrix();
		matrix.postScale((float) 1, (float) 1);
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
				bmpHeight, matrix, true);
		return resizeBmp;
	}

	/**
	 * 初始化头像
	 */
	public static Bitmap initAvatar(Context context, int tx) {
		Bitmap bitmap = null, bitmap2 = null;
		bitmap = BitmapFactory.decodeResource(context.getResources(), tx);

		bitmap2 = toRoundBitmap(bitmap);
		return bitmap2;
	}



		/**
	 * 转化SQL为数组进行建库
	 * 
	 * @param context
	 * @return
	 */
	public static String[] addSqlString(Context context) {
		String[] strtmp = null;
		StringBuilder sb = new StringBuilder("");
		String tmp = null;
		try {
			String filePath = context.getFilesDir() + "/area.txt";
			if (!new File(filePath).exists()) {
				return null;
			}
			InputStream in = context.openFileInput("area.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in),
					8 * 1024);
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
			br.close();
			in.close();
		} catch (FileNotFoundException e3) {
			e3.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		strtmp = sb.toString().split(";");
		return strtmp;
	}


	/**
	 * 获取头像路径
	 * 
	 * @return
	 */
	public static String getHeadPortraitPath(Context context) {
		if (AppTools.getSDPath().equals("")) {
			return context.getFilesDir().getPath();
		}
		File file = new File(AppTools.getSDPath()
				+ "/XXB/profession/dcim/touxiang");
		if (!file.exists()) {
			if (file.mkdirs()) {
				return file.getPath();
			}
			return "";
		}
		return file.getPath();
	}

	/**
	 * 下载图片
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static Bitmap downloadPicture(String urlStr) throws Exception {
		Bitmap bm = null;
		URL url = new URL(urlStr.trim());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		bm = BitmapFactory.decodeStream(is);
		return bm;
	}

	/**
	 * 弹出软键盘
	 */
	public static void focus(Context context, EditText et) {
		et.setFocusable(true);
		et.requestFocus();
		et.setCursorVisible(true);
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(et, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 软键盘消失
	 */
	public static void dismissSoftKey(Context context, EditText et) {
		et.requestFocus();
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}

	/**
	 * 唤醒屏幕并解锁
	 */
	public static void wakeUpAndUnlock(Context context) {
		// 获取电源管理器对象
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// 点亮屏幕
		wl.acquire();
		// 释放
		wl.release();
		wl.acquire(10000);

		KeyguardManager km = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		// 解锁
		kl.disableKeyguard();
	}

	/**
	 * Toast
	 * 
	 * @param content
	 */
	public static void getToast( String content) {
		Toast.makeText(AppConfig.getContext(), content, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 预定时分和当前时分秒差
	 */
	public static long deltaT(String t) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int h = Integer.valueOf((String) t.subSequence(0, 2));
		int m = Integer.valueOf((String) t.subSequence(3, 5));
		long c = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
		long s = h * 60 * 60 * 1000 + m * 60 * 1000;
		if (s < c) {
			return AlarmManager.INTERVAL_DAY - c + s;
		}
		if (s > c) {
			return s - c;
		}
		if (s == c) {
			return AlarmManager.INTERVAL_DAY;
		}
		return 0;
	}

	/**
	 * 向内存中写数据
	 * 
	 * @param content
	 */
	public static void write(String content, Context context) {
		FileOutputStream fileOut = null;
		try {
			// 以追加的方式打开文件输出流
			fileOut = context
					.openFileOutput("address.txt", context.MODE_APPEND);
			// 写入数据
			fileOut.write(content.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 强制刷新
				fileOut.flush();
				// 关闭文件输出流
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向内存中读取数据
	 * 
	 * @return
	 */
	public static String read(Context context) {
		FileInputStream fileInput = null;
		BufferedReader br = null;
		try {
			// 打开文件输入流
			fileInput = context.openFileInput("address.txt");
			br = new BufferedReader(new InputStreamReader(fileInput));
			String str = null;
			StringBuilder stb = new StringBuilder();
			while ((str = br.readLine()) != null) {
				stb.append(str);
			}
			return stb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fileInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
