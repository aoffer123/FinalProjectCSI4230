package com.example.layoutexample;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH;
    private static String DB_NAME = "mydata"; //yourDB file name
    // private static  String DB_NAME = "mydata.db"; //yourDB file name
    private SQLiteDatabase db;
    private Context myContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);
        this.myContext = context;
        this.DB_PATH = this.myContext.getDatabasePath(DB_NAME).getAbsolutePath();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");

            }
        }

    }


    public void openDataBase() throws SQLException {
        //Open the database
        db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }
    private void copyDataBase() throws IOException {
        //Open your local db as the input file
        InputStream myInput = myContext.getAssets().open(DB_NAME + ".db");
        //InputStream myInput = myContext.getAssets().open(DB_NAME );
        // Path to the just created empty db
        //Open the empty db file that was created by DBHelper as an output file
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams or the output file
        myOutput.flush();//write to output file
        myOutput.close();
        myInput.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
