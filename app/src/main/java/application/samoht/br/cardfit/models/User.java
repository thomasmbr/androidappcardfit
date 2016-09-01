package application.samoht.br.cardfit.models;

import java.util.ArrayList;

/**
 * Created by Thomas on 6/26/16.
 */
public class User {

    private String name;
    private boolean trainer;
    private ArrayList<ArrayList<CardItem>> cards = new ArrayList<>();

    public User(){}

    public String getName() {
        return name;
    }

    public boolean getTrainer(){
        return trainer;
    }

    public ArrayList<ArrayList<CardItem>> getCards() {
        return cards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrainer(boolean trainer) {
        this.trainer = trainer;
    }

    public void setCards(ArrayList<ArrayList<CardItem>> cards) {
        this.cards = cards;
    }

}
