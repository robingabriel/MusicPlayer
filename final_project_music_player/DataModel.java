package uph.android.final_project_music_player;

public class DataModel {

    private int Id;
    private String Title;
    private String Singer;
    private Integer Picture;

    DataModel(int id, String title, String singer, Integer picture) {
        this.Id = id;
        this.Title = title;
        this.Singer = singer;
        this.Picture = picture;
    }

    public int getId() {
        return Id;
    }

    public String getSinger() {
        return Singer;
    }

    public String getTitle() {
        return Title;
    }

    public Integer getPicture() { return Picture; }

}
