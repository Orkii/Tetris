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

import java.util.Random;

public class GameActivity extends View {
    private static final float BORDER_WIDTH = 5;
    private static final Paint BORDER_COLOR = new Paint();
    private static final Paint BACKGROUND_COLOR = new Paint();
    private static final Paint INNER_BORDER_COLOR = new Paint();
    private static final Paint CELL_COLOR = new Paint();
    private static final Pair GAME_FIELD_SIZE = new Pair(10,20);
    private PairF GAME_SCREEN_SIZE = new PairF(-1,-1);
    private PairF CELL_SIZE = new PairF(-1,-1);


    private TetrisFigure nowFall;

    private char lastMove = '0';    // qe - вращение
                                    // ad - движение

    private static final Boolean _ = false;
    private static final Boolean A = true;
    //private boolean[][] field = new boolean[GAME_FIELD_SIZE.y][GAME_FIELD_SIZE.x] {
    private boolean[][] field = new boolean[][] {
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_},
        {_,_,_,_,A,_,_,_,_,_},
        {_,_,_,_,_,_,_,_,_,_}};

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

    private void init(){
        BORDER_COLOR.setColor(Color.BLACK);
        BACKGROUND_COLOR.setColor(Color.WHITE);
        INNER_BORDER_COLOR.setColor(Color.GRAY);
        CELL_COLOR.setColor(Color.GREEN);

        nowFall = new TetrisFigure('T');
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        //super.onDraw(canvas);
        if (GAME_SCREEN_SIZE.x == -1){
            float a = canvas.getWidth();
            float b = canvas.getHeight();

            GAME_SCREEN_SIZE.x = (a - (BORDER_WIDTH*2));
            GAME_SCREEN_SIZE.y = (b - (BORDER_WIDTH*2));

            CELL_SIZE.x = (a - (BORDER_WIDTH*4)) / ((float)GAME_FIELD_SIZE.x);  //  ?
            CELL_SIZE.y = (b - (BORDER_WIDTH*4)) / ((float)GAME_FIELD_SIZE.y);  //  ?
        }
        drawBorder(canvas);
        drawCell(canvas);
        drawLines(canvas);
        Log.d("myLog", "HERE2");
    }

    protected boolean checkCollision(){
        int a = field[0].length;
        int b = nowFall.rightestPos();
        if (nowFall.leftestPos() < 0) {
            unDo(); // Вышел влево
            return false;
        }
        if (nowFall.rightestPos() >= field[0].length) {
            unDo();  // Вышел вправо
            return false;
        }

        for (int i = 0; i < nowFall.FIGURE_FIELD_SIZE.y; i++) {
            for (int j = 0; j < nowFall.FIGURE_FIELD_SIZE.x; j++) {
                if (nowFall.figureField[i][j] &&
                    field[i + nowFall.position.y][j + nowFall.position.x]){
                    unDo();
                    return false;
                }
            }
        }

        return true;
    }
    protected void unDo(){
        if (lastMove == 'q')      hardInputRotateR();
        else if (lastMove == 'e') hardInputRotateL();
        else if (lastMove == 'a') hardInputMoveR();
        else if (lastMove == 'd') hardInputMoveL();
    }


    public void inputDrop(){
        nowFall.drop();
        Log.d("myLog", nowFall.position.toString());
        checkCollision();
    }
    public void inputRotateR(){
        lastMove = 'e';
        nowFall.rotateR();
        checkCollision();
    }
    public void inputRotateL(){
        lastMove = 'q';
        nowFall.rotateL();
        checkCollision();
    }
    public void inputMoveR(){
        lastMove = 'd';
        nowFall.moveR();
        checkCollision();
    }
    public void inputMoveL(){
        lastMove = 'a';
        nowFall.moveL();
        checkCollision();
    }

    protected void hardInputDrop(){
        nowFall.drop();
        Log.d("myLog", nowFall.position.toString());
    }
    protected void hardInputRotateR(){
        nowFall.rotateR();
    }
    protected void hardInputRotateL(){
        nowFall.rotateL();
    }
    protected void hardInputMoveR(){
        nowFall.moveR();
    }
    protected void hardInputMoveL(){
        nowFall.moveL();
    }

    protected void drawBorder (@NonNull Canvas canvas){
        canvas.drawRect(0,0,GAME_SCREEN_SIZE.x,GAME_SCREEN_SIZE.y, BORDER_COLOR);
        canvas.drawRect(BORDER_WIDTH,
                        BORDER_WIDTH,
                        GAME_SCREEN_SIZE.x - BORDER_WIDTH,
                        GAME_SCREEN_SIZE.y - BORDER_WIDTH, BACKGROUND_COLOR);
    }
    protected void drawLines (@NonNull Canvas canvas){
        for (int i = 1; i < GAME_FIELD_SIZE.x; i++){
            canvas.drawLine(BORDER_WIDTH + (CELL_SIZE.x * i),
                            BORDER_WIDTH + 0,
                            BORDER_WIDTH + (CELL_SIZE.x * i),
                            GAME_SCREEN_SIZE.y - BORDER_WIDTH ,
                            INNER_BORDER_COLOR);
        }
        for (int i = 1; i < GAME_FIELD_SIZE.y; i++){
            canvas.drawLine(BORDER_WIDTH + 0 ,
                            BORDER_WIDTH + (CELL_SIZE.y * i),
                            GAME_SCREEN_SIZE.x - BORDER_WIDTH,
                            BORDER_WIDTH + (CELL_SIZE.y * i),
                            INNER_BORDER_COLOR);
        }
    }
    protected void drawCell (@NonNull Canvas canvas){

        for (int i = 0; i < nowFall.FIGURE_FIELD_SIZE.y; i++) {
            for (int j = 0; j < nowFall.FIGURE_FIELD_SIZE.x; j++) {
                if (nowFall.figureField[i][j] == true){

                    canvas.drawRect(
                            BORDER_WIDTH +     (nowFall.position.x + j)*CELL_SIZE.x,
                            BORDER_WIDTH +      (nowFall.position.y + i)*CELL_SIZE.y,
                            BORDER_WIDTH +    (nowFall.position.x + j)*CELL_SIZE.x + CELL_SIZE.x,
                            BORDER_WIDTH +   (nowFall.position.y + i)*CELL_SIZE.y + CELL_SIZE.y, CELL_COLOR);
                }
            }
        }

        for (int i = 0; i < GAME_FIELD_SIZE.x; i++) {
            for (int j = 0; j < GAME_FIELD_SIZE.y; j++) {
                if (field[j][i] == true){
                    canvas.drawRect(
                            BORDER_WIDTH +     i * CELL_SIZE.x,
                            BORDER_WIDTH +      j * CELL_SIZE.y,
                            BORDER_WIDTH +    i * CELL_SIZE.x + CELL_SIZE.x,
                            BORDER_WIDTH +   j * CELL_SIZE.y + CELL_SIZE.y, CELL_COLOR);
                }
            }
        }

    }
}
