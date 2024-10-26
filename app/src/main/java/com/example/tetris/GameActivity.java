package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameActivity extends View {
    private static final int BORDER_WIDTH = 10;
    private static final Paint BORDER_COLOR = new Paint();
    private static final Paint BACKGROUND_COLOR = new Paint();


    public GameActivity(Context context) {
        super(context);
        init();
    }

    public GameActivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameActivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GameActivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void paint(){
        //Bitmap bm = new Bitmap()

    }
    private void init(){
        BORDER_COLOR.setColor(Color.BLACK);
        BACKGROUND_COLOR.setColor(Color.WHITE);
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        //super.onDraw(canvas);
        Log.d("myLog", "HERE1");
        Paint p = new Paint();
        p.setColor(Color.RED);

        canvas.drawCircle(0,0,50, p);
        //canvas.drawRect(0,0,500,500, p);
        drawBorder(canvas);
        Log.d("myLog", "HERE2");
    }

    protected void drawBorder (@NonNull Canvas canvas){
        int h = canvas.getHeight();
        int w = canvas.getWidth();

        canvas.drawRect(0,0,w,h, BORDER_COLOR);
        canvas.drawRect(BORDER_WIDTH, BORDER_WIDTH,w - BORDER_WIDTH, h - BORDER_WIDTH, BACKGROUND_COLOR);
    }

}
