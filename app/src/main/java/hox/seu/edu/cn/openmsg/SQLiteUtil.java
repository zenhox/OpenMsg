package hox.seu.edu.cn.openmsg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteUtil extends SQLiteOpenHelper {
    //定义数据库的各种常量，如数据库名，表名，表的字段
    protected static final String DBNAME = "OpenMsg";
    protected static final String TABLE_NAME = "messages";
    protected static final String TABLE_INFO_COLUM_TEXT  = "item";//主键前面一般都带下划线，也可不带

    //本类的构造方法
    public SQLiteUtil(Context context, int version) {
        //上面定义了数据库名，故可直接写死DBNAME
        super(context, DBNAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表,使用StringBuffer代替String减少内存消耗
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE IF NOT EXISTS ");
        stringBuffer.append(TABLE_NAME + "(");
        stringBuffer.append(TABLE_INFO_COLUM_TEXT+" varchar(300))");//执行操作
        db.execSQL(stringBuffer.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
