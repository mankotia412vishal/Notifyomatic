package com.example.todo;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfile extends AppCompatActivity {
    TextInputEditText fullName, email, phoneNo, password;
    ImageView img;
    // email,phoneNo,password;
//    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, usernameLabel;
    Button  eemailS;
    ImageView btnLogout;

    // Global variables to hold user inside this activity
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String _USERNAME, _NAME, _EMAIL, _PHONENO, _PASSWORD;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        btnLogout = findViewById(R.id.btnLogout);
        eemailS= findViewById(R.id.sendE);

//        setTitle("User Profile");
        getSupportActionBar().hide();
        reference = FirebaseDatabase.getInstance().getReference("users");
        //Hooks
//        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);
























//Logout button ka haii sab
        img = findViewById(R.id.imageView2);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserProfile.this,DashBoard.class);
                startActivity(intent);
            }
        });
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                Intent intent = new Intent(UserProfile.this, Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(UserProfile.this, "Logout Sucessfull!!", Toast.LENGTH_SHORT).show();

            }
        });

        //Show all Data
        showAllUserData();



        eemailS.setOnClickListener(view -> {



            String emailsend = email.getText().toString();
            String emailsubject =  phoneNo.getText().toString();
            String emailbody =password.getText().toString();

            // define Intent object with action attribute as ACTION_SEND
            Intent intent = new Intent(Intent.ACTION_SEND);

            // add three fields to intent using putExtra function

            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailsend});

            intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
            intent.putExtra(Intent.EXTRA_TEXT, emailbody);

            // set type of intent
            intent.setType("message/rfc822");

            // startActivity with intent with chooser as Email client using createChooser function
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        });

    }


    //
    private void showAllUserData() {

//        Intent intent = getIntent();
//        String user_username = intent.getStringExtra("username");
//        String user_name = intent.getStringExtra("name");
//        String user_email = intent.getStringExtra("email");
//        String user_phoneNo = intent.getStringExtra("phoneNo");
//        String user_password = intent.getStringExtra("password");
//        Log.e("err",user_name+user_username+user_name+user_email+user_phoneNo+user_password);


        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _NAME = intent.getStringExtra("name");
        _EMAIL = intent.getStringExtra("email");
        _PHONENO = intent.getStringExtra("phoneNo");
        _PASSWORD = intent.getStringExtra("password");

//
        fullNameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
//        fullName.setText(_NAME);
        email.setText(_EMAIL);
        phoneNo.setText(_PHONENO);
        password.setText(_PASSWORD);

//        fullNameLabel.setText(_NAME);
//        usernameLabel.setText(_USERNAME);
//        fullName.getEditText().setText(_NAME);
//        email.getEditText().setText(_EMAIL);
//        phoneNo.getEditText().setText(_PHONENO);
//        password.getEditText().setText(_PASSWORD);

    }

//    public void update(View view) {
//
//        if (isNameChanged() || isPasswordChanged()) {
//            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
//        }
//    }

//    private boolean isPasswordChanged() {
//        if (!_PASSWORD.equals(password.getEditText().getText().toString())) {
//            reference.child(_USERNAME).child("password").setValue(password.getEditText().getText().toString());
//            _PASSWORD = password.getEditText().getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isNameChanged() {
//        if (!_NAME.equals(fullName.getEditText().getText().toString())) {
//            reference.child(_USERNAME).child("name").setValue(fullName.getEditText().getText().toString());
//            _NAME = fullName.getEditText().getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(UserProfile.this, Categories.class));
        finish();

    }

}