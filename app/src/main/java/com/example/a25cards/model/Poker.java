package com.example.a25cards.model;


public class Poker {

    private int point;
    private String kind;
    public static int SMALL_JOKER = 100;
    public static int LARGE_JOKER = 200;

    public int getPoint() {
        return point;
    }

    public String getKind() {
        return kind;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

}
