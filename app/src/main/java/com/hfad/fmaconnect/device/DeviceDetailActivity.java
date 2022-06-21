package com.hfad.fmaconnect.device;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.fmaconnect.R;
import com.hfad.fmaconnect.database.DeviceDatabaseHelper;

public class DeviceDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DEVICE_ID = "deviceId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Show information about product
        int deviceId = (Integer) getIntent().getExtras().get(EXTRA_DEVICE_ID);

        //Create cursor
        try {
            SQLiteOpenHelper deviceInfoDatabaseHelper =
                    new DeviceDatabaseHelper(this);
            SQLiteDatabase db = deviceInfoDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DEVICE",
                    new String[]{"NAME", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{Integer.toString(deviceId)},
                    null, null, null);
            //Move to first row in the cursor
            if (cursor.moveToFirst()) {

                //Take information about product
                String nameText = cursor.getString(0);
                int photoId = cursor.getInt(1);

                //Show name
                TextView name = (TextView) findViewById(R.id.device_text);
                name.setText(nameText);

                //Show photo
                ImageView photo = (ImageView) findViewById(R.id.device_image);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
            }
            cursor.close();
            db.close();
        } catch (
                SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database is unavailable!",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }

}