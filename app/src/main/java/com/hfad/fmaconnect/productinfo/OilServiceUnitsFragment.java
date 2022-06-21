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
import com.hfad.fmaconnect.database.ProductDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class OilServiceUnitsFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView oilServiceRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_oil_service_units, container, false );

        SQLiteOpenHelper oilServiceDatabaseHelper =
                new ProductDatabaseHelper(getActivity());

        List<ProductInfo> listServiceUnit = new ArrayList<ProductInfo>();
        db = oilServiceDatabaseHelper.getReadableDatabase();

        cursor = db.query("SERVICE_UNITS",
                new String[] {"NAME", "IMAGE_RESOURCE_ID"},
                null, null, null, null, null );
        while (cursor.moveToNext()) {
            ProductInfo productOilService = new ProductInfo();
            productOilService.setProductName(cursor.getString(cursor.getColumnIndex("NAME")));
            productOilService.setProductImage(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
            listServiceUnit.add(productOilService);
        }

        String[] productNames = new String[9];
        for ( int i = 0; i < productNames.length; i++) {
            productNames[i] = listServiceUnit.get(i).getProductName();
        }

        int[] productImages = new int[9];
        for ( int i = 0; i < productImages.length; i++) {
            productImages[i] = listServiceUnit.get(i).getProductImage();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(
                productNames, productImages);
        oilServiceRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        oilServiceRecycler.setLayoutManager(layoutManager);
        adapter.setListener(new CaptionedImagesAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, (int) position + 1);
                getActivity().startActivity(intent);
            }
        });
        return oilServiceRecycler;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}