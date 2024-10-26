package com.example.tetris;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    GameActivity gameActivity;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gameActivity = findViewById(R.id.mainFrame);
    }


    public void onClick(View v) {
        Log.d("myLog", "HERE0");
        Button b = ((Button)v);
        //gameActivity.invalidate();
    }
    public void onClickDrop(View v) {
        Log.d("myLog", "Drop Button");
        gameActivity.inputDrop();
        gameActivity.invalidate();
    }
    public void onClickRotateR(View v) {
        Log.d("myLog", "Drop Button");
        gameActivity.inputRotateR();
        gameActivity.invalidate();
    }
    public void onClickRotateL(View v) {
        Log.d("myLog", "Drop Button");
        gameActivity.inputRotateL();
        gameActivity.invalidate();
    }

    public void onClickMoveR(View v) {
        Log.d("myLog", "Drop Button");
        gameActivity.inputMoveR();
        gameActivity.invalidate();
    }
    public void onClickMoveL(View v) {
        Log.d("myLog", "Drop Button");
        gameActivity.inputMoveL();
        gameActivity.invalidate();
    }
}