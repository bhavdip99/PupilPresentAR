package com.bhavdip.pupilpresentar.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bhavdip.pupilpresentar.model.User;

import java.util.ArrayList;

public class UsersModel extends Model {
    private static final String TABLE_USERS = "pp_user";

    //Fields
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_FULLNAME = "full_name";
    private static final String KEY_MOBILE = "mobile";

    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_IMAGE = "image";

    private static final String CREATE_TABLE_USER = "create table if not exists "
            + TABLE_USERS + " ( "
            + KEY_USER_ID + " integer primary key autoincrement, "
            + KEY_USER_NAME + " text NOT NULL unique,"
            + KEY_USER_FULLNAME + " text,"
            + KEY_USER_EMAIL + " text NOT NULL,"
            + KEY_USER_PASSWORD + " text NOT NULL,"
            + KEY_MOBILE + " text NOT NULL,"
            + KEY_GENDER + " text,"
            + KEY_IMAGE + " BLOB);";

    public UsersModel(Context ctx) {
        super(ctx);
    }

    /**
     * Drop Requests table
     *
     * @param db SQLiteDatabase instance
     */
    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersModel.TABLE_USERS);
    }

    /**
     * Create the Requests table
     *
     * @param db SQLiteDatabase instance
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL(UsersModel.CREATE_TABLE_USER);
    }

    /**
     * Clears the tables in the db
     *
     * @param db
     */
    public static void clearTable(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + UsersModel.TABLE_USERS);
    }

    public boolean insertEntry(User user) {
        open();

        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(KEY_USER_FULLNAME, user.getFullName());
        newValues.put(KEY_USER_EMAIL, user.getEmail());
        newValues.put(KEY_GENDER, user.getGender());
        newValues.put(KEY_MOBILE, user.getMobile());
        newValues.put(KEY_USER_NAME, user.getUserName());
        newValues.put(KEY_USER_PASSWORD, user.getPassword());
//        newValues.put(KEY_IMAGE, img);

        // Insert the row into your table
        long rowInserted = db.insert(UsersModel.TABLE_USERS, null, newValues);
        if (rowInserted != -1) {
            return true;
        } else {
            return false;
        }
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();

//        user.setRollno(cursor.getString(cursor
//                .getColumnIndex(UsersModel.KEY_ROLL_NO)));

        user.setFullName(cursor.getString(cursor
                .getColumnIndex(UsersModel.KEY_USER_FULLNAME)));

//        user.setLastName(cursor.getString(cursor
//                .getColumnIndex(UsersModel.KEY_LAST_NAME)));

//        user.setEmail(cursor.getString(cursor
//                .getColumnIndex(UsersModel.KEY_EMAIL)));

        user.setGender(cursor.getString(cursor
                .getColumnIndex(UsersModel.KEY_GENDER)));

//        user.setParent_mobile(cursor.getString(cursor
//                .getColumnIndex(UsersModel.KEY_PARENT_MOBILE)));

//        user.setParent_occupation(cursor.getString(cursor
//                .getColumnIndex(UsersModel.KEY_PARENT_OCCUPATION)));

        user.setImage(cursor.getBlob(cursor
                .getColumnIndex(UsersModel.KEY_IMAGE)));

        return user;
    }

    public ArrayList<User> getUserList() {
        open();
        String selectQuery = "SELECT * FROM " + UsersModel.TABLE_USERS;

//        selectQuery = selectQuery + " WHERE " + UserModel.KEY_ROLL_NO + "=" + rollno;
//        selectQuery = selectQuery + " AND " + UserModel.KEY_INCLUDE_IN_MENU + "= 1";
//        if(parentId>0)
//        {
//            selectQuery = selectQuery + " AND " + UserModel.KEY_PARENT_ID + "=" + parentId;
//        }

//        selectQuery = selectQuery + " ORDER BY " + UserModel.KEY_POSITION
//                + " COLLATE NOCASE ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<User> list = new ArrayList<User>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            list.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public User getSelectedEntry(String username) {
        open();
        User user = new User();
        try {

            Cursor cursor = db.query(UsersModel.TABLE_USERS, new String[]{KEY_USER_FULLNAME,KEY_MOBILE,KEY_USER_NAME,KEY_GENDER, KEY_USER_EMAIL, KEY_USER_PASSWORD,KEY_IMAGE},
                    KEY_USER_NAME + "=?", new String[]{username}, null, null, null, null);
            if (cursor.getCount() < 1) // UserName Not Exist
            {
                cursor.close();
                return user;
            }
            cursor.moveToFirst();
            user.setFullName(cursor.getString(cursor.getColumnIndex(UsersModel.KEY_USER_FULLNAME)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UsersModel.KEY_MOBILE)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UsersModel.KEY_USER_NAME)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UsersModel.KEY_GENDER)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(UsersModel.KEY_USER_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UsersModel.KEY_USER_PASSWORD)));
            user.setImage(cursor.getBlob(cursor.getColumnIndex(UsersModel.KEY_IMAGE)));
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }
}
