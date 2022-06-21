package com.hfad.fmaconnect.productinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.fmaconnect.R;
import com.hfad.fmaconnect.database.DeviceDatabaseHelper;
import com.hfad.fmaconnect.device.Device;
import com.hfad.fmaconnect.device.DeviceDetailActivity;
import com.hfad.fmaconnect.productinfo.CaptionedImagesAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserDeviceListFragment extends Fragment {


    private SQLiteDatabase db;
    private Cursor cursor;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView deviceInfoRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_user_device_list, container, false );

        SQLiteOpenHelper deviceInfoDatabaseHelper =
                new DeviceDatabaseHelper(getActivity());

        List<Device> listDeviceInfo = new ArrayList<Device>();
        db = deviceInfoDatabaseHelper.getReadableDatabase();

        cursor = db.query("DEVICE",
                new String[] {"NAME", "IMAGE_RESOURCE_ID"},
                null, null, null, null, null );
        while (cursor.moveToNext()) {
            Device deviceInfo = new Device();
            deviceInfo.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            deviceInfo.setDeviceImage(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
            listDeviceInfo.add(deviceInfo);
        }

        String[] deviceNames = new String[1];
        for ( int i = 0; i < deviceNames.length; i++) {
            deviceNames[i] = listDeviceInfo.get(i).getName();
        }

        int[] deviceImages = new int[1];
        for ( int i = 0; i < deviceImages.length; i++) {
            deviceImages[i] = listDeviceInfo.get(i).getDeviceImage();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(
                deviceNames, deviceImages);
        deviceInfoRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        deviceInfoRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DeviceDetailActivity.class);
                intent.putExtra(DeviceDetailActivity.EXTRA_DEVICE_ID, position + 1);
                getActivity().startActivity(intent);

            }

        });
        return deviceInfoRecycler;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

}