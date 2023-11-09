package comp3350.ptw.presentation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.RatedShow;
import comp3350.ptw.objects.Show;

/**
 * this class is an adapter for the objects that go in the watched list, it extends ListItemAdapter
 */
public class WatchedListItemAdapter extends ListItemAdapter {
    private UIShowAccess UIShowAccess;


    public WatchedListItemAdapter(Context context, int textViewResourceId, List<Show> objects, UIShowAccess UIShowAccess) {
        super(context, textViewResourceId, objects);
        this.UIShowAccess = UIShowAccess;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Show movie = super.getMovie();

        getViewHolder().showAdd.setVisibility(View.GONE);
        if (movie instanceof RatedShow) {
            getViewHolder().ratingBar.setRating(((RatedShow) movie).getRating());
        }

        getViewHolder().ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser && movie instanceof RatedShow) {
                    ((RatedShow) movie).setRating(rating);
                    UIShowAccess.updateRating(movie);
                }
            }
        });
        super.addViewHolderDisplayInfo();

        getViewHolder().showDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowAccess.remove(movie, ShowSubSet.WATCHEDSHOWS);
                ListUpdate.refreshWatchedList();
            }
        });

        getViewHolder().showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsClick(movie);
            }
        });

        return view;
    }
}
