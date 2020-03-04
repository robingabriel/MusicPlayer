package uph.android.final_project_music_player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaylistAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private int lastPosition = -1;
    private ArrayList<DataModel> dataSet;
    private Context mContext;

    public PlaylistAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.playlist_layout, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel) object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataModel dataModel = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.playlist_layout, parent, false);


            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.playlist_layout_text1);

            viewHolder.txtArtist = (TextView) convertView.findViewById(R.id.playlist_layout_text2);
            viewHolder.imgs = (ImageView) convertView.findViewById(R.id.playlist_layout_img1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        lastPosition = position;

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtArtist.setText(dataModel.getSinger());
        viewHolder.imgs.setImageResource(dataModel.getPicture());
        return convertView;
    }

    private static class ViewHolder {
        private TextView txtTitle;
        private TextView txtArtist;
        private ImageView imgs;
    }
}

