package comp3350.ptw.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import comp3350.ptw.R;
import comp3350.ptw.objects.Show;

/**
 * This class is an abstract super class for the List Item Adapters classes
 */
public abstract class ListItemAdapter extends ArrayAdapter<Show> {
    private int resourceId;
    private ViewHolder viewHolder;
    private Show movie;
    AssetManager assetManager;
    private View view;

    public ListItemAdapter(Context context, int textViewResourceId, List<Show> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        assetManager = context.getAssets();
        viewHolder = null;
        movie = null;
    }


    public View getView(int position, @Nullable View convertView, ViewGroup parent, Boolean priority) {
        movie = getItem(position);

        if (convertView == null) {
            createViewHolder();
            if (priority) {
                fillViewHolderPriority(parent);
            } else {
                fillViewHolder(parent);
            }


        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


        return view;
    }

    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        return getView(position, convertView, parent, false);
    }

    public void detailsClick(Show show) {
        Intent intent = new Intent();
        intent.putExtra("movie", show);
        intent.setClass(getContext(), ContentActivity.class);
        getContext().startActivity(intent);
    }


    private void fillViewHolderHelper(ViewGroup parent) {

        instantiateView(parent);

        viewHolder.showImage = view.findViewById(R.id.movie_image);
        viewHolder.showName = view.findViewById(R.id.movie_name);
        viewHolder.showGenre = view.findViewById(R.id.movie_genre);
        viewHolder.showDuration = view.findViewById(R.id.movie_duration);
        viewHolder.showAdd = view.findViewById(R.id.movie_add);
        viewHolder.showAdd.setBackgroundResource(R.drawable.ic_playlist_add_black_24dp);
        viewHolder.showDelete = view.findViewById(R.id.movie_delete);
        viewHolder.showDelete.setBackgroundResource(R.drawable.trash_can);
        viewHolder.priorityDropDown = view.findViewById(R.id.priorityDropDown);
        viewHolder.showDetails = view.findViewById(R.id.movie_details);
        viewHolder.ratingBar = view.findViewById(R.id.ratingBar);


        view.setTag(viewHolder);


    }

    public void fillViewHolder(ViewGroup parent) {
        fillViewHolderHelper(parent);
        viewHolder.priorityDropDown.setVisibility(View.GONE);
    }

    public void fillViewHolderPriority(ViewGroup parent) {
        fillViewHolderHelper(parent);
    }

    public void addViewHolderDisplayInfo() {
        viewHolder.showImage.setImageBitmap(getBitmapFromAsset(movie.getImageID()));
        viewHolder.showName.setText(movie.getTitle() + " (" + movie.getReleaseYear() + ") ");
        viewHolder.showGenre.setText("Type : " + movie.getGenre());
        viewHolder.showDuration.setText("Duration :" + movie.getDuration() + " mins");
    }

    private void instantiateView(ViewGroup parent) {
        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
    }

    public ViewHolder getViewHolder() {
        return viewHolder;
    }

    public ViewHolder createViewHolder() {
        viewHolder = new ViewHolder();
        return viewHolder;
    }

    public View getViewObject() {
        return view;
    }

    public Show getMovie() {
        return movie;
    }

    public Show setMovie(int position) {
        movie = getItem(position);
        return movie;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setViewHolder(ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    protected class ViewHolder {
        ImageView showImage;
        TextView showName;
        TextView showGenre;
        TextView showDuration;
        Button showDelete;
        Button showAdd;
        Spinner priorityDropDown;
        Button showDetails;
        RatingBar ratingBar;
    }

    public Bitmap getBitmapFromAsset(String strName) {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("images/" + strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}