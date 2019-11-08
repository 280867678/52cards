package com.example.a25cards.view;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.a25cards.R;
import com.example.a25cards.model.Deck;
import com.example.a25cards.model.Poker;
import com.example.a25cards.model.User;
import com.example.a25cards.view.GameView;

public class GameActivity extends AppCompatActivity {

    private Deck myDeck = new Deck();
    private User user = new User();

    private void testPoker() {
        for (int points=3; points<8; points++) {
            for (String kind: Poker.kinds) {
                myDeck.getPokersHand().add(new Poker(points, kind));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        GameView gameView = new GameView(this);
        testPoker();
        gameView.setMyDeck(myDeck);
        gameView.setUser(user);
        setContentView(gameView);
        super.onCreate(savedInstanceState);
    }
}
