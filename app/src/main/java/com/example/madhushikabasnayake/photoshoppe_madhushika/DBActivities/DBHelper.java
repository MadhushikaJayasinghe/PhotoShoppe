package com.example.madhushikabasnayake.photoshoppe_madhushika.DBActivities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by madhushikabasnayake on 7/24/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "photoshoppe.db";
    public static final String DB_LOCATION="/data/data/com.example.madhushikabasnayake.photoshoppe_madhushika/databases/";

    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase(){
        String dbpath =context.getDatabasePath(DATABASE_NAME).getPath();
        if(sqLiteDatabase!=null && sqLiteDatabase.isOpen()){
            return;
        }
        sqLiteDatabase=SQLiteDatabase.openDatabase(DB_LOCATION,null,SQLiteDatabase.OPEN_READWRITE);
    }


    public void closeDB(){
        if(sqLiteDatabase!=null){
            sqLiteDatabase.close();
        }
    }


    public Photographer[] getPhotographers(){

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Photographer> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM photographer",null);

        while (cursor.moveToNext()){

            String firstName = cursor.getString(0);
            String lastName = cursor.getString(1);
            String telephone = cursor.getString(2);
            String email=cursor.getString(3);


            Photographer aPhotographer = new Photographer(firstName, lastName, telephone,email);
            list.add(aPhotographer);
        }

        cursor.close();
        db.close();

        return list.toArray(new Photographer[list.size()]);
    }

}
