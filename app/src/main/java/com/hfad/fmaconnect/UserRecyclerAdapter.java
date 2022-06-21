package com.hfad.fmaconnect;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.fmaconnect.profile.User;
import java.util.List;

public class UserRecyclerAdapter extends
        RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> {


    private List<User>listUsers;

    public UserRecyclerAdapter(List<User>listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_user_recycler, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listUsers.get(position).getName());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        Log.v(UserRecyclerAdapter.class.getSimpleName(), ""+listUsers.size());
        return listUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;

        public UserViewHolder(View v) {
            super(v);
            textViewName = (AppCompatTextView) v.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) v.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) v.findViewById(R.id.textViewPassword);
        }

    }

}
