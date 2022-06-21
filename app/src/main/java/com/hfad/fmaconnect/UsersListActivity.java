package com.hfad.fmaconnect;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hfad.fmaconnect.database.UserDatabaseHelper;
import com.hfad.fmaconnect.profile.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User>listUsers;
    private UserRecyclerAdapter userRecyclerAdapter;
    private UserDatabaseHelper userDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        initViews();
        initObjects();
    }

    /**
     * Method to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }

    /**
     * Method to initialize object to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        userRecyclerAdapter = new UserRecyclerAdapter(listUsers);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);

        recyclerViewUsers.setAdapter(userRecyclerAdapter);
        userDatabaseHelper = new UserDatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }

    /**
     * Method to fetch all user records from SQLite
     */
    private void getDataFromSQLite(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void...params){
                listUsers.clear();
                listUsers.addAll(userDatabaseHelper.getAllUser());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                userRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}