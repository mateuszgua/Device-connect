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

public class OffLineFiltersFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView offLineRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_off_line_filters, container, false );

        SQLiteOpenHelper offLineDatabaseHelper =
                new ProductDatabaseHelper(getActivity());

        List<ProductInfo> listOffLine = new ArrayList<ProductInfo>();
        db = offLineDatabaseHelper.getReadableDatabase();

        cursor = db.query("OFF_LINE",
                new String[] {"NAME", "IMAGE_RESOURCE_ID"},
                null, null, null, null, null );
        while (cursor.moveToNext()) {
            ProductInfo productOffLine = new ProductInfo();
            productOffLine.setProductName(cursor.getString(cursor.getColumnIndex("NAME")));
            productOffLine.setProductImage(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
            listOffLine.add(productOffLine);
        }

        String[] productNames = new String[15];
        for ( int i = 0; i < productNames.length; i++) {
            productNames[i] = listOffLine.get(i).getProductName();
        }

        int[] productImages = new int[15];
        for ( int i = 0; i < productImages.length; i++) {
            productImages[i] = listOffLine.get(i).getProductImage();
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
