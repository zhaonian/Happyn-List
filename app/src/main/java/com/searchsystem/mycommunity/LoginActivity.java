package com.searchsystem.mycommunity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.searchsystem.mycommunity.ClientServerConnector;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLogInClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                login();
            }
        }).start();
    }

    protected void login() {
        ClientServerConnector clientServerConnector = new ClientServerConnector();

        Map<String, String> map = new HashMap<>();
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        map.put("email", email.getText().toString());
        map.put("password", password.getText().toString());

        JSONObject jsonObject = clientServerConnector.getJsonObject("LogIn", map);
        try {
            if ((boolean) jsonObject.get("loggedIn")) {
                Intent goToIntent = new Intent(this, EventsActivity.class);
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

    public void onSignUpClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                signup();
            }
        }).start();
    }

    protected void signup() {
        Intent goToIntent = new Intent(this, SignupActivity.class);
        startActivity(goToIntent);
    }
}
