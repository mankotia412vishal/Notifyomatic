package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class SignUp extends AppCompatActivity {
    //Variables


    TextToSpeech textToSpeech;

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    public static final String TAG="TAG";
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks
//        callSignUp = findViewById(R.id.signup_screen);
        getSupportActionBar().hide();// It hides Tittle bar
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);




        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });




//        validatePassword();
//        validateName();
//        validateUsername();
//        validateEmail();
//        validatePhoneNo();

//        regBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rootNode = FirebaseDatabase.getInstance();
//                reference = rootNode.getReference("users");
//
//                String name = regName.getEditText().getText().toString();
//                String username = regUsername.getEditText().getText().toString();
//                String email = regEmail.getEditText().getText().toString();
//                String phoneNo = regPhoneNo.getEditText().getText().toString();
//                String password = regPassword.getEditText().getText().toString();
//                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
//                reference.child(username).setValue(helperClass);
////
////                Intent intent = new Intent(SignUp.this, Categories.class);
////
////                startActivity(intent);
//                Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "User Created  Successfully!!", Toast.LENGTH_SHORT).show();
////                Toast.makeText(getApplicationContext(), "Go to the Login Page For Login!!", Toast.LENGTH_SHORT).show();
////                progressBar.setVisibility(View.VISIBLE);
////                finish();
//                Intent intent=new Intent(SignUp.this,Login.class);
//
//                startActivity(intent);
//                finish();
////                startActivity(new Intent(SignUp.this, Login.class));
////                finish();
//
////               reference.setValue("Change the Course data");
//            }
//        });

    }


    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();


        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

//    //    This function will execute when user click on Register Button
    public void registerUser(View view) {
        // Save Data in Firebase on Button click

//        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()) {
//
//            return;
//
//        }




        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                reference.child(username).setValue(helperClass);

                Intent intent = new Intent(SignUp.this, Categories.class);

                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "User Created  Successfully!!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                finish();


//               reference.setValue("Change the Course data");
            }
        });




//        //get all the valyes in String
//        String name=regName.getEditText().getText().toString();
//        String username=regUsername.getEditText().getText().toString();
//        String email=regEmail.getEditText().getText().toString();
//        String phoneNo=regPhoneNo.getEditText().getText().toString();
//        String password=regPassword.getEditText().getText().toString();
//        UserHelperClass helperClass =new UserHelperClass(name,username,email,phoneNo,password);
//        reference.child(username).setValue(helperClass);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    //SElect lang
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);

                }
            }
        });

    }

//public void speak(View view){
//
//    //get edit text value
//    String s=editText.getText().toString();
//
//    //Text to speech
//    int speech=textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
//}

}



/*

textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
@Override
public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
        //SElect lang
        int lang = textToSpeech.setLanguage(Locale.ENGLISH);

        }
        }
        });
        b2.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        //get edit text value
        String s=editText.getText().toString();

        //Text to speech
        int speech=textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);


        }
        });
        b1.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        //clear the edit text
        editText.setText(' ');
        }
        });



 */
