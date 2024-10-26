package com.example.tetris;

import androidx.annotation.NonNull;

public class TetrisFigure {                 // T J L Z S I O
    public Pair FIGURE_FIELD_SIZE = new Pair(5,5);
    public boolean[][] figureField;// = new boolean[GAME_FIELD_SIZE.y][GAME_FIELD_SIZE.x];


    private static final Boolean _ = false;
    private static final Boolean A = true;

    public Pair position = new Pair(0,0);

    public TetrisFigure(char s){
        if (s == 'T'){
            figureField = new boolean[][]{
                    {_,_,_,_,_},
                    {_,_,A,_,_},
                    {_,A,A,A,_},
                    {_,_,_,_,_},
                    {_,_,_,_,_}};
        }
        else if (s == 'J'){
            figureField = new boolean[][]{
                    {_,_,_,_,_},
                    {_,_,A,_,_},
                    {_,_,A,_,_},
                    {_,A,A,_,_},
                    {_,_,_,_,_}};
        }
        else if (s == 'L'){
            figureField = new boolean[][]{
                    {_,_,_,_,_},
                    {_,_,A,_,_},
                    {_,_,A,_,_},
                    {_,_,A,A,_},
                    {_,_,_,_,_}};
        }
        else if (s == 'O'){
            figureField = new boolean[][]{
                    {_,_,_,_,_},
                    {_,_,_,_,_},
                    {_,A,A,_,_},
                    {_,A,A,_,_},
                    {_,_,_,_,_}};
        }
        else if (s == 'Z'){
            figureField = new boolean[][]{
                    {_,_,_,_,_},
                    {_,_,_,_,_},
                    {_,A,A,_,_},
                    {_,_,A,A,_},
                    {_,_,_,_,_}};
        }
        else if (s == 'S'){
            figureField = new boolean[][]{
                    {_,_,_,_,_},
                    {_,_,_,_,_},
                    {_,_,A,A,_},
                    {_,A,A,_,_},
                    {_,_,_,_,_}};
        }
        else if (s == 'I'){
            figureField = new boolean[][]{
                    {_,_,A,_,_},
                    {_,_,A,_,_},
                    {_,_,A,_,_},
                    {_,_,A,_,_},
                    {_,_,_,_,_}};
        }
    }


    public void rotateR(){
        boolean[][] rotated = new boolean[FIGURE_FIELD_SIZE.y][FIGURE_FIELD_SIZE.x];
        for (int i = 0; i < FIGURE_FIELD_SIZE.x; i++)
            for (int j = 0; j < FIGURE_FIELD_SIZE.y; j++)
                rotated[j][i] = figureField[FIGURE_FIELD_SIZE.x-i-1][j];

        int temp = FIGURE_FIELD_SIZE.x;
        FIGURE_FIELD_SIZE.x = FIGURE_FIELD_SIZE.y;
        FIGURE_FIELD_SIZE.y = temp;
        figureField = rotated;

    }
    public void rotateL(){
        rotateR();
        rotateR();
        rotateR();
    }
    public void moveR(){
        position.x++;
    }
    public void moveL(){
        position.x--;
    }
    public void drop(){
        position.y++;
    }

    public int leftestPos() {
        for (int i = 0; i < FIGURE_FIELD_SIZE.x; i++){
            for (int j = 0; j < FIGURE_FIELD_SIZE.y; j++){
                if (figureField[j][i]){
                    return (i + position.x);
                }
            }
        }
        return -1;
        //throw new Exception("?");
    }
    public int rightestPos() {
        for (int i = FIGURE_FIELD_SIZE.x - 1; i > 0; i--){
            for (int j = 0; j < FIGURE_FIELD_SIZE.y; j++){
                if (figureField[j][i]){
                    return (i + position.x);
                }
            }
        }
        return -1;
        //throw new Exception("?");
    }

}
