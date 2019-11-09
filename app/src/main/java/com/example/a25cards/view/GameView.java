package com.example.a25cards.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.a25cards.R;
import com.example.a25cards.model.Deck;
import com.example.a25cards.model.Poker;
import com.example.a25cards.model.User;
import com.example.a25cards.util.PokerTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private User user;
    private Deck myDeck;
    private FlushThread flushThread; //绘图线程
    private InputStream inputStream = null;
    private Bitmap card;        // 扑克图片来源
    private Bitmap desk;        // 牌桌背景
    private Bitmap pokerBack;   // 扑克背面
    private Bitmap bt_ready;
    private Bitmap bt_leave;
    private Bitmap bt_pass;
    private Bitmap bt_discard;
    private Bitmap bt_setting;
    private Bitmap bt_back;
    private Bitmap player; //玩家形象
    private Bitmap bt_rechoose;
    private AssetManager assetManager;
    private int screenWidth ;  //屏幕宽度
    private int screenHeight;  //屏幕高度
    private float []posY = new float[35];
    private boolean []isSelected = new boolean[35];
    private int lastType = 0;   // 上次出牌类型
    private int lastWeight = 0; // 上次出牌权重
    private float initX = 350;  // 第一张卡牌左距
    private float initY = 350;  // 卡牌上距
    private float spanX = 45;   // 卡牌间距
    private float spanY = 35;   // 选牌升降距离
    private final float rate = 1.65f; //图片放大比例
    public static final int TIME_IN_FRAME = 30;

    public void setMyDeck(Deck myDeck) {
        this.myDeck = myDeck;
    }

    public Deck getMyDeck() {
        return myDeck;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void resetPos() {
        for (int i=0; i<35; i++) {
            posY[i] = initY;
            isSelected[i] = false;
        }
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
                long startTime = System.currentTimeMillis();
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
                long endTime = System.currentTimeMillis();
                /**计算出一次更新的毫秒数**/
                int diffTime  = (int)(endTime - startTime);
                /**确保每次更新时间为30帧**/
                while(diffTime <=TIME_IN_FRAME) {
                    diffTime = (int)(System.currentTimeMillis() - startTime);
                    /**线程等待**/
                    Thread.yield();
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
        this.context = context;
        getHolder().addCallback(this);
        flushThread= new FlushThread(getHolder(), this);
        assetManager = context.getAssets();
        Resources resources = this.getResources();;
        DisplayMetrics dm  = resources.getDisplayMetrics();
        float density = dm.density;
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        initMap();
        spanX = (float)0.025*screenWidth;
        initX = (float) 0.2*screenWidth;
        initX = (float)0.15*screenWidth;
        initY = (float)0.75*screenHeight;
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
        String pokerBackSrc = "images/牌背面.png";
        String playerSrc = "images/人物.png";
        String bt_readySrc ="images/准备.png";
        String bt_leaveSrc = "images/离开.png";
        String bt_passSrc = "images/不出.png";
        String bt_discardSrc = "images/出牌.png";
        String bt_settingSrc = "images/setting.png";
        String bt_backSrc = "images/back.png";
        String bt_rechooseSrc = "images/重选.png";
        try {
            desk = BitmapFactory.decodeStream(assetManager.open(deskSrc));
            card = BitmapFactory.decodeStream(assetManager.open(cardSrc));
            pokerBack = BitmapFactory.decodeStream(assetManager.open(pokerBackSrc));
            player = BitmapFactory.decodeStream(assetManager.open(playerSrc));
            bt_ready = BitmapFactory.decodeStream(assetManager.open(bt_readySrc));
            bt_leave = BitmapFactory.decodeStream(assetManager.open(bt_leaveSrc));
            bt_discard = BitmapFactory.decodeStream(assetManager.open(bt_discardSrc));
            bt_pass = BitmapFactory.decodeStream(assetManager.open(bt_passSrc));
            bt_setting = BitmapFactory.decodeStream(assetManager.open(bt_settingSrc));
            bt_back = BitmapFactory.decodeStream(assetManager.open(bt_backSrc));
            bt_rechoose = BitmapFactory.decodeStream(assetManager.open(bt_rechooseSrc));
            //屏幕自适应大小
            Matrix matrix = new Matrix();
            matrix.postScale((float)(1.0*screenWidth/desk.getWidth()), (float)(1.0*screenHeight/desk.getHeight()));
            desk = Bitmap.createBitmap(desk, 0, 0, desk.getWidth(),desk.getHeight(),matrix,true);

            matrix = new Matrix();
            matrix.postScale(rate, rate);
            pokerBack = Bitmap.createBitmap(pokerBack, 0, 0, pokerBack.getWidth(), pokerBack.getHeight(),matrix,true);
            bt_ready = Bitmap.createBitmap(bt_ready, 0, 0, bt_ready.getWidth(), bt_ready.getHeight(),matrix,true);
            bt_leave = Bitmap.createBitmap(bt_leave, 0, 0, bt_leave.getWidth(), bt_leave.getHeight(),matrix,true);
            bt_pass = Bitmap.createBitmap(bt_pass, 0, 0, bt_pass.getWidth(), bt_pass.getHeight(),matrix,true);
            bt_discard = Bitmap.createBitmap(bt_discard, 0, 0, bt_discard.getWidth(), bt_discard.getHeight(),matrix,true);
            bt_rechoose = Bitmap.createBitmap(bt_rechoose, 0, 0, bt_rechoose.getWidth(), bt_rechoose.getHeight(),matrix,true);
            matrix.postScale(0.5f,0.5f);
            bt_setting = Bitmap.createBitmap(bt_setting, 0, 0, bt_setting.getWidth(), bt_setting.getHeight(),matrix,true);
            matrix.postScale(0.35f,0.35f);
            bt_back = Bitmap.createBitmap(bt_back, 0, 0, bt_back.getWidth(), bt_back.getHeight(),matrix,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void buttonPaint(Canvas canvas) {
        canvas.drawBitmap(bt_setting, (float)0.93* screenWidth ,(float)0.01*screenHeight,null);
        canvas.drawBitmap(bt_back, (float)0.01* screenWidth ,(float)0.01*screenHeight,null);
        //readyButtonPaint(canvas);
        gameButtonPaint(canvas);
    }

    private void startButtonPaint(Canvas canvas) {

    }

    private void readyButtonPaint(Canvas canvas) {
        canvas.drawBitmap(bt_leave, (float)0.28* screenWidth ,(float)0.58*screenHeight,null);
        canvas.drawBitmap(bt_ready, (float)0.55* screenWidth ,(float)0.58*screenHeight,null);
    }

    private void gameButtonPaint(Canvas canvas) {
        canvas.drawBitmap(bt_pass, (float)0.2* screenWidth ,(float)0.58*screenHeight,null);
        canvas.drawBitmap(bt_rechoose, (float)0.45* screenWidth ,(float)0.58*screenHeight,null);
        canvas.drawBitmap(bt_discard, (float)0.7* screenWidth ,(float)0.58*screenHeight,null);
    }
    private void deskPaint(Canvas canvas) {
        canvas.drawBitmap(desk, 0, 0, null);
    }



    private void userCardsPaint(Canvas canvas) {

        List<Poker> pokers = myDeck.getPokersHand();
        initX = (float) (screenWidth-(pokers.size()-1)*spanX-card.getWidth()*1.5)/2;
        float x = initX;
        Matrix matrix = new Matrix();

        canvas.drawBitmap(pokerBack, (float)0.12*screenWidth, (float)0.36*screenHeight,null);
        canvas.drawBitmap(pokerBack, (float)0.85*screenWidth, (float)0.36*screenHeight,null);
        canvas.drawBitmap(pokerBack, (float)0.55*screenWidth, (float)0.1*screenHeight,null);

        matrix = new Matrix();
        matrix.postScale(rate, rate);
        for (int i=0; i<pokers.size(); i++) {
            Poker poker = pokers.get(i);
            String name = "images/" + poker.getKind() + poker.getPoints() + ".png";
            try {
                inputStream = assetManager.open(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap thisCard = BitmapFactory.decodeStream(inputStream);
            thisCard = Bitmap.createBitmap(thisCard, 0, 0, thisCard.getWidth(),thisCard.getHeight(), matrix,true);
            canvas.drawBitmap(thisCard, x, posY[i],null);
            x += spanX;
         /*   getHolder().unlockCanvasAndPost(canvas);
            try {
                flushThread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canvas = getHolder().lockCanvas();*/
        }
    }

    private void userPaint(Canvas canvas) {
        //drawBitmap(player, 0 ,0,null);
    }

    private void lastDeckPaint(Canvas canvas) {

    }

    private void statusChange(int index) {
        int points = myDeck.getPokersHand().get(index).getPoints();
        if (isSelected[index]) {
            myDeck.setSumCards(myDeck.getSumCards()-1);
            PokerTool.removeFromMap(myDeck.getCardsMap(), points);
            isSelected[index] = false;
            posY[index] += spanY;
        } else {
            myDeck.setSumCards(myDeck.getSumCards()+1);
            PokerTool.addToMap(myDeck.getCardsMap(), points);
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
                if (x>=initX && x<=initX+(num-1)*spanX+card.getWidth()*rate) {   // 手牌范围内
                    int index;
                    // 判断点击的卡牌
                    if (x>initX+(num-1)*spanX) {
                        index = num - 1;
                    } else {
                        index = (int) ((x - initX) / spanX);
                    }
                    if (y>=posY[index]&&y<=posY[index]+card.getHeight()*rate) {
                        statusChange(index);    // 改变选取状态
                        if (PokerTool.canPlayCards(lastType, lastWeight, myDeck)) {    // 出牌判定

                        }
                        String text = "type：" + myDeck.getType()
                                + "  weight: " + myDeck.getWeight()
                                + "  sumNum: " + myDeck.getSumCards()
                                + "  difNum: " + myDeck.getCardsMap().size()
                                + "  mapInf: " + myDeck.getCardsMap();
                        Toast.makeText(context, text,Toast.LENGTH_SHORT).show();
                    }
                }
                if(x>=0.45*screenWidth && x<= 0.45*screenWidth+bt_rechoose.getWidth() && y>=0.58*screenHeight && y<=0.58*screenHeight+bt_rechoose.getHeight()){
                    for(int i = 0; i <= myDeck.getPokersHand().size(); i++){
                        if(isSelected[i]){
                            statusChange(i);
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
