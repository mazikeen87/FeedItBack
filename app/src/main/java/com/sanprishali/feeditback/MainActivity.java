package com.sanprishali.feeditback;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin,buttonSignup;
    LinearLayout loginLayout,signupLayout;
    TextView loginTextViewHint,signupTextViewHint;
    TextInputEditText editTextemail,editTextpassword,editTextname,editTextemail1,editTextpassword1,editTextpassword2;
    Spinner spinnerDesignation;
    String designation1;
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
        editTextemail = (TextInputEditText)findViewById(R.id.editTextEmail);
        editTextpassword = (TextInputEditText)findViewById(R.id.editTextPassword);
        editTextname = (TextInputEditText)findViewById(R.id.editTextName);
        editTextemail1 = (TextInputEditText)findViewById(R.id.editTextEmail1);
        editTextpassword1 = (TextInputEditText)findViewById(R.id.editTextPassword1);
        editTextpassword2 = (TextInputEditText)findViewById(R.id.editTextPassword2);

        String email = editTextemail.getText().toString();
        String password = editTextpassword.getText().toString();
        String name = editTextname.getText().toString();
        String email1 = editTextemail1.getText().toString();
        String password1 = editTextpassword1.getText().toString();
        String password2 = editTextpassword2.getText().toString();

        String[] designation = new String[]{
                "",
                "HR",
                "Food",
                "Employee"
        };
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_layout,designation
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerDesignation.setAdapter(spinnerArrayAdapter);
        spinnerDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                designation1 = spinnerDesignation.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

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
                loginActivity();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupActivity();
            }
        });
    }

    public void loginActivity(){
        //
    }

    public void signupActivity(){
        //
    }
}
