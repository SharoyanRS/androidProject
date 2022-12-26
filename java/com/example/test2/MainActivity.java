package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView goToTask1 = (TextView) findViewById(R.id.goToTask1);
        goToTask1.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToTask1:
                Intent intent = new Intent(this, InfoActivityActivity.class);
                startActivity(intent);
                break;
        }
    }


}