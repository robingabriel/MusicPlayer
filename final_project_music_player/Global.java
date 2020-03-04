package uph.android.final_project_music_player;

import android.app.Application;

import java.util.ArrayList;

public class Global extends Application {

    public static ArrayList<DataModel> songListArray = new ArrayList<>();
    public static ArrayList<UserModel> userArray = new ArrayList<>();

    public void addSong () {
        if(songListArray.size() == 0) {
            songListArray.add(new DataModel(1,"download", "singer d", R.drawable.download));
            songListArray.add(new DataModel(2,"rapgod", "Eminem", R.drawable.rapgod_album));
            songListArray.add(new DataModel(3,"photograph", "Ed Sheeran", R.drawable.x_album));
            songListArray.add(new DataModel(4,"bohemianrhapsody", "Queen", R.drawable.queen_album));
            songListArray.add(new DataModel(5,"lights", "BTS", R.drawable.bts_album));
            songListArray.add(new DataModel(6,"hello","Adele",R.drawable.adele));
            songListArray.add(new DataModel(7,"vivalavida","Coldplay",R.drawable.coldplay));

        }
    }

    public static ArrayList<DataModel> getSong() {
        return songListArray;
    }

    public static String getTitle(int id) {
        if(id < 1) {
            id = (songListArray.size());
        }else if(id > songListArray.size()) {
            id = 1;
        }

        for (DataModel x : songListArray) {
            if(x.getId() == id){
                return x.getTitle();
            }
        }
        return null;
    }

    public static Integer getPicture(int id) {
        if(id < 1) {
            id = (songListArray.size());
        }else if(id > songListArray.size()) {
            id = 1;
        }

        for (DataModel x : songListArray) {
            if(x.getId() == id){
                return x.getPicture();
            }
        }
        return R.drawable.download;
    }

    public static int getID(Integer picture) {
        for (DataModel x : songListArray) {
            if(x.getPicture().equals(picture)){
                return x.getId();
            }
        }
        return R.drawable.download;
    }

    public static String getSinger(int id) {
        if(id < 1) {
            id = (songListArray.size());
        }else if(id > songListArray.size()) {
            id = 1;
        }

        for (DataModel x : songListArray) {
            if(x.getId() == id){
                return x.getSinger();
            }
        }
        return null;
    }

    public static int getIdByTitle(String title) {
        for (DataModel x : songListArray) {
            if(x.getTitle().equals(title)) {
                return x.getId();
            }
        }
        return 0;
    }

    public static DataModel getSong(int id) {
        for (DataModel x : songListArray) {
            if(x.getId() == id){
                return x;
            }
        }
        return null;
    }

}
