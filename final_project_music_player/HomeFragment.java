package uph.android.final_project_music_player;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private ImageButton Rcmd1;
    private ImageButton Rcmd2;
    private ImageButton Rcmd3;
    private ImageButton Rcmd4;
    private ImageButton Rcmd5;
    private TextView TV1;
    private TextView TV2;
    private TextView TV3;
    private TextView TV4;
    private TextView TV5;
    private Global global = new Global();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Rcmd1 = view.findViewById(R.id.Rcmd1);
        Rcmd2 = view.findViewById(R.id.Rcmd2);
        Rcmd3 = view.findViewById(R.id.Rcmd3);
        Rcmd4 = view.findViewById(R.id.Rcmd4);
        Rcmd5 = view.findViewById(R.id.Rcmd5);
        TV1 = view.findViewById(R.id.TV1);
        TV2 = view.findViewById(R.id.TV2);
        TV3 = view.findViewById(R.id.TV3);
        TV4 = view.findViewById(R.id.TV4);
        TV5 = view.findViewById(R.id.TV5);

        global.addSong();

        Random r = new Random();
        final int[] randomID = {0, 0, 0, 0, 0};
        int loop = 0;
        while(loop < randomID.length){
            int random = r.nextInt(5) + 1;
            for(int i = 0; i <= loop; i++) {
                if(i == loop) {
                    randomID[i] = random;
                    loop++;
                    break;
                }else if( randomID[i] == random) {
                    break;
                }
            }
        }

        Rcmd1.setBackgroundResource(global.getPicture(randomID[0]));
        Rcmd2.setBackgroundResource(global.getPicture(randomID[1]));
        Rcmd3.setBackgroundResource(global.getPicture(randomID[2]));
        Rcmd4.setBackgroundResource(global.getPicture(randomID[3]));
        Rcmd5.setBackgroundResource(global.getPicture(randomID[4]));
        TV1.setText(global.getTitle(randomID[0]));
        TV2.setText(global.getTitle(randomID[1]));
        TV3.setText(global.getTitle(randomID[2]));
        TV4.setText(global.getTitle(randomID[3]));
        TV5.setText(global.getTitle(randomID[4]));

        Rcmd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = global.getID(global.getPicture(randomID[0]));
                Homepage_Activity.title.setText(global.getTitle(id));
            }
        });

        Rcmd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = global.getID(global.getPicture(randomID[1]));
                Homepage_Activity.title.setText(global.getTitle(id));
            }
        });

        Rcmd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = global.getID(global.getPicture(randomID[2]));
                Homepage_Activity.title.setText(global.getTitle(id));
            }
        });

        Rcmd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = global.getID(global.getPicture(randomID[3]));
                Homepage_Activity.title.setText(global.getTitle(id));
            }
        });

        Rcmd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = global.getID(global.getPicture(randomID[4]));
                Homepage_Activity.title.setText(global.getTitle(id));
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                ((Homepage_Activity)this.getActivity()).title.setText(data.getStringExtra("data"));
            }
        }
    }
}
