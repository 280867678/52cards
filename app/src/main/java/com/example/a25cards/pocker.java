package com.example.a25cards;

import android.content.Context;
import android.view.View;

public class pocker extends View {

    private int point;
    private String kind;

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




    public pocker(Context context) {
        super(context);
    }

}
