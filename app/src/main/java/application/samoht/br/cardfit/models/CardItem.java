package application.samoht.br.cardfit.models;

import java.util.ArrayList;

/**
 * Created by Thomas on 21/04/16.
 */
public class CardItem {

    //private String id;
    private String name;
    private String description;
    private String note;
    private String thumbnail;
    private String video;
    private ArrayList<Serie> series = new ArrayList<>();
    //private int card;
    private int restTime;

    public CardItem(){
    }

    /*public void setId (String id){
        this.id = id;
    }*/

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setNote(String note){
        this.note = note;
    }

    public void setThumbnail(String urlThumbnail){
        this.thumbnail = urlThumbnail;
    }

    public void setVideo(String urlVideo){
        this.video = urlVideo;
    }

    public void addSerie(int repetition, int charge){
        Serie serie = new Serie(repetition, charge);
        series.add(serie);
    }

    public void setRestTime(int restTime){
        this.restTime = restTime;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public String getNote(){
        return this.note;
    }

    public String getThumbnail(){
        return this.thumbnail;
    }

    public String getVideo(){ return  this.video; }

    public Serie getSerie(int index){
        return this.series.get(index);
    }

    public ArrayList<Serie> getSeries(){
        return this.series;
    }

    public int getRestTime(){
        return this.restTime;
    }

}