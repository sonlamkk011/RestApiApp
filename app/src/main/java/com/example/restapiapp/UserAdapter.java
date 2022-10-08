package com.example.restapiapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private Activity activity;
    private List<User> users;

    public UserAdapter(Activity activity, List<User> users) {
        this.activity = activity;
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        view = layoutInflater.inflate(R.layout.user_item, parent, false);
        User user = users.get(position);
        TextView txtUsername = view.findViewById(R.id.txtUserName);
        txtUsername.setText(user.getFullName());
        return view;
    }
}
