package com.example.tetris;

public class TetrisFigure {                 // T J L Z S I O
    public static final Pair FIGURE_FIELD_SIZE = new Pair(5,5);
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
}
