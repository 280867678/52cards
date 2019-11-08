package com.example.a25cards;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.a25cards.model.Deck;
import com.example.a25cards.model.Poker;
import com.example.a25cards.model.User;
import com.example.a25cards.view.GameView;


public class GameActivity extends AppCompatActivity {

    private Deck myDeck = new Deck();
    private User user = new User();

    private void testPoker() {
        myDeck.getPokersHand().add(new Poker(3, "红桃"));
        myDeck.getPokersHand().add(new Poker(4, "黑桃"));
        myDeck.getPokersHand().add(new Poker(5, "方块"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏、隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        GameView gameView = new GameView(this);
        testPoker();
        gameView.setMyDeck(myDeck);
        gameView.setUser(user);
        setContentView(gameView);
    }
}
