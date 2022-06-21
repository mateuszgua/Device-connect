package com.hfad.fmaconnect.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfad.fmaconnect.device.Device;
import com.hfad.fmaconnect.R;

import java.util.ArrayList;
import java.util.List;

public class DeviceDatabaseHelper extends SQLiteOpenHelper{

    //Database version
    private static final int DB_VERSION = 1;

    //Database name
    private static final String DB_NAME = "deviceManager";

    //Database table name
    private static final String TABLE1 = "DEVICE";

    //Device table column name
    private static final String COLUMN_DEVICE_ID = "_id";
    private static final String COLUMN_DEVICE_NAME = "NAME";
    private static final String COLUMN_DEVICE_IMAGE = "IMAGE_RESOURCE_ID";

    /**
     * Constructior
     *
     * @param context
     */
    public DeviceDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabaseDeviceInfo(db, 0, DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabaseDeviceInfo(db, oldVersion, newVersion);

    }

    private static void insertInfo(SQLiteDatabase db, String name, int resourceID) {
        ContentValues deviceValues = new ContentValues();
        deviceValues.put(COLUMN_DEVICE_NAME, name);
        deviceValues.put(COLUMN_DEVICE_IMAGE, resourceID);
        db.insert( TABLE1, null, deviceValues);

    }

    private void updateMyDatabaseDeviceInfo(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1) {
            db.execSQL(" CREATE TABLE " + TABLE1
                    + "(" + COLUMN_DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TEXT, "
                    + " IMAGE_RESOURCE_ID INTEGER); ");

            insertInfo(db, "Test Device 1", R.drawable.fa_016_fapc_016);
        }

    }

    /**
     * This method is for create device record
     *
     * @param device
     */
    public void addDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DEVICE_NAME, device.getName());

        //Interesting row
        db.insert(TABLE1, null, values);
        db.close();
    }

    /**
     * This method is fentch all device and return the list of used device
     *
     * @return
     */
    @SuppressLint("Range")
    public List<Device> getAllDevices() {
        //Array to columns for fetch
        String[] columns = {
                COLUMN_DEVICE_ID,
                COLUMN_DEVICE_NAME,
                COLUMN_DEVICE_IMAGE
        };

        //Sorting orders
        String sortOrder =
                COLUMN_DEVICE_NAME + " ASC";
        List<Device> device_List = new ArrayList<Device>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Query the device table
        /**
         * Here query function is used to fetch records from device table
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
                Device device = new Device();
                device.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_ID))));
                device.setName(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NAME)));
                //Adding device record to list
                device_List.add(device);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        //Return device list
        return device_List;

    }

    /**
     * This method is update device record
     * @param device
     */
    public void updateDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DEVICE_NAME, device.getName());

        //Updating row
        db.update(TABLE1, values, COLUMN_DEVICE_ID + " = ? ",
                new String[]{String.valueOf(device.getId())});
        db.close();
    }

    /**
     * This method is delete device record
     * @param device
     */
    public void deleteDevice(Device device){
        SQLiteDatabase db = this.getWritableDatabase();

        //Delete device record by ID
        db.delete(TABLE1, COLUMN_DEVICE_ID + " = ? ",
                new String[]{String.valueOf(device.getId())});
        db.close();
    }

    /**
     * Check if device exist or not
     * @param name
     * @return true/false
     */
    public boolean checkDevice(String name) {
        //Array for columns to fetch
        String[] columns = {COLUMN_DEVICE_ID};

        SQLiteDatabase db = this.getReadableDatabase();

        //Select criteria
        String selection = COLUMN_DEVICE_NAME + " = ? ";

        //Selection argument
        String[] selectionArgs = {name};

        //Query device table with condition
        /**
         *Here query function is used to fetch records from device table
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

}

