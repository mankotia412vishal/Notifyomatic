package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.example.todo.databinding.ActivityDataInsertBinding;

public class DataInsertActivity extends AppCompatActivity {
    ActivityDataInsertBinding binding;
//    LottieAnimationView imag;
//    View imag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_insert);

        binding = ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String type = getIntent().getStringExtra("type");

        if (type.equals("update")) {
            setTitle("Update");

            binding.title.setText(getIntent().getStringExtra("title"));
            binding.desp.setText(getIntent().getStringExtra("disp"));
            int id = getIntent().getIntExtra("id", 0);

            binding.add.setText("update note");

            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("disp", binding.desp.getText().toString());
                    intent.putExtra("id", id);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else {


            setTitle("Add Note");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("disp", binding.desp.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivity.this, MainActivity.class));
    }

    public void dd(View view){
        Intent intent = new Intent();


        Intent p=    intent.putExtra("title", binding.title.getText().toString());
        Intent p2=        intent.putExtra("disp", binding.desp.getText().toString());
    }
}