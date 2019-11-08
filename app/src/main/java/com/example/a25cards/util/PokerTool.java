package com.example.a25cards.util;

import android.widget.Toast;

import com.example.a25cards.model.Deck;
import com.example.a25cards.model.Poker;

import java.util.Map;


public class PokerTool {

    public static boolean canPlayCards(int lastType, int lastWeight, Deck thisDeck) {

        Rule.judgeType(thisDeck);

        // 错误牌型
        if (thisDeck.getType()==Rule.WRONG) {
            return false;
        }

        // 该轮首次出牌
        if (lastType==Rule.NONE) {
            return true;
        }

        // 同牌型，比较权重
        if (lastType==thisDeck.getType() && thisDeck.getWeight()>lastWeight ) {
            return true;
        }

        // 强压牌型，比较压制等级
        if (thisDeck.getType()>=Rule.BOMB && thisDeck.getType()>lastWeight ) {
            return true;
        }

        return false;
    }

    public static void addToMap(Map<Integer, Integer> cardsMap, int points) {
        if (cardsMap.containsKey(points)) {
            cardsMap.put(points, cardsMap.get(points)+1);
        } else {
            cardsMap.put(points, 1);
        }
    }

    public static void removeFromMap(Map<Integer, Integer> cardsMap, int points) {
        if (cardsMap.get(points)==1) {
            cardsMap.remove(points);
        } else {
            cardsMap.put(points, cardsMap.get(points)-1);
        }
    }

}
