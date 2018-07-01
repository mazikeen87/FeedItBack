package com.sanprishali.feeditback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin,buttonSignup;
    LinearLayout loginLayout,signupLayout;
    TextView loginTextViewHint,signupTextViewHint;
    Spinner spinnerDesignation;
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
        spinnerDesignation = (Spinner)findViewById(R.id.spinnerDesgination);

        String[] designation = new String[]{
                "Designation",
                "HR",
                "Food"
        };
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_layout,designation
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerDesignation.setAdapter(spinnerArrayAdapter);

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
