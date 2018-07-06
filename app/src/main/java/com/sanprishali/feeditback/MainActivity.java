package com.sanprishali.feeditback;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin,buttonSignup;
    LinearLayout loginLayout,signupLayout;
    TextView loginTextViewHint,signupTextViewHint,anonymousTextViewHint;
    EditText editTextemail,editTextpassword,editTextname,editTextemail1,editTextpassword1,editTextpassword2;
    Spinner spinnerDesignation;
    String designation1,name1,email1;
    private DatabaseReference mDatabase;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    DataSnapshot snapshot;
    int loginType = 0;

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
        anonymousTextViewHint = (TextView)findViewById(R.id.anonymousFeedback);
        spinnerDesignation = (Spinner)findViewById(R.id.spinnerDesgination);

        editTextemail = (EditText)findViewById(R.id.editTextEmail);
        editTextpassword = (EditText)findViewById(R.id.editTextPassword);
        editTextname = (EditText)findViewById(R.id.editTextName);
        editTextemail1 = (EditText)findViewById(R.id.editTextEmail1);
        editTextpassword1 = (EditText)findViewById(R.id.editTextPassword1);
        editTextpassword2 = (EditText)findViewById(R.id.editTextPassword2);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                snapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        HashMap<String,String> fake = new HashMap<>();
        fake.put("Fakeid","FakeData");
        mDatabase.child("user").child("fake").setValue(fake);

        String[] designation = new String[]{
                "",
                "HR",
                "CEO",
                "CTO",
                "Manager",
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

        anonymousTextViewHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginType = 2;
                onSuccessfulValidation();
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

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateDuplicateEmail(String email) {
        for (DataSnapshot datasnapshot:snapshot.getChildren()) {
            if(datasnapshot.getKey().toString().equals(email)){
                return true;
            }
        }
        return false;
    }

    public int validateLogin(String email,String password) {
        int pos = email.indexOf("@");
        String email_ = email.substring(0,pos);
        for (DataSnapshot datasnapshot:snapshot.getChildren()) {
            if(datasnapshot.getKey().toString().equals(email_)){
                if (datasnapshot.child("password").getValue().toString().equals(password)){
                    name1 = datasnapshot.child("name").getValue().toString();
                    email1 = datasnapshot.child("email").getValue().toString();
                    designation1 = datasnapshot.child("designation").getValue().toString();
                    return 1;
                }else{
                    return 2;
                }
            }
        }
        return 0;
    }

    public void loginActivity(){
        if(!validateEmail(editTextemail.getText().toString())){
            Toast.makeText(this, "Invalid Email id", Toast.LENGTH_SHORT).show();
        }else{
            if (editTextpassword.getText().toString().length()==0){
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            }else{
                int result = validateLogin(editTextemail.getText().toString(),editTextpassword.getText().toString());
                switch(result){
                    case 0:
                        Toast.makeText(this, "Email id does not exist", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        loginType = 1;
                        onSuccessfulValidation();
                        break;
                    case 2:
                        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    public void signupActivity(){
        if(editTextname.getText().toString().length()==0){
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        }else{
            if(!validateEmail(editTextemail1.getText().toString())){
                Toast.makeText(this, "Invalid Email id", Toast.LENGTH_SHORT).show();
            }else{
                if(designation1.length()==0){
                    Toast.makeText(this, "Choose a designation", Toast.LENGTH_SHORT).show();
                }else {
                    if (editTextpassword1.getText().toString().length() == 0 && editTextpassword2.getText().toString().length() == 0) {
                        Toast.makeText(this, "Passwords cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        if(editTextpassword1.getText().toString().equals(editTextpassword2.getText().toString())) {
                            sendSignupData(editTextname.getText().toString(),editTextemail1.getText().toString(),designation1,editTextpassword1.getText().toString());
                        }else{
                            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    public void sendSignupData(String name,String email,String designation,String password){
        name1 = name;
        email1 = email;
        int pos = email.indexOf("@");
        String email_ = email.substring(0,pos);
        if(validateDuplicateEmail(email_)){
            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, String> data = new HashMap<>();
            data.put("name", name);
            data.put("email", email);
            data.put("designation", designation);
            data.put("password", password);
            mDatabase.child("user").child(email_).setValue(data);
            loginType = 1;
            onSuccessfulValidation();
        }
    }

    public void onSuccessfulValidation(){
        final Intent mainIntent = new Intent(MainActivity.this, Feedback.class);
        Bundle bundle = new Bundle();
        bundle.putInt("loginType",loginType);
        bundle.putString("designation",designation1);
        bundle.putString("name",name1);
        bundle.putString("email",email1);
        mainIntent.putExtras(bundle);
        if (!MainActivity.this.isFinishing()) {
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }
    }
}
