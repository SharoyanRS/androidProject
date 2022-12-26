package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoActivityActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        Button btn = (Button) findViewById(R.id.goExperiment);
        btn.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goExperiment:
                Intent intent = new Intent(this, task1Activity1.class);
                startActivity(intent);
                break;
        }
    }

}