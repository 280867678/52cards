package com.example.a25cards.view;

import android.content.Context;
import android.view.View;

import com.example.a25cards.model.Deck;
import com.example.a25cards.model.Poker;

import java.util.Map;

public class PokerView extends View implements View.OnClickListener {

    Poker poker;
    Deck myDeck;
    private boolean isSelected = false;



    public Poker getPoker() {
        return poker;
    }

    public void setPoker(Poker poker) {
        this.poker = poker;
    }

    public PokerView(Context context) {
        super(context);
    }



    @Override
    public void onClick(View v) {
        if (isSelected ==false) {
            isSelected = true;
            Map<Integer, Integer> cardsMap = myDeck.getCardsMap();
            int points = getPoker().getPoint();
            if (cardsMap.containsKey(points)) {
                cardsMap.put(points, cardsMap.get(points)+1);
            } else {
                cardsMap.put(points, 1);
            }
        } else {
            isSelected = false;
            Map<Integer, Integer> cardsMap = myDeck.getCardsMap();
            int points = getPoker().getPoint();
            int num = cardsMap.get(points);
            if (num==1) {
                cardsMap.remove(points);
            } else {
                cardsMap.put(points, num-1);
            }
        }
        myDeck.getType();
    }
}
