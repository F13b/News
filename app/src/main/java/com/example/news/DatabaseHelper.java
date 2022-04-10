package com.example.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Запрос на создание таблицы БД
        sqLiteDatabase.execSQL("create Table UserInfo(login Text primary key, " +
                "password Text, role Text)");
        sqLiteDatabase.execSQL("create Table News(newsname Text primary key, " +
                "newstext Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Условие на удаление таблицы UserInfo
        sqLiteDatabase.execSQL("drop Table if exists UserInfo");
        sqLiteDatabase.execSQL("drop Table if exists News");
    }

    public Boolean insertUsers(String login, String password, String role) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", login);
        contentValues.put("password", password);
        contentValues.put("role", role);
        long result = DB.insert("UserInfo", null, contentValues);
        return result != 1;
    }

    public Boolean insertNews(String newsname, String newstext) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("newsname", newsname);
        contentValues.put("newstext", newstext);
        long result = DB.insert("News", null, contentValues);
        return result != 1;
    }

    public Cursor getNewsData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from News", null);
    }

    public Boolean updateNews(String newsname, String newstext) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("newsname", newsname);
        contentValues.put("newstext", newstext);
        int result = db.update("News", contentValues, "newsname = " + "'" + newsname + "'", null);
        db.close();
        return result > 0;
    }

    public Boolean deleteNews(String newsname) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("News", "newsname = " + "'" + newsname + "'", null);
        db.close();
        return result > 0;

    }
}
