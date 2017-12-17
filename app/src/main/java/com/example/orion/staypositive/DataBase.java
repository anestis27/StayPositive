package com.example.orion.staypositive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Orion on 27/4/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    private static final int Database_Version = 1;
    private static final String Database_Name = "DaysDatabase.db";
    private static final String TABLE_DAYS = "Days";
    //Table Days columns
    private static final String KEY_DAYS_ID = "_id";
    private static final String KEY_DAYS_DATE = "date";
    private static final String KEY_DAYS_COLOR = "color";
    private static final String KEY_DAYS_NOTE1 = "note1";
    private static final String KEY_DAYS_NOTE2 = "note2";
    private static final String KEY_DAYS_NOTE3 = "note3";

    private static final String Create_Days_Table = "CREATE TABLE " + TABLE_DAYS + "(" + KEY_DAYS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_DAYS_DATE + " TEXT NOT NULL," + KEY_DAYS_COLOR + " INT," + KEY_DAYS_NOTE1 + " TEXT," + KEY_DAYS_NOTE2 + " TEXT," +
            KEY_DAYS_NOTE3 + " TEXT" + ")";

    private SQLiteDatabase db;


    public DataBase(Context context) {
        super(context, Database_Name, null, Database_Version);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Days_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYS);
        onCreate(db);
    }


    public void InsertDay(Day item) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_DAYS_DATE, item.getDate());
        cv.put(KEY_DAYS_COLOR, item.getColor());
        cv.put(KEY_DAYS_NOTE1, item.getNote1());
        cv.put(KEY_DAYS_NOTE2, item.getNote2());
        cv.put(KEY_DAYS_NOTE3, item.getNote3());

        Log.i("Category", item.getDate());
        Log.i("Amount", item.getColor() + "");
        Log.i("Time", item.getNote1());
        Log.i("Date", item.getNote2());
        Log.i("Date", item.getNote3());


        db.insert(TABLE_DAYS, null, cv);

    }

    public Cursor getAllDaysItems() throws SQLiteException {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_DAYS + " ORDER BY date(" +  KEY_DAYS_DATE + ")" , null);
    }

    public void deleteDataBase() {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYS);
        onCreate(db);
    }

    public Cursor getAnItem(String date) {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_DAYS + " WHERE " + KEY_DAYS_DATE + " = '" + date + "'", null);
    }

    public void deleteAnItem(String date) {
        db.delete(TABLE_DAYS, KEY_DAYS_DATE + "='" + date + "'", null);
    }

    public void editAnItem(String date, String color, String note1, String note2, String note3) {
        ContentValues values = new ContentValues();
        values.put(KEY_DAYS_COLOR, color);
        values.put(KEY_DAYS_NOTE1, note1);
        values.put(KEY_DAYS_NOTE2, note2);
        values.put(KEY_DAYS_NOTE3, note3);
        db.update(TABLE_DAYS, values, KEY_DAYS_DATE + "= '" + date + "'", null);
    }
}
