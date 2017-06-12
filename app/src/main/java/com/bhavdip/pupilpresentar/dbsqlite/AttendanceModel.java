package com.bhavdip.pupilpresentar.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bhavdip.pupilpresentar.model.Attendance;

import java.util.ArrayList;

public class AttendanceModel extends Model {
    private static final String TABLE_ATTENDANCE = "pp_attendance";

    //Fields
    private static final String KEY_ATTENDANCE_ID = "attendance_id";

    private static final String KEY_ROLL_NO = "roll_no";
    private static final String KEY_PRESENT = "present";
    private static final String KEY_DATE = "attendance_date";

    private static final String CREATE_TABLE_ATTENDANCE = "create table if not exists "
            + TABLE_ATTENDANCE+ " ( "
            + KEY_ATTENDANCE_ID + " integer primary key autoincrement, "
            + KEY_ROLL_NO + " text not null unique,"
            + KEY_DATE+ " text,"
            + KEY_PRESENT + " text);";

    public AttendanceModel(Context ctx) {
        super(ctx);
    }

    /**
     * Drop Requests table
     *
     * @param db SQLiteDatabase instance
     */
    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + AttendanceModel.TABLE_ATTENDANCE);
    }

    /**
     * Create the Requests table
     *
     * @param db SQLiteDatabase instance
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL(AttendanceModel.CREATE_TABLE_ATTENDANCE);
    }

    /**
     * Clears the tables in the db
     *
     * @param db
     */
    public static void clearTable(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + AttendanceModel.TABLE_ATTENDANCE);
    }

    public void insertEntry(String strRollno, String gender, String strFirstname) {

        open();
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(KEY_ROLL_NO, strRollno);
        newValues.put(KEY_DATE, gender);
        newValues.put(KEY_PRESENT, strFirstname);

        // Insert the row into your table
        db.insert(AttendanceModel.TABLE_ATTENDANCE, null, newValues);
    }
    private Attendance cursorToStudent(Cursor cursor)
    {
        Attendance student = new Attendance();

        student.setRollno(cursor.getString(cursor
                .getColumnIndex(AttendanceModel.KEY_ROLL_NO)));

        student.setDate(cursor.getString(cursor
                .getColumnIndex(AttendanceModel.KEY_DATE)));

        student.setPresent(cursor.getString(cursor
                .getColumnIndex(AttendanceModel.KEY_PRESENT)));

        return student;
    }

    public ArrayList<Attendance> getStudentList()
    {
        open();
        String selectQuery = "SELECT * FROM " + AttendanceModel.TABLE_ATTENDANCE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Attendance> list = new ArrayList<Attendance>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Attendance student = cursorToStudent(cursor);
            list.add(student);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public String getSinlgeEntry(String roll_no) {
        open();
        Cursor cursor = db.query(AttendanceModel.TABLE_ATTENDANCE, null, " roll_no=?", new String[]{roll_no}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String email = cursor.getString(cursor.getColumnIndex(AttendanceModel.KEY_PRESENT));
        cursor.close();
        return email;
    }

}
