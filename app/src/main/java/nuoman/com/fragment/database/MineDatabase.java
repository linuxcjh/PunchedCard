package nuoman.com.fragment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

import nuoman.com.framwork.network.NMConstants;

/**
 * Created by Administrator on 2015/4/19.
 */
public class MineDatabase extends SQLiteOpenHelper {

    private Context context;

    public MineDatabase(Context context) {
        super(context, NMConstants.DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.beginTransaction();
            db.execSQL("CREATE TABLE person (id INTEGER PRIMARY KEY AUTOINCREMENT,person_name TEXT,person_number TEXT,person_tel TEXT)");
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
