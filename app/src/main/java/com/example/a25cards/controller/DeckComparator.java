package com.example.a25cards.controller;

import com.example.a25cards.model.Deck;


public class DeckComparator {

    public static boolean canPlayCards(Deck preDeck, Deck thisDeck) {
        thisDeck.judgeType();

        if (preDeck.getType()==thisDeck.getType() && thisDeck.getWeight()>preDeck.getWeight() ) {
            return true;
        }

        if (thisDeck.getType()>=Deck.BOMB && thisDeck.getType()>preDeck.getType() ) {
            return true;
        }

        return false;
    }

}
