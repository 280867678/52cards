package com.example.a25cards.model;


import com.example.a25cards.util.Rule;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Deck {

    List<Poker> pokersHand = new LinkedList<>();        // 手上的牌
    List<Poker> pokersSelected = new LinkedList<>();    // 要出的牌
    Map<Integer, Integer> cardsMap = new TreeMap<>();   // 要出的牌的统计
    private int sumCards;
    private int type;
    private int weight;


    public List<Poker> getPokersHand() {
        return pokersHand;
    }

    public void setPokersHand(List<Poker> pokersHand) {
        this.pokersHand = pokersHand;
    }

    public List<Poker> getPokersSelected() {
        return pokersSelected;
    }

    public void setPokersSelected(List<Poker> pokersSelected) {
        this.pokersSelected = pokersSelected;
    }

    public void clearCardsMap() {
        this.cardsMap.clear();
    }

    public Map<Integer, Integer> getCardsMap() {
        return cardsMap;
    }

    public void setCardsMap(Map<Integer, Integer> cardsMap) {
        this.cardsMap = cardsMap;
    }

    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSumCards() {
        return sumCards;
    }

    public void setSumCards(int sumCards) {
        this.sumCards = sumCards;
    }

}
