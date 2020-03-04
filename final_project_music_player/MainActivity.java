package uph.android.final_project_music_player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static TextView txtTitle;
    public static SeekBar seek_song_progressbar;
    public static FloatingActionButton btn_play;
    public static MusicUtils utils = null;
    public static long currentDuration;

    private Global global = new Global();
    private Boolean repeat = false;
    private Boolean shuffle = false;
    private View parent_view;
    private TextView txtSinger;
    private ImageView dropBtn;
    private int random;


    private static String data;
    private static TextView tv_song_current_duration, tv_song_total_duration;
    private static CircularImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMusicPlayerComponents();

        txtSinger = findViewById((R.id.txtSinger));
        txtTitle = findViewById(R.id.txtTitle);

        Intent intent = getIntent();
        data = intent.getStringExtra("song");

        txtTitle.setText(data);

        if (Homepage_Activity.mp.isPlaying()) {
            btn_play.setImageResource(R.drawable.ic_pause);
        } else {
            btn_play.setImageResource(R.drawable.ic_play_arrow);
        }

        for (DataModel s: global.getSong()) {
            if (s.getTitle().equals(data)){
                txtSinger.setText(s.getSinger());
                break;
            }
        }

        play(0);
        dropBtn = findViewById(R.id.dropBtn);
        dropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setMusicPlayerComponents() {
        utils = new MusicUtils();

        parent_view = findViewById(R.id.parent_view);
        btn_play = findViewById(R.id.btn_play);
        seek_song_progressbar = findViewById(R.id.seek_song_progressbar);
        tv_song_current_duration =  findViewById(R.id.tv_song_current_duration);
        tv_song_total_duration = findViewById(R.id.total_duration);
        image =  findViewById(R.id.image);

        seek_song_progressbar.setProgress(0);
        seek_song_progressbar.setMax(MusicUtils.MAX_PROGRESS);

        Homepage_Activity.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(shuffle) {
                    Random ran = new Random();
                    do {
                        random = ran.nextInt(7) + 1;
                    }while (global.getIdByTitle(txtTitle.getText().toString()) == random);

                    play(random);
                }else{
                    play(1);
                }
            }
        });

        seek_song_progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Homepage_Activity.mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Homepage_Activity.mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = Homepage_Activity.mp.getDuration();
                int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
                Homepage_Activity.mp.seekTo(currentPosition);
                Homepage_Activity.mHandler.post(mUpdateTimeTask);
            }
        });

        buttonPlayerAction();
        updateTimerAndSeekbar();
    }

    private void check() {
        if (Homepage_Activity.mp.isPlaying()) {
            Homepage_Activity.mp.pause();
            btn_play.setImageResource(R.drawable.ic_play_arrow);
            Homepage_Activity.btn_mini_play.setImageResource(R.drawable.ic_play_arrow);
        } else {
            Homepage_Activity.mp.start();
            btn_play.setImageResource(R.drawable.ic_pause);
            Homepage_Activity.btn_mini_play.setImageResource(R.drawable.ic_pause);
            Homepage_Activity.mHandler.post(mUpdateTimeTask);
        }
        rotateTheDisk();
    }

    private void buttonPlayerAction() {
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                check();
            }
        });
    }

    public void controlClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.btn_repeat: {
                toggleButtonColor((ImageButton) v);
                break;
            }

            case R.id.btn_shuffle: {
                toggleButtonColor((ImageButton) v);
                break;
            }

            case R.id.btn_prev: {
                play(-1);
                break;
            }

            case R.id.btn_next: {
                if(shuffle){
                    Random ran = new Random();
                    do {
                        random = ran.nextInt(7) + 1;
                    }while (global.getIdByTitle(txtTitle.getText().toString()) == random);
                    play(random);
                }else if(shuffle && repeat){
                    repeat();
                }else {
                    play(1);
                }
                break;
            }

        }
    }

    private void play(int action) {
        int songId = global.getIdByTitle(txtTitle.getText().toString()) + action;

        if (action == 0) {
            //none
        } else if (action == 1 ) {
            Homepage_Activity.title.setText(global.getTitle(songId));
            Snackbar.make(parent_view, "Next", Snackbar.LENGTH_SHORT).show();
        } else if (action == -1) {
            Homepage_Activity.title.setText(global.getTitle(songId));
            Snackbar.make(parent_view, "Previous", Snackbar.LENGTH_SHORT).show();
        } else {
            Homepage_Activity.title.setText(global.getTitle(songId));
            Snackbar.make(parent_view, "Next", Snackbar.LENGTH_SHORT).show();
        }

        btn_play.setImageResource(R.drawable.ic_pause_black_24dp);
        txtTitle.setText(global.getTitle(songId));
        txtSinger.setText(global.getSinger(songId));
        image.setImageResource(global.getPicture(songId));

        rotateTheDisk();
        updateTimerAndSeekbar();
        Homepage_Activity.mHandler.post(mUpdateTimeTask);

        repeat();
    }

    private void repeat() {
        if (repeat) {
            Homepage_Activity.mp.setLooping(true);
        }else {
            Homepage_Activity.mp.setLooping(false);
        }
    }

    private boolean toggleButtonColor(ImageButton bt ) {
        String selected = (String) bt.getTag(bt.getId());

        if (selected != null) { // selected

            bt.setColorFilter(getResources().getColor(R.color.colorNotSelected), PorterDuff.Mode.SRC_ATOP);
            bt.setTag(bt.getId(), null);

            if(bt.getId() == R.id.btn_repeat) {
                Homepage_Activity.mp.setLooping(false);
                repeat = false;
                Snackbar.make(parent_view, "No-Repeat", Snackbar.LENGTH_SHORT).show();
            }else if (bt.getId() == R.id.btn_shuffle) {
                shuffle = false;
            }
            return false;

        } else {

            bt.setTag(bt.getId(), "selected");
            bt.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

            if(bt.getId() == R.id.btn_repeat) {
                Homepage_Activity.mp.setLooping(true);
                repeat = true;
                Snackbar.make(parent_view, "Repeat", Snackbar.LENGTH_SHORT).show();
            }else if(bt.getId() == R.id.btn_shuffle){
                shuffle = true;
            }
            return true;
        }
    }

    public static Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            updateTimerAndSeekbar();

            if (Homepage_Activity.mp.isPlaying()) {
                Homepage_Activity.mHandler.postDelayed(this, 100);
            }
        }
    };

    public static void updateTimerAndSeekbar() {
        long totalDuration = Homepage_Activity.mp.getDuration();
        currentDuration = Homepage_Activity.mp.getCurrentPosition();
        int progress = (int) (utils.getProgressSeekBar(currentDuration, totalDuration));

        Homepage_Activity.progressBar.setMax(Homepage_Activity.mp.getDuration());

        tv_song_total_duration.setText(utils.milliSecondsToTimer(totalDuration));
        tv_song_current_duration.setText(utils.milliSecondsToTimer(currentDuration));

        Homepage_Activity.progressBar.setProgress(Homepage_Activity.mp.getCurrentPosition());
        seek_song_progressbar.setProgress(progress);
    }

    public static void rotateTheDisk() {
        if (!Homepage_Activity.mp.isPlaying()) return;
        image.animate().setDuration(100).rotation(image.getRotation() + 2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            rotateTheDisk();
            super.onAnimationEnd(animation);
            }
        });
    }
}

