package application.samoht.br.cardfit.models;

/**
 * Created by Thomas on 7/6/16.
 */
public class StandardActivity {

    private String name;
    private String description;
    private String note;
    private String thumbnail;
    private String video;

    public StandardActivity(){}

    public StandardActivity(String name, String description, String note, String thumbnail, String video){
        this.name = name;
        this.description = description;
        this.note = note;
        this.thumbnail = thumbnail;
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getNote() {
        return note;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
