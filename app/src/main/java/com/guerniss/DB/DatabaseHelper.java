package com.guerniss.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "product_db";

    // Table Names
    private static final String DB_TABLE = "table_product";

    // column names
    private static final String KEY_NAME = "image_name";
    private static final String KEY_IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            KEY_NAME + " TEXT," +
            KEY_IMAGE + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }

    public void addEntry( String name, byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(KEY_NAME,     name);
        cv.put(KEY_IMAGE,     image);
        database.insert( DB_TABLE, null, cv );
    }

    public ArrayList<LocalDBProduct> localDbProduct() {

        //final String TABLE_NAME = "name of table";

        String selectQuery = "SELECT  * FROM " + DB_TABLE;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        ArrayList<LocalDBProduct> data  = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                String prod_name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                byte[] image = cursor.getBlob(1);

                LocalDBProduct localDBProduct = new LocalDBProduct(prod_name,DbBitmapUtility.getImage(image));
                data.add(localDBProduct);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    public void delete(SQLiteDatabase db) {
        db.execSQL("delete from "+ DB_TABLE);
    }
}
