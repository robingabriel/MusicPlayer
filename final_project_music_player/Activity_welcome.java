package uph.android.final_project_music_player;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Activity_welcome extends AppCompatActivity {

    private ImageButton nxtButton;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        nxtButton = findViewById(R.id.welcome_next_btn);

        loadFragment(new Welcome1());

        nxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 1) {
                    i ++;
                    loadFragment(new Welcome2());
                }else if(i == 2) {
                    i++;
                    loadFragment(new Welcome3());
                    nxtButton.setVisibility(view.INVISIBLE);
                }
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.your_placeholder, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        i--;
        nxtButton.setVisibility(View.VISIBLE);
        if(i == 0){
            finish();
        }
    }
}
