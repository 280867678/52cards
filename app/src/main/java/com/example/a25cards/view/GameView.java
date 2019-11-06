package com.example.a25cards.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.a25cards.R;
import com.example.a25cards.model.Deck;
import com.example.a25cards.model.Poker;
import com.example.a25cards.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private User user;
    private Deck myDeck;
    private GameThread gameThread;  // 绘图线程
    private Bitmap card;        // 扑克图片来源
    private Bitmap desk;        // 牌桌背景
    private Bitmap pokerBack;   // 扑克背面
    private AssetManager assetManager;

    public void setMyDeck(Deck myDeck) {
        this.myDeck = myDeck;
    }

    public void setUser(User user){
        this.user = user;
    }

    class GameThread extends Thread {
        private final int span = 300;
        private boolean gaming = true;
        private final GameView gameView;
        private final SurfaceHolder holder;
        public GameThread(SurfaceHolder holder, GameView gameView) {
            this.gameView = gameView;
            this.holder = holder;
        }
        @Override
        public void run() {
            Canvas canvas;
            System.out.println(this.gaming);
            while (this.gaming) {
                canvas = null;
                try {
                    canvas = holder.lockCanvas(null);
                    synchronized (this.holder) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        canvas.drawColor(Color.RED, PorterDuff.Mode.CLEAR);
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

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        assetManager = context.getAssets();
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
    }

    private void deskPaint(Canvas canvas) {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("images/牌桌.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bg = BitmapFactory.decodeStream(inputStream);
        canvas.drawBitmap(bg, 0, 0, null);
    }

    private void userCardsPaint(Canvas canvas) {
        List<Poker> pokers = myDeck.getPokersHand();
        int x = 100;
        int y = 300;
        for (Poker poker:pokers) {
            InputStream inputStream = null;
            String name = "images/"+poker.getKind()+poker.getPoint()+".png";
            try {
                inputStream = assetManager.open(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap thisCard = BitmapFactory.decodeStream(inputStream);
            canvas.drawBitmap(thisCard, x, y,null);
            x += 20;
        }
    }

    private void userPaint(Canvas canvas) {

    }

    private void lastDeckPaint(Canvas canvas) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {    // 点击事件
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int c=0;
                float downX=event.getRawX();
                float downY=event.getRawY();

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
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setGaming(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }







}
