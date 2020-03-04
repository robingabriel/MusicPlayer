package uph.android.final_project_music_player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter implements Filterable {

    ArrayList<DataModel> mStringFilterList;
    private ArrayList<DataModel> dataSet;
    private Context mContext;
    private LayoutInflater inflater;
    private FilterData filterData;


    public SearchAdapter(ArrayList<DataModel> data, Context context) {
        this.dataSet = data;
        this.mContext=context;
        mStringFilterList = data;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.playlist_layout, null);
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.playlist_layout_text1);
        TextView txtSinger = (TextView) view.findViewById(R.id.playlist_layout_text2);
        ImageView imgs = (ImageView) view.findViewById(R.id.playlist_layout_img1);

        DataModel list = dataSet.get(i);
        String title = list.getTitle();
        String singer = list.getSinger();
        Integer img = list.getPicture();

        txtTitle.setText(title);
        txtSinger.setText(singer);
        imgs.setImageResource(img);
        return view;
    }

    @Override
    public Filter getFilter(){
        if(filterData == null) {
            filterData = new FilterData();
        }
        return filterData;
    }

    private class FilterData extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                ArrayList<DataModel> filterList = new ArrayList<DataModel>();

                for (int i = 0; i < mStringFilterList.size(); i++) {

                    if ((mStringFilterList.get(i).getTitle().toLowerCase()).contains(constraint.toString().toLowerCase())) {

                        DataModel song = new DataModel(mStringFilterList.get(i). getId(),mStringFilterList.get(i)
                                .getTitle(), mStringFilterList.get(i)
                                .getSinger(), mStringFilterList.get(i).getPicture());

                        filterList.add(song);

                    }
                }

                results.count = filterList.size();
                results.values = filterList;

            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mStringFilterList = (ArrayList<DataModel>) results.values; // has the filtered values
            notifyDataSetChanged();
        }
    }
}