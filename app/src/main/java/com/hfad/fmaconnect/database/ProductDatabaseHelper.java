package com.hfad.fmaconnect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfad.fmaconnect.R;

public class ProductDatabaseHelper extends SQLiteOpenHelper{

    //Database version
    private static final int DB_VERSION = 2;

    //Database name
    private static final String DB_NAME = "productInfo";

    //Database table name
    private static final String TABLE1 = "PRODUCT_INFO";
    private static final String TABLE2 = "OFF_LINE";
    private static final String TABLE3 = "SERVICE_UNITS";
    private static final String TABLE4 = "DEWATERING";

    /**
     * Constructor
     *
     * @param context
     */
    public ProductDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabaseProductInfo(db, 0, DB_VERSION);
        updateMyDatabaseOffLine(db, 0, DB_VERSION);
        updateMyDatabaseServiceUnits(db, 0, DB_VERSION);
        updateMyDatabaseDewatering(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabaseProductInfo(db, oldVersion, newVersion);
        updateMyDatabaseOffLine(db, oldVersion, newVersion);
        updateMyDatabaseServiceUnits(db, oldVersion, newVersion);
        updateMyDatabaseDewatering(db, oldVersion, newVersion);

    }

    private static void insertProductInfo(SQLiteDatabase db, String name, int resourceID) {
        ContentValues productValues = new ContentValues();
        productValues.put("NAME", name);
        productValues.put("IMAGE_RESOURCE_ID", resourceID);
        db.insert("PRODUCT_INFO", null, productValues);

    }

    private static void insertOffLine(SQLiteDatabase db, String name, int resourceID) {
        ContentValues productValues = new ContentValues();
        productValues.put("NAME", name);
        productValues.put("IMAGE_RESOURCE_ID", resourceID);
        db.insert("OFF_LINE", null, productValues);

    }

    private static void insertServiceUnits(SQLiteDatabase db, String name, int resourceID) {
        ContentValues productValues = new ContentValues();
        productValues.put("NAME", name);
        productValues.put("IMAGE_RESOURCE_ID", resourceID);
        db.insert("SERVICE_UNITS", null, productValues);

    }

    private static void insertDewatering(SQLiteDatabase db, String name, int resourceID) {
        ContentValues productValues = new ContentValues();
        productValues.put("NAME", name);
        productValues.put("IMAGE_RESOURCE_ID", resourceID);
        db.insert("DEWATERING", null, productValues);

    }

    private void updateMyDatabaseProductInfo(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1) {
            db.execSQL(" CREATE TABLE " + TABLE1 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TEXT, "
                    + " IMAGE_RESOURCE_ID INTEGER); ");

            insertProductInfo(db, "Off-line Filters", R.drawable.offline_icon);
            insertProductInfo(db, "Oil Service Units", R.drawable.oilservice_icon);
            insertProductInfo(db, "Dewatering Systems", R.drawable.dewatering_icon);
        }

    }

    private void updateMyDatabaseOffLine(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1) {
            db.execSQL(" CREATE TABLE " + TABLE2 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TEXT, "
                    + " IMAGE_RESOURCE_ID INTEGER); ");

            insertOffLine(db, "FN1 040", R.drawable.fn1_040);
            insertOffLine(db, "FN 060", R.drawable.fn_060);
            insertOffLine(db, "FN 300", R.drawable.fn_300);
            insertOffLine(db, "FNS 040", R.drawable.fns_040);
            insertOffLine(db, "FNS 060", R.drawable.fn_060);
            insertOffLine(db, "FNA 014", R.drawable.fna_014);
            insertOffLine(db, "FNA1 008", R.drawable.fna1_008);
            insertOffLine(db, "FNA1 016", R.drawable.fna1_016);
            insertOffLine(db, "FN1HV 008", R.drawable.fna1hv_008);
            insertOffLine(db, "FN1HV 016", R.drawable.fna1_016);
            insertOffLine(db, "FNA 040-553", R.drawable.fna_040);
            insertOffLine(db, "FNA 045", R.drawable.fna_045);
            insertOffLine(db, "FNAPC1 045", R.drawable.fnapc_045);
            insertOffLine(db, "FNAPC1 016", R.drawable.fnapc1_016);
            insertOffLine(db, "FNU 008", R.drawable.fnu_008);
        }


    }

    private void updateMyDatabaseServiceUnits(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1) {
            db.execSQL(" CREATE TABLE " + TABLE3 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TEXT, "
                    + " IMAGE_RESOURCE_ID INTEGER); ");

            insertServiceUnits(db, "CFP 03", R.drawable.cfp_003);
            insertServiceUnits(db, "FA1 / FA1HV 008", R.drawable.fa1_fa1hv_008);
            insertServiceUnits(db, "FA 016 / FAPC 016", R.drawable.fa_016_fapc_016);
            insertServiceUnits(db, "UM 045 / UMPC 045", R.drawable.um_045_umpc_045);
            insertServiceUnits(db, "UMPCL 045 Lightline", R.drawable.umpcl_045_lightline);
            insertServiceUnits(db, "FA 003-2341", R.drawable.fa_003_2341);
            insertServiceUnits(db, "FA 016-1160", R.drawable.fa_016_1160);
            insertServiceUnits(db, "Set FA 016.1775", R.drawable.fa_016_1775);
            insertServiceUnits(db, "Set FNA 008.1700", R.drawable.fna_008_fna016);
        }
  }

    private void updateMyDatabaseDewatering(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1) {
            db.execSQL(" CREATE TABLE " + TABLE4 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " NAME TEXT, "
                    + " IMAGE_RESOURCE_ID INTEGER); ");

            insertDewatering(db, "OPS 010 / OPS550", R.drawable.ops_010_ops_550);
            insertDewatering(db, "EXAPOR AQUA", R.drawable.exapor_aqua);
        }

    }

}
