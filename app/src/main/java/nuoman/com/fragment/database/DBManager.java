package nuoman.com.fragment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import nuoman.com.fragment.entity.NewsInfo;
import nuoman.com.fragment.entity.PersonInfo;

/**
 * Created by Administrator on 2015/4/19.
 */
public class DBManager {

    private MineDatabase mineDatabase;
    private SQLiteDatabase db;
    private static DBManager dbManagerInstance;

    public static DBManager getDbManagerInstance(Context context) {
        if (dbManagerInstance == null) {
            dbManagerInstance = new DBManager(context);
        }
        return dbManagerInstance;
    }

    public DBManager(Context context) {
        mineDatabase = new MineDatabase(context);
        db = mineDatabase.getWritableDatabase();
    }

    /**
     * Add person info
     *
     * @param list
     */
    public void addData(List<PersonInfo> list) {
        db.beginTransaction();
        try {
            for (PersonInfo personInfo : list) {
                db.execSQL("INSERT INTO t_person VALUES(null,?,?,?)", new Object[]{personInfo.getName(), personInfo.getCardno(), personInfo.getNumber()});
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    /**
     * Add person info
     *
     * @param list
     */
    public void addNewsData(List<NewsInfo> list) {
        db.beginTransaction();
        try {
            for (NewsInfo newsInfo : list) {
                db.execSQL("INSERT INTO t_news VALUES(null,?,?,?)", new Object[]{newsInfo.getPkid(), newsInfo.getTitle(), newsInfo.getImageUrl()});
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    /**
     * clear data
     */
    public void clearData( ){

        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM person");
            db.execSQL("DELETE FROM sqlite_sequence");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    /**
     * close database
     */
    public void closeDb() {
        db.close();
    }


}
