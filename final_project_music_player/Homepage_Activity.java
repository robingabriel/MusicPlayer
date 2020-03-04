package uph.android.final_project_music_player;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static uph.android.final_project_music_player.MainActivity.utils;

public class Homepage_Activity extends AppCompatActivity {

    public static TextView title;
    public static ImageButton btn_mini_play;
    public static ProgressBar progressBar;
    public static Handler mHandler = new Handler();
    public static MediaPlayer mp;

    private String data = "";
    private static Global global = new Global();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        BottomNavigationView bottomNav =findViewById(R.id.home_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,new HomeFragment()).commit();

        setMusicPlayerComponents();
        global.addSong();

        Intent intent = getIntent();
        data = intent.getStringExtra("song");

        //Play Music When Title Text Change
        title.setText(data);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mp.reset();
                btn_mini_play.setImageResource(R.drawable.ic_pause_black_24dp);
                chooseSong(title.getText().toString());
                play();
                mHandler.post(mUpdateTimeTask);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btn_mini_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    mp.pause();
                    btn_mini_play.setImageResource(R.drawable.ic_play_arrow);
                } else {
                    mp.start();
                    btn_mini_play.setImageResource(R.drawable.ic_pause_black_24dp);
                    mHandler.post(mUpdateTimeTask);
                }
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Homepage_Activity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("play", true);
                bundle.putString("song", title.getText().toString());
                intent.putExtras(bundle);

                MainActivity.currentDuration = mp.getCurrentPosition();

                startActivity(intent);
            }
        });
    }

    //Nav Action
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        switch (item.getItemId()){

            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;

            case R.id.nav_search:
                selectedFragment = new SearchFragment();
                break;

            case R.id.nav_playlist:
                selectedFragment = new PlaylistFragment();
                break;

            case R.id.nav_account:
                selectedFragment = new AccountFragment();
                break;

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,selectedFragment).commit();
        return true;

        }
    };

    public void play() {
        mp.start();
        mHandler.post(mUpdateTimeTask);
    }

    private void setMusicPlayerComponents() {

        mp = new MediaPlayer();
        title = findViewById(R.id.miniTitle);
        btn_mini_play = (ImageButton) findViewById(R.id.btn_mini_play);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setProgress(0);
        progressBar.setMax(MusicUtils.MAX_PROGRESS);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int songId = Global.getIdByTitle(title.getText().toString())+1;
                btn_mini_play.setImageResource(R.drawable.ic_pause_black_24dp);
                title.setText(global.getTitle(songId));
                title.setText(global.getTitle(songId));
                mHandler.post(mUpdateTimeTask);
        }
        });

        utils = new MusicUtils();
        updateTimerAndSeekbar();

    }

    private void chooseSong(String song) {
        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = getAssets().openFd(song+".mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (Exception e) {}
    }

    public static Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            updateTimerAndSeekbar();
            if (mp.isPlaying()) {
                mHandler.postDelayed(this, 100);
            }
        }
    };

    public static void updateTimerAndSeekbar() {
        Homepage_Activity.progressBar.setMax(mp.getDuration());
        Homepage_Activity.progressBar.setProgress(mp.getCurrentPosition());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onBackPressed() { finishAffinity(); }

}
