package uph.android.final_project_music_player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

public class SearchFragment extends ListFragment implements SearchView.OnQueryTextListener {

    private Global global;
    public static ListView listView;
    private ListAdapter listAdapter;
    private SearchView search;
    SearchAdapter searchAdapter;
    ArrayList<DataModel> searchList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        listView.setTextFilterEnabled(true);
        search = rootView.findViewById(R.id.search_edit_search);

        searchAdapter = new SearchAdapter( global.songListArray, this.getContext());
        listView.setAdapter(searchAdapter);
        search.setOnQueryTextListener(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setIconified(false);
            }
        });

        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.equals("")){ listView.setVisibility(View.INVISIBLE); }
        else { listView.setVisibility(View.VISIBLE); }
        searchList = new ArrayList<>();
        ArrayList<DataModel> mainList = global.songListArray;
        for (int i = 0; i < mainList.size(); i++){
            if(mainList.get(i).getTitle().contains(s)){
                searchList.add(mainList.get(i));
            }
        }
        searchAdapter = new SearchAdapter( searchList, this.getContext());
        listView.setAdapter(searchAdapter);
        return false;
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {

        String title = searchList.get(position).getTitle();
        Homepage_Activity.title.setText(title);

    }
}
