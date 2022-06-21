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

public class DewateringSystemsFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView offLineRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_dewatering_systems, container, false );

        SQLiteOpenHelper dewateringDatabaseHelper =
                new ProductDatabaseHelper(getActivity());

        List<ProductInfo> listDewatering = new ArrayList<ProductInfo>();
        db = dewateringDatabaseHelper.getReadableDatabase();

        cursor = db.query("DEWATERING",
                new String[] {"NAME", "IMAGE_RESOURCE_ID"},
                null, null, null, null, null );
        while (cursor.moveToNext()) {
            ProductInfo productDewatering = new ProductInfo();
            productDewatering.setProductName(cursor.getString(cursor.getColumnIndex("NAME")));
            productDewatering.setProductImage(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
            listDewatering.add(productDewatering);
        }

        String[] productNames = new String[2];
        for ( int i = 0; i < productNames.length; i++) {
            productNames[i] = listDewatering.get(i).getProductName();
        }

        int[] productImages = new int[2];
        for ( int i = 0; i < productImages.length; i++) {
            productImages[i] = listDewatering.get(i).getProductImage();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(
                productNames, productImages);
        offLineRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        offLineRecycler.setLayoutManager(layoutManager);
        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, (int) position + 1);
                getActivity().startActivity(intent);
            }
        });

        return offLineRecycler;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}