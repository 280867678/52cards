package com.example.a25cards.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.a25cards.model.Deck;
import com.example.a25cards.model.Poker;
import com.example.a25cards.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private User user;
    private Deck myDeck;    // 我的牌
    private Deck preDeck;   // 上次牌
    private Context context;
    private FlushThread flushThread;    // 绘图线程
    private InputStream inputStream = null;
    private AssetManager assetManager;
    private Bitmap card;        // 牌桌背景
    private Bitmap desk;        // 牌桌背景
    private Bitmap pokerBack;   // 扑克背面
    private float []posY = new float[35];
    private boolean []isSelected = new boolean[35];
    private float initX = 350;  // 第一张卡牌左距
    private float initY = 350;  // 卡牌上距
    private float spanX = 45;   // 卡牌间距
    private float spanY = 35;   // 选牌升降距离

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Deck getMyDeck() {
        return myDeck;
    }

    public void setMyDeck(Deck myDeck) {
        this.myDeck = myDeck;
    }

    public void resetPos() {
        for (int i=0; i<35; i++) {
            posY[i] = initY;
            isSelected[i] = false;
        }
    }

    public GameView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        flushThread = new FlushThread(getHolder(), this);
        assetManager = context.getAssets();
        initMap();
    }


    class FlushThread extends Thread {
        private final int span = 50;
        private boolean gaming = true;
        private final GameView gameView;
        private final SurfaceHolder holder;

        public FlushThread(SurfaceHolder holder, GameView gameView) {
            this.gameView = gameView;
            this.holder = holder;
        }
        @Override
        public void run() {
            Canvas canvas;
            boolean flag = true;
            while (this.gaming) {
                if (flag) { // 轮到自己出牌
                    resetPos();
                    flag = false;
                }
                canvas = null;
                try {
                    canvas = holder.lockCanvas(null);
                    synchronized (this.holder) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        gameView.myDraw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        this.holder.unlockCanvasAndPost(canvas);
                    }
                }

                try {
                    Thread.sleep(span);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean isGaming() {
            return gaming;
        }

        public void setGaming(boolean flag) {
            this.gaming = flag;
        }
    }


    private void myDraw(Canvas canvas) {
        // 牌桌
        deskPaint(canvas);
        // 人物
        userPaint(canvas);
        // 手牌
        userCardsPaint(canvas);
        // 上次出牌
        lastDeckPaint(canvas);
        // 按钮
        buttonPaint(canvas);
    }

    private void initMap() {
        String deskSrc = "images/牌桌.jpg";
        String cardSrc = "images/方块2.png";
        try {
            desk = BitmapFactory.decodeStream(assetManager.open(deskSrc));
            card = BitmapFactory.decodeStream(assetManager.open(cardSrc));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buttonPaint(Canvas canvas) {

    }

    private void deskPaint(Canvas canvas) {
        canvas.drawBitmap(desk, 0, 0, null);
    }

    private void userCardsPaint(Canvas canvas) {
        List<Poker> pokers = myDeck.getPokersHand();
        float x = initX;

        for (int i=0; i<pokers.size(); i++) {
            Poker poker = pokers.get(i);
            String name = "images/" + poker.getKind() + poker.getPoints() + ".png";
            try {
                inputStream = assetManager.open(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap thisCard = BitmapFactory.decodeStream(inputStream);
            canvas.drawBitmap(thisCard, x, posY[i],null);
            x += spanX;
        }
    }

    private void userPaint(Canvas canvas) {     // 人物

    }

    private void lastDeckPaint(Canvas canvas) { // 上次出牌

    }

    private void statusChange(int index) {
        if (isSelected[index]) {
            isSelected[index] = false;
            posY[index] += spanY;
        } else {
            isSelected[index] = true;
            posY[index] -= spanY;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {    // 点击事件

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getRawX();
                float y = event.getRawY();
                int num = myDeck.getPokersHand().size();
                System.out.print(x);

                if (x>=initX && x<=initX+(num-1)*spanX+card.getWidth()) {   // 手牌范围内
                    int index;
                    // 判断点击的卡牌
                    if (x>initX+(num-1)*spanX) {
                        index = num - 1;
                    } else {
                        index = (int) ((x - initX) / spanX);
                    }
                    if (y>=posY[index]&&y<=posY[index]+card.getHeight()) {
                        statusChange(index);    // 改变选取状态
                        myDeck.getType();
                        if ( true) {    // 出牌判定

                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flushThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        flushThread.setGaming(false);
        while (retry) {
            try {
                flushThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
