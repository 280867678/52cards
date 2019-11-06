package com.example.a25cards.model;


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

    public static final int NO = 0;
    public static final int SINGLE = 1;         // 单张
    public static final int PAIR = 2;           // 对子
    public static final int TRIPLET = 3;        // 三张
    public static final int TRIPLET_PAIR = 4;   // 三带对
    public static final int PAIRS = 10;         // 连对
    public static final int TRIPLETS = 40;      // 飞机
    public static final int TRIPLETS_PAIR = 70; // 飞机带对
    public static final int STRAIGHT = 100;     // 顺子
    public static final int BOMB = 130;         // 炸弹
    public static final int ROCKET = 150;       // 王炸
    public static final int SUPER_ROCKET = 200; // 开天王炸

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

    public void judgeType() {

        this.type = NO;

        int difNum = cardsMap.size();   // 不同点数牌的数量
        int sumNum = getSumCards();     // 总牌数

        //开天王炸
        if ( sumNum==4 && difNum==2 ) {
            int flag = 0;
            for (int points: cardsMap.keySet()) {
                if (points==Poker.LARGE_JOKER || points==Poker.SMALL_JOKER) {
                    flag ++;
                } else {
                    break;
                }
            }
            if (flag==2) {
                this.type = SUPER_ROCKET;
                return;
            }
        }

        // 单点数牌
        if (difNum==1) {
            int points = 0;
            for(int key :cardsMap.keySet()) {
                points = key;
            }
            if (sumCards==1) { // 单牌
                this.type = SINGLE;
            } else if (sumNum==2) { // 对子 王炸
                if ( points==Poker.SMALL_JOKER || points==Poker.LARGE_JOKER ) {
                    this.type = ROCKET;
                } else {
                    this.type = PAIR;
                }
            } else if (sumNum==3) {  // 三
                this.type = TRIPLET;
            } else {    // 炸弹
                this.type = BOMB + sumNum-4;
            }
            this.weight = points;
            return;
        }

        // 顺子
        if ( sumNum>=5 && sumNum==difNum ) {
            int pre = NO;
            for (int points: cardsMap.keySet()) {
                if (pre==NO) {
                    pre = points;
                    continue;
                }
                if (points!=pre+1) {
                    return;
                }
                this.weight = points;
            }
            this.type = STRAIGHT + difNum;
            return;
        }

        // 三带对
        if ( difNum==2 && sumNum==5 ) {
            boolean flag = true;
            for (int points: cardsMap.keySet()) {
                int num = cardsMap.get(points);
                if (num==2 || num==3) {
                    if (num==3) {
                        this.weight = points;
                    }
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                this.type = TRIPLET_PAIR;
                return;
            }
        }

        // 连对
        if ( difNum*2==sumNum && difNum>=3 ) {
            int pre = NO;
            boolean flag = true;
            for (int points: cardsMap.keySet()) {
                if ( cardsMap.get(points) != 2 ) {
                    flag = false;
                    break;
                }
                if (pre==NO) {
                    pre = points;
                    continue;
                }
                if (points!=pre+1) {
                    return;
                }
                this.weight = points;
            }
            if (flag) {
                this.type = PAIRS;
                return;
            }
        }

        //飞机
        if ( difNum*3==sumNum && difNum>=3 ) {
            int pre = NO;
            boolean flag = true;
            for (int points: cardsMap.keySet()) {
                if ( cardsMap.get(points) != 3 ) {
                    flag = false;
                    break;
                }
                if (pre==NO) {
                    pre = points;
                    continue;
                }
                if (points!=pre+1) {
                    return;
                }
                this.weight = points;
            }
            if (flag) {
                this.type = TRIPLETS + difNum;
                return;
            }
        }

        // 飞机带对
        if ( sumNum%5==0 && sumNum/5*2==difNum && difNum>=6 ) {
            int pre = NO;
            int triplets = 0;
            int pairs = 0;
            boolean flag = true;
            for (int points: cardsMap.keySet()) {
                int num = cardsMap.get(points);
                if (num==3&&pre==NO) {
                    pre = points;
                    continue;
                }
                if (num==2 || num==3) {
                    if (num==3) {
                        this.weight = points;
                        triplets ++;
                    } else {
                        pairs ++;
                    }
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag && triplets==pairs) {
                this.type = TRIPLETS_PAIR + difNum;
            }
        }
    }
}
