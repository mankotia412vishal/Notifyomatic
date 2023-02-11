package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText desp,title;
    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        desp=findViewById(R.id.desp);
        title=findViewById(R.id.title);

//        img = findViewById(R.id.imageView2);


//        imag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(MainActivity.this,MapsActivity.class);
//                startActivity(intent);
//            }
//        });
        setContentView(binding.getRoot());
        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataInsertActivity.class);
              intent.putExtra("type","addMode");

                startActivityForResult(intent, 1);
            }
        });

        binding.RV.setLayoutManager(new LinearLayoutManager(this));
        binding.RV.setHasFixedSize(true);
        RVAdapter adapter = new RVAdapter();

        binding.RV.setAdapter(adapter);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

      if (direction==ItemTouchHelper.RIGHT){


          noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
          Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();



      }
      else{


          Intent intent=new Intent(MainActivity.this,DataInsertActivity.class);
          intent.putExtra("type","update");
          intent.putExtra("title",adapter.getNote(viewHolder.getAdapterPosition()).getTitle());
          intent.putExtra("disp",adapter.getNote(viewHolder.getAdapterPosition()).getDisp());
          intent.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());

        startActivityForResult(intent,2);


      }
            }
        }).attachToRecyclerView(binding.RV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");


            Note note = new Note(title, disp);

            noteViewModel.insert(note);
            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();

        }

        else if(requestCode==2){
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");

            Note note = new Note(title, disp);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);

            Toast.makeText(MainActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, Categories.class));
        finish();

    }

}