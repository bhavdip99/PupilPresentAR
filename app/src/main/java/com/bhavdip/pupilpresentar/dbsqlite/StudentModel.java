package com.bhavdip.pupilpresentar.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by aakash on 05/09/14.
 */
public class StudentModel extends Model {
    private static final String TABLE_STUDENTS = "pp_users";

    //Fields
    private static final String KEY_USERS_ID = "user_id";

    private static final String KEY_ROLL_NO = "roll_no";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PARENT_MOBILE = "parent_mobile";
    private static final String KEY_PARENT_OCCUPATION = "parent_occupation";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_IMAGE = "image";

    private static final String CREATE_TABLE_USERS = "create table if not exists "
            + TABLE_STUDENTS
            + " ( "
            + KEY_USERS_ID
            + " integer primary key autoincrement, "
            + KEY_ROLL_NO
            + " text not null unique,"
            + KEY_FIRST_NAME
            + " text,"
            + KEY_LAST_NAME
            + " text,"
            + KEY_EMAIL
            + " text,"
            + KEY_PARENT_MOBILE
            + " text,"
            + KEY_PARENT_OCCUPATION
            + " text,"
            + KEY_GENDER
            + " text,"
            + KEY_IMAGE
            + " BLOB);";

    public StudentModel(Context ctx) {
        super(ctx);
    }

    /**
     * Drop Requests table
     *
     * @param db SQLiteDatabase instance
     */
    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + StudentModel.TABLE_STUDENTS);
    }

    /**
     * Create the Requests table
     *
     * @param db SQLiteDatabase instance
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL(StudentModel.CREATE_TABLE_USERS);
    }

    /**
     * Clears the tables in the db
     *
     * @param db
     */
    public static void clearTable(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + StudentModel.TABLE_STUDENTS);
    }

    public void insertEntry(String strRollno, String gender, String strFirstname, String strLastname, String strEmail,
                            String strMobile, String strOccupation) {

        open();
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(KEY_ROLL_NO, strRollno);
        newValues.put(KEY_GENDER, gender);
        newValues.put(KEY_FIRST_NAME, strFirstname);
        newValues.put(KEY_LAST_NAME, strLastname);
        newValues.put(KEY_EMAIL, strEmail);
        newValues.put(KEY_PARENT_MOBILE, strMobile);
        newValues.put(KEY_PARENT_OCCUPATION, strOccupation);

        // Insert the row into your table
        db.insert(StudentModel.TABLE_STUDENTS, null, newValues);
    }

    public String getSinlgeEntry(String roll_no) {
        open();
        Cursor cursor = db.query(StudentModel.TABLE_STUDENTS, null, " roll_no=?", new String[]{roll_no}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String email = cursor.getString(cursor.getColumnIndex(StudentModel.KEY_EMAIL));
        cursor.close();
        return email;
    }

}
