package com.sanprishali.feeditback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin,buttonSignup;
    LinearLayout loginLayout,signupLayout;
    TextView loginTextViewHint,signupTextViewHint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonSignup = (Button)findViewById(R.id.buttonSignup);
        loginLayout = (LinearLayout)findViewById(R.id.loginLayout);
        signupLayout = (LinearLayout)findViewById(R.id.signupLayout);
        signupTextViewHint = (TextView)findViewById(R.id.signupTextViewHint);
        loginTextViewHint = (TextView)findViewById(R.id.loginTextViewHint);

        signupTextViewHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.INVISIBLE);
                signupLayout.setVisibility(View.VISIBLE);
            }
        });

        loginTextViewHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.VISIBLE);
                signupLayout.setVisibility(View.INVISIBLE);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login activity
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signup activity
            }
        });
    }
}
