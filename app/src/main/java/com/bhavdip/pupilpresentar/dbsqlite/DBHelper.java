package com.bhavdip.pupilpresentar.dbsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bhavdip.pupilpresentar.model.Attendance;


/**
 * Class that handles a Singleton DB connection.
 *
 * @author Bhavdip Bhalodia
 */

public class DBHelper extends SQLiteOpenHelper {
    /**
     * SQLite DB name
     */
    private static final String DB_NAME = "pp_db";
    /**
     * Current DB Version. When Schema is changed update this value.
     */
    private static final int DB_VERSION = 1;
    /**
     * Static instance point to itself
     */
    private static DBHelper mInstance = null;

    /**
     * constructor should be private to prevent direct instantiation. make call
     * to static factory method "getInstance()" instead.
     *
     * @param ctx the Applications context
     */

    private DBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    /**
     * Checks if there a static pointer to itself exists if not creates one
     *
     * @param ctx
     * @return
     */
    public static DBHelper getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new DBHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    /**
     * @see SQLiteOpenHelper#onCreate(SQLiteDatabase)
     */
    public void onCreate(SQLiteDatabase db) {
        StudentModel.createTable(db);
        AttendanceModel.createTable(db);
        //User extra any no of table here - by Bhavdip
    }

    @Override
    /**
     * @see SQLiteOpenHelper#onUpgrade(SQLiteDatabase, int, int)
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        StudentModel.dropTable(db);
        AttendanceModel.dropTable(db);
        // Create tables again
        onCreate(db);
    }
}
