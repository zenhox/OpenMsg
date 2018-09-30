package hox.seu.edu.cn.openmsg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    private SQLiteUtil sqLiteUtil;

    public ItemDao(Context context){
        sqLiteUtil = new SQLiteUtil(context,1);
    }

    public List getAllItems(){
        List list = new ArrayList();
        SQLiteDatabase db = sqLiteUtil.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteUtil.TABLE_NAME,null);
        String item  = new String();
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex(SQLiteUtil.TABLE_INFO_COLUM_TEXT)));
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean addItem(String string){
        SQLiteDatabase db = sqLiteUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteUtil.TABLE_INFO_COLUM_TEXT,string);
        db.insert(SQLiteUtil.TABLE_NAME,null,contentValues);
        db.close();
        return true;
    }

    public boolean delItem(String string){
        SQLiteDatabase db = sqLiteUtil.getWritableDatabase();
        db.delete(SQLiteUtil.TABLE_NAME,SQLiteUtil.TABLE_INFO_COLUM_TEXT + "= ?", new String[]{string});
        db.close();
        return true;
    }
}
