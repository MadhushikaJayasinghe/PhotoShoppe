package com.example.madhushikabasnayake.photoshoppe_madhushika;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by madhushikabasnayake on 7/24/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Photographers.db";
    private Context context;

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
    public Photographer[] getPhotographers(){

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Photographer> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Photographer",null);

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
