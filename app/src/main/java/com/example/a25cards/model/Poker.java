package com.example.a25cards.model;


public class Poker {

    private int point;
    private String kind;
    public static int SMALL_JOKER = 100;
    public static int LARGE_JOKER = 200;
    public Poker(int point, String kind){
        this.point = point;
        this.kind = kind;
    }
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
