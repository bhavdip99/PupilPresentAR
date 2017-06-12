package com.bhavdip.pupilpresentar.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.bhavdip.pupilpresentar.model.Student;
import com.bhavdip.pupilpresentar.utility.Utility;

import java.util.ArrayList;

public class StudentModel extends Model {
    private static final String TABLE_STUDENTS = "pp_student";

    //Fields
    private static final String KEY_STUDENT_ID = "student_id";

    private static final String KEY_ROLL_NO = "roll_no";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PARENT_MOBILE = "parent_mobile";
    private static final String KEY_PARENT_OCCUPATION = "parent_occupation";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_IMAGE = "image";

    private static final String CREATE_TABLE_STUDENT = "create table if not exists "
            + TABLE_STUDENTS + " ( "
            + KEY_STUDENT_ID + " integer primary key autoincrement, "
            + KEY_ROLL_NO + " text not null unique,"
            + KEY_FIRST_NAME + " text,"
            + KEY_LAST_NAME + " text,"
            + KEY_EMAIL + " text,"
            + KEY_PARENT_MOBILE + " text,"
            + KEY_PARENT_OCCUPATION + " text,"
            + KEY_GENDER + " text,"
            + KEY_IMAGE + " BLOB);";

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
        db.execSQL(StudentModel.CREATE_TABLE_STUDENT);
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
                            String strMobile, String strOccupation,Bitmap img) {


        byte[] image = Utility.getBitmapAsByteArray(img);
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
        if (img!=null) {
            newValues.put(KEY_IMAGE, image);
        }else{
            newValues.put(KEY_IMAGE, new byte[0]);
        }
        // Insert the row into your table
        db.insert(StudentModel.TABLE_STUDENTS, null, newValues);
    }
    private Student cursorToStudent(Cursor cursor)
    {
        Student student = new Student();

        student.setRollno(cursor.getString(cursor
                .getColumnIndex(StudentModel.KEY_ROLL_NO)));

        student.setFirstName(cursor.getString(cursor
                .getColumnIndex(StudentModel.KEY_FIRST_NAME)));

        student.setLastName(cursor.getString(cursor
                .getColumnIndex(StudentModel.KEY_LAST_NAME)));

        student.setEmail(cursor.getString(cursor
                .getColumnIndex(StudentModel.KEY_EMAIL)));

        student.setGender(cursor.getString(cursor
                .getColumnIndex(StudentModel.KEY_GENDER)));

        student.setParent_mobile(cursor.getString(cursor
                .getColumnIndex(StudentModel.KEY_PARENT_MOBILE)));

        student.setParent_occupation(cursor.getString(cursor
                .getColumnIndex(StudentModel.KEY_PARENT_OCCUPATION)));

        student.setImage(cursor.getBlob(cursor
                .getColumnIndex(StudentModel.KEY_IMAGE)));

        return student;
    }

    public ArrayList<Student> getStudentList()
    {
        open();
        String selectQuery = "SELECT * FROM " + StudentModel.TABLE_STUDENTS;

//        selectQuery = selectQuery + " WHERE " + StudentModel.KEY_ROLL_NO + "=" + rollno;
//        selectQuery = selectQuery + " AND " + StudentModel.KEY_INCLUDE_IN_MENU + "= 1";
//        if(parentId>0)
//        {
//            selectQuery = selectQuery + " AND " + StudentModel.KEY_PARENT_ID + "=" + parentId;
//        }

//        selectQuery = selectQuery + " ORDER BY " + StudentModel.KEY_POSITION
//                + " COLLATE NOCASE ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Student> list = new ArrayList<Student>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Student student = cursorToStudent(cursor);
            list.add(student);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
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
        String rollno = cursor.getString(cursor.getColumnIndex(StudentModel.KEY_ROLL_NO));
        cursor.close();
        return rollno;
    }

}
