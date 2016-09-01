package application.samoht.br.cardfit.models;

/**
 * Created by Thomas on 23/04/16.
 */
public class Serie{

    private int repetition;
    private int charge;

    public Serie(){}

    public Serie(int repetition, int charge){
        this.repetition = repetition;
        this.charge = charge;
    }

    public void setRepetition(int repetition){ this.repetition = repetition; }

    public void setCharge(int charge) { this.charge = charge; }

    public int getRepetition(){ return this.repetition; }

    public int getCharge(){ return this.charge; }

}
