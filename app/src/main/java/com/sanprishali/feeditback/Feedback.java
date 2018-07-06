package com.sanprishali.feeditback;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Feedback extends AppCompatActivity {

    LinearLayout feedbackLayout,viewLayout,profileLayout;
    String userChoosenTask = null;
    SharedPreferences sp;
    ImageView photo;
    boolean result;
    final int REQUEST_CAMERA = 123;
    final int SELECT_FILE = 456;
    ImageButton selectPhoto;
    Button buttonEditDetails,buttonSaveEditDetails;
    EditText editTextProfileName,editTextProfileEmail,editTextProfileDesignation;
    int loginType;
    Button Submit;
    LinearLayout CategoryLayout,FeedbackLayout,RatingLayout;
    TextView RateText,Category;
    RatingBar RateIt;
    Spinner Spin;
    float rating;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String timeDate,category;
    String Comment,stringRating;
    EditText CommentBox;
    String designation,profileName,profileEmail;
    private DatabaseReference mDatabase;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feedback:
                    feedbackActivity();
                    return true;
                case R.id.navigation_view:
                    viewActivity();
                    return true;
                case R.id.navigation_profile:
                    profileActivity();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackLayout = (LinearLayout) findViewById(R.id.feedbackLayout);
        viewLayout = (LinearLayout) findViewById(R.id.viewLayout);
        profileLayout = (LinearLayout) findViewById(R.id.profileLayout);
        photo = (ImageView)findViewById(R.id.profilePhoto);
        selectPhoto = (ImageButton)findViewById(R.id.buttonSelectPhoto);
        buttonEditDetails = (Button)findViewById(R.id.buttonEditDetails);
        buttonSaveEditDetails = (Button)findViewById(R.id.buttonSaveEditDetails);
        editTextProfileName = (EditText)findViewById(R.id.editTextProfileName);
        editTextProfileEmail = (EditText)findViewById(R.id.editTextProfileEmail);
        editTextProfileDesignation = (EditText)findViewById(R.id.editTextProfileDesignation);
        RatingLayout = (LinearLayout)findViewById(R.id.commentLayout);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        loginType = bundle.getInt("loginType");
        designation = bundle.getString("designation");
        profileEmail = bundle.getString("email");
        profileName = bundle.getString("name");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Submit = (Button)findViewById(R.id.submitButton);
        CategoryLayout = (LinearLayout)findViewById(R.id.CategoryLayout);
        FeedbackLayout = (LinearLayout)findViewById(R.id.feedbackLayout);
        Spin = (Spinner) findViewById(R.id.Spin);
        RateIt = (RatingBar) findViewById(R.id.RateIt);
        RateText = (TextView) findViewById(R.id.RateText);
        Category = (TextView)findViewById(R.id.Category);
        CommentBox = (EditText) findViewById(R.id.editTextFeedback);

        String Comment = CommentBox.getText().toString();
        final String[] category = new String[]{
                "",
                "HR",
                "Manager",
                "Food",
                "Admin",
                "Security",
                "Others"
        };
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,category);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
        Spin.setAdapter(spinnerArrayAdapter);
        Spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    RatingLayout.setVisibility(View.VISIBLE);
                    getFeedback();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void getFeedback(){
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = RateIt.getRating();
                Comment = CommentBox.getText().toString();
                stringRating = Float.toString(rating);
                calendar = Calendar.getInstance();
                category = Spin.getSelectedItem().toString();
                simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
                timeDate = simpleDateFormat.format(calendar.getTime());
                HashMap<String,String> data = new HashMap<>();
                data.put("name",profileName);
                data.put("designation",designation);
                data.put("category",category);
                data.put("dateTime",timeDate);
                data.put("comment",Comment);
                data.put("rating",stringRating);
                if (loginType ==1) {
                    int pos = profileEmail.indexOf("@");
                    String email_ = profileEmail.substring(0, pos);
                    mDatabase.child("feedback").child(email_).setValue(data);
                    Toast.makeText(Feedback.this, Comment + "\t" + stringRating + "\t" + timeDate + "\t" + category, Toast.LENGTH_SHORT).show();
                }else if (loginType == 2){
                    mDatabase.child("feedback").push().setValue(data);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Feedback.this,MainActivity.class);
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        if (!Feedback.this.isFinishing()) {
            Feedback.this.startActivity(intent);
            Feedback.this.finish();
        }
    }

    public void feedbackActivity(){
        feedbackLayout.setVisibility(View.VISIBLE);
        viewLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
    }

    public void viewActivity(){
        feedbackLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
        if (designation.equals("HR")){
            viewLayout.setVisibility(View.VISIBLE);
            //can view every feedback
        }else if (designation.equals("Manager")){
            viewLayout.setVisibility(View.VISIBLE);
            //can view feedback of manager,admin,security,food,others
        }else if(designation.equals("CEO")){
            viewLayout.setVisibility(View.VISIBLE);
            //can view feedback of ceo,manager,admin,security
        }else if (designation.equals("CTO")){
            viewLayout.setVisibility(View.VISIBLE);
            //can view feedback of cto,ceo,manager,admin
        }else if (designation.equals("Employee")){
            viewLayout.setVisibility(View.VISIBLE);
            //can view feedback of food,others
        }else if (loginType == 2){
            Toast.makeText(this, "Feedback cannot be viewed by anonymous", Toast.LENGTH_SHORT).show();
        }
    }

    public void profileActivity(){
        feedbackLayout.setVisibility(View.INVISIBLE);
        viewLayout.setVisibility(View.INVISIBLE);
        switch (loginType){
            case 1:
                profileLayout.setVisibility(View.VISIBLE);
                sp = getSharedPreferences("permission", MODE_PRIVATE);
                result = sp.getBoolean("permissionKey", false);
                editTextProfileName.setText(profileName);
                editTextProfileEmail.setText(profileEmail);
                editTextProfileDesignation.setText(designation);
                selectPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });
                buttonEditDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editTextProfileName.setEnabled(true);
                        buttonSaveEditDetails.setEnabled(true);
                    }
                });
                buttonSaveEditDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = editTextProfileName.getText().toString();
                        int pos = profileEmail.indexOf("@");
                        String email_ = profileEmail.substring(0,pos);
                        mDatabase.child("user").child(email_).child("name").setValue(newName).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Feedback.this, "Changes saved", Toast.LENGTH_SHORT).show();
                                editTextProfileName.setEnabled(false);
                                buttonSaveEditDetails.setEnabled(false);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //
                            }
                        });
                    }
                });
                break;
            case 2:
                Toast.makeText(this, "Not allowed as anonymous", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Feedback.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        photo.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        photo.setImageBitmap(thumbnail);
    }
}
