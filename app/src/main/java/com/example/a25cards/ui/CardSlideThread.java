package com.example.a25cards.ui;


public class CardSlideThread extends Thread{
    float[] posY;
    int index;
    int direction;
    public CardSlideThread(float[] posY, int index, int direction) {
        this.direction = direction;
        this.posY = posY;
        this.index = index;
    }
    @Override
    public void run() {
        posY[index] += 30 * direction;
        /*
        for (int i=0; i<15; i++){
            try {
                posY[index] += 2 * direction;
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
    }
}
