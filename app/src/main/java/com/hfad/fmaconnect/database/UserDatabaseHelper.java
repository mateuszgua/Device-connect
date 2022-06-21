package com.hfad.fmaconnect.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfad.fmaconnect.profile.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper{

    //Database version
    private static final int DB_VERSION = 1;

    //Database name
    private static final String DB_NAME = "userManager";

    //Database table name
    private static final String TABLE1 = "USER";

    //User table column name
    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_USER_NAME = "NAME";
    private static final String COLUMN_USER_EMAIL = "EMAIL";
    private static final String COLUMN_USER_PASSWORD = "PASSWORD";

    /**
     * Constructior
     *
     * @param context
     */
    public UserDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabaseUserInfo(db, 0, DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabaseUserInfo(db, oldVersion, newVersion);

    }

    private static void insertInfo(SQLiteDatabase db, String name, String email, String password) {
        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_USER_NAME, name);
        userValues.put(COLUMN_USER_EMAIL, email);
        userValues.put(COLUMN_USER_PASSWORD, password);
        db.insert( TABLE1, null, userValues);

    }

    private void updateMyDatabaseUserInfo(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1) {
            db.execSQL(" CREATE TABLE " + TABLE1
                    + "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TEXT, "
                    + " EMAIL TEXT, "
                    + " PASSWORD TEXT); ");

            insertInfo(db, "Mateusz", "admin@gmail.com", "admin");
        }

    }

    /**
     * This method is for create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        //Interesting row
        db.insert(TABLE1, null, values);
        db.close();
    }

    /**
     * This method is fentch all user and return the list of used users
     *
     * @return
     */
    @SuppressLint("Range")
    public List<User> getAllUser() {
        //Array to columns for fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };

        //Sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> user_List = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Query the user table
        /**
         * Here query function is used to fetch records from user table
         * this function works like we use sql query.
         *
         */
        Cursor cursor = db.query(TABLE1, //Table name
                columns,                    //Columns to return
                null,               //Columns to the WHERE clause
                null,            //The values for the WHERE clause
                null,               //Group the rows
                null,                //Filter by the rows
                sortOrder);                 //The sort order

        //Traversing through all rows adding to list
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                //Adding user record to list
                user_List.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        //Return user list
        return user_List;

    }

    /**
     * This method is update user record
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        //Updating row
        db.update(TABLE1, values, COLUMN_USER_ID + " = ? ",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is delete user record
     * @param user
     */
    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        //Delete user record by ID
        db.delete(TABLE1, COLUMN_USER_ID + " = ? ",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Check if user exist or not
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        //Array for columns to fetch
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();

        //Select criteria
        String selection = COLUMN_USER_EMAIL + " = ? ";

        //Selection argument
        String[] selectionArgs = {email};

        //Query user table with condition
        /**
         *Here query function is used to fetch records from user table
         *this function works like we use sql query.
         *
         */
        Cursor cursor = db.query(TABLE1, //Table to query
                columns,                    //Columns to return
                selection,                  //Columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,               //Group the rows
                null,               //Filter by the rows groups
                null);              //The sort order

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;

    }

    /**
     * This methor is check that user is exist or not
     * @param email
     * @param password
     * @return
     */
    public boolean checkUser(String email, String password) {
        //Array of columns to fetch
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();

        //Selection criteria
        String selection = COLUMN_USER_EMAIL + " = ? " + " AND " + COLUMN_USER_PASSWORD + " = ? ";

        //Selection arguments
        String[] selectionArgs = {email, password};

        //Query user table for conditions
        /**
         * Here query function is used to fetch records from user table
         * this function works like we use sql query.
         */
        Cursor cursor = db.query(TABLE1, //Table to query
                columns,                    //Columns to return
                selection,                  //Columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,               //Group the rows
                null,               //Filter by the groups
                null);              //The sort order

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;

    }

}
