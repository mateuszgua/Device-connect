package com.hfad.fmaconnect.device;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hfad.fmaconnect.R;

public class AddDeviceActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final AppCompatActivity activity = AddDeviceActivity.this;
    private AppCompatButton appCompactButtonAddManual;
    private AppCompatButton appCompactButtonAddBluetooth;
    private AppCompatButton appCompactButtonAddQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();

    }

    /**
     * Initialize views
     */
    private void initViews(){
        appCompactButtonAddManual =
                (AppCompatButton) findViewById(R.id.appCompactButtonAddManual);
        appCompactButtonAddBluetooth =
                (AppCompatButton) findViewById(R.id.appCompactButtonAddBluetooth);
        appCompactButtonAddQRCode =
                (AppCompatButton) findViewById(R.id.appCompactButtonAddQRCode);
    }

    /**
     * Initialize listeners
     */
    private void initListeners(){
        appCompactButtonAddManual.setOnClickListener(this);
        appCompactButtonAddBluetooth.setOnClickListener(this);
        appCompactButtonAddQRCode.setOnClickListener(this);

    }

    /**
     * Method to listen the click on view
     * @param v
     */
    @Override
    public void onClick(View v){
        Intent intent = null;

        switch (v.getId()) {
            case R.id.appCompactButtonAddManual:
                intent = new Intent(this, AddDeviceManualActivity.class);
                break;

        /*    case R.id.appCompactButtonAddBluetooth:
                intent = new Intent(this, AddDeviceManualActivity.class);
                break;

            case R.id.appCompactButtonAddQRCode:
                intent = new Intent(this, AddDeviceManualActivity.class);
                break;

         */
        }

        startActivity(intent);
    }

}