package com.mulakat.huseyin.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewOutlineProvider;

import com.mulakat.huseyin.interfaces.ontaskend;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huseyin on 2.11.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "words.db";
    public static final String WORDS_TABLE_NAME = "words_lt";
    public static final String WORDS_COLUMN_NAME = "name";
    public static final String WORDS_COLUMN_COUNT = "cnt";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table words_lt " +
                        "(name text,cnt integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS words_lt");
        onCreate(db);
    }

    public boolean insertWord (String name, Integer count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("cnt", count);
        db.insert("words_lt", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM words_lt ORDER BY cnt LIMIT 0, 5", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, WORDS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (String name, Integer count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("cnt", count);
        db.update("words_lt", contentValues, "name = ? ", new String[] { name } );
        return true;
    }

    public Integer deleteWord (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("words_lt",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void deleteAllWords () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM words_lt");
    }

    public ArrayList<ArchiveItemWord> getAllWords() {
        ArrayList<ArchiveItemWord> array_list = new ArrayList<ArchiveItemWord>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT name, cnt FROM words_lt ORDER BY cnt DESC LIMIT 0, 5", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(new ArchiveItemWord(res.getString(0), res.getInt(1)));
            res.moveToNext();
        }
        return array_list;
    }
}
