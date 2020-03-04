package uph.android.final_project_music_player;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import org.jetbrains.annotations.Nullable;

public class PlaylistFragment extends ListFragment {

    private Global global = new Global();
    private ListView listView;
    private ListAdapter listAdapter;

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAdapter = new PlaylistAdapter(global.songListArray, getActivity());
        setListAdapter(listAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playlist,container,false);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        String title = global.getTitle(position+1);
        Homepage_Activity.title.setText(title);
    }
}
