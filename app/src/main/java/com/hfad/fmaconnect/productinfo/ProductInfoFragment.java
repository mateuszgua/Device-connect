package com.hfad.fmaconnect.productinfo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.fmaconnect.R;
import com.hfad.fmaconnect.database.ProductDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductInfoFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView productInfoRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_product_info, container, false );

        SQLiteOpenHelper productInfoDatabaseHelper =
                new ProductDatabaseHelper(getActivity());

        List<ProductInfo> listProductInfo = new ArrayList<ProductInfo>();
        db = productInfoDatabaseHelper.getReadableDatabase();

        cursor = db.query("PRODUCT_INFO",
                new String[] {"NAME", "IMAGE_RESOURCE_ID"},
                null, null, null, null, null );
        while (cursor.moveToNext()) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductName(cursor.getString(cursor.getColumnIndex("NAME")));
            productInfo.setProductImage(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
            listProductInfo.add(productInfo);
        }

        String[] productNames = new String[3];
        for ( int i = 0; i < productNames.length; i++) {
            productNames[i] = listProductInfo.get(i).getProductName();
        }

        int[] productImages = new int[3];
        for ( int i = 0; i < productImages.length; i++) {
            productImages[i] = listProductInfo.get(i).getProductImage();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(
                productNames, productImages);
        productInfoRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        productInfoRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Fragment fragment = null;

                switch (position) {

                    case 0:
                        fragment = new OffLineFiltersFragment();
                        break;

                    case 1:
                        fragment = new OilServiceUnitsFragment();
                        break;

                    case 2:
                        fragment = new DewateringSystemsFragment();
                        break;

                    default:
                        fragment = new ProductInfoFragment();
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    //ft.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame));
                    //ft.add(R.id.content_frame, fragment, null);
                    //ft.show(fragment);
                    ft.setReorderingAllowed(true);
                    ft.addToBackStack("replacement");
                    ft.commit();

                }
            }

        });
        return productInfoRecycler;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

}
