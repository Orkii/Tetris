package com.example.tetris;

import androidx.annotation.NonNull;

public class PairF {
    public float x;
    public float y;
    public PairF(float a, float b){
        x = a;
        y = b;
    }
    @NonNull
    @Override
    public String toString() {
        return "{x:" + x + ";y:" + y + "}";
    }
}
