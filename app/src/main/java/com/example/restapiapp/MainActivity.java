package com.example.restapiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnSubmit;
    private Button btnEdit;
    private Button btnDelete;
    private List<User> users;
    private ArrayAdapter<User> arrayAdapter;
    private long itemId;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        users = new ArrayList<>();
//        arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
        userAdapter = new UserAdapter(this, users);
        ListView listUser = findViewById(R.id.listUser);
        listUser.setAdapter(userAdapter);
        getListDataUser();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hello click", "Hello");
                User newUser = new User();
                newUser.setFullName(txtName.getText().toString());
                newUser.setEmail(txtEmail.getText().toString());
                newUser.setPassword(txtPassword.getText().toString());
                saveUser(newUser);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User();
                newUser.setFullName(txtName.getText().toString());
                newUser.setEmail(txtEmail.getText().toString());
                newUser.setPassword(txtPassword.getText().toString());
                update(itemId, newUser);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(itemId);
            }
        });
        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 User user = arrayAdapter.getItem(position);
                 itemId = user.getId();
                getUserById(user.getId());
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.btnSubmit) {
            Log.d("Hello click", "Hello");
            User newUser = new User();
            newUser.setFullName(txtName.getText().toString());
            newUser.setEmail(txtEmail.getText().toString());
            newUser.setPassword(txtPassword.getText().toString());
            saveUser(newUser);
//        }
    }

    private void getListDataUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUserManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUserManager service = retrofit.create(APIUserManager.class);
        service.getListData().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> list = response.body();
                Log.d("Response", String.valueOf(list));
                if(list != null) {
//                    arrayAdapter.clear();
//                    arrayAdapter.addAll(list);
//                    arrayAdapter.notifyDataSetChanged();
                    userAdapter.setUsers(list);
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
               Log.d("error 3333333", t.getMessage());
            }
        });
    }

    public void getUserById(long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUserManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUserManager service = retrofit.create(APIUserManager.class);
        service.findById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d("user", user.toString());
                txtName.setText(user.getFullName());
                txtEmail.setText(user.getEmail());
                Log.d("User", user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Error message", t.getMessage());
            }
        });
    }

    public void saveUser(User newUser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUserManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUserManager service = retrofit.create(APIUserManager.class);
        service.save(newUser).enqueue(new Callback<User>() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("success update", response.body().toString());
                if(response.code() == 200) {
                    User user = response.body();
                    arrayAdapter.addAll();
                    arrayAdapter.notifyDataSetChanged();
                    Log.d("User", user.toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Error message", t.getMessage());
            }
        });
    }

    public void update(long id, User newUser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUserManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUserManager service = retrofit.create(APIUserManager.class);
        service.update(id, newUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("success update", response.body().toString());
                if(response.code() == 200) {
                    User user = response.body();
                    getListDataUser();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Error message", t.getMessage());
            }
        });
    }

    public void delete(long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUserManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIUserManager service = retrofit.create(APIUserManager.class);
        service.delete(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("Delete success", response.body().toString());
                if(response.code() == 200) {
                    getListDataUser();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Error message", t.getMessage());
            }
        });
    }
}