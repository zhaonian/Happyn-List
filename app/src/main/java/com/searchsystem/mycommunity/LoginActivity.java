package com.searchsystem.mycommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (AccessToken.getCurrentAccessToken() != null) {
            Intent eventIntent = new Intent(getApplicationContext(), FBEventsActivity.class);
            eventIntent.putExtra("accessToken", AccessToken.getCurrentAccessToken().getToken());
            startActivity(eventIntent);
        } else {
            loginButton = (LoginButton) findViewById(R.id.fb_login_button);
            callbackManager = CallbackManager.Factory.create();
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    System.out.println("success");
                    // accessToken
                    Intent eventIntent = new Intent(getApplicationContext(), FBEventsActivity.class);
                    String accessToken = loginResult.getAccessToken().getToken();
                    eventIntent.putExtra("accessToken", accessToken);
                    // make a toast here
//                    ((Activity) getApplicationContext()).runOnUiThread(new Runnable() {
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "Successfully logged in with FB", Toast.LENGTH_LONG).show();
//                        }
//                    });
                    startActivity(eventIntent);
                }

                @Override
                public void onCancel() {
                    System.out.println("cancelled");
                    // make a toast here

                }

                @Override
                public void onError(FacebookException error) {
                    System.out.println("failure" + error);
                    // make a toast here
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

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
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_LONG).show();
                    }
                });
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
