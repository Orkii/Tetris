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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameActivity extends View {
    private static final float BORDER_WIDTH = 5;
    private static final Paint BORDER_COLOR = new Paint();
    private static final Paint BACKGROUND_COLOR = new Paint();
    private static final Paint INNER_BORDER_COLOR = new Paint();
    private static final Paint CELL_COLOR = new Paint();
    private static final Paint END_TEXT_COLOR = new Paint();
    private static final Pair GAME_FIELD_SIZE = new Pair(10,20);
    private PairF GAME_SCREEN_SIZE = new PairF(-1,-1);
    private PairF CELL_SIZE = new PairF(-1,-1);

    private char[] figureType = new char[] {'T', 'J', 'L', 'Z', 'S', 'I', 'O'};

    private int msToFall = 200;
    private boolean gameOver = false;
    private TetrisFigure nowFall;

    private char lastMove = '0';    // qe - вращение
                                    // ad - движение
                                    // f  - падение по времени

    Random random = new Random();

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
        {_,_,_,_,_,_,_,_,_,_},
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
        END_TEXT_COLOR.setColor(Color.RED);
        END_TEXT_COLOR.setTextSize(100);
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
        if (gameOver){
            drawGameOver(canvas);
        }
        Log.d("myLog", "HERE2");
    }


    void tick(){

        if (!gameOver) {
            nowFall.drop();
            lastMove = 'f';
            checkCollision();
        }
        invalidate();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(new Runnable() {
            @Override
            public void run() {
                tick();
            }
        }, msToFall, TimeUnit.MILLISECONDS);
        service.shutdown();
    }

    public void start(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(new Runnable() {
            @Override
            public void run() {
                tick();
            }
        }, msToFall, TimeUnit.MILLISECONDS);
        service.shutdown();

    }



    protected boolean checkCollision(){
        int a = field[0].length;
        int b = nowFall.rightestPos();
        if (nowFall.leftestPos() < 0) {
            unDo(); // Вышел влево
            return false;
        }
        if (nowFall.rightestPos() >= GAME_FIELD_SIZE.x) {
            unDo();  // Вышел вправо
            return false;
        }
        if (nowFall.lowestPos() >= GAME_FIELD_SIZE.y) {
            unDo();
            //putNowFallToField();
            //createNewFigure();
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
        else if (lastMove == 'f') {
            nowFall.position.y--;
            putNowFallToField();
            checkLines();
            checkGameOver();
            createNewFigure();
        }
    }

    protected void checkGameOver(){
        boolean end = false;
        for (int j = 0; j < GAME_FIELD_SIZE.x; j++) {
            if (field[0][j]){
                gameOver = true;
            }
        }
    }

    protected void checkLines(){
        for (int i = 0; i < GAME_FIELD_SIZE.y; i++) {
            boolean full = true;
            for (int j = 0; j < GAME_FIELD_SIZE.x; j++) {
                if (!field[i][j]){
                    full = false;
                    break;
                }
            }
            if (full){

                for (int k = i - 1; k > 0 ; k--){
                    for (int l = 0; l < GAME_FIELD_SIZE.x; l++) {
                        field[k+1][l] = field[k][l];
                    }
                }
                for (int l = 0; l < GAME_FIELD_SIZE.x; l++) {
                    field[0][l] = field[0][l];
                }

            }
        }
    }


    protected void putNowFallToField(){
        for (int i = 0; i < nowFall.FIGURE_FIELD_SIZE.y; i++) {
            for (int j = 0; j < nowFall.FIGURE_FIELD_SIZE.x; j++) {
                if (nowFall.figureField[i][j] == true){
                    if (i + nowFall.position.y >= 0) {
                        field[i + nowFall.position.y][j + nowFall.position.x] = true;
                    }
                    else{
                        gameOver = true;
                        return;
                    }
                }
            }
        }
    }

    protected void createNewFigure(){   // T J L Z S I O
        nowFall = new TetrisFigure(figureType[random.nextInt(7)]);
    }

    public void timeFall(){
        nowFall.drop();
        lastMove = 'f';
    }
    public void inputDrop(){
        if (gameOver) return;
        while(checkCollision()) {
            nowFall.drop();
        }
        Log.d("myLog", nowFall.position.toString());
        checkCollision();
    }
    public void inputRotateR(){
        if (gameOver) return;
        lastMove = 'e';
        nowFall.rotateR();
        checkCollision();
    }
    public void inputRotateL(){
        if (gameOver) return;
        lastMove = 'q';
        nowFall.rotateL();
        checkCollision();
    }
    public void inputMoveR(){
        if (gameOver) return;
        lastMove = 'd';
        nowFall.moveR();
        checkCollision();
    }
    public void inputMoveL(){
        if (gameOver) return;
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
    protected void drawGameOver(@NonNull Canvas canvas){
        canvas.drawText("ЦЕНОК", 200, 200, END_TEXT_COLOR);
    }

}
