package com.searchsystem.mycommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luan on 3/7/17.
 */

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onRegisterClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                register();
            }
        }).start();
    }

    protected void register() {
        ClientServerConnector clientServerConnector = new ClientServerConnector();

        Map<String, String> map = new HashMap<>();
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText firstName = (EditText) findViewById(R.id.firstName);
        EditText lastName = (EditText) findViewById(R.id.lastName);
        EditText address = (EditText) findViewById(R.id.address);

        map.put("email", email.getText().toString());
        map.put("password", password.getText().toString());
        map.put("firstName", firstName.getText().toString());
        map.put("lastName", lastName.getText().toString());
        map.put("address", address.getText().toString());

        JSONObject jsonObject = null;

        if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
            jsonObject = clientServerConnector.getJsonObject("Register", map);
        } else {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Email/Password cannot be empty", Toast.LENGTH_LONG).show();
                }
            });
        }

        try {
            if ((Integer) jsonObject.get("register") != 0) {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                    }
                });
                Intent goToIntent = new Intent(this, LoginActivity.class);
                startActivity(goToIntent);
            } else {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Invalid email/password", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
