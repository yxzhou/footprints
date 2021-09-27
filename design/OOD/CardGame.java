package design.OOD;

import java.util.List;



class Card{
    
    Suit suit;
    Rank rank;
    
    public enum Suit{
        SPADES(4), HEARTS(3), DIAMONDS(1), CLUBS(2);
        
        private final int value;
        private Suit(int value){
            this.value = value;
        }
    }
    public enum Rank{
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), J(11), Q(12), K(13), A(14);
        
        private final int value;
        private Rank(int value){
            this.value = value;
        }
    }
}

class Deck{
    Card[] cards = new Card[52];
    
    private void init(){
        
    }
    
    public void shuffle(){
        
    }
}

class Player{
    int id;
    
    String name;
    
}

public class CardGame {

    Deck deck = new Deck();
    Player[] players = new Player[4]; // 2 groups, (0 and 2) vs (1 and 3)
    int activePlayerIndex = 0; // from 0 to 3
    int activeCardIndex = 0; // from 0 to 51

    List<CardGameState> history;

    CardGame(){

        deck.shuffle();// deck.cards

    }

    String toJson(){
        //TODO 
        
        return null;
    }

    public String toString(){
        //TODO
        
        return null;
    }
}

class CardGameState{
    long timestamp;
    
    int activePlayerIndex = 0; 
    int activeCardIndex = 0; 
    
}

class Snapshot{
    
}
