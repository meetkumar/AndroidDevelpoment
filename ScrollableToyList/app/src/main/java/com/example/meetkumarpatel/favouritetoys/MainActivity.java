package com.example.meetkumarpatel.favouritetoys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView = findViewById(R.id.id_tv_names);
        String[] ToyNames = ToyBox.getToyNames();
        for(String toy: ToyNames){
            myTextView.append(toy+"\n\n\n");
        }
    }
}
