package com.example.tetris;

import androidx.annotation.NonNull;

public class Pair {
    public int x;
    public int y;
    public Pair(int a, int b){
        x = a;
        y = b;
    }

    @NonNull
    @Override
    public String toString() {
        return "{x:" + x + ";y:" + y + "}";
    }
}


